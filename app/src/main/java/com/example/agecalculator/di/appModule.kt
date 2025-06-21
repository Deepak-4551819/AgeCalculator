package com.example.agecalculator.di

import com.example.agecalculator.data.local.DatabaseFactory
import com.example.agecalculator.data.local.OccasionDao
import com.example.agecalculator.data.local.OccasionDatabase
import com.example.agecalculator.data.repository.OccasionRepositoryImpl
import com.example.agecalculator.domain.repository.OccasionRepository
import com.example.agecalculator.presentation.calculator.CalculatorViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    single<OccasionDatabase> { DatabaseFactory.create(get())  }
    single<OccasionDao> { get<OccasionDatabase>().occasionDao() }

    singleOf(::OccasionRepositoryImpl).bind<OccasionRepository>()
    viewModelOf(::CalculatorViewModel)
}
