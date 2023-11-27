package com.coffee.homerista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 프래그먼트의 레이아웃을 인플레이트합니다.
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        // CalendarView와 TextView를 참조합니다.
        val calendarView: CalendarView = rootView.findViewById(R.id.calendarView)
        val displayedDateTextView: TextView = rootView.findViewById(R.id.displayedDateTextView)

        // CalendarView의 날짜 선택 이벤트를 처리합니다.
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // 월(month)는 0부터 시작하므로 1을 더해줍니다.
            val selectedDate = "$year-${month + 1}-$dayOfMonth"
            displayedDateTextView.text = selectedDate
        }

        return rootView
    }
}
