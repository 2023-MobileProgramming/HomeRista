package com.coffee.homerista.ProFile

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.coffee.homerista.R
import com.coffee.homerista.extract.ProfileSlideFragment
import com.coffee.homerista.extract.RecordFragment
import com.coffee.homerista.ui.viewmoel.RecordViewModel

@RequiresApi(Build.VERSION_CODES.O)
class ProfileFragment : Fragment() {
    private val viewModel: RecordViewModel by activityViewModels { RecordViewModel.Factory }

    private lateinit var profileAddButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_profile, container, false) // 프래그먼트의 레이아웃을 인플레이트합니다.

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("Record", "${viewModel.dataList.value}")

        profileAddButton = view.findViewById(R.id.profileAddButton)

        // CalendarView와 TextView를 참조합니다.
        val calendarView: CalendarView = view.findViewById(R.id.calendarView)
        //val displayedDateTextView: TextView = view.findViewById(R.id.displayedDateTextView)

        // CalendarView의 날짜 선택 이벤트를 처리합니다.
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // 월(month)는 0부터 시작하므로 1을 더해줍니다.
            //displayedDateTextView.text = selectedDate

            //Profile view
            //클릭시 상세페이지로 이동
            val profileSlideFragment = ProfileSlideFragment.newInstance(year, month + 1, dayOfMonth)

            // Fragment를 추가하고 트랜잭션을 커밋
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fl, profileSlideFragment)
                .addToBackStack(null) // Back 버튼으로 돌아올 수 있도록 백 스택에 추가
                .commit()

        }

        profileAddButton.setOnClickListener {
            val recordFragment = RecordFragment()

            // Fragment를 추가하고 트랜잭션을 커밋
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fl, recordFragment)
                .addToBackStack(null) // Back 버튼으로 돌아올 수 있도록 백 스택에 추가
                .commit()
        }

    }
}
