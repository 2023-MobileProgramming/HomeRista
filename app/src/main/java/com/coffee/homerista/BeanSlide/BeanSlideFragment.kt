package com.coffee.homerista.BeanSlide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.coffee.homerista.R

/**
 * The number of pages (wizard steps) to show in this demo.
 */
private const val NUM_PAGES = 5

class BeanSlideFragment : Fragment() {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private lateinit var viewPager: ViewPager2
    private lateinit var viewGroup: ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewGroup = inflater.inflate(R.layout.fragment_bean_slide, container, false) as ViewGroup
        viewPageInit()
        return viewGroup
    }

    private fun viewPageInit() {
        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = viewGroup.findViewById(R.id.pager)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity())
        viewPager.adapter = pagerAdapter

        viewPager.setPageTransformer(CompositePageTransformer())
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = BeanSlidePageFragment()
    }
}