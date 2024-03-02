package com.example.uas_197064516002_muhammadmahivabentarraharja

data class data( val id: String?,
                 val nama: String,
                 val tanggal: String,
                 val tempat: String,
                 val no: String,
                 val email: String)
{
    constructor():this("", "", "", "", "", "")
}