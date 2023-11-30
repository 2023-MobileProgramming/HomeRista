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

/**
 * A simple [Fragment] subclass.
 * Use the [DetailProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailProfileFragment(private val record: Record) : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var min : TextView
    private lateinit var sec: TextView
    private lateinit var temp: TextView
    private lateinit var decomp: TextView
    private lateinit var weight: TextView
    private lateinit var beanName: TextView
    //private lateinit var date: TextView
    private lateinit var title: TextView
    private lateinit var rating: TextView
    private lateinit var comment: TextView

    private lateinit var modifyBtn: Button
    private lateinit var trashBtn: Button
    var isEditable = false

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
        //date = view.findViewById(R.id.)
        title = view.findViewById(R.id.title_tv)
        rating = view.findViewById(R.id.rating_tv)
        comment = view.findViewById(R.id.comment_tv)

        val textViews: MutableList<TextView> = mutableListOf()
        val editTexts: MutableList<EditText> = mutableListOf()
        textViews.add(min)
        textViews.add(sec)
        textViews.add(temp)
        textViews.add(decomp)
        textViews.add(weight)
        textViews.add(beanName)
        textViews.add(title)
        textViews.add(rating)
        textViews.add(comment)

        modifyBtn = view.findViewById(R.id.modifyBtn)
        trashBtn = view.findViewById(R.id.trashBtn)

        bindProfileData()
        modifyBtn.setOnClickListener {
            modifyProfile(textViews, editTexts)
        }

    }

    private fun modifyProfile(textViews: MutableList<TextView>, editTexts: MutableList<EditText>) {
        isEditable = !isEditable
        var textArray = arrayOf<String>()
        var count : Int = 0

        if (isEditable) {
            for (textView in textViews) {
                val editedText = textView.text.toString()
                textArray[count] = editedText
                count++
                val editText = EditText(context)
                editText.setText(editedText)
                editText.id = textView.id
                editText.isEnabled = true
                editText.requestFocus()
                val params = textView.layoutParams
                val index = (textView.parent as ViewGroup).indexOfChild(textView)
                (textView.parent as ViewGroup).removeView(textView)
                (textView.parent as ViewGroup).addView(editText, index, params)
            }
            modifyBtn.setBackgroundResource(R.drawable.check)
        } else {
            for (textView in textViews) {
                val editedText = textArray[count]
                count++
                textView.text = editedText
                textView.isEnabled = false
                (textView.parent as ViewGroup).removeView(textView)
                (textView.parent as ViewGroup).addView(textView)
            }
            modifyBtn.setBackgroundResource(R.drawable.modify)
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
}