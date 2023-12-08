package com.coffee.homerista.extract

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.EditText
import android.widget.RatingBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.coffee.homerista.R
import com.coffee.homerista.data.entities.Record
import com.coffee.homerista.ui.viewmoel.RecordViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class RecordFragment : Fragment() {
    private val viewModel: RecordViewModel by activityViewModels { RecordViewModel.Factory}

    private lateinit var startBtn: Button
    private lateinit var resetBtn: Button
    private lateinit var saveBtn: Button
    private lateinit var timer: Chronometer
    private lateinit var temp: EditText
    private lateinit var decomp: EditText
    private lateinit var weight: EditText
    private lateinit var bean: EditText
    private lateinit var title: EditText
    private lateinit var rating: RatingBar
    private lateinit var comment: EditText
    private lateinit var recordBtn1: Button
    private lateinit var recordBtn2: Button
    private lateinit var saveRecordButton: Button
    private var recordTime: Long = 0L
    private var pauseTime = 0L


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var recordView = inflater.inflate(R.layout.fragment_record, container, false)

        return recordView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startBtn = view.findViewById(R.id.startBtn)
        resetBtn = view.findViewById(R.id.resetBtn)
        saveBtn = view.findViewById(R.id.saveBtn)
        timer = view.findViewById(R.id.timer)

        recordBtn1 = view.findViewById(R.id.recordBtn1)
        recordBtn2 = view.findViewById(R.id.recordBtn2)
        saveRecordButton = view.findViewById(R.id.recordBtn3)



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
            resetBtn.isEnabled = true
            timer.stop()
            recordTime = SystemClock.elapsedRealtime() - timer.base

        }

        recordBtn1.setOnClickListener {
            showDialogInfo()
        }

        recordBtn2.setOnClickListener {
            showRecordDialog()
        }

        saveRecordButton.setOnClickListener {
            val totalSec = recordTime / 1000
            val sec = totalSec % 60
            val min = totalSec / 60
            var record = Record(min.toInt(), sec.toInt(), temp.text.toString().toDouble(),
                decomp.text.toString().toDouble(), weight.text.toString().toDouble(), bean.text.toString(),
                LocalDate.now(), title.text.toString(), rating.rating.toInt(), comment.text.toString())

            viewModel.insert(record)
        }

    }

    private fun showDialogInfo() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.fragment_dialog_info, null)
        dialogBuilder.setView(dialogView)
        val dialog = dialogBuilder.create()
        dialog.show()

        val saveButton = dialog.findViewById<Button>(R.id.di_saveBtn)
        val cancelButton = dialog.findViewById<Button>(R.id.di_cancelBtn)

        saveButton.setOnClickListener {
            temp = dialog.findViewById(R.id.et_temp)
            decomp = dialog.findViewById(R.id.et_decomp)
            weight = dialog.findViewById(R.id.et_weight)
            bean = dialog.findViewById(R.id.et_bean)
            dialog.cancel()
        }

        cancelButton.setOnClickListener {
            dialog.cancel()
        }
    }

    private fun showRecordDialog(){
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_record, null)
        dialogBuilder.setView(dialogView)
        val dialog = dialogBuilder.create()
        dialog.show()

        val saveButton = dialog.findViewById<Button>(R.id.record_saveBtn)
        val cancelButton = dialog.findViewById<Button>(R.id.record_cancelBtn)

        saveButton.setOnClickListener {
            title = dialog.findViewById(R.id.et_title)
            rating = dialog.findViewById(R.id.et_rating)
            comment = dialog.findViewById(R.id.et_comment)
            dialog.cancel()
        }

        cancelButton.setOnClickListener {
            dialog.cancel()
        }
    }
}