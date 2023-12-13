package app.raihan.londri_capstone.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("response")
	val response: ResultResponse? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ResultResponse(

	@field:SerializedName("token")
	val token: String? = null
)
