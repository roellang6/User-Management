package dev.ran.usermanage.ui.main.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ran.usermanage.data.Connection.RetroService
import dev.ran.usermanage.data.Model.*
import retrofit2.Call
import retrofit2.Response

class UserViewModel : ViewModel() {

    private var dataService = RetroService()

    private val getdUser = ArrayList<UserModel>()

    private val filterUser = MutableLiveData<List<UserModel>>()
    val livedUserfilter: MutableLiveData<List<UserModel>> get() = filterUser
    val loaderrorfilter = MutableLiveData<Boolean>()

    private val getdUserAll = MutableLiveData<List<UserModel>>()
    val livedUser: MutableLiveData<List<UserModel>> get() = getdUserAll
    val loaderrorUser = MutableLiveData<Boolean>()

    private val filterCountryname = ArrayList<String>()

    private val getdCountry = MutableLiveData<List<String>>()
    val livedCountry: MutableLiveData<List<String>> get() = getdCountry
    val loaderrorCountry = MutableLiveData<Boolean>()

    fun refreshUser() {
        getUserData()
    }

    fun refreshCountry() {
        getCountryData()
    }

    fun refreshUserFilter(iduser : Int) {
        getUser(iduser)
    }

    private fun getUserData() {
        dataService.getuserApi().enqueue(object : retrofit2.Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>,
                response: Response<List<UserModel>>
            ) {
                try {
                    getdUserAll.value = response.body()!!
                } catch (e: Exception) {
                    loaderrorUser.value = false
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                loaderrorUser.value = false
            }
        })
    }

    private fun getCountryData() {
        dataService.getcountryApi().enqueue(object : retrofit2.Callback<CountryModel> {
            override fun onResponse(call: Call<CountryModel>, response: Response<CountryModel>) {
                try {
                    val filterCountryName: List<ResultCountry> = response.body()!!.result
                    for (countryname in filterCountryName) {
                        filterCountryname.add(countryname.name)
                    }
                    getdCountry.value = filterCountryname
                } catch (e: Exception) {
                    loaderrorCountry.value = false
                }
            }

            override fun onFailure(call: Call<CountryModel>, t: Throwable) {
                loaderrorCountry.value = false
            }
        })
    }

    private fun getUser(id: Int) {
        dataService.getuserApi().enqueue(object : retrofit2.Callback<List<UserModel>> {
            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                try {
                    val data: List<UserModel> = response.body()!!
                    for (userfilter in data) {
                        if (userfilter.id == id) {
                            getdUser.addAll(listOf(userfilter))
                        }
                    }
                    filterUser.value = getdUser

                } catch (e: Exception) {
                    loaderrorfilter.value = false
                }
            }
            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                loaderrorfilter.value = false
            }
        })
    }
}