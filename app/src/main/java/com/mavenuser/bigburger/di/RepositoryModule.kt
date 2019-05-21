package com.mavenuser.bigburger.di

import com.mavenuser.bigburger.data.api.BurgerListApi
import com.mavenuser.bigburger.data.mapper.BurgerEntityMapper
import com.mavenuser.bigburger.data.repository.BurgerListLoadingRespositoryImpl
import com.mavenuser.bigburger.domain.repository.BurgerListLoadingRespository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideBurgerListLoadingRespository(burgerListApi: BurgerListApi, burgerEntityMapper: BurgerEntityMapper): BurgerListLoadingRespository{
        return BurgerListLoadingRespositoryImpl(burgerListApi, burgerEntityMapper)
    }

    @Provides
    @Singleton
    fun provideBurgerEntityMapper(): BurgerEntityMapper{
        return BurgerEntityMapper()
    }


}