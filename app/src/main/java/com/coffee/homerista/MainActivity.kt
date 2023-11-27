package com.coffee.homerista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels

import com.coffee.homerista.BeanSlide.BeanSlideFragment
import com.coffee.homerista.BeanSlide.BeanViewModel
import com.coffee.homerista.extract.ExtractFragment
import com.coffee.homerista.shop.ShopFragment
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.coffee.homerista.settings.SettingsFragment
import com.coffee.homerista.home.FirstFragment
import com.coffee.homerista.ProfileFragment

class MainActivity : AppCompatActivity() {

    //bean에서 사용할 viewModel 생성
    private val beanViewModel: BeanViewModel by viewModels { BeanViewModel.Factory }

    lateinit var firstFragment: FirstFragment
    lateinit var beanSlideFragment: BeanSlideFragment
    lateinit var shopFragment: ShopFragment
    lateinit var recordFragment: RecordFragment
    lateinit var extractFragment: ExtractFragment
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var settingsFragment: SettingsFragment
    lateinit var ProfileFragment: ProfileFragment


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
}