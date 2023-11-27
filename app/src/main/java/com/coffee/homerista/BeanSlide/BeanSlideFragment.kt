package com.coffee.homerista.BeanSlide

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.coffee.homerista.R
import com.coffee.homerista.data.entities.Bean
import kotlin.math.abs

class BeanSlideFragment : Fragment() {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private lateinit var viewPager: ViewPager2
    private lateinit var viewGroup: ViewGroup
    private lateinit var beanAddButton: ImageView
    private val viewModel: BeanViewModel by activityViewModels {BeanViewModel.Factory}

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        beanAddButton = view.findViewById(R.id.beanAddButton)
        beanAddButton.setOnClickListener {
            val beanEditFragment = BeanEditFragment.newInstance()

            // Fragment를 추가하고 트랜잭션을 커밋
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fullScreen, beanEditFragment)
                .addToBackStack(null) // Back 버튼으로 돌아올 수 있도록 백 스택에 추가
                .commit()
        }
    }

    private fun viewPageInit() {
        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = viewGroup.findViewById(R.id.pager)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ScreenSlidePagerAdapter(this)
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
            Log.d("로그", "${dataList} 변화 감지")
            Log.d("로그", "${dataList.size} 변화 감지")
            val pagerAdapter = viewPager.adapter as? ScreenSlidePagerAdapter
            pagerAdapter?.setDataList(dataList)
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fg: Fragment) : FragmentStateAdapter(fg) {
        private var dataList: List<Bean> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        fun setDataList(newList: List<Bean>) {
            dataList = newList
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = dataList.size

        override fun createFragment(position: Int): Fragment = BeanSlidePageFragment.newInstance(dataList[position])

        override fun getItemId(position: Int): Long {
            return dataList[position].id.toLong()
        }

        override fun containsItem(itemId: Long): Boolean {
            return dataList.any { bean -> bean.id.toLong() == itemId }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BeanSlideFragment()
    }
}