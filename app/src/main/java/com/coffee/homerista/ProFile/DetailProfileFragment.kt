package com.coffee.homerista.ProFile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.coffee.homerista.R
import com.coffee.homerista.data.entities.Record

private const val RECORD = "record"
class DetailProfileFragment() : Fragment() {
    private lateinit var record: Record

    private lateinit var min : TextView
    private lateinit var sec: TextView
    private lateinit var temp: TextView
    private lateinit var decomp: TextView
    private lateinit var weight: TextView
    private lateinit var beanName: TextView
    private lateinit var minEt :  EditText
    private lateinit var secEt:  EditText
    private lateinit var tempEt:  EditText
    private lateinit var decompEt:  EditText
    private lateinit var weightEt:  EditText
    private lateinit var beanNameEt:  EditText
    //private lateinit var date: TextView
    private lateinit var title: TextView
    private lateinit var rating: TextView
    private lateinit var comment: TextView
    private lateinit var titleEt: EditText
    private lateinit var ratingEt: EditText
    private lateinit var commentEt: EditText

    private lateinit var modifyBtn: Button
    private lateinit var trashBtn: Button
    var isEditable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            record = it.getSerializable(RECORD) as Record
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        min = view.findViewById(R.id.min)
        sec = view.findViewById(R.id.second)
        temp = view.findViewById(R.id.temp)
        decomp = view.findViewById(R.id.decomp)
        weight = view.findViewById(R.id.weight)
        beanName = view.findViewById(R.id.bean)
        minEt = view.findViewById(R.id.minEt)
        secEt = view.findViewById(R.id.secondEt)
        tempEt = view.findViewById(R.id.tempEt)
        decompEt = view.findViewById(R.id.decompEt)
        weightEt = view.findViewById(R.id.weightEt)
        beanNameEt = view.findViewById(R.id.beanEt)

        title = view.findViewById(R.id.title_tv)
        rating = view.findViewById(R.id.rating_tv)
        comment = view.findViewById(R.id.comment_tv)
        titleEt = view.findViewById(R.id.title_et)
        ratingEt = view.findViewById(R.id.rating_et)
        commentEt = view.findViewById(R.id.comment_et)

        modifyBtn = view.findViewById(R.id.modifyBtn)
        trashBtn = view.findViewById(R.id.trashBtn)

        bindProfileData()

        val textToEdit = listOf(min, sec, temp, decomp, weight, beanName, title, rating, comment)
        val editToText = listOf(minEt, secEt, tempEt, decompEt, weightEt, beanNameEt, titleEt, ratingEt, commentEt)
        var stringArr = arrayOfNulls<String>(9)
        var isEditable = false


        modifyBtn.setOnClickListener {
            isEditable =! isEditable

            if(isEditable) {
                var count : Int = 0
                textToEdit.forEach { view ->
                    view.visibility = View.INVISIBLE
                    stringArr[count]= view.text.toString()
                    count++
                }
                count = 0
                editToText.forEach { view ->
                    view.hint = stringArr[count]
                    count++
                    view.visibility = View.VISIBLE
                }
                modifyBtn.setBackgroundResource(R.drawable.check)
            } else {
                var count : Int = 0
                editToText.forEach { view ->
                    stringArr[count]= view.text.toString()
                    count++
                    view.visibility = View.INVISIBLE
                }
                count = 0
                textToEdit.forEach {view ->
                    view.text = stringArr[count]
                    count++
                    view.visibility = View.VISIBLE
                }
                modifyBtn.setBackgroundResource(R.drawable.modify)
            }

        }

    }


    private fun bindProfileData() {
        min.text = record.min.toString()
        sec.text = record.sec.toString()
        temp.text = record.temp.toString()
        decomp.text = record.decomp.toString()
        weight.text = record.weight.toString()
        beanName.text = record.beanName
        // date.text = record.temp.toString()
        title.text = record.title
        rating.text = record.rating.toString()
        comment.text = record.comment
    }

    companion object {
        @JvmStatic
        fun newInstance(record: Record) =
            DetailProfileFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(RECORD, record)
                }
            }
    }
}