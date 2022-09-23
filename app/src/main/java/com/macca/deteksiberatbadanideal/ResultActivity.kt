package com.macca.deteksiberatbadanideal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val nama = findViewById<TextView>(R.id.et_hasil_nama)
        val jenisKelamin = findViewById<TextView>(R.id.et_hasil_jk)
        val umur = findViewById<TextView>(R.id.et_hasil_umur)
        val tinggi = findViewById<TextView>(R.id.et_hasil_tinggi)
        val berat = findViewById<TextView>(R.id.et_hasil_berat)
        val kategori = findViewById<TextView>(R.id.et_hasil_kategori)
        val alamat = findViewById<TextView>(R.id.et_hasil_alamat)
        val btnBack = findViewById<Button>(R.id.btn_kembali)

        nama.text = intent.getStringExtra("nama")
        jenisKelamin.text = intent.getStringExtra("jk")
        umur.text = intent.getStringExtra("umur")
        tinggi.text = intent.getStringExtra("tinggi")
        berat.text = intent.getStringExtra("berat")
        kategori.text = intent.getStringExtra("kategori")
        alamat.text = intent.getStringExtra("alamat")

        btnBack.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }
}