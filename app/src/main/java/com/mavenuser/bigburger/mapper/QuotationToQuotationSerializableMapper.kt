package com.mavenuser.bigburger.mapper

import com.mavenuser.bigburger.domain.model.Quoutation
import com.mavenuser.bigburger.model.QuotationSerializable
import javax.inject.Inject

class QuotationToQuotationSerializableMapper @Inject constructor() {
    fun map(listOfQuotations: List<Quoutation>): List<QuotationSerializable> {
        return listOfQuotations.map { it.mapp() }
    }




    fun reverseMap(quotationSerializable: QuotationSerializable): Quoutation {
        quotationSerializable.apply {
            return Quoutation(subTotal, delivary, tax, total )
        }
    }
}

 fun Quoutation.mapp(): QuotationSerializable {
    return QuotationSerializable(subTotal, delivary, tax, total )
}

