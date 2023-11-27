package com.coffee.homerista.BeanSlide

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
private const val BEAN = "bean"

class BeanSlidePageFragment() : Fragment() {
    private lateinit var bean: Bean
    private val viewModel: BeanViewModel by activityViewModels {BeanViewModel.Factory}
    private lateinit var beanName: TextView
    private lateinit var beanCountries: TextView
    private lateinit var beanBody: SeekBar
    private lateinit var beanSweetSalty: SeekBar
    private lateinit var beanBitterSour: SeekBar
    private lateinit var beanDarkLight: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bean = it.getSerializable(BEAN) as Bean
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_bean_slide_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("page", "페이지 재생성 ${bean}")

        //값이 수정되면 변한 값으로 보여주기 위한 observe 등록
        viewModel.dataList.observe(viewLifecycleOwner) { dataList ->
            bean = dataList.find { bean -> bean.id == this.bean.id } ?: bean
            bindData()
        }

        val content: ConstraintLayout = view.findViewById(R.id.beanPage)

        beanName = view.findViewById(R.id.beanName)
        beanCountries = view.findViewById(R.id.beanCountries)
        beanBody = view.findViewById(R.id.beanBody)
        beanSweetSalty = view.findViewById(R.id.beanSweetSalty)
        beanBitterSour = view.findViewById(R.id.beanBitterSour)
        beanDarkLight = view.findViewById(R.id.beanDarkLight)

        bindData()

        //클릭시 상세페이지로 이동
        content.setOnClickListener {
            // 클릭 이벤트가 발생한 경우, 새로운 Fragment를 생성하고 추가
            val beanDetailFragment = BeanDetailFragment.newInstance(bean)

            // Fragment를 추가하고 트랜잭션을 커밋
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fullScreen, beanDetailFragment)
                .addToBackStack(null) // Back 버튼으로 돌아올 수 있도록 백 스택에 추가
                .commit()
        }
    }

    private fun bindData() {
        beanName.text = bean.name
        beanCountries.text = bean.countries

        beanBody.progress = bean.body
        beanSweetSalty.progress = bean.sweetSalty
        beanBitterSour.progress = bean.bitterSour
        beanDarkLight.progress = bean.darkLight
    }

    companion object {
        @JvmStatic
        fun newInstance(bean: Bean) =
            BeanSlidePageFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BEAN, bean)
                }
            }
    }
}