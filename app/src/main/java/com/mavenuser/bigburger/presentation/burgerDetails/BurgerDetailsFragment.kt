package com.mavenuser.bigburger.presentation.burgerDetails

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mavenuser.bigburger.R
import com.mavenuser.bigburger.model.BurgerSerializable


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val BURGER_ITEM_EXTRA = "param1"
const val OPEN_TO_PAY = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BurgerDetailsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BurgerDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class BurgerDetailsFragment : Fragment() {
    private var burgerSerializable: BurgerSerializable? = null
    private var openToPay: Boolean? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            burgerSerializable = it.getSerializable(BURGER_ITEM_EXTRA) as BurgerSerializable?
            openToPay = it.getBoolean(OPEN_TO_PAY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_burger_details, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BurgerDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: BurgerSerializable, param2: Boolean) =
            BurgerDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BURGER_ITEM_EXTRA, param1)
                    putBoolean(OPEN_TO_PAY, param2)
                }
            }
    }
}
