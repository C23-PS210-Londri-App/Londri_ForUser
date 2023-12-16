package app.raihan.londri_capstone.data

sealed class Result<out R> private constructor() {

    data class Error(val errorModel: String) : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    object Loading : Result<Nothing>()
}