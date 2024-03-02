package com.example.uas_197064516002_muhammadmahivabentarraharja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var InputNama : EditText
    private lateinit var InputTanggal : EditText
    private lateinit var InputTempat : EditText
    private lateinit var InputNo : EditText
    private lateinit var InputEmail : EditText
    private lateinit var btnSave : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        InputNama = findViewById(R.id.input_nama)
        InputTanggal = findViewById(R.id.input_tanggal)
        InputTempat = findViewById(R.id.input_tempat)
        InputNo = findViewById(R.id.input_tlpn)
        InputEmail = findViewById(R.id.input_email)
        btnSave = findViewById(R.id.btn_simpan)

        btnSave.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_simpan->{
                simpandata()
                val intent = Intent(this@MainActivity, ListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun simpandata() {

        val nama = InputNama.text.toString().trim()
        val tanggal = InputTanggal.text.toString().trim()
        val tempat = InputTempat.text.toString().trim()
        val no = InputNo.text.toString().trim()
        val email = InputEmail.text.toString().trim()

        if (nama.isEmpty()) {
            InputNama.error = "isi kolom nama";
            return
        } else if (tanggal.isEmpty()) {
            InputTanggal.error = "isi kolom tanggal";
            return
        } else if (tempat.isEmpty()) {
            InputTempat.error = "isi kolom tanggal";
            return
        } else if (no.isEmpty()) {
            InputNo.error = "isi kolom tanggal";
            return
        } else if (email.isEmpty()) {
            InputEmail.error = "isi kolom tanggal";
            return
        }


        val firebaseobj = FirebaseDatabase.getInstance().getReference("mahasiswa")
        val id_mahasiswa = firebaseobj.push().key
        val datamahasiswa = data(id_mahasiswa, nama, tanggal, tempat, no, email)


        if (id_mahasiswa != null) {
            firebaseobj.child(id_mahasiswa).setValue(datamahasiswa).addOnCompleteListener {
                Toast.makeText(applicationContext, "Data berhasil tersimpan", Toast.LENGTH_LONG).show()
            }
        }
    }
}