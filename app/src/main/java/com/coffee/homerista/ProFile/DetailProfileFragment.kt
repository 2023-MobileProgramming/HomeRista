package com.coffee.homerista.ProFile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.coffee.homerista.R
import com.coffee.homerista.data.entities.Record
import com.coffee.homerista.ui.viewmoel.RecordViewModel


private const val RECORD = "record"
private const val POSITION = "position"
@RequiresApi(Build.VERSION_CODES.O)
class DetailProfileFragment() : Fragment() {
    private lateinit var record: Record
    private var position = 0
    private val viewModel: RecordViewModel by activityViewModels { RecordViewModel.Factory}

    //view
    private lateinit var viewLayout: LinearLayout

    private lateinit var imageView: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var beanName: TextView
    private lateinit var comment: TextView
    private lateinit var temp: TextView
    private lateinit var decomp: TextView
    private lateinit var weight: TextView
    private lateinit var time: TextView

    private lateinit var modifyBtn: Button
    private lateinit var trashBtn: Button

    //edit
    private lateinit var editLayout: LinearLayout

    private lateinit var editImageView: ImageView
    private lateinit var editTitle: EditText
    private lateinit var editRating: RatingBar
    private lateinit var editBeanName: EditText
    private lateinit var editComment: EditText
    private lateinit var editTemp: EditText
    private lateinit var editDecomp: EditText
    private lateinit var editWeight: EditText
    private lateinit var editTime: TextView

    private lateinit var saveBtn: Button
    private lateinit var cancelBtn: Button

    private var tmpMin = 0
    private var tmpSec = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            record = it.getSerializable(RECORD) as Record
            position = it.getInt(POSITION)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        observeData()
        return inflater.inflate(R.layout.fragment_detail_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //view
        viewLayout = view.findViewById(R.id.profileView)

        imageView = view.findViewById(R.id.imageView)
        title = view.findViewById(R.id.profileTitle)
        rating = view.findViewById(R.id.profileRating)
        beanName = view.findViewById(R.id.profileBeanName)
        comment = view.findViewById(R.id.profileComment)
        temp = view.findViewById(R.id.profileTemp)
        decomp = view.findViewById(R.id.profileDecomp)
        weight = view.findViewById(R.id.profileWeight)
        time = view.findViewById(R.id.profileTime)

        modifyBtn = view.findViewById(R.id.modifyBtn)
        trashBtn = view.findViewById(R.id.trashBtn)

        changeImage(position, imageView)

        modifyBtn.setOnClickListener {
            viewLayout.visibility = View.INVISIBLE
            editLayout.visibility = View.VISIBLE
        }

        trashBtn.setOnClickListener {
            viewModel.delete(record)
        }

        //edit
        editLayout = view.findViewById(R.id.profileEdit)

        editImageView = view.findViewById(R.id.imageView_ed)
        editTitle = view.findViewById(R.id.profileEditTitle)
        editRating = view.findViewById(R.id.profileEditRating)
        editBeanName = view.findViewById(R.id.profileEditBeanName)
        editComment = view.findViewById(R.id.profileEditComment)
        editTemp = view.findViewById(R.id.profileEditTemp)
        editDecomp = view.findViewById(R.id.profileEditDecomp)
        editWeight = view.findViewById(R.id.profileEditWeight)
        editTime = view.findViewById(R.id.profileEditTime)

        changeImage(position, editImageView)

        editTime.setOnClickListener {
            showTimePickerDialog()
        }

        cancelBtn = view.findViewById(R.id.cancelBtn)
        saveBtn = view.findViewById(R.id.saveBtn)

        cancelBtn.setOnClickListener {
            bindEditData()
            viewLayout.visibility = View.VISIBLE
            editLayout.visibility = View.INVISIBLE
        }

        saveBtn.setOnClickListener {
            record.title = editTitle.text.toString()
            record.rating = editRating.rating.toInt()
            record.beanName = editBeanName.text.toString()
            record.comment = editComment.text.toString()
            record.temp = editTemp.text.toString().toDouble()
            record.decomp = editDecomp.text.toString().toDouble()
            record.weight = editWeight.text.toString().toDouble()
            record.min = tmpMin
            record.sec = tmpSec

            viewModel.update(record)

            viewLayout.visibility = View.VISIBLE
            editLayout.visibility = View.INVISIBLE

        }

        bindViewData()
        bindEditData()
    }

    private fun observeData() {
        //값이 수정되면 변한 값으로 보여주기 위한 observe 등록
        viewModel.dataByDateList.observe(viewLifecycleOwner) { dataList ->
            record = dataList.find { record -> record.id == this.record.id } ?: record
            bindViewData()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindViewData() {
        title.text = record.title
        rating.rating = record.rating.toFloat()
        beanName.text = record.beanName
        comment.text = record.comment
        temp.text = record.temp.toString()
        decomp.text = record.decomp.toString()
        weight.text = record.weight.toString()
        time.text = record.min.toString() + " : " + record.sec.toString()
    }

    private fun bindEditData() {
        editTitle.setText(record.title)
        editRating.rating = record.rating.toFloat()
        editBeanName.setText(record.beanName)
        editComment.setText(record.comment)
        editTemp.setText(record.temp.toString())
        editDecomp.setText(record.decomp.toString())
        editWeight.setText(record.weight.toString())
        editTime.text = String.format("%02d:%02d", record.min, record.sec)
    }

    private fun showTimePickerDialog() {
        val hour = record.min
        val minute = record.sec

        val timePickerDialog = TimePickerDialog(
            context,
            R.style.TimePickerTheme,
            { _, selectedMin, selectedSec ->
                val formattedTime = String.format("%02d:%02d", selectedMin, selectedSec)
                editTime.text = formattedTime
                tmpMin = selectedMin
                tmpSec = selectedSec
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
        timePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
        timePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
    }

    private fun changeImage(position: Int, imageView: ImageView) {
        when(position % 5) {
            0 -> imageView.setImageResource(R.drawable.coffee1)
            1 -> imageView.setImageResource(R.drawable.coffee2)
            2 -> imageView.setImageResource(R.drawable.coffee3)
            3 -> imageView.setImageResource(R.drawable.coffee4)
            4 -> imageView.setImageResource(R.drawable.coffee5)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(record: Record, position: Int) =
            DetailProfileFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(RECORD, record)
                    putInt(POSITION, position)
                }
            }
    }
}