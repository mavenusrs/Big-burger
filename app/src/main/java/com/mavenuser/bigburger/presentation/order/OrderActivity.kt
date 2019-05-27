package com.mavenuser.bigburger.presentation.order

import android.os.Bundle
import android.view.View
import androidx.databinding.BindingAdapter
import com.mavenuser.bigburger.databinding.ActivityOrderBinding
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mavenuser.bigburger.R
import com.mavenuser.bigburger.model.OrderSerializable
import com.mavenuser.bigburger.presentation.BaseActivity

import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.fragment_burger_list.view.*
import javax.inject.Inject

const val ORER_EXTRA = "ORER_EXTRA"

class OrderActivity : BaseActivity() {

    @Inject
    lateinit var orderViewModel: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenComponent.inject(this)


        val activityOrderBinding : ActivityOrderBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_order)

        initSwipeToRefresh()


        activityOrderBinding.orderViewModel = orderViewModel

        orderViewModel.orderSerializable = intent.getSerializableExtra(ORER_EXTRA) as OrderSerializable
        orderViewModel.bind()


    }

    private fun initSwipeToRefresh() {
        bl_swipeRefreshLayout.setOnRefreshListener { orderViewModel.bind() }
    }

    override fun onDestroy() {
        super.onDestroy()

        orderViewModel.unbind()
    }


    companion object {

        @JvmStatic
        @BindingAdapter("adapter")
        fun bindAdapter(recyclerView: RecyclerView, orderViewModel: OrderViewModel){

            val layoutManager = LinearLayoutManager(recyclerView.context)

            val adapter = OrderAdapter(orderViewModel.burgerList)

            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
        }
    }
}
