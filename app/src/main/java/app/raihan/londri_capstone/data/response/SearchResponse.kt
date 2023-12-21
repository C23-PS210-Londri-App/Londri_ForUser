package app.raihan.londri_capstone.data.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("resultData")
	val resultData: List<ResultDataSearchItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ResultDataSearchItem(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("layanan")
	val layanan: List<LayananItems>,

	@field:SerializedName("fotoLaundry")
	val fotoLaundry: String,

	@field:SerializedName("namaLaundry")
	val namaLaundry: String,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("nomorTelepon")
	val nomorTelepon: String,

	@field:SerializedName("alamat")
	val alamat: String,

	@field:SerializedName("longitude")
	val longitude: String,

	@field:SerializedName("status")
	val status: String
)

data class LayananItems(

	@field:SerializedName("namaLayanan")
	val namaLayanan: String,

	@field:SerializedName("hargaLayanan")
	val hargaLayanan: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("status")
	val status: String
)
