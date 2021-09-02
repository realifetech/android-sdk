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
import androidx.lifecycle.ViewModelProvider
import com.realifetech.realifetech_sdk.R
import com.realifetech.realifetech_sdk.databinding.FragmentWebOrderingBinding
import com.realifetech.sdk.core.database.configuration.ConfigurationStorage
import com.realifetech.sdk.core.utils.ColorPallet
import com.realifetech.sdk.di.Injector
import com.realifetech.sdk.utils.NetworkUtil
import com.realifetech.sdk.utils.clicks
import com.realifetech.sdk.utils.setTaggableOnSurfaceTint
import com.realifetech.sdk.utils.tint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
class WebOrderingFragment : Fragment() {

    private var _binding: FragmentWebOrderingBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var colorPallet: ColorPallet

    @Inject
    lateinit var configuation: ConfigurationStorage

    @Inject
    internal lateinit var networkUtil: NetworkUtil

    private lateinit var viewModel: WebOrderingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.getComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebOrderingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WebOrderingViewModel::class.java)
        listenToViewModel()
        setupToolbar()
        setupNavBar()
    }

    private fun listenToViewModel() {
        binding.apply {
            viewModel.screenState.observe(viewLifecycleOwner, { screen ->
                when (screen) {
                    WebOrderingViewModel.ScreenState.WebViewGoBack -> webView.goBack()
                    WebOrderingViewModel.ScreenState.WebViewGoForward -> webView.goForward()
                    WebOrderingViewModel.ScreenState.WebViewRefresh -> webView.reload()
                }
            })
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let { binding.webView.restoreState(it) } ?: run { setupWebView() }
    }

    private fun setupNavBar() {
        binding.apply {
            navigationLayout.setBackgroundColor(colorPallet.colorSurface)
            navForward.setTaggableOnSurfaceTint(colorPallet)
            viewModel.handleNavForward(navForward.clicks())
            navBack.setTaggableOnSurfaceTint(colorPallet)
            viewModel.handleNavBack(navBack.clicks())
        }
    }

    private fun setupToolbar() {
        binding.apply {
            val activity = activity as? AppCompatActivity
            activity?.setSupportActionBar(toolbar)
            activity?.supportActionBar?.apply {
                this.setBackgroundDrawable(ColorDrawable(colorPallet.colorPrimary))
                context?.let {
                    setHomeAsUpIndicator(
                        ContextCompat.getDrawable(it, R.drawable.ic_close_rlt)?.apply {
                            tint(colorPallet.colorOnPrimary)
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
                    setupSettings()
                }
            }
            webView.webViewClient = getWebViewClient()
            webView.loadUrl(configuation.webOrderingJourneyUrl)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_web_ordering, menu)
        context?.let {
            menu.findItem(R.id.refresh_web)
                .setIcon(ContextCompat.getDrawable(it, R.drawable.ic_refresh_rlt)?.apply {
                    tint(colorPallet.colorOnPrimary)
                })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val refreshMenuItem = menu.findItem(R.id.refresh_web)
        viewModel.handleRefresh(refreshMenuItem.clicks())
        super.onPrepareOptionsMenu(menu)
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
        if (!networkUtil.isNetworkAvailable()) {
            cacheMode = LOAD_CACHE_ELSE_NETWORK
        }
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
                    if (networkUtil.isNetworkAvailable()) LOAD_DEFAULT else LOAD_CACHE_ELSE_NETWORK
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