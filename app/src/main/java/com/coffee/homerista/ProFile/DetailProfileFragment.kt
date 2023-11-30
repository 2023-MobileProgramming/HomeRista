package com.coffee.homerista.ProFile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.coffee.homerista.R

/**
 * A simple [Fragment] subclass.
 * Use the [DetailProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_profile, container, false)
    }
}