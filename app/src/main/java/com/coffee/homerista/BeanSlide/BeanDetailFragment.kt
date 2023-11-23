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

    lateinit var beanName: TextView
    lateinit var beanCountries: TextView
    lateinit var beanProcessing: TextView
    lateinit var beanBody: SeekBar
    lateinit var beanSweetSalty: SeekBar
    lateinit var beanBitterSour: SeekBar
    lateinit var beanDarkLight: SeekBar
    lateinit var beanCupNote: TextView

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

        //값이 수정되면 변한 값으로 보여주기 위한 observe 등록
        viewModel.dataList.observe(viewLifecycleOwner) { dataList ->
            bean = dataList.find { bean -> bean.id == this.bean.id } ?: bean
            bindData()
        }

        //뒤로가기 버튼
        val backButton: AppCompatButton = view.findViewById(R.id.beanDetailBackButton)
        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        beanName = view.findViewById(R.id.beanDetailName)
        beanCountries = view.findViewById(R.id.beanDetailCountries)
        beanProcessing = view.findViewById(R.id.beanDetailProcessing)
        beanBody = view.findViewById(R.id.beanDetailBody)
        beanSweetSalty = view.findViewById(R.id.beanDetailSweetSalty)
        beanBitterSour= view.findViewById(R.id.beanDetailBitterSour)
        beanDarkLight = view.findViewById(R.id.beanDetailDarkLight)
        beanCupNote = view.findViewById(R.id.beanDetailCupNote)

        bindData()

        val editButton: AppCompatButton = view.findViewById(R.id.beanDetailEditButton)
        val removeButton: AppCompatButton = view.findViewById(R.id.beanDetailRemoveButton)

        editButton.setOnClickListener {
            val beanEditFragment = BeanEditFragment.newInstance(bean)

            // Fragment를 추가하고 트랜잭션을 커밋
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fullScreen, beanEditFragment)
                .addToBackStack(null) // Back 버튼으로 돌아올 수 있도록 백 스택에 추가
                .commit()
        }

        removeButton.setOnClickListener {
            viewModel.delete(bean)
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

    // LiveData가 변경될 때 호출되는 UI 업데이트 메서드
    private fun bindData() {
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