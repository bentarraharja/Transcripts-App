package com.example.uas_197064516002_muhammadmahivabentarraharja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase

class ListActivity : AppCompatActivity() {

    private lateinit var reference: DatabaseReference
    private lateinit var mahasiswalist: MutableList<data>
    private lateinit var mahasiswa_list: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        reference = FirebaseDatabase.getInstance().getReference("mahasiswa")

        mahasiswalist = mutableListOf()
        mahasiswa_list = findViewById(R.id.mahasiswa_list)


        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {
                    mahasiswalist.clear()
                    for (h in p0.children) {
                        val datamahasiswa = h.getValue(data::class.java)
                        //if (datamahasiswa != null) {
                        mahasiswalist.add(datamahasiswa!!)
                        //}
                    }


                    val adapter = MahasiswaAdapter(this@ListActivity, R.layout.activity_item, mahasiswalist)
                    mahasiswa_list.adapter = adapter
                }
            }

        })
    }
}