package dev.ran.usermanage.data.Model

data class CountryModel(
    val code: Int,
    val extra: List<Any>,
    val result: List<ResultCountry>
)