package com.coffee.homerista.BeanSlide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.coffee.homerista.R
import com.coffee.homerista.data.entities.Bean
import com.coffee.homerista.repository.BeanRepository
import kotlin.math.abs

class BeanSlideFragment : Fragment() {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private lateinit var viewPager: ViewPager2
    private lateinit var viewGroup: ViewGroup
    private val viewModel: BeanSlideViewModel by viewModels {
        val repository = BeanRepository(requireActivity().application)
        BeanSlideViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewGroup = inflater.inflate(R.layout.fragment_bean_slide, container, false) as ViewGroup
        viewPageInit()
        observeData()
        return viewGroup
    }

    private fun viewPageInit() {
        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = viewGroup.findViewById(R.id.pager)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity())
        viewPager.adapter = pagerAdapter

        //화면에 3개의 page 보이도록 하는 설정
        viewPager.offscreenPageLimit = 3

        viewPager.setPageTransformer { page, position ->
            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.margin_large)
            val pagerWidth = resources.getDimensionPixelOffset(R.dimen.page_width)
            val screenWidth = resources.displayMetrics.widthPixels
            val offsetPx = screenWidth - pageMarginPx - pagerWidth

            page.translationX = position * -offsetPx

            val v = 1- abs(position)
            page.scaleY = 0.8f + v * 0.2f
        }
    }

    private fun observeData() {
        // LiveData를 관찰하고 데이터가 변경될 때마다 호출되는 observe 메서드
        viewModel.dataList.observe(viewLifecycleOwner) { dataList ->
            // 데이터가 변경되었을 때 화면 업데이트 등의 작업 수행
            // 예: pagerAdapter에 새로운 데이터 설정
            val pagerAdapter = viewPager.adapter as? ScreenSlidePagerAdapter
            pagerAdapter?.setDataList(dataList)
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        private var dataList: List<Bean> = viewModel.dataList.value?: emptyList()
        fun setDataList(newList: List<Bean>) {
            dataList = newList
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = viewModel.dataList.value?.count() ?: 0

        override fun createFragment(position: Int): Fragment = BeanSlidePageFragment(dataList[position])
    }
}