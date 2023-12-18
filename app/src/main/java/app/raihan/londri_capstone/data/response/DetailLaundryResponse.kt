package app.raihan.londri_capstone.data.response

import com.google.gson.annotations.SerializedName

data class DetailLaundryResponse (

    @field: SerializedName("resultData")
    val resultData: DataDetailLaundryResponse,

    @field: SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class DataDetailLaundryResponse (

    @field: SerializedName("namaLaundry")
    val namaLaundry: String,

    @field: SerializedName("fotoLaundry")
    val fotoLaundry: String,

    @field: SerializedName("nomorTelepon")
    val nomorTelepon: String,

    @field: SerializedName("alamat")
    val alamat: String,

    @field: SerializedName("status")
    val status: String
)

data class DataLayanan (

    @field: SerializedName("id")
    val id: Int,

    @field: SerializedName("status")
    val status: String,

    @field: SerializedName("hargaLayanan")
    val hargaLayanan: Int
)