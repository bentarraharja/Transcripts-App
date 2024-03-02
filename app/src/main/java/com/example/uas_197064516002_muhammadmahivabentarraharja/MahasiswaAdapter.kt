package com.example.uas_197064516002_muhammadmahivabentarraharja

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MahasiswaAdapter(val cCtx : Context, val layoutResId : Int, val Clist : List<data> ):ArrayAdapter<data>(cCtx,layoutResId,Clist){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(cCtx)

        val view = layoutInflater.inflate(layoutResId, null)
        val tv_nama : TextView = view.findViewById(R.id.tv_nama)
        val tv_tanggal : TextView = view.findViewById(R.id.tv_tanggal)
        val tv_tempat : TextView = view.findViewById(R.id.tv_tempat)
        val tv_no : TextView = view.findViewById(R.id.tv_no)
        val tv_email : TextView = view.findViewById(R.id.tv_email)
        val tv_edit : TextView =view.findViewById(R.id.tv_edit)

        val datamahasiswa   = Clist[position]

        tv_edit.setOnClickListener{
            showUpdateDialog(datamahasiswa)
        }
        tv_nama.text = datamahasiswa.nama
        tv_tanggal.text = datamahasiswa.tanggal
        tv_tempat.text = datamahasiswa.tempat
        tv_no.text = datamahasiswa.no
        tv_email.text = datamahasiswa.email

        return view
    }
    fun showUpdateDialog(mahasiswa: data){
        val builder = AlertDialog.Builder(cCtx)
        builder.setTitle("Update Data")
        val inflater =LayoutInflater.from(cCtx)
        val view = inflater.inflate(R.layout.activity_update,null)

        val et_inputnama = view.findViewById<EditText>(R.id.input_nama)
        val et_inputtanggal = view.findViewById<EditText>(R.id.input_tanggal)
        val et_inputtempat = view.findViewById<EditText>(R.id.input_tempat)
        val et_inputno = view.findViewById<EditText>(R.id.input_tlpn)
        val et_inputemail = view.findViewById<EditText>(R.id.input_email)

        et_inputnama.setText(mahasiswa.nama)
        et_inputtanggal.setText(mahasiswa.tanggal)
        et_inputtempat.setText(mahasiswa.tempat)
        et_inputno.setText(mahasiswa.no)
        et_inputemail.setText(mahasiswa.email)

        builder.setView(view)


        builder.setPositiveButton("Update"){p0,p1 ->
            val dbMahasiswa = FirebaseDatabase.getInstance().getReference("mahasiswa")

            val nama = et_inputnama.text.toString().trim()
            val tanggal = et_inputtanggal.text.toString().trim()
            val tempat = et_inputtempat.text.toString().trim()
            val no = et_inputno.text.toString().trim()
            val email = et_inputemail.text.toString().trim()

            if(nama.isEmpty()){
                et_inputnama.error="Wajib Diisi";
                et_inputnama.requestFocus()
                return@setPositiveButton
            }
            if(tanggal.isEmpty()){
                et_inputtanggal.error="Wajib Diisi";
                et_inputtanggal.requestFocus()
                return@setPositiveButton
            }
            if(tempat.isEmpty()){
                et_inputtempat.error="Wajib Diisi";
                et_inputtempat.requestFocus()
                return@setPositiveButton
            }
            if(no.isEmpty()){
                et_inputno.error="Wajib Diisi";
                et_inputno.requestFocus()
                return@setPositiveButton
            }
            if(email.isEmpty()){
                et_inputemail.error="Wajib Diisi";
                et_inputemail.requestFocus()
                return@setPositiveButton
            }

            val mahasiswaData = data(mahasiswa.id,nama, tanggal, tempat, no, email)
            dbMahasiswa.child(mahasiswa.id!!).setValue(mahasiswaData)

            Toast.makeText(cCtx, "Data Berhasil Di Update", Toast.LENGTH_SHORT).show()

        }
        builder.setNeutralButton("Delete"){p0,p1 ->
            val dbMahasiswa = FirebaseDatabase.getInstance().getReference("mahasiswa").child(mahasiswa.id!!).removeValue()
            Toast.makeText(cCtx,"Data Terhapus",Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("Cancel"){p0,p1 ->

        }


        val alert = builder.create()
        alert.show()
    }
}