package org.sluman.mvvmfragmentviews.data

import org.sluman.mvvmfragmentviews.domain.CountryDomainEntity

data class MainState(
    val dataItems: List<CountryDomainEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
