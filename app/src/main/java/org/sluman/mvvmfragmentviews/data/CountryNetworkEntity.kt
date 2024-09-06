package org.sluman.mvvmfragmentviews.data

data class CountryNetworkEntity(
    val name: String,
    val region: String,
    val capital: String,
    val code: String,
    val flag: String,
    val currency: CurrencyNetworkEntity,
    val language: LanguageNetworkEntity
)

data class CurrencyNetworkEntity(
    val code: String,
    val name: String,
    val symbol: String?
)

data class LanguageNetworkEntity(
    val code: String?,
    val name: String,
)