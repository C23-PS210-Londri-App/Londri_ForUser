package app.raihan.londri_capstone.data.response

import com.google.gson.annotations.SerializedName

data class OnProcessLaundryResponse(
    @field:SerializedName("resultRiwayat")
    val  resultRiwayat: List<ResultRiwayatItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class ResultRiwayatItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("orderTrx")
    val orderTrx: String
)