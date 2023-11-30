package com.coffee.homerista.extract

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.coffee.homerista.ProFile.DetailProfileFragment
import com.coffee.homerista.R
import com.coffee.homerista.data.entities.Bean

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val YEAR = "year"
private const val MONTH = "month"
private const val DAY = "day"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileSlideFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileSlideFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var year: Int? = null
    private var month: Int? = null
    private var day: Int? = null
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewGroup =  inflater.inflate(R.layout.fragment_profile_slide, container, false) as ViewGroup

        viewPageInit()
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


    private inner class ScreenSlidePagerAdapter(fg: Fragment) : FragmentStateAdapter(fg) {
        private var dataList: List<Bean> = emptyList()
//        @SuppressLint("NotifyDataSetChanged")
//        fun setDataList(newList: List<Bean>) {
//            dataList = newList
//            notifyDataSetChanged()
//        }

        override fun getItemCount(): Int = 5

        override fun createFragment(position: Int): Fragment = DetailProfileFragment()

//        override fun getItemId(position: Int): Long {
//            return dataList[position].id.toLong()
//        }
//
//        override fun containsItem(itemId: Long): Boolean {
//            return dataList.any { bean -> bean.id.toLong() == itemId }
//        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileSlideFragment.
         */
        // TODO: Rename and change types and number of parameters
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