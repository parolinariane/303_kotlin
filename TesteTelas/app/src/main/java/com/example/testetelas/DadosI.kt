package com.example.testetelas

import com.google.gson.annotations.SerializedName
import java.sql.*

data class DadosI(
    @SerializedName("RA") val ra: String? = null,
    @SerializedName("latitude") val lat: String? = null,
    @SerializedName("longitude") val lon: String? = null,
    @SerializedName("foto") val foto: String? = null,
    @SerializedName("data_hora") val data_hora: Date? = null
): java.io.Serializable

