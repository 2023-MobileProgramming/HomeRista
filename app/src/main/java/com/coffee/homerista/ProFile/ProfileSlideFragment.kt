package com.coffee.homerista.ProFile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.coffee.homerista.R
import com.coffee.homerista.data.entities.Record
import com.coffee.homerista.ui.viewmoel.RecordViewModel
import java.time.LocalDate

private const val YEAR = "year"
private const val MONTH = "month"
private const val DAY = "day"



class ProfileSlideFragment : Fragment() {
    private var year: Int? = null
    private var month: Int? = null
    private var day: Int? = null

    private val viewModel: RecordViewModel by activityViewModels { RecordViewModel.Factory }

    private lateinit var viewPager: ViewPager2
    private lateinit var viewGroup: ViewGroup
    private lateinit var layout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            year = it.getInt(YEAR)
            month = it.getInt(MONTH)
            day = it.getInt(DAY)
        }
    }

    @SuppressLint("NewApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewGroup =  inflater.inflate(R.layout.fragment_profile_slide, container, false) as ViewGroup
        Log.d("date", "${year}, ${month}, ${day}")
        viewModel.loadDataByDate(LocalDate.of(year ?: 0, month ?: 0, day ?: 0))
        viewPageInit()
        observeData()
        return viewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout = view.findViewById(R.id.profileSlideLayout)

        //레이아웃 눌렀을 때 뒤로 가기
        layout.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun viewPageInit() {
        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = viewGroup.findViewById(R.id.profilePager)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        //화면에 3개의 page 보이도록 하는 설정
        viewPager.offscreenPageLimit = 3

        // increase this offset to show more of left/right
        val offsetPx = 20.dpToPx(resources.displayMetrics)
        viewPager.setPadding(offsetPx, 0, offsetPx, 0)
        // increase this offset to increase distance between 2 items
        val pageMarginPx = 10.dpToPx(resources.displayMetrics)
        val marginTransformer = MarginPageTransformer(pageMarginPx)
        viewPager.setPageTransformer(marginTransformer)
    }

    private fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()

    private fun observeData() {
        // LiveData를 관찰하고 데이터가 변경될 때마다 호출되는 observe 메서드
        viewModel.dataByDateList.observe(viewLifecycleOwner) { dataList ->
            // 데이터가 변경되었을 때 화면 업데이트 등의 작업 수행
            // 예: pagerAdapter에 새로운 데이터 설정
            Log.d("로그", "${dataList} 변화 감지")
            Log.d("로그", "${dataList.size} 변화 감지")
            val pagerAdapter = viewPager.adapter as? ProfileSlideFragment.ScreenSlidePagerAdapter
            pagerAdapter?.setDataList(dataList)
        }
    }

    private inner class ScreenSlidePagerAdapter(fg: Fragment) : FragmentStateAdapter(fg) {
        private var dataList: List<Record> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        fun setDataList(newList: List<Record>) {
            dataList = newList
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = dataList.size

        override fun createFragment(position: Int): Fragment =
            DetailProfileFragment(dataList[position])

        override fun getItemId(position: Int): Long {
            return dataList[position].id.toLong()
        }

        override fun containsItem(itemId: Long): Boolean {
            return dataList.any { record -> record.id.toLong() == itemId }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(year: Int, month: Int, day: Int) =
            ProfileSlideFragment().apply {
                arguments = Bundle().apply {
                    putInt(YEAR, year)
                    putInt(MONTH, month)
                    putInt(DAY, day)
                }
            }
    }
}