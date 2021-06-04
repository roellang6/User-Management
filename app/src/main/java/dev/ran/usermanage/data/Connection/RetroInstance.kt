package dev.ran.usermanage.data.Connection

import dev.ran.usermanage.data.Model.CountryModel
import dev.ran.usermanage.data.Model.UserModel
import retrofit2.Call
import retrofit2.http.GET

interface RetroInstance {

    @GET("users")
    fun userData() : Call<List<UserModel>>

    @GET("users")
    fun userDataget() : Call<UserModel>

    @GET("countries")
    fun countryData() : Call<CountryModel>
}