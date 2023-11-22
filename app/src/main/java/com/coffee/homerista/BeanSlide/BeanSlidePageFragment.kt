package com.coffee.homerista.BeanSlide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
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

        val content: ConstraintLayout = view.findViewById(R.id.beanPage)

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