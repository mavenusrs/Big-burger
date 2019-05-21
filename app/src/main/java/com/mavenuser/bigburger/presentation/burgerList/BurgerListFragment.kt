package com.mavenuser.bigburger.presentation.burgerList

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.mavenuser.bigburger.R
import kotlinx.android.synthetic.main.fragment_burger_list.*
import kotlinx.android.synthetic.main.fragment_burger_list.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment
 */

val BURGER_LIST_FRAGMENT_TAG = BurgerListFragment::class.java.name

class BurgerListFragment : Fragment() {


    private var isLoading: Boolean = true

    @Inject lateinit var burgerListViewModel: BurgerListViewModel

    private val burgerListRecyclerAdapter by lazy {
        BurgerListRecyclerAdapter<List<BurgerListState>>()
    }

    private val stateObserver = Observer<BurgerListState> { state ->
        state?.let {
            when (state) {
                is BurgerListState.DefaultState -> {
                    bl_swipeRefreshLayout.isRefreshing = false
                    burgerListRecyclerAdapter.updateDataList(state.data)
                    isLoading = false

                }

                is BurgerListState.ErrorState -> {
                    bl_swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(context, state.throwable.message, Toast.LENGTH_LONG).show()
                    isLoading = false
                }

                is BurgerListState.LoadingState -> {
                    bl_swipeRefreshLayout.isRefreshing = true
                    isLoading = true

                }
            }
        }

    }


    override fun onCreateView(inflater: LayoutInflater
                              , container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_burger_list, container, false)

        initToolbar(view)
        initRecyclerView(view)
        initSwipeToRefresh(view)

        return view

    }

    private fun initSwipeToRefresh(view: View) {

        view.bl_swipeRefreshLayout.setOnRefreshListener { burgerListViewModel.resetBurgerList() }
    }

    private fun initRecyclerView(view: View) {
        val linearLayoutManager = LinearLayoutManager(context)
        view.bl_recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = burgerListRecyclerAdapter
        }

    }

    private fun initToolbar(view: View) {
        view.bl_toolbar.title = getString(R.string.burger_list_title)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity!! as MainActivity).screenComponent.inject(this)


        observeViewModel()

        savedInstanceState?.let {
            burgerListViewModel.resetBurgerList()
        }?:burgerListViewModel.loadList()
    }

    private fun observeViewModel() {

        burgerListViewModel.stateLiveData.observe(this, stateObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        burgerListViewModel.stateLiveData.removeObserver ( stateObserver)

    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment BurgerListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = BurgerListFragment()
    }
}
