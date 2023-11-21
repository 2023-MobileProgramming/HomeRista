package com.coffee.homerista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log;
import android.view.MenuItem;
import androidx.fragment.app.Fragment

import com.coffee.homerista.FirstFragment
import com.coffee.homerista.BeanSlide.BeanSlideFragment
import com.coffee.homerista.BeanSlide.BeanSlidePageFragment
import com.coffee.homerista.extract.ExtractFragment
import com.coffee.homerista.shop.ShopFragment
import com.coffee.homerista.RecordFragment
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.coffee.homerista.R
import com.coffee.homerista.settings.SettingsFragment
import kotlin.math.abs

class MainActivity : AppCompatActivity() {


    lateinit var firstFragment: FirstFragment
    lateinit var beanSlidePageFragment: BeanSlidePageFragment
    lateinit var shopFragment: ShopFragment
    lateinit var recordFragment: RecordFragment
    lateinit var extractFragment: ExtractFragment
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var settingsFragment: SettingsFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        firstFragment = FirstFragment()
        beanSlidePageFragment = BeanSlidePageFragment()
        shopFragment = ShopFragment()
        extractFragment = ExtractFragment()
        settingsFragment = SettingsFragment()

        recordFragment = RecordFragment()



        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl, firstFragment)
            .commitAllowingStateLoss()

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {


                R.id.navigation_bean -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl, beanSlidePageFragment)
                        .commitAllowingStateLoss()
                    true
                }


                R.id.navigation_extract -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl, extractFragment )
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