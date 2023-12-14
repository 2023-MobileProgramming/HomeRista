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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.text.isDigitsOnly
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

            if(!::temp.isInitialized || !::title.isInitialized || temp.text.isNullOrEmpty() || title.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "추출 정보랑 후기를 작성해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 람다 함수 종료
            }

            val record = Record(min.toInt(), sec.toInt(), temp.text.toString().toDouble(),
                decomp.text.toString().toDouble(), weight.text.toString().toDouble(), bean.text.toString(),
                LocalDate.now(), title.text.toString(), rating.rating.toInt(), comment.text.toString())

            viewModel.insert(record)

            val now = LocalDate.now()

            val profileSlideFragment = ProfileSlideFragment.newInstance(now.year, now.monthValue, now.dayOfMonth)

            // Fragment를 추가하고 트랜잭션을 커밋
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fl, profileSlideFragment)
                .addToBackStack(null) // Back 버튼으로 돌아올 수 있도록 백 스택에 추가
                .commit()
        }

    }

    private fun showDialogInfo() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.fragment_info, null)
        dialogBuilder.setView(dialogView)
        val dialog = dialogBuilder.create()
        dialog.show()

        if(::temp.isInitialized) {
            val tmpTemp = temp.text.toString()
            val tmpDecomp = decomp.text.toString()
            val tmpWeight = weight.text.toString()
            val tmpBean = bean.text.toString()

            temp = dialog.findViewById(R.id.et_temp)
            temp.setText(tmpTemp)
            decomp = dialog.findViewById(R.id.et_decomp)
            decomp.setText(tmpDecomp)
            weight = dialog.findViewById(R.id.et_weight)
            weight.setText(tmpWeight)
            bean = dialog.findViewById(R.id.et_bean)
            bean.setText(tmpBean)

        } else {
            temp = dialog.findViewById(R.id.et_temp)
            decomp = dialog.findViewById(R.id.et_decomp)
            weight = dialog.findViewById(R.id.et_weight)
            bean = dialog.findViewById(R.id.et_bean)
        }

        val saveButton = dialog.findViewById<Button>(R.id.di_saveBtn)
        val cancelButton = dialog.findViewById<Button>(R.id.di_cancelBtn)

        saveButton.setOnClickListener {
            if(temp.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "온도를 입력해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 람다 함수 종료
            }
            if(!temp.text.isDigitsOnly()) {
                Toast.makeText(requireContext(), "온도는 숫자만 입력해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 람다 함수 종료
            }

            if(decomp.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "분쇄도를 입력해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 람다 함수 종료
            }
            if(!decomp.text.isDigitsOnly()) {
                Toast.makeText(requireContext(), "분쇄도는 숫자만 입력해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 람다 함수 종료
            }

            if(weight.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "무개를 입력해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 람다 함수 종료
            }
            if(!weight.text.isDigitsOnly()) {
                Toast.makeText(requireContext(), "무개는 숫자만 입력해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 람다 함수 종료
            }

            if(bean.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "원두이름을 입력해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 람다 함수 종료
            }

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

        if(::title.isInitialized) {
            val tmpTitle = title.text.toString()
            val tmpRating = rating.rating
            val tmpComment = comment.text.toString()

            title = dialog.findViewById(R.id.et_title)
            title.setText(tmpTitle)
            rating = dialog.findViewById(R.id.et_rating)
            rating.rating = tmpRating
            comment = dialog.findViewById(R.id.et_comment)
            comment.setText(tmpComment)

        } else {
            title = dialog.findViewById(R.id.et_title)
            rating = dialog.findViewById(R.id.et_rating)
            comment = dialog.findViewById(R.id.et_comment)
        }

        saveButton.setOnClickListener {

            if(title.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "제목을 입력해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 람다 함수 종료
            }

            if(comment.text.isNullOrEmpty()) {
                println("isNull!")
                comment.setText("")
            }

            dialog.cancel()
        }

        cancelButton.setOnClickListener {
            dialog.cancel()
        }
    }
}