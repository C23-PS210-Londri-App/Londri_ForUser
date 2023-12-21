package app.raihan.londri_capstone.data.response

import com.google.gson.annotations.SerializedName

data class NearestLaundryResponse(

    @field:SerializedName("resultData")
    val resultData: List<ResultNearestDataItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class ResultNearestDataItem(

    @field:SerializedName("layanan")
    val layanan: List<Any>,

    @field:SerializedName("fotoLaundry")
    val fotoLaundry: String,

    @field:SerializedName("namaLaundry")
    val namaLaundry: String,

    @field:SerializedName("latitude")
    val latitude: String,

    @field:SerializedName("nomorTelepon")
    val nomorTelepon: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("alamat")
    val alamat: String,

    @field:SerializedName("longitude")
    val longitude: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("distance")
    val distance: String
)
