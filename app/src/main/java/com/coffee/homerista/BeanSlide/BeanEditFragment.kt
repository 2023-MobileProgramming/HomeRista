package com.coffee.homerista.BeanSlide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import com.coffee.homerista.R
import com.coffee.homerista.data.entities.Bean

private const val BEAN = "bean"

class BeanEditFragment : Fragment() {
    private var bean: Bean? = null
    private val viewModel: BeanViewModel by activityViewModels {BeanViewModel.Factory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.fragment_bean_edit)
        arguments?.let {
            bean = it.getSerializable(BEAN) as Bean
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bean_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val beanName: TextView = view.findViewById(R.id.beanEditName)

        val beanCountries: AutoCompleteTextView = view.findViewById(R.id.beanEditCountries)

        val beanProcessing: RadioGroup = view.findViewById(R.id.beanRadioGroup)
        val beanNatural: RadioButton = view.findViewById(R.id.radio_natural)
        val beanWashed: RadioButton = view.findViewById(R.id.radio_washed)
        val beanBlending: RadioButton = view.findViewById(R.id.radio_blending)
        val beanBody: SeekBar = view.findViewById(R.id.beanEditBody)
        val beanSweetSalty: SeekBar = view.findViewById(R.id.beanEditSweetSalty)
        val beanBitterSour: SeekBar = view.findViewById(R.id.beanEditBitterSour)
        val beanDarkLight: SeekBar = view.findViewById(R.id.beanEditDarkLight)
        val beanCupNote: TextView = view.findViewById(R.id.beanEditCupNote)


        val countriesArray = arrayOf("브라질","베트남","콜롬비아","인도네시아","에티오피아","온두라스","인도","페루","과테말라","우간다","멕시코","라오스","중국","니카라과","코르티부아르","코스타리카","필리핀","파푸아뉴기나","탄자니아","마다가스카르","케냐","베네수엘라","아이티","엘살바도르","타이","카메룬","콩고민주공화국","대한민국","미국","일본","가나")
        val adapter = ArrayAdapter(requireContext(), R.layout.custom_fragment_bean_edit, countriesArray)

        beanCountries.setAdapter(adapter)

        if(bean != null) {
            beanName.text = bean!!.name
            beanCountries.setText(bean!!.countries, false)
            beanBody.progress = bean!!.body
            beanSweetSalty.progress = bean!!.sweetSalty
            beanBitterSour.progress = bean!!.bitterSour
            beanDarkLight.progress = bean!!.darkLight
            beanCupNote.text = bean!!.cupNote

            when (bean!!.processing) {
                "워시드" -> beanProcessing.check(beanWashed.id)
                "내추럴" -> beanProcessing.check(beanNatural.id)
                "블렌딩" -> beanProcessing.check(beanBlending.id)
            }

            //

            beanCountries.setText(bean!!.countries, false)
        }

        //취소버튼
        val cancelButton: AppCompatButton = view.findViewById(R.id.beanEditCancelButton)
        cancelButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        //완료 버튼
        val confirmButton: AppCompatButton = view.findViewById(R.id.beanEditConfirmButton)
        confirmButton.setOnClickListener {
            //processing gruop -> text 로 변환
            val processing: String = when(beanProcessing.checkedRadioButtonId) {
                R.id.radio_washed -> "워시드"
                R.id.radio_natural -> "내추럴"
                else -> "블렌딩"
            }

            //bean 수정 및 저장
            if (bean != null) {
                bean?.name = beanName.text.toString()
                bean?.countries = beanCountries.text.toString()
                bean?.processing = processing
                bean?.cupNote = beanCupNote.text.toString()
                bean?.body = beanBody.progress
                bean?.sweetSalty = beanSweetSalty.progress
                bean?.bitterSour = beanBitterSour.progress
                bean?.darkLight = beanDarkLight.progress

                viewModel.update(bean!!)
            } else {
                viewModel.insert(Bean(beanName.text.toString(),
                    beanCountries.text.toString(),
                    processing, beanCupNote.text.toString(),
                    beanBody.progress, beanSweetSalty.progress,
                    beanBitterSour.progress, beanDarkLight.progress))
            }
           // beanCountries.setAdapter(adapter)

            //이전 화면으로 돌아가기
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(bean: Bean) =
            BeanEditFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BEAN, bean)
                }
            }

        @JvmStatic
        fun newInstance() = BeanEditFragment()
    }
}