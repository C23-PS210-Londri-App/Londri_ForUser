package app.raihan.londri_capstone.data.api

import app.raihan.londri_capstone.data.response.AllLaundryResponse
import app.raihan.londri_capstone.data.response.DetailLaundryResponse
import app.raihan.londri_capstone.data.response.FormOrderResponse
import app.raihan.londri_capstone.data.response.LoginResponse
import app.raihan.londri_capstone.data.response.OnProcessLaundryResponse
import app.raihan.londri_capstone.data.response.ProfileResponse
import app.raihan.londri_capstone.data.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("auth/user/register")
    suspend fun register(
        @Field("nama") name: String,
        @Field("email") email: String,
        @Field("nomor_telepon") telephone: String,
        @Field("password") password: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("auth/user/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse

    @GET("user/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String,
    ):ProfileResponse

    @GET("laundry")
    suspend fun getAllLaundry(
        @Header("Authorization") token: String,
    ):AllLaundryResponse

    @GET("laundry/detail/{laundryId}")
    suspend fun getDetailLaundry(
        @Header("Authorization") token: String,
        @Path("laundryId") laundryId: String
    ): DetailLaundryResponse

    @GET("laundry/status/process")
    suspend fun getProcessLaundry(
        @Header("Authorization") token: String,
    ): OnProcessLaundryResponse

    @POST("laundry/order/{laundryID")
    suspend fun postOrderLaundry(
        @Header("Authorization") token: String,
        @Path("laundryID") laundryId: String
    ): FormOrderResponse
}