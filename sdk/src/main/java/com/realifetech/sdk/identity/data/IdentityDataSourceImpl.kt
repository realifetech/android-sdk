package com.realifetech.sdk.identity.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.AuthenticateUserBySignedUserInfoMutation
import com.realifetech.GenerateNonceMutation
import com.realifetech.GetDeviceIdQuery
import com.realifetech.fragment.AuthToken
import com.realifetech.type.SignedUserInfoInput
import javax.inject.Inject

class IdentityDataSourceImpl @Inject constructor(private val apolloClient: ApolloClient) :
    IdentityDataSource {

    override fun generateNonce(callback: (error: Exception?, response: String?) -> Unit) {
        try {
            apolloClient.mutate(GenerateNonceMutation())
                .enqueue(object : ApolloCall.Callback<GenerateNonceMutation.Data>() {
                    override fun onResponse(response: Response<GenerateNonceMutation.Data>) {
                        response.data?.generateNonce?.let { callback.invoke(null, it.token) }
                            ?: run { callback.invoke(Exception(), null) }
                    }

                    override fun onFailure(e: ApolloException) {
                        callback.invoke(e, null)
                    }
                })
        } catch (exception: Exception) {
            callback.invoke(exception, null)
        }
    }

    override fun getDeviceId(callback: (error: Exception?, response: String?) -> Unit) {
        try {
            val response = apolloClient.query(GetDeviceIdQuery()).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST).build()
            response.enqueue(object :
                ApolloCall.Callback<GetDeviceIdQuery.Data>() {
                override fun onResponse(response: Response<GetDeviceIdQuery.Data>) {
                    response.data?.me?.device?.let {
                        callback.invoke(null, it.id)
                    } ?: run {
                        callback.invoke(
                            Exception(), null
                        )
                    }
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }
            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun authenticateUserBySignedUserInfo(
        userInfo: SignedUserInfoInput,
        callback: (error: Exception?, response: AuthToken?) -> Unit
    ) {
        try {
            val response = apolloClient.mutate(AuthenticateUserBySignedUserInfoMutation(userInfo))
            response.enqueue(object :
                ApolloCall.Callback<AuthenticateUserBySignedUserInfoMutation.Data>() {
                override fun onResponse(response: Response<AuthenticateUserBySignedUserInfoMutation.Data>) {
                    response.data?.authenticateUserBySignedUserInfo?.fragments?.let {
                        callback.invoke(null, it.authToken)
                    } ?: run {
                        callback.invoke(
                            Exception(), null
                        )
                    }
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