package com.coffee.homerista.extract

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import com.coffee.homerista.R

class RecordFragment : Fragment() {



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var recordView = inflater.inflate(R.layout.fragment_record, container, false)
        var startBtn: Button = recordView.findViewById(R.id.startBtn)
        var resetBtn: Button = recordView.findViewById(R.id.resetBtn)
        var saveBtn: Button = recordView.findViewById(R.id.saveBtn)
        var timer: Chronometer = recordView.findViewById(R.id.timer)
        var recordBtn: Button = recordView.findViewById(R.id.recordBtn)
        var recordTime: Long = 0
        var pauseTime = 0L


        startBtn.setOnClickListener {
            timer.base = SystemClock.elapsedRealtime() - recordTime
            timer.start()
            // 버튼 표시 여부 조정
            startBtn.isEnabled = false
            resetBtn.isEnabled = true
            saveBtn.isEnabled = true
        }

        resetBtn.setOnClickListener {
            timer.base = SystemClock.elapsedRealtime()
            recordTime = 0
            timer.start()
            startBtn.isEnabled = true
            resetBtn.isEnabled = false
        }

        saveBtn.setOnClickListener {
            startBtn.isEnabled = false
            resetBtn.isEnabled = false
            timer.stop()
            recordTime = SystemClock.elapsedRealtime() - timer.base

        }

        recordBtn.setOnClickListener {
            showRecordDialog()
        }
        return recordView
    }

    private fun showRecordDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_record, null)
        dialogBuilder.setView(dialogView)
        val dialog = dialogBuilder.create()
        dialog.show()
    }
}