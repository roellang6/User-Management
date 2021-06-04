package dev.ran.usermanage.data.Connection

import dev.ran.usermanage.data.Model.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroService {

    private val userUrl = "https://jsonplaceholder.typicode.com/"
    private val countryUrl = "https://api.printful.com/"

    private  val  userApi : RetroInstance = Retrofit.Builder().baseUrl(userUrl).addConverterFactory(
        GsonConverterFactory.create()).build().create(RetroInstance::class.java)

    private  val  countryApi : RetroInstance = Retrofit.Builder().baseUrl(countryUrl).addConverterFactory(
        GsonConverterFactory.create()).build().create(RetroInstance::class.java)

    fun getuserApi(): Call<List<UserModel>> {return userApi.userData()}

    fun userApi(): Call<UserModel> {return userApi.userDataget()}

    fun getcountryApi(): Call<CountryModel>{return countryApi.countryData()}
}