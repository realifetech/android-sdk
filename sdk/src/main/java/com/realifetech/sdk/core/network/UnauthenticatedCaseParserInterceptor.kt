package com.realifetech.sdk.core.network

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

class UnauthenticatedCaseParserInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        val body = response.body?.string()
        val error = getErrorAsJson(if (body.isNullOrEmpty()) null else JSONObject(body))
        response = if (isUnauthorizedResponse(error)) {
            updateResponseCode(error, response, body)
        } else {
            reAssignBodyToResponse(response, body)
        }
        return response
    }

    private fun reAssignBodyToResponse(
        response: Response,
        body: String?
    ) = response.newBuilder()
        .code(response.code)
        .message(response.message)
        .body(
            body?.toResponseBody(
                response.body?.contentType()
            )
        ).build()

    private fun updateResponseCode(
        error: JSONObject?,
        response: Response,
        body: String?
    ): Response {
        return try {
            val message = getErrorMessage(error) ?: DEFAULT_MESSAGE
            response.newBuilder()
                .code(HTTP_UNAUTHORIZED)
                .message(message)
                .body(
                    "{\"$MESSAGE\":\"$message\"".toResponseBody(
                        response.body?.contentType()
                    )
                ).build()
        } catch (e: Exception) {
            return reAssignBodyToResponse(response, body)
        }
    }


    private fun isUnauthorizedResponse(error: JSONObject?): Boolean {
        val code = error?.getJSONObject(EXTENSIONS)?.getString(CODE)
        return code == UNAUTHENTICATED
    }

    private fun getErrorMessage(error: JSONObject?): String? {
        return error?.getString(MESSAGE)
    }

    private fun getErrorAsJson(body: JSONObject?): JSONObject? {
        return try {
            body?.getJSONArray(ERRORS)?.getJSONObject(0)
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        const val UNAUTHENTICATED = "UNAUTHENTICATED"
        const val ERRORS = "errors"
        const val MESSAGE = "message"
        const val EXTENSIONS = "extensions"
        const val CODE = "code"
        const val DEFAULT_MESSAGE = "User is not authorised"
    }
}