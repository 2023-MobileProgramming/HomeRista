package com.coffee.homerista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var extractionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        extractionButton = findViewById(R.id.extractionButton)

        val beanFragment = BeanTabFragment()
        val extractionFragment = ExtractionTabFragment()
        val shoppingFragment = ShoppingTabFragment()

        setCurrentFragment(beanFragment)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_bean -> setCurrentFragment(beanFragment)
                R.id.navigation_extract -> setCurrentFragment(extractionFragment)
                R.id.navigation_shop -> setCurrentFragment(shoppingFragment)
            }
            true
        }

        // FloatingActionButton에 클릭 이벤트 추가
        extractionButton.setOnClickListener {
            // 팝업창 띄우는 로직을 여기에 추가
            // 예를 들어, DialogFragment를 사용하여 팝업창을 띄울 수 있습니다.
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl, fragment)
            commit()
        }
}
