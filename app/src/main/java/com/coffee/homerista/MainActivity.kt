package com.coffee.homerista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels

import com.coffee.homerista.BeanSlide.BeanSlideFragment
import com.coffee.homerista.BeanSlide.BeanViewModel
import com.coffee.homerista.extract.ExtractFragment
import com.coffee.homerista.extract.RecordFragment
import com.coffee.homerista.shop.ShopFragment
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.coffee.homerista.settings.SettingsFragment
import com.coffee.homerista.home.FirstFragment
import com.coffee.homerista.ProfileFragment
import com.coffee.homerista.ui.viewmoel.RecordViewModel

class MainActivity : AppCompatActivity() {

    //bean에서 사용할 viewModel 생성
    private val beanViewModel: BeanViewModel by viewModels{ BeanViewModel.Factory }
    //record에서 사용할 ViewModel 생성
    private val recordViewModel: RecordViewModel by viewModels { RecordViewModel.Factory }

    lateinit var firstFragment: FirstFragment
    lateinit var beanSlideFragment: BeanSlideFragment
    lateinit var shopFragment: ShopFragment
    lateinit var recordFragment: RecordFragment
    lateinit var extractFragment: ExtractFragment
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var settingsFragment: SettingsFragment
    lateinit var ProfileFragment: ProfileFragment
    lateinit var shopNavi: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        firstFragment = FirstFragment()
        beanSlideFragment = BeanSlideFragment.newInstance()
        shopFragment = ShopFragment()
        extractFragment = ExtractFragment()
        settingsFragment = SettingsFragment()
        ProfileFragment = ProfileFragment()

        recordFragment = RecordFragment()



        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.selectedItemId = R.id.navigation_home

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl, firstFragment)
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

                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl, firstFragment)
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



                R.id.navigation_setting -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl, settingsFragment)
                        .commitAllowingStateLoss()
                    true
                }





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