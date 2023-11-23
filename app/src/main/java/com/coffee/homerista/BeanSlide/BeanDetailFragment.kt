package com.coffee.homerista.BeanSlide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import com.coffee.homerista.R
import com.coffee.homerista.data.entities.Bean

private const val BEAN = "bean"

class BeanDetailFragment : Fragment() {
    private lateinit var bean: Bean
    private val viewModel: BeanViewModel by activityViewModels {BeanViewModel.Factory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bean = it.getSerializable(BEAN) as Bean
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bean_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //뒤로가기 버튼
        val backButton: AppCompatButton = view.findViewById(R.id.beanDetailBackButton)
        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        val beanName: TextView = view.findViewById(R.id.beanDetailName)
        val beanCountries: TextView = view.findViewById(R.id.beanDetailCountries)
        val beanProcessing: TextView = view.findViewById(R.id.beanDetailProcessing)
        val beanBody: SeekBar = view.findViewById(R.id.beanDetailBody)
        val beanSweetSalty: SeekBar = view.findViewById(R.id.beanDetailSweetSalty)
        val beanBitterSour: SeekBar = view.findViewById(R.id.beanDetailBitterSour)
        val beanDarkLight: SeekBar = view.findViewById(R.id.beanDetailDarkLight)
        val beanCupNote: TextView = view.findViewById(R.id.beanDetailCupNote)

        beanName.text = bean.name
        beanCountries.text = bean.countries
        beanProcessing.text = bean.processing
        beanBody.progress = bean.body
        beanSweetSalty.progress = bean.sweetSalty
        beanBitterSour.progress = bean.bitterSour
        beanDarkLight.progress = bean.darkLight
        beanCupNote.text = bean.cupNote

    }

    companion object {
        @JvmStatic
        fun newInstance(bean: Bean) =
            BeanDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BEAN, bean)
                }
            }
    }
}