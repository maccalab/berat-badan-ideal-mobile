package com.macca.deteksiberatbadanideal

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = FirebaseDatabase.getInstance()
        val nama = findViewById<EditText>(R.id.et_nama)
        val jenisKelamin = findViewById<RadioGroup>(R.id.radio_group)
        val umur = findViewById<EditText>(R.id.et_umur)
        val alamat = findViewById<EditText>(R.id.et_alamat)
        val btnDeteksi = findViewById<Button>(R.id.btn_deteksi)

        val send = database.getReference("Send")
        val tinggiBadan = database.getReference("Tinggi")
        val beratBadan = database.getReference("Berat")
        val kategori = database.getReference("Ideal")
        var count = -1;
        send.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(data: DataSnapshot) {
                Log.d("DIDIMAMAN", data.value.toString())
                count +=1
                if(count > 0){
                    val namaLengkap = nama.text.toString()
                    var _jenisKelamin = ""
                    if (jenisKelamin.checkedRadioButtonId == 1){
                        _jenisKelamin = "Laki - laki"
                    }else{
                        _jenisKelamin = "Perempuan"
                    }
                    val umur = umur.text.toString()
                    val alamat = alamat.text.toString()

                    var tinggi = ""
                    tinggiBadan.get().addOnSuccessListener {
                        tinggi = it.value.toString()
                    }
                    var berat = ""
                    beratBadan.get().addOnSuccessListener {
                        berat = it.value.toString()
                    }
                    var ideal = ""
                    kategori.get().addOnSuccessListener {
                        var result = it.value.toString()
                        if(result == "1"){
                            ideal = "Berat Badan Ideal"
                        }else{
                            ideal = "Berat Badan TIDAK IDEAL"
                        }
                        _moveToResult(namaLengkap, _jenisKelamin, umur, tinggi, berat, ideal, alamat)
                    }
                }
            }

            override fun onCancelled(dataBaseError: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        btnDeteksi.setOnClickListener {
            ProgressDialog.show(this, "Mohon tunggu", "Menghitung Berat Ideal");
                if(nama.text.toString().trim().isEmpty() || umur.text.toString().trim().isEmpty() || alamat.text.toString().trim().isEmpty() || jenisKelamin.checkedRadioButtonId == -1){
                    Toast.makeText(this,"Mohon Lengkapi Data", Toast.LENGTH_LONG).show()
                }else{
                    // Write a message to the database
                    val berat = database.getReference("Berat")
                    val gender = database.getReference("Gender")
                    val ideal = database.getReference("Ideal")
//                    val send = database.getReference("Send")
                    val status = database.getReference("Status")
                    val tinggi = database.getReference("Tinggi")

                    var jk = ""
                    if (jenisKelamin.checkedRadioButtonId == 1){
                        jk = "L"
                    }else{
                        jk = "P"
                    }
                    gender.setValue(jk)
                }
        }
    }

    private fun _moveToResult(nama : String, jenisKelamin : String, umur : String, tinggi : String, berat : String, kategori : String, alamat : String){
        val i = Intent(this, ResultActivity::class.java)
        i.putExtra("nama", nama)
        i.putExtra("jk", jenisKelamin)
        i.putExtra("umur", umur)
        i.putExtra("tinggi", tinggi)
        i.putExtra("berat", berat)
        i.putExtra("kategori", kategori)
        i.putExtra("alamat", alamat)
        startActivity(i)
    }
}