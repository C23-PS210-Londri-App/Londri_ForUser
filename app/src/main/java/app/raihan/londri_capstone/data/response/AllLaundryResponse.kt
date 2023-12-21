package app.raihan.londri_capstone.data.response

import com.google.gson.annotations.SerializedName

data class AllLaundryResponse(

	@field:SerializedName("resultData")
	val resultData: List<ResultDataItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ResultDataItem(

	@field:SerializedName("layanan")
	val layanan: List<Any>,

	@field:SerializedName("fotoLaundry")
	val fotoLaundry: String,

	@field:SerializedName("namaLaundry")
	val namaLaundry: String,

	@field:SerializedName("nomorTelepon")
	val nomorTelepon: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("alamat")
	val alamat: String,

	@field:SerializedName("status")
	val status: String
)

data class LayananItem(

	@field:SerializedName("namaLayanan")
	val namaLayanan: String,

	@field:SerializedName("hargaLayanan")
	val hargaLayanan: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("status")
	val status: String
)
