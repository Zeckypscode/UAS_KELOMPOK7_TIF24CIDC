package com.example.siapsen.ui.register

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.siapsen.R
import com.example.siapsen.data.AppDatabase
import com.example.siapsen.data.UserEntity
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        etNama = findViewById(R.id.etNama)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {

            val nama = etNama.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Semua data wajib diisi",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            lifecycleScope.launch {

                val db = AppDatabase.getInstance(this@RegisterActivity)

                db.userDao().insert(
                    UserEntity(
                        username = username,
                        password = password,
                        nama = nama,
                        role = "Pegawai"
                    )
                )

                Toast.makeText(
                    this@RegisterActivity,
                    "Register Berhasil",
                    Toast.LENGTH_SHORT
                ).show()

                finish()

            }

        }

    }

}