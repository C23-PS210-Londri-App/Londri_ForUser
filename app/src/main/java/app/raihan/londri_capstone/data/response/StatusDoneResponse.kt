package app.raihan.londri_capstone.data.response

import com.google.gson.annotations.SerializedName

data class StatusDoneResponse(

	@field:SerializedName("resultRiwayat")
	val resultRiwayat: List<ResultDoneItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ResultDoneItem(

	@field:SerializedName("namaLaundry")
	val namaLaundry: String,

	@field:SerializedName("latitude")
	val latitude: Any,

	@field:SerializedName("catatan")
	val catatan: String,

	@field:SerializedName("estimasiBerat")
	val estimasiBerat: Int,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("namaLayanan")
	val namaLayanan: String,

	@field:SerializedName("hargaTotal")
	val hargaTotal: Int,

	@field:SerializedName("customerId")
	val customerId: Int,

	@field:SerializedName("alamatCustomer")
	val alamatCustomer: Any,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("orderTrx")
	val orderTrx: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("longitude")
	val longitude: Any
)
