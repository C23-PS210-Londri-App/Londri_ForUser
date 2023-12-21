package app.raihan.londri_capstone.data.api

import app.raihan.londri_capstone.data.response.AllLaundryResponse
import app.raihan.londri_capstone.data.response.DetailLaundryResponse
import app.raihan.londri_capstone.data.response.LayananByLaundryResponse
import app.raihan.londri_capstone.data.response.LoginResponse
import app.raihan.londri_capstone.data.response.NearestLaundryResponse
import app.raihan.londri_capstone.data.response.OrderResponse
import app.raihan.londri_capstone.data.response.ProfileResponse
import app.raihan.londri_capstone.data.response.RegisterResponse
import app.raihan.londri_capstone.data.response.ResultDataItem
import app.raihan.londri_capstone.data.response.SearchResponse
import app.raihan.londri_capstone.data.response.StatusDoneResponse
import app.raihan.londri_capstone.data.response.StatusProcessResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("auth/user/register")
    fun register(
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("alamat") alamat: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String,
        @Field("nomor_telepon") telp: String,
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("auth/user/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("user/profile")
    fun getProfile(@Header("Authorization") token: String): Call<ProfileResponse>


    @GET("laundry")
    fun getAllLaundry(): Call<AllLaundryResponse>

    @GET("nearest/laundry")
    fun getNearestLaundry(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): Call<NearestLaundryResponse>

    @GET("laundry/detail/{laundryId}")
    fun getDetailLaundry(
        @Header("Authorization") token: String,
        @Path("laundryId") laundryId: String
    ): Call<DetailLaundryResponse>

    @GET("layanan/laundry/{laundryId}")
    fun getLayananByLaundry(
        @Path("laundryId") laundryId: String
    ): Call<LayananByLaundryResponse>

    @FormUrlEncoded
    @POST("laundry/order/{laundryId}")
    fun postOrder(
        @Header("Authorization") token: String,
        @Path("laundryId") laundryId: String,
        @Field("layanan_id") layananId: String,
        @Field("estimasi_berat") estimasiBerat: String,
        @Field("catatan") catatan: String,
    ): Call<OrderResponse>

    @GET("laundry/status/process")
    fun getStatusProcess(
        @Header("Authorization") token: String,
    ): Call<StatusProcessResponse>

    @GET("laundry/status/selesai")
    fun getStatusDone(
        @Header("Authorization") token: String,
    ): Call<StatusDoneResponse>

    @GET("laundry/search")
    fun searchLaundry(
        @Query("laundry") laundry: String
    ): Call<SearchResponse>
}