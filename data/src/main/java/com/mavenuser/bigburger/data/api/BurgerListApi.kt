package com.mavenuser.bigburger.data.api

import com.mavenuser.bigburger.data.entity.BurgerResponse
import io.reactivex.Single
import retrofit2.http.GET

interface   BurgerListApi {

    @GET("mobiletest1.json")
    fun getBurgerList() : Single<List<BurgerResponse>>
}