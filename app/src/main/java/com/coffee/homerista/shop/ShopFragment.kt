package com.coffee.homerista.shop

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.coffee.homerista.R

class ShopFragment : Fragment() {

    companion object {
        fun newInstance() = ShopFragment()
    }

    private lateinit var viewModel: ShopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shop, container, false)



        //웹 뷰에 접속하기 위한 세팅과 메소드

        val webView = view.findViewById<WebView>(R.id.webView)
        val webSettings: WebSettings = webView.settings


        val newUserAgent = "Mozilla/5.0 (Linux; Android 10; Android SDK built for x86) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Mobile Safari/537.36"
        webSettings.userAgentString = newUserAgent


        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://msearch.shopping.naver.com/search/all?query=%EC%BB%A4%ED%94%BC&frm=NVSHSRC&vertical=search")

        // https://shoppinglive.naver.com/search/lives?query=%EC%BB%A4%ED%94%BC
        webView.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    requireActivity().onBackPressed()
                }
            }
            true
        }
        return view



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        // TODO: Use the ViewModel
    }

}