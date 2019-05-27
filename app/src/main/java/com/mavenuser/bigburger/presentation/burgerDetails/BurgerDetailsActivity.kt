package com.mavenuser.bigburger.presentation.burgerDetails

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.mavenuser.bigburger.databinding.ActivityBurgerDetailsBinding
import com.mavenuser.bigburger.model.BurgerSerializable
import com.mavenuser.bigburger.presentation.BaseActivity
import com.mavenuser.bigburger.presentation.burgerList.BURGER_LIST_FRAGMENT_TAG
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_burger_details.*
import java.io.IOException
import javax.inject.Inject
import androidx.appcompat.app.AlertDialog
import com.mavenuser.bigburger.R
import com.mavenuser.bigburger.router.Router


const val BURGER_ITEM_EXTRA = "param1"

class BurgerDetailsActivity : BaseActivity() {

    private val compositeDisposable = CompositeDisposable()

    @Inject lateinit var itemDetailViewModel: ItemDetailViewModel
    @Inject lateinit var router:Router


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenComponent.inject(this)

        val activityBurgerDetailsBinding : ActivityBurgerDetailsBinding
        = DataBindingUtil.setContentView(this,
            R.layout.activity_burger_details )

        activityBurgerDetailsBinding.item = intent.getSerializableExtra(BURGER_ITEM_EXTRA) as BurgerSerializable?

        activityBurgerDetailsBinding.itemDetailViewModel = itemDetailViewModel

        initToolbar()

        handleViewModel()

    }

    private fun handleViewModel() {

        itemDetailViewModel.getCurrentOrder()

    }

    private fun initToolbar() {
        setSupportActionBar( detail_toolbar as Toolbar)
        detail_toolbar.menu.clear()
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(com.mavenuser.bigburger.R.drawable.baseline_close_white_24)
        }


    }

    override fun onResume() {
        super.onResume()

        itemDetailViewModel.errorPublishSubject.subscribe {

            Log.d(BURGER_LIST_FRAGMENT_TAG, it.throwable.message!!)

            when(it?.throwable){
                is IOException ->
                    Toast.makeText(this, getString(com.mavenuser.bigburger.R.string.connectionError), Toast.LENGTH_LONG).show()
                else -> {
                    Toast.makeText(this, it.throwable.message!!, Toast.LENGTH_LONG).show()
                }

            }
        }.addTo(compositeDisposable)


        itemDetailViewModel.itemAddedPublishSubject.subscribe {
            AlertDialog.Builder(this)
                .setTitle(R.string.go_to_order)
                .setMessage(getString(R.string.item_added_message))
                .setPositiveButton(getString(R.string.go_to_order)){dialog, which ->
                    dialog.dismiss()
                    finish()
                    router.navigate(Router.ROUTE.ORDER, Bundle())

                }
                .setNegativeButton(getString(R.string.continue_shopping)){dialog, which ->
                    dialog.dismiss()
                    finish()
                }.show()
        }.addTo(compositeDisposable)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    override fun onDestroy() {
        super.onDestroy()

        itemDetailViewModel.unBind()
        compositeDisposable.clear()
    }
}
