package com.realifetech.sdk.identity.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.realifetech.*
import com.realifetech.fragment.AuthToken
import com.realifetech.type.SignedUserInfoInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class IdentityDataSourceImpl @Inject constructor(private val apolloClient: ApolloClient) :
    IdentityDataSource {

    override suspend fun generateNonce(): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = apolloClient.mutation(GenerateNonceMutation()).execute()

                if (response.hasErrors()) {
                    throw ApolloException(
                        response.errors?.firstOrNull()?.message ?: "Unknown error"
                    )
                } else {
                    response.data?.generateNonce?.token
                        ?: throw Exception("No data found")
                }
            } catch (exception: ApolloException) {
                throw Exception(exception)
            }
        }
    }

    override suspend fun getDeviceId(): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = apolloClient.query(GetDeviceIdQuery()).execute()

                if (response.hasErrors()) {
                    throw ApolloException(
                        response.errors?.firstOrNull()?.message ?: "Unknown error"
                    )
                } else {
                    response.data?.me?.device?.id
                        ?: throw Exception("No data found")
                }
            } catch (exception: HttpException) {
                throw Exception(exception)
            }
        }
    }

    override suspend fun authenticateUserBySignedUserInfo(userInfo: SignedUserInfoInput): AuthToken {
        return withContext(Dispatchers.IO) {
            try {
                val mutation = AuthenticateUserBySignedUserInfoMutation(userInfo)
                val response = apolloClient.mutation(mutation).execute()

                if (response.hasErrors()) {
                    throw ApolloException(
                        response.errors?.firstOrNull()?.message ?: "Unknown error"
                    )
                } else {
                    response.data?.authenticateUserBySignedUserInfo?.authToken
                        ?: throw Exception("No data found")
                }
            } catch (exception: HttpException) {
                throw Exception(exception)
            }
        }
    }

    override suspend fun deleteMyAccount(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = apolloClient.mutation(DeleteMyAccountMutation()).execute()

                if (response.hasErrors()) {
                    throw ApolloException(
                        response.errors?.firstOrNull()?.message ?: "Unknown error"
                    )
                } else {
                    response.data?.deleteMyAccount?.success
                        ?: throw Exception("No data found")
                }
            } catch (exception: HttpException) {
                throw Exception(exception)
            }
        }
    }

    override suspend fun getSSO(provider: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val query = GetSSOQuery(provider)
                val response = apolloClient.query(query).execute()

                if (response.hasErrors()) {
                    throw ApolloException(
                        response.errors?.firstOrNull()?.message ?: "Unknown error"
                    )
                } else {
                    response.data?.getSSO?.authUrl
                }
            } catch (exception: HttpException) {
                throw Exception(exception)
            }
        }
    }
}