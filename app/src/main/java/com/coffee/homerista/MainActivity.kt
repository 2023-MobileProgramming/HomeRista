package com.coffee.homerista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels

import com.coffee.homerista.BeanSlide.BeanSlideFragment
import com.coffee.homerista.BeanSlide.BeanViewModel
import com.coffee.homerista.extract.RecordFragment
import com.coffee.homerista.shop.ShopFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.coffee.homerista.settings.SettingsFragment
import com.coffee.homerista.home.CuratingFragment
import com.coffee.homerista.ProFile.ProfileFragment
import com.coffee.homerista.ui.viewmoel.RecordViewModel

class MainActivity : AppCompatActivity() {

    //bean에서 사용할 viewModel 생성
    private val beanViewModel: BeanViewModel by viewModels{ BeanViewModel.Factory }
    //record에서 사용할 ViewModel 생성
    private val recordViewModel: RecordViewModel by viewModels { RecordViewModel.Factory }

    lateinit var curatingFragment: CuratingFragment
    lateinit var beanSlideFragment: BeanSlideFragment
    lateinit var shopFragment: ShopFragment
    lateinit var recordFragment: RecordFragment
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var settingsFragment: SettingsFragment
    lateinit var ProfileFragment: ProfileFragment
    lateinit var shopNavi: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_message, null)

        // 텍스트 설정
        val text = layout.findViewById<TextView>(R.id.custom_message_text)
        text.text = "환영합니다"

        Toast.makeText(this, "환영합니다", Toast.LENGTH_LONG).show();


        curatingFragment = CuratingFragment()
        beanSlideFragment = BeanSlideFragment.newInstance()
        shopFragment = ShopFragment()
        settingsFragment = SettingsFragment()
        ProfileFragment = ProfileFragment()

        recordFragment = RecordFragment()



        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.selectedItemId = R.id.navigation_record

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl, recordFragment)
            .commitAllowingStateLoss()

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {


                R.id.navigation_bean -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl, beanSlideFragment)
                        .commitAllowingStateLoss()
                    true
                }


                R.id.navigation_extract -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl, ProfileFragment )
                        .commitAllowingStateLoss()
                    true
                }

                R.id.navigation_record -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl, recordFragment)
                        .commitAllowingStateLoss()
                    true
                }

                R.id.navigation_curating -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl, curatingFragment)
                        .commitAllowingStateLoss()
                    true
                }

                R.id.navigation_shop -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl, shopFragment)
                        .commitAllowingStateLoss()
                    true
                }

                /*
                R.id.navigation_shop -> {
                    showShopMenu(findViewById(R.id.navigation_shop))
                    true
                }
                */







                else -> false


            }
        }
    }

    private fun showShopMenu(anchor: View) {
        val popup = PopupMenu(this, anchor)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_shop, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            // 팝업 메뉴 항목 클릭 이벤트 처리
            true
        }
        popup.show()
    }
}