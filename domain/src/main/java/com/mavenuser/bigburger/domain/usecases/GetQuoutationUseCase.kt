package com.mavenuser.bigburger.domain.usecases

import com.mavenuser.bigburger.domain.model.Burger
import com.mavenuser.bigburger.domain.model.Quoutation
import com.mavenuser.bigburger.domain.usecases.base.ObservableUseCaseWithParameter
import io.reactivex.Observable
import javax.inject.Inject

class GetQuoutationUseCase @Inject constructor() :
    ObservableUseCaseWithParameter<List<Burger>, Quoutation> {
    /**
     * get The list of burger
     * @return Observable<BurgerListState> obsrvable of state returned
     */
    override fun execute(parameter:
                         List<Burger>): Observable<Quoutation> {

        val subTotal = getSubTotal(parameter)
        val delivary = getDelivary()
        val tax = getTax(subTotal, delivary)
        val total = getTotal(subTotal, delivary, tax)
        return Observable.just(Quoutation(subTotal, delivary, tax, total))

}

private fun getSubTotal(burgerList: List<Burger>): Double {

    var subTotal = 0.0

    burgerList.map {
        subTotal += (it.price * it.count)
    }

    return subTotal
}


/**
 * Delivary will be 50 TL for texting only
 */
private fun getDelivary(): Double {
    return 5000.0
}

/**
 * set tax to be 14% for testing Only
 */
private fun getTax(subTotal: Double, delivary: Double): Double {


    return (subTotal + delivary) * 0.14
}

/**
 * Total is text + delivary+ subtotal
 */
private fun getTotal(subTotal: Double, delivary: Double, tax: Double): Double {


    return subTotal + delivary + tax
}


}