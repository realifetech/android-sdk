package com.realifetech.sdk.sell.weboredering

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.webkit.*
import android.webkit.WebSettings.LOAD_CACHE_ELSE_NETWORK
import android.webkit.WebSettings.LOAD_DEFAULT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.realifetech.core_sdk.domain.CoreConfiguration
import com.realifetech.realifetech_sdk.R
import com.realifetech.realifetech_sdk.databinding.FragmentWebOrderingBinding
import com.realifetech.sdk.utils.ColorPallet.colorOnPrimary
import com.realifetech.sdk.utils.ColorPallet.colorPrimary
import com.realifetech.sdk.utils.ColorPallet.colorSurface
import com.realifetech.sdk.utils.isNetworkAvailable
import com.realifetech.sdk.utils.setTaggableOnSurfaceTint
import com.realifetech.sdk.utils.tint

class WebOrderingFragment : Fragment() {

    private var _binding: FragmentWebOrderingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebOrderingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupNavBar()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let { binding.webView.restoreState(it) } ?: run { setupWebView() }
    }

    private fun setupNavBar() {
        binding.apply {
            navigationLayout.setBackgroundColor(colorSurface)
            navForward.apply {
                setTaggableOnSurfaceTint()
                setOnClickListener { webView.goForward() }
            }
            navBack.apply {
                setTaggableOnSurfaceTint()
                setOnClickListener { webView.goBack() }
            }
        }
    }

    private fun setupToolbar() {
        binding.apply {
            val activity = activity as? AppCompatActivity
            activity?.setSupportActionBar(toolbar)
            activity?.supportActionBar?.apply {
                this.setBackgroundDrawable(ColorDrawable(colorPrimary))
                context?.let {
                    setHomeAsUpIndicator(
                        ContextCompat.getDrawable(it, R.drawable.ic_close_rlt)?.apply {
                            tint(colorOnPrimary)
                        })
                }
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowTitleEnabled(false)
            }
        }
        setHasOptionsMenu(true)
    }

    private fun setupWebView() {
        binding.apply {
            webView.apply {
                context?.let {
                    setBackgroundColor(ContextCompat.getColor(it, R.color.RLT_SDK_Color_Surface))
                }
                setOnLongClickListener { true }
                isHapticFeedbackEnabled = false
                isLongClickable = false
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    builtInZoomControls = true
                    useWideViewPort = true
                    loadWithOverviewMode = true
                    allowFileAccess = true
                    CookieManager.getInstance().removeAllCookies(null)
                    CookieManager.getInstance().flush()
                    if (!isNetworkAvailable()) {
                        cacheMode = LOAD_CACHE_ELSE_NETWORK
                    }
                }

            }
            webView.webViewClient = getWebViewClient()
            webView.loadUrl(CoreConfiguration.webOrderingJourneyUrl)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_web_ordering, menu)
        context?.let {
            menu.findItem(R.id.refresh_web)
                .setIcon(ContextCompat.getDrawable(it, R.drawable.ic_refresh_rlt)?.apply {
                    tint(colorOnPrimary)
                })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val refreshMenuItem = menu.findItem(R.id.refresh_web)
        refreshMenuItem.setOnMenuItemClickListener {
            binding.webView.reload()
            true
        }
        super.onPrepareOptionsMenu(menu)
    }

    private fun FragmentWebOrderingBinding.getWebViewClient() =
        object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (isAdded) {
                    if (URLUtil.isNetworkUrl(url)) {
                        return false
                    }
                }
                return true
            }

            override fun onLoadResource(view: WebView, url: String) {
                webView.settings.cacheMode =
                    if (isNetworkAvailable()) LOAD_DEFAULT else LOAD_CACHE_ELSE_NETWORK
                webView.visibility = View.VISIBLE
                navForward.isEnabled = webView.canGoForward()
                navBack.isEnabled = webView.canGoBack()
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                webView.isVisible = false
                if (webView.settings.cacheMode != LOAD_CACHE_ELSE_NETWORK) {
                    webView.settings.cacheMode = LOAD_CACHE_ELSE_NETWORK
                    view?.loadUrl(error?.description.toString())
                }
            }

        }

    override fun onSaveInstanceState(outState: Bundle) {
        binding.webView.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = WebOrderingFragment()
    }

}