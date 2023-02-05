package com.realifetech.sdk.access.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetMyTicketsQuery
import com.realifetech.GetUpcomingTicketsQuery
import com.realifetech.sdk.access.data.model.Ticket
import com.realifetech.sdk.access.data.model.asModel
import com.realifetech.sdk.analytics.data.model.asAnalyticEvent
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.type.TicketFilter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AccessDataSourceImpl @Inject constructor(private val apolloClient: ApolloClient) :
    AccessDataSource {
    override fun getMyTickets(
        pageSize: Int,
        callback: (error: Exception?, response: PaginatedObject<Ticket?>?) -> Unit
    ) {
        try {
            val response = apolloClient.query(GetMyTicketsQuery(pageSize))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                .build()

            response.enqueue(object : ApolloCall.Callback<GetMyTicketsQuery.Data>(){
                override fun onResponse(response: Response<GetMyTicketsQuery.Data>) {
                    val tickets = response.data?.getMyTickets?.edges?.map { result -> result?.fragments?.fragmentTicket?.asModel }
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

    override fun getMyTicketById() {
        TODO("Not yet implemented")
    }

    override fun getNextUpcomingTicket(callback: (error: Exception?, ticket: Ticket?) -> Unit) {
        try {
            val response = apolloClient.query(
                GetUpcomingTicketsQuery(
                pageSize = 1,
                filters = Input.optional(TicketFilter(status = Input.optional(ACTIVE), sessionDateAfter = Input.optional(twentyFourHourDateTimeStamp)))
            )
            )
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                .build()
            response.enqueue(object : ApolloCall.Callback<GetUpcomingTicketsQuery.Data>(){
                override fun onResponse(response: Response<GetUpcomingTicketsQuery.Data>) {
                    callback.invoke(null, response.data?.getMyTickets?.edges?.first()?.fragments?.fragmentTicket?.asModel)
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }
            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun getMyTicketAuths() {
        TODO("Not yet implemented")
    }

    private var startOfCurrentDay: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    private val time: Date
        get() = startOfCurrentDay.time

    private val currentTime: Long
        get() = startOfCurrentDay.timeInMillis

    private var twentyFourHourDateTimeStamp = SimpleDateFormat(
        DATE_FORMAT,
        Locale.getDefault()
    ).format(Date(currentTime))


}

private const val ACTIVE = "active"
private const val DATE_FORMAT = "yyyy-MM-d'T'HH:mm:ssXXX"