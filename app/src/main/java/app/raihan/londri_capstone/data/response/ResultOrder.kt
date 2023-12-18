package app.raihan.londri_capstone.data.response


import com.google.gson.annotations.SerializedName

data class ResultOrder(
    @SerializedName("catatan")
    val catatan: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("customerId")
    val customerId: Int,
    @SerializedName("estimasiBerat")
    val estimasiBerat: Int,
    @SerializedName("hargaTotal")
    val hargaTotal: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("namaLaundry")
    val namaLaundry: String,
    @SerializedName("namaLayanan")
    val namaLayanan: String,
    @SerializedName("orderTrx")
    val orderTrx: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)