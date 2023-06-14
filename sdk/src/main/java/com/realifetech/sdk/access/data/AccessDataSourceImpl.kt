package com.realifetech.sdk.access.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
import com.realifetech.GetMyTicketAuthsQuery
import com.realifetech.GetMyTicketQuery
import com.realifetech.GetMyTicketsQuery
import com.realifetech.GetUpcomingTicketsQuery
import com.realifetech.sdk.RealifeTech.apolloClient
import com.realifetech.sdk.access.data.model.Ticket
import com.realifetech.sdk.access.data.model.TicketAuth
import com.realifetech.sdk.access.data.model.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.type.TicketFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AccessDataSourceImpl @Inject constructor(apolloClient: ApolloClient) : AccessDataSource {

    override suspend fun getMyTickets(
        pageSize: Int
    ): PaginatedObject<Ticket?> {
        return withContext(Dispatchers.IO) {
            try {
                val query = GetMyTicketsQuery(pageSize)
                val response = apolloClient.query(query).execute()
                val tickets =
                    response.data?.getMyTickets?.edges?.mapNotNull { it?.fragmentTicket?.asModel }
                val nextPage = response.data?.getMyTickets?.nextPage
                PaginatedObject(tickets, nextPage)
            } catch (exception: ApolloHttpException) {
                throw Exception(exception)
            } catch (exception: ApolloException) {
                throw Exception(exception)
            }
        }
    }

    override suspend fun getMyTicketById(
        id: Int
    ): Ticket? {
        return withContext(Dispatchers.IO) {
            try {
                val query = GetMyTicketQuery(id = id.toString())
                val response = apolloClient.query(query).execute()
                response.data?.getMyTicket?.fragmentTicket?.asModel
            } catch (exception: ApolloHttpException) {
                throw Exception(exception)
            } catch (exception: ApolloException) {
                throw Exception(exception)
            }
        }
    }

    override suspend fun getNextUpcomingTicket(): Ticket? {
        return withContext(Dispatchers.IO) {
            try {
                val query = GetUpcomingTicketsQuery(
                    pageSize = 1,
                    filters = Optional.presentIfNotNull(
                        TicketFilter(
                            status = if (ACTIVE != null) Optional.presentIfNotNull(ACTIVE) else Optional.presentIfNotNull(""),
                            sessionDateAfter = if (twentyFourHourDateTimeStamp != null) Optional.present(twentyFourHourDateTimeStamp) else Optional.present("")
                        )
                    )
                )
                val response = apolloClient.query(query).execute()
                response.data?.getMyTickets?.edges?.firstOrNull()?.fragmentTicket?.asModel
            } catch (exception: ApolloHttpException) {
                throw Exception(exception)
            } catch (exception: ApolloException) {
                throw Exception(exception)
            }
        }
    }

    override suspend fun getMyTicketAuths(): List<TicketAuth?> {
        return withContext(Dispatchers.IO) {
            try {
                val query = GetMyTicketAuthsQuery()
                val response = apolloClient.query(query).execute()
                response.data?.getMyTicketAuths?.map { it?.fragmentTicketAuth?.asModel }
                    ?: emptyList()
            } catch (exception: ApolloHttpException) {
                throw Exception(exception)
            } catch (exception: ApolloException) {
                throw Exception(exception)
            }
        }
    }
}

private var startOfCurrentDay: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

private val time: Date
    get() = startOfCurrentDay.time

private val currentTime: Long
    get() = startOfCurrentDay.timeInMillis

private val twentyFourHourDateTimeStamp = SimpleDateFormat(
    "yyyy-MM-d'T'HH:mm:ssXXX",
    Locale.getDefault()
).format(Date(currentTime))

private const val ACTIVE = "active"