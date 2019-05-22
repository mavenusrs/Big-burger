package com.mavenuser.bigburger.presentation.burgerList

import android.arch.lifecycle.Observer
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.mavenuser.bigburger.R
import com.mavenuser.bigburger.domain.usecases.BurgerListState
import kotlinx.android.synthetic.main.fragment_burger_list.*
import kotlinx.android.synthetic.main.fragment_burger_list.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment
 */

val BURGER_LIST_FRAGMENT_TAG = BurgerListFragment::class.java.name

class BurgerListFragment : Fragment() {



    @Inject lateinit var burgerListViewModel: BurgerListViewModel




    override fun onCreateView(inflater: LayoutInflater
                              , container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {


        val fragmentListBinging :com.mavenuser.bigburger.databinding.FragmentBurgerListBinding
                =DataBindingUtil.inflate(inflater, R.layout.fragment_burger_list, container, false)

        val view = fragmentListBinging.root

        initToolbar(view)

        initSwipeToRefresh(view)

        fragmentListBinging.burgerViewModel = burgerListViewModel

        burgerListViewModel.bind()

        return view

    }

    private fun initSwipeToRefresh(view: View) {

        view.bl_swipeRefreshLayout.setOnRefreshListener { burgerListViewModel.bind() }
    }


    private fun initToolbar(view: View) {
        view.bl_toolbar.title = getString(R.string.burger_list_title)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as MainActivity).screenComponent.inject(this)


    }

    override fun onDestroy() {
        super.onDestroy()

        burgerListViewModel.unBound()
    }

    companion object {

        @JvmStatic
        @BindingAdapter("adapter")
        fun bindList(recyclerView: RecyclerView, burgerListViewModel: BurgerListViewModel){
            val linearLayoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.apply {
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                layoutManager = linearLayoutManager


                val burgerListRecyclerAdapter = BurgerListRecyclerAdapter(burgerListViewModel.burgerListObservableList)
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
