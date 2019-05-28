package com.mavenuser.bigburger.model

import java.io.Serializable

class QuotationSerializable(var subTotal: Double, var delivary:  Double, var tax: Double = 0.0, var total: Double):Serializable