package app.raihan.londri_capstone.data.response


import com.google.gson.annotations.SerializedName

data class FormOrderResponse(

    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("resultOrder")
    val resultOrder: ResultOrderLaundry
)

data class ResultOrderLaundry(

    @SerializedName("id")
    val id: Int,

    @SerializedName("orderTrx")
    val orderTrx: String,

    @SerializedName("estimasiBerat")
    val estimasiBerat: Int,

    @SerializedName("hargaTotal")
    val hargaTotal: Int,

    @SerializedName("catatan")
    val catatan: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updateAt")
    val updateAt: String,

    @SerializedName("costumerId")
    val costumerId: Int,

    @SerializedName("namaLAundry")
    val namaLaundry: String,

    @SerializedName("namaLayanan")
    val namaLayanan: String
)