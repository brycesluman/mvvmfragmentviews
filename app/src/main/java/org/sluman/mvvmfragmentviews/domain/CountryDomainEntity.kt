package org.sluman.mvvmfragmentviews.domain

data class CountryDomainEntity(
    val name: String,
    val region: String,
    val capital: String,
    val code: String,
    val flag: String,
    val currency: CurrencyDomainEntity,
    val language: LanguageDomainEntity
)

data class CurrencyDomainEntity(
    val code: String,
    val name: String,
    val symbol: String?
)

data class LanguageDomainEntity(
    val code: String?,
    val name: String,
)

