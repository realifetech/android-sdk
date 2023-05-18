package com.realifetech.sdk.access.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
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
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AccessDataSourceImpl @Inject constructor(apolloClient: ApolloClient) : AccessDataSource {

    override fun getMyTickets(
        pageSize: Int,
        callback: (error: Exception?, response: PaginatedObject<Ticket?>?) -> Unit
    ) {
        try {
            val response = apolloClient.query(GetMyTicketsQuery(pageSize))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build()

            response.enqueue(object : ApolloCall.Callback<GetMyTicketsQuery.Data>() {
                override fun onResponse(response: Response<GetMyTicketsQuery.Data>) {
                    val tickets =
                        response.data?.getMyTickets?.edges?.map { result -> result?.fragments?.fragmentTicket?.asModel }
                    val nextPage = response.data?.getMyTickets?.nextPage
                    val paginatedObject = PaginatedObject(tickets, nextPage)
                    callback.invoke(null, paginatedObject)
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }
            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun getMyTicketById(
        id: Int,
        callback: (error: Exception?, response: Ticket?) -> Unit
    ) {
        try {
            val response = apolloClient.query(GetMyTicketQuery(id = id.toString())).toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK).build()
            response.enqueue(object : ApolloCall.Callback<GetMyTicketQuery.Data>() {
                override fun onResponse(response: Response<GetMyTicketQuery.Data>) {
                    val ticket = response.data?.getMyTicket?.fragments?.fragmentTicket?.asModel
                    callback.invoke(null, ticket)
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }

            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun getNextUpcomingTicket(callback: (error: Exception?, ticket: Ticket?) -> Unit) {
        try {
            val response = apolloClient.query(
                GetUpcomingTicketsQuery(
                    pageSize = 1,
                    filters = Input.optional(
                        TicketFilter(
                            status = Input.optional(ACTIVE),
                            sessionDateAfter = Input.optional(twentyFourHourDateTimeStamp)
                        )
                    )
                )
            )
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build()
            response.enqueue(object : ApolloCall.Callback<GetUpcomingTicketsQuery.Data>() {
                override fun onResponse(response: Response<GetUpcomingTicketsQuery.Data>) {
                    callback.invoke(
                        null,
                        response.data?.getMyTickets?.edges?.first()?.fragments?.fragmentTicket?.asModel
                    )
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }
            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun getMyTicketAuths(callback: (error: Exception?, tickets: List<TicketAuth?>?) -> Unit) {
        try {
            val response = apolloClient.query(GetMyTicketAuthsQuery()).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST).build()
            response.enqueue(object : ApolloCall.Callback<GetMyTicketAuthsQuery.Data>() {
                override fun onResponse(response: Response<GetMyTicketAuthsQuery.Data>) {
                    callback.invoke(
                        null,
                        response.data?.getMyTicketAuths?.map { it?.fragments?.fragmentTicketAuth?.asModel })
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }

            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
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