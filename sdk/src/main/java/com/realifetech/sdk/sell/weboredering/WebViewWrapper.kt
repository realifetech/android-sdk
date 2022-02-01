package com.realifetech.sdk.sell.weboredering

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.*
import android.widget.ImageView
import androidx.core.view.isVisible
import com.realifetech.fragment.AuthToken
import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.utils.NetworkUtil
import javax.inject.Inject

// This can be tested using UI test
class WebViewWrapper @Inject constructor(
    private val networkUtil: NetworkUtil,
    private val configuration: ConfigurationStorage,
    private val storage: AuthTokenStorage
) {

    var webView: WebView? = null

    fun configureWebView(
        backgroundColor: Int,
        isAdded: Boolean,
        navBack: ImageView,
        navForward: ImageView,
        context: Context
    ) {
        webView?.apply {
            setBackgroundColor(backgroundColor)
            setOnLongClickListener { true }
            isHapticFeedbackEnabled = false
            isLongClickable = false
            settings.apply {
                setupSettings()
            }
        }
        webView?.webViewClient = getWebViewClient(isAdded, navBack, navForward, context)
        webView?.loadUrl(configuration.webOrderingJourneyUrl)

    }

    private fun WebSettings.setupSettings() {
        javaScriptEnabled = true
        domStorageEnabled = true
        builtInZoomControls = false
        useWideViewPort = true
        loadWithOverviewMode = true
        allowFileAccess = true
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()
    }

    private fun getWebViewClient(
        isAdded: Boolean,
        navBack: ImageView,
        navForward: ImageView,
        context: Context
    ) =
        object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url.contains(DEEP_LINK)) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    context.startActivity(intent)
                    return true;
                }
                if (isAdded) {
                    if (URLUtil.isNetworkUrl(url)) {
                        return false
                    }
                }
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                storage.webAuthToken?.let {
                    authenticate(it)
                    storage.deleteWebAuthToken()
                }
            }

            override fun onLoadResource(view: WebView, url: String) {
                webView?.apply {
                    settings.cacheMode =
                        if (networkUtil.isNetworkAvailable())
                            WebSettings.LOAD_DEFAULT else WebSettings.LOAD_CACHE_ELSE_NETWORK
                    isVisible = true
                    navBack.isEnabled = canGoBack()
                    navForward.isEnabled = canGoForward()
                }

            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                webView?.apply {
                    isVisible = false
                    if (settings.cacheMode != WebSettings.LOAD_CACHE_ELSE_NETWORK) {
                        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                    }
                }

            }

        }

    private fun getJavaScript(authToken: AuthToken): String =
        "acceptAuthDetails(" +
                "\"${authToken.accessToken}\"," +
                " \"${authToken.refreshToken}\"," +
                " ${authToken.expiresIn}," +
                " \"${authToken.tokenType}\")"

    fun clearCacheAndStorage() {
        webView?.clearCache(true)
        WebStorage.getInstance().deleteAllData()
    }

    fun authenticate(authToken: AuthToken) {
        webView?.apply {
            evaluateJavascript(getJavaScript(authToken)) { value ->
                Log.d("React Data", value.toString())
            }
            reload()
        }
    }

    companion object {
        private const val DEEP_LINK = "rlthostapplogin"
    }
}