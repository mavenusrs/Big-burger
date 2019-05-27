package com.mavenuser.bigburger.di

import com.mavenuser.bigburger.data.api.BurgerListApi
import com.mavenuser.bigburger.data.local.OrderDAO
import com.mavenuser.bigburger.data.local.dao.BurgerDAO
import com.mavenuser.bigburger.data.mapper.BurgerEntityMapper
import com.mavenuser.bigburger.data.mapper.OrderEntityMapper
import com.mavenuser.bigburger.data.repository.BurgerListLoadingRespositoryImpl
import com.mavenuser.bigburger.data.repository.OrderRepositoryImpl
import com.mavenuser.bigburger.domain.repository.BurgerListLoadingRespository
import com.mavenuser.bigburger.domain.repository.OrderRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideBurgerListLoadingRespository(burgerListApi: BurgerListApi,
                                            burgerDAO: BurgerDAO, burgerEntityMapper: BurgerEntityMapper): BurgerListLoadingRespository{
        return BurgerListLoadingRespositoryImpl(burgerListApi, burgerDAO, burgerEntityMapper)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(orderDAO: OrderDAO, orderEntityMapper: OrderEntityMapper): OrderRepository{
        return OrderRepositoryImpl(orderDAO, orderEntityMapper)
    }

    @Provides
    @Singleton
    fun provideBurgerEntityMapper(): BurgerEntityMapper{
        return BurgerEntityMapper()
    }


}