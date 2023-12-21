package app.raihan.londri_capstone.utils

import android.annotation.SuppressLint
import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import app.raihan.londri_capstone.data.api.ApiService
import app.raihan.londri_capstone.data.response.AllLaundryResponse
import app.raihan.londri_capstone.data.response.DataDetailLaundryResponse
import app.raihan.londri_capstone.data.response.DetailLaundryResponse
import app.raihan.londri_capstone.data.response.LayananByLaundryResponse
import app.raihan.londri_capstone.data.response.LoginResponse
import app.raihan.londri_capstone.data.response.NearestLaundryResponse
import app.raihan.londri_capstone.data.response.OrderResponse
import app.raihan.londri_capstone.data.response.ProfileResponse
import app.raihan.londri_capstone.data.response.RegisterResponse
import app.raihan.londri_capstone.data.response.ResultDataItem
import app.raihan.londri_capstone.data.response.ResultDataLayananByLaundryItem
import app.raihan.londri_capstone.data.response.ResultDataSearchItem
import app.raihan.londri_capstone.data.response.ResultDoneItem
import app.raihan.londri_capstone.data.response.ResultNearestDataItem
import app.raihan.londri_capstone.data.response.ResultProcessItem
import app.raihan.londri_capstone.data.response.ResultProfile
import app.raihan.londri_capstone.data.response.SearchResponse
import app.raihan.londri_capstone.data.response.StatusDoneResponse
import app.raihan.londri_capstone.data.response.StatusProcessResponse
import app.raihan.londri_capstone.models.UserModel
import app.raihan.londri_capstone.pref.UserPreference
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response


class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _orderResponse = MutableLiveData<OrderResponse>()
    val orderResponse: LiveData<OrderResponse> = _orderResponse

    private val _profileResponse = MutableLiveData<List<ResultProfile>>()
    val profileResponse: LiveData<List<ResultProfile>> = _profileResponse

    private val _detailLaundryResponse = MutableLiveData<List<DataDetailLaundryResponse>>()
    val detailLaundryResponse: LiveData<List<DataDetailLaundryResponse>> = _detailLaundryResponse

    private val _layananByLaundry = MutableLiveData<List<LayananByLaundryResponse>>()
    val layananByLaundry: LiveData<List<LayananByLaundryResponse>> = _layananByLaundry


    private val _insideLayananByLaundry = MutableLiveData<List<ResultDataLayananByLaundryItem>?>()
    val insideLayananByLaundry: LiveData<List<ResultDataLayananByLaundryItem>?> = _insideLayananByLaundry

    private val _laundryResponse = MutableLiveData<List<ResultDataItem>?>()
    val laundryResponse: LiveData<List<ResultDataItem>?> = _laundryResponse

    private val _nearestLaundryResponse = MutableLiveData<List<ResultNearestDataItem>?>()
    val nearestLaundryResponse: LiveData<List<ResultNearestDataItem>?> = _nearestLaundryResponse

    private val _statusProcessResponse = MutableLiveData<List<ResultProcessItem>?>()
    val statusProcessResponse: LiveData<List<ResultProcessItem>?> = _statusProcessResponse

    private val _statusDoneResponse = MutableLiveData<List<ResultDoneItem>?>()
    val statusDoneResponse: LiveData<List<ResultDoneItem>?> = _statusDoneResponse

    private val _listSearchResponse = MutableLiveData<List<ResultDataSearchItem>?>()
    val listSearchResponse: LiveData<List<ResultDataSearchItem>?> = _listSearchResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun login() {
        userPreference.login()
    }

    fun postRegister(
        name: String,
        email: String,
        password: String,
        alamat: String,
        lat: String,
        long: String,
        noTelp: String
    ) {
        _isLoading.value = true
        val client = apiService.register(name, email, password, alamat, lat, long, noTelp)

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _registerResponse.value = response.body()
                } else {
                    Log.e(
                        TAG,
                        "ErrorMessage: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(TAG, "ErrorMessage: ${t.message.toString()}")
            }
        })
    }

    fun postLogin(email: String, password: String) {
        _isLoading.value = true
        val client = apiService.login(email, password)

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _loginResponse.value = response.body()
                } else {
                    Log.e(
                        TAG,
                        "ErrorMessage: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "ErrorMessage: ${t.message.toString()}")
            }
        })
    }

    fun postOrder(
        tokenSession: String,
        idLaundry: String,
        layananId: String,
        estimasiBerat: String,
        catatan: String
    ) {
        _isLoading.value = true
        val client =
            apiService.postOrder(tokenSession, idLaundry, layananId, estimasiBerat, catatan)

        client.enqueue(object : Callback<OrderResponse> {
            override fun onResponse(
                call: Call<OrderResponse>,
                response: Response<OrderResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _orderResponse.value = response.body()
                } else {
                    Log.e(
                        TAG,
                        "ErrorMessage: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Log.e(TAG, "ErrorMessage: ${t.message.toString()}")
            }
        })
    }

    fun getProfile(token: String) {
//        _isLoading.value = true
        val client = apiService.getProfile(token)
        client.enqueue(object : Callback<ProfileResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val userDetail = response.body()?.response
                    if (userDetail != null) {
                        _profileResponse.value = listOf(userDetail)
                    } else {
                        Log.e("Error", "onFailure: ${response.message()}")
                    }
                } else {
                    _isLoading.value = false
                    Log.e("ProfileViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.e("ProfileViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getAllLaundry() {
//        _isLoading.value = true
        val client = apiService.getAllLaundry()
        client.enqueue(object : Callback<AllLaundryResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<AllLaundryResponse>,
                response: Response<AllLaundryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val laundryDetail = response.body()?.resultData
                    if (laundryDetail != null) {
                        _laundryResponse.value = laundryDetail
                    } else {
                        Log.e("Error", "onFailure: ${response.message()}")
                    }
                } else {
                    _isLoading.value = false
                    Log.e("HomeViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AllLaundryResponse>, t: Throwable) {
                Log.e("HomeViewModel", "onFailure: ${t.message}")

            }
        })
    }

    fun getNearestLaundry(latitude: String, longitude: String) {
//        _isLoading.value = true
        val client = apiService.getNearestLaundry(latitude, longitude)
        client.enqueue(object : Callback<NearestLaundryResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<NearestLaundryResponse>,
                response: Response<NearestLaundryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val laundryDetail = response.body()?.resultData
                    if (laundryDetail != null) {
                        _nearestLaundryResponse.value = laundryDetail
                    } else {
                        Log.e("Error", "onFailure: ${response.message()}")
                    }
                } else {
                    _isLoading.value = false
                    Log.e("HomeViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NearestLaundryResponse>, t: Throwable) {
                Log.e("HomeViewModel", "onFailure: ${t.message}")

            }
        })
    }

    fun getDetailLaundry(token: String, laundryId: String) {
        val client = apiService.getDetailLaundry(token, laundryId)
        client.enqueue(object : Callback<DetailLaundryResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<DetailLaundryResponse>,
                response: Response<DetailLaundryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val laundryDetail = response.body()?.resultData
                    if (laundryDetail != null) {
                        _detailLaundryResponse.value = listOf(laundryDetail)
                    } else {
                        Log.e("Error", "onFailure: ${response.message()}")
                    }
                } else {
                    _isLoading.value = false
                    Log.e("DetailViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailLaundryResponse>, t: Throwable) {
                Log.e("DetailViewModel", "onFailure: ${t.message}")

            }
        })
    }

    fun getLayananByLaundry(laundryId: String) {
        val client = apiService.getLayananByLaundry(laundryId)
        client.enqueue(object : Callback<LayananByLaundryResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<LayananByLaundryResponse>,
                response: Response<LayananByLaundryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val layananByLaundry = response.body()
                    val insideLayanan = response.body()?.resultData
                    if (layananByLaundry != null) {
                        _layananByLaundry.value = listOf(layananByLaundry)
                        _insideLayananByLaundry.value = insideLayanan
                    } else {
                        Log.e("Error", "onFailure: ${response.message()}")
                    }
                } else {
                    _isLoading.value = false
                    Log.e("OrderViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LayananByLaundryResponse>, t: Throwable) {
                Log.e("DetailViewModel", "onFailure: ${t.message}")

            }
        })
    }

    fun getStatusProcess(token: String) {
        val client = apiService.getStatusProcess(token)
        client.enqueue(object : Callback<StatusProcessResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<StatusProcessResponse>,
                response: Response<StatusProcessResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val laundryDetail = response.body()?.resultRiwayat
                    if (laundryDetail != null) {
                        _statusProcessResponse.value = laundryDetail
                    } else {
                        Log.e("Error", "onFailure: ${response.message()}")
                    }
                } else {
                    _isLoading.value = false
                    Log.e("HomeViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StatusProcessResponse>, t: Throwable) {
                Log.e("HomeViewModel", "onFailure: ${t.message}")

            }
        })
    }

    fun getStatusDone(token: String) {
        val client = apiService.getStatusDone(token)
        client.enqueue(object : Callback<StatusDoneResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<StatusDoneResponse>,
                response: Response<StatusDoneResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val laundryDetail = response.body()?.resultRiwayat
                    if (laundryDetail != null) {
                        _statusDoneResponse.value = laundryDetail
                    } else {
                        Log.e("Error", "onFailure: ${response.message()}")
                    }
                } else {
                    _isLoading.value = false
                    Log.e("HomeViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StatusDoneResponse>, t: Throwable) {
                Log.e("HomeViewModel", "onFailure: ${t.message}")

            }
        })
    }

    fun getSearch(q: String) {
        val client = apiService.searchLaundry(q)
        client.enqueue(object : Callback<SearchResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val laundry = response.body()?.resultData
                    if (laundry != null) {
                        _listSearchResponse.value = laundry
                    } else {
                        Log.e("Error", "onFailure: ${response.message()}")
                    }
                } else {
                    _isLoading.value = false
                    Log.e("HomeViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e("HomeViewModel", "onFailure: ${t.message}")

            }
        })
    }

    companion object {
        private const val TAG = "UserRepository"

        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}