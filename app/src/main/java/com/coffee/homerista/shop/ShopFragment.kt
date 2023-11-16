package com.coffee.homerista.shop

import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.coffee.homerista.R


class ShopFragment : Fragment() {

    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // fragment_show.xml을 inflate하여 View 객체를 얻습니다.
        val view = inflater.inflate(R.layout.activity_main, container, false)

        // webView를 초기화합니다.
        webView = view.findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true

        // https://shopping.naver.com/home로 웹뷰를 연결합니다.
        webView.loadUrl("https://search.shopping.naver.com/search/all?query=%EC%BB%A4%ED%94%BC&cat_id=&frm=NVSHATC")

        return view
    }
}
