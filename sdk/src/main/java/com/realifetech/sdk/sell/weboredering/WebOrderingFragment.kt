package com.realifetech.sdk.sell.weboredering

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.realifetech.realifetech_sdk.R
import com.realifetech.realifetech_sdk.databinding.FragmentWebOrderingBinding
import com.realifetech.sdk.core.utils.*
import com.realifetech.sdk.di.Injector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
class WebOrderingFragment : Fragment() {

    @Inject
    lateinit var colorPallet: ColorPallet

    @Inject
    lateinit var networkUtil: NetworkUtil

    @Inject
    lateinit var webViewWrapper: WebViewWrapper

    private var _binding: FragmentWebOrderingBinding? = null
    private val binding get() = _binding!!

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
                    WebOrderingViewModel.ScreenState.WebViewGoBack -> webViewWrapper.webView?.goBack()
                    WebOrderingViewModel.ScreenState.WebViewGoForward -> webViewWrapper.webView?.goForward()
                    WebOrderingViewModel.ScreenState.WebViewRefresh -> webViewWrapper.webView?.reload()
                }
            })
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        webViewWrapper.webView?.let { webView ->
            savedInstanceState?.let { webView.restoreState(it) } ?: run { setupWebView() }
        } ?: kotlin.run { setupWebView() }

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
            webViewWrapper.webView = webView
            context?.let {
                webViewWrapper.configureWebView(
                    ContextCompat.getColor(
                        it,
                        R.color.RLT_SDK_Color_Surface
                    ), isAdded, navBack, navForward, it
                )
            }
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

    override fun onSaveInstanceState(outState: Bundle) {
        webViewWrapper.webView?.saveState(outState)
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