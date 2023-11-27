package com.coffee.homerista

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import java.util.Timer
import java.util.TimerTask
import java.util.logging.Logger
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class RecordFragment : Fragment() {


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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


