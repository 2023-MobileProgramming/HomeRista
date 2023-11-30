package com.coffee.homerista.ProFile

import android.os.Bundle
import android.text.Editable
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
        title = view.findViewById(R.id.title_tv)
        rating = view.findViewById(R.id.rating_tv)
        comment = view.findViewById(R.id.comment_tv)

        modifyBtn = view.findViewById(R.id.modifyBtn)
        trashBtn = view.findViewById(R.id.trashBtn)

        bindProfileData()

        val viewsToConvert = listOf(min, sec, temp, decomp, weight, beanName, title, rating, comment)
        var isEditable = false


        modifyBtn.setOnClickListener {
            isEditable =! isEditable

            if(isEditable) {
                viewsToConvert.forEach { view ->
                    if (view is TextView) {
                        val editText = EditText(context)
                        editText.textSize = view.textSize
                        editText.hint = view.text
                        editText.setText(view.text)
                        editText.layoutParams = view.layoutParams
                        editText.id = view.id

                        val parentLayout = view.parent as ViewGroup
                        val index = parentLayout.indexOfChild(view)
                        parentLayout.removeView(view)
                        parentLayout.addView(editText, index)
                    }
                }
                modifyBtn.setBackgroundResource(R.drawable.check)
            } else {
                viewsToConvert.forEach { view ->
                    if (view is EditText) {
                        val textView = TextView(context)
                        textView.textSize = view.textSize
                        textView.text = view.text
                        textView.layoutParams = view.layoutParams
                        textView.id = view.id

                        val parentLayout = view.parent as ViewGroup
                        val index = parentLayout.indexOfChild(view)
                        parentLayout.removeView(view)
                        parentLayout.addView(textView, index)
                    }
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
}