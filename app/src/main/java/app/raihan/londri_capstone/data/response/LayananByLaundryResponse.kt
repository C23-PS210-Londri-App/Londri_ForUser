package app.raihan.londri_capstone.data.response

import com.google.gson.annotations.SerializedName

data class LayananByLaundryResponse(

	@field:SerializedName("resultData")
	val resultData: List<ResultDataLayananByLaundryItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ResultDataLayananByLaundryItem(

	@field:SerializedName("namaLayanan")
	val namaLayanan: String,

	@field:SerializedName("hargaLayanan")
	val hargaLayanan: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("status")
	val status: String
)
