package com.mavenuser.bigburger.presentation.order

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.mavenuser.bigburger.databinding.ActivityOrderBinding
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mavenuser.bigburger.R
import com.mavenuser.bigburger.model.OrderSerializable
import com.mavenuser.bigburger.presentation.BaseActivity
import com.mavenuser.bigburger.presentation.burgerList.BURGER_LIST_FRAGMENT_TAG
import com.mavenuser.bigburger.router.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

import kotlinx.android.synthetic.main.activity_order.*
import java.io.IOException
import javax.inject.Inject

const val ORER_EXTRA = "ORER_EXTRA"

class OrderActivity : BaseActivity() {

    @Inject lateinit var router: Router

    @Inject
    lateinit var orderViewModel: OrderViewModel

    private val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenComponent.inject(this)

        initDatabinding()

        initSwipeToRefresh()

        initToolbar()


        orderViewModel.bind()
    }

    private fun initDatabinding() {
        val activityOrderBinding: ActivityOrderBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_order)


        activityOrderBinding.orderViewModel = orderViewModel

        intent.getSerializableExtra(ORER_EXTRA).apply {
            orderViewModel.orderSerializable = this as OrderSerializable
        }
    }

    private fun initToolbar() {

        setSupportActionBar(bl_toolbar)


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.order_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.apply {
            if (itemId == R.id.delete_order) {
                orderViewModel.deleteCurrentOrder()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initSwipeToRefresh() {
        bl_swipeRefreshLayout.setOnRefreshListener { orderViewModel.bind() }
    }

    override fun onResume() {
        super.onResume()
        orderViewModel.errorSubjectPublish
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            Log.d(BURGER_LIST_FRAGMENT_TAG, it.throwable.message!!)
            it?.throwable?.let { it1 ->
                onFailToDeleteOrder(it1) }

        }.addTo(compositeDisposable)

        orderViewModel.deleteSubjectPublish
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            it?.let {
                if (it){
                    onDeleteOrderDoneSuccessfully()
                }else{
                    onFailToDeleteOrder(Throwable(getString(R.string.delete_order_error)))
                }
            }

        }.addTo(compositeDisposable)


    }
    override fun onDestroy() {
        super.onDestroy()

        orderViewModel.unbind()
    }

    fun onDeleteOrderDoneSuccessfully(){
        Toast.makeText(this, getString(R.string.order_deleted), Toast.LENGTH_LONG).show()

        router.finishCurrentActivity()
        router.navigate(Router.ROUTE.BURGER_LIST, Bundle())
    }

    fun onFailToDeleteOrder(throwable: Throwable){
        when(throwable){
            is IOException ->
                Toast.makeText(this, getString(com.mavenuser.bigburger.R.string.connectionError), Toast.LENGTH_LONG).show()
            else -> {
                Toast.makeText(this, throwable.message!!, Toast.LENGTH_LONG).show()
            }

        }
    }


    companion object {

        @JvmStatic
        @BindingAdapter("adapter")
        fun bindAdapter(recyclerView: RecyclerView, orderViewModel: OrderViewModel){

            val layoutManager = LinearLayoutManager(recyclerView.context)

            val adapter = OrderAdapter(orderViewModel.burgerList)

            adapter.onBurgerItemClickListener = {orderViewModel.onBurgerItemClick(it)}
            adapter.onDeleteItemClickListener = {orderViewModel.deleteItem(it)}

            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter

            val swipeTodeleteCallback = object : SwipeTodeleteCallback(recyclerView.context){
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition

                    orderViewModel.deleteItem(position)
                }
            }

            val itemTouchHelper = ItemTouchHelper(swipeTodeleteCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)
        }
    }
}
