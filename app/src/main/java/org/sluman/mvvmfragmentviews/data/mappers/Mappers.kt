package org.sluman.mvvmfragmentviews.data.mappers

import org.sluman.mvvmfragmentviews.data.CountryNetworkEntity
import org.sluman.mvvmfragmentviews.data.CurrencyNetworkEntity
import org.sluman.mvvmfragmentviews.data.LanguageNetworkEntity
import org.sluman.mvvmfragmentviews.domain.CountryDomainEntity
import org.sluman.mvvmfragmentviews.domain.CurrencyDomainEntity
import org.sluman.mvvmfragmentviews.domain.LanguageDomainEntity

fun CountryNetworkEntity.toDomain(): CountryDomainEntity {
    return CountryDomainEntity(
        name = name,
        region = region,
        capital = capital,
        code = code,
        flag = flag,
        currency = currency.toDomain(),
        language = language.toDomain()
    )
}

fun CurrencyNetworkEntity.toDomain(): CurrencyDomainEntity {
    return CurrencyDomainEntity(
        code = code,
        name = name,
        symbol = symbol
    )
}

fun LanguageNetworkEntity.toDomain(): LanguageDomainEntity {
    return LanguageDomainEntity(
        code = code,
        name = name
    )
}