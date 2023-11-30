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
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShopFragment : Fragment() {

    companion object {
        fun newInstance() = ShopFragment()
    }

    private lateinit var viewModel: ShopViewModel
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shop, container, false)

        // 웹 뷰 설정
        webView = view.findViewById(R.id.webView)
        val webSettings: WebSettings = webView.settings
        webSettings.userAgentString = "Mozilla/5.0 (Linux; Android 10; Android SDK built for x86) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Mobile Safari/537.36"
        webSettings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        // 바텀 네비게이션 뷰 설정
        val shopNavi = view.findViewById<BottomNavigationView>(R.id.shop_nav)
        shopNavi.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.naver -> webView.loadUrl("https://msearch.shopping.naver.com/search/all?query=%EC%BB%A4%ED%94%BC&frm=NVSHSRC&vertical=search")
                R.id.gmarket -> webView.loadUrl("https://browse.gmarket.co.kr/search?keyword=%EC%BB%A4%ED%94%BC")
                R.id.coupang -> webView.loadUrl("https://www.coupang.com/np/search?component=&q=%EC%BB%A4%ED%94%BC&channel=user")
                R.id.ssg -> webView.loadUrl("https://www.ssg.com/search.ssg?target=all&query=%EC%BB%A4%ED%94%BC")
                R.id.st11 -> webView.loadUrl("http://search.11st.co.kr/MW/search?searchKeyword=%25EC%25BB%25A4%25ED%2594%25BC&decSearchKeyword=%25EC%25BB%25A4%25ED%2594%25BC#_filterKey=1701360994476")
            }
            true
        }
        //https://search.11st.co.kr/total-search?kwd=%25EC%25BB%25A4%25ED%2594%25BC&tabId=TOTAL_SEARCH
        //http://search.11st.co.kr/MW/search?searchKeyword=%25EC%25BB%25A4%25ED%2594%25BC&decSearchKeyword=%25EC%25BB%25A4%25ED%2594%25BC#_filterKey=1701360994476

        // 기본 URL 설정
        webView.loadUrl("https://msearch.shopping.naver.com/search/all?query=%EC%BB%A4%ED%94%BC&frm=NVSHSRC&vertical=search")

        // 뒤로가기 키 이벤트 처리
        webView.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    requireActivity().onBackPressed()
                }
                true
            } else false
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        // ViewModel 사용
    }
}