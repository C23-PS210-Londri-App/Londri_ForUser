package app.raihan.londri_capstone.data.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("response")
	val response: ResultProfile,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ResultProfile(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("passwordToken")
	val passwordToken: Any,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("fotoProfil")
	val fotoProfil: Any,

	@field:SerializedName("nomorTelepon")
	val nomorTelepon: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("namaLengkap")
	val namaLengkap: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("alamat")
	val alamat: String,

	@field:SerializedName("longitude")
	val longitude: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
