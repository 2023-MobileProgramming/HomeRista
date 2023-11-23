package com.coffee.homerista.BeanSlide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.coffee.homerista.R
import com.coffee.homerista.data.entities.Bean

/**
 * A simple [Fragment] subclass.
 * Use the [BeanSlidePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BeanSlidePageFragment(val bean: Bean) : Fragment() {
    private val viewModel: BeanSlideViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_bean_slide_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val beanName: TextView = view.findViewById(R.id.beanName)
        val beanCountries: TextView = view.findViewById(R.id.beanCountries)
        val beanBody: SeekBar = view.findViewById(R.id.beanBody)
        val beanSweetSalty: SeekBar = view.findViewById(R.id.beanSweetSalty)
        val beanBitterSour: SeekBar = view.findViewById(R.id.beanBitterSour)
        val beanDarkLight: SeekBar = view.findViewById(R.id.beanDarkLight)

        // ViewModel에서 데이터 가져오기
        beanName.text = bean.name
        beanCountries.text = bean.countries

        beanBody.progress = bean.body
        beanSweetSalty.progress = bean.sweetSalty
        beanBitterSour.progress = bean.bitterSour
        beanDarkLight.progress = bean.darkLight
    }
}