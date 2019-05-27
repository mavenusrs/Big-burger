package com.mavenuser.bigburger.presentation.burgerList

import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.mavenuser.bigburger.R
import com.mavenuser.bigburger.router.Router
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_burger_list.view.*
import java.io.IOException
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment
 */

val BURGER_LIST_FRAGMENT_TAG = BurgerListFragment::class.java.name

class BurgerListFragment : androidx.fragment.app.Fragment() {

    val compositeDisposable = CompositeDisposable()

    @Inject lateinit var burgerListViewModel: BurgerListViewModel

    @Inject lateinit var router: Router

    override fun onCreateView(inflater: LayoutInflater
                              , container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {


        val fragmentListBinging :com.mavenuser.bigburger.databinding.FragmentBurgerListBinding
                =DataBindingUtil.inflate(inflater, R.layout.fragment_burger_list, container, false)

        val view = fragmentListBinging.root

        initSwipeToRefresh(view)

        fragmentListBinging.burgerViewModel = burgerListViewModel

        burgerListViewModel.bind()

        return view

    }

    override fun onResume() {
        super.onResume()

        //TODO unhandled front-end message, till gother all errors code and return suitable message
        burgerListViewModel.errorPublishSubject.subscribe {

            Log.d(BURGER_LIST_FRAGMENT_TAG, it.throwable.message!!)

            when(it.throwable){
                is IOException ->
                    Toast.makeText(context, getString(R.string.connectionError), Toast.LENGTH_LONG).show()
                else -> {
                    Toast.makeText(context, it.throwable.message!!, Toast.LENGTH_LONG).show()
                }

            }

        }.addTo(compositeDisposable)
    }
    private fun initSwipeToRefresh(view: View) {

        view.bl_swipeRefreshLayout.setOnRefreshListener { burgerListViewModel.bind() }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as MainActivity).screenComponent.inject(this)


    }

    override fun onDestroy() {
        super.onDestroy()

        burgerListViewModel.unBound()

        compositeDisposable.clear()
    }

    companion object {

        @JvmStatic
        @BindingAdapter("adapter")
        fun bindList(recyclerView: androidx.recyclerview.widget.RecyclerView, burgerListViewModel: BurgerListViewModel){
            val gridLayoutManager = androidx.recyclerview.widget.GridLayoutManager(recyclerView.context, 3)
            recyclerView.apply {
                layoutManager = gridLayoutManager

                val burgerListRecyclerAdapter = BurgerListRecyclerAdapter(burgerListViewModel.burgerListObservableList)

                burgerListRecyclerAdapter.onClickListener = {burgerListViewModel.onItemClick(it)}

                adapter = burgerListRecyclerAdapter

            }

        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment BurgerListFragment.
         */
        @JvmStatic
        fun newInstance() = BurgerListFragment()



    }
}
