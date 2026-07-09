package com.example.siapsen.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.siapsen.ui.register.RegisterActivity
import com.example.siapsen.utils.SessionManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.siapsen.MainActivity
import com.example.siapsen.R
import com.example.siapsen.data.AppDatabase
import kotlinx.coroutines.launch
import com.example.siapsen.data.UserEntity

class LoginActivity : AppCompatActivity() {

    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var tvRegister: TextView

    private fun buatAkunDefault() {

        lifecycleScope.launch {

            val dao = AppDatabase
                .getInstance(this@LoginActivity)
                .userDao()

            if (dao.getByUsername("atasan") == null) {
                dao.insert(
                    UserEntity(
                        username = "atasan",
                        password = "123",
                        nama = "Budi Santoso",
                        role = "Atasan"
                    )
                )
            }

            if (dao.getByUsername("hr") == null) {
                dao.insert(
                    UserEntity(
                        username = "hr",
                        password = "123",
                        nama = "Sinta Lestari",
                        role = "HR"
                    )
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (SessionManager(this).isLogin()) {

            startActivity(
                Intent(this, MainActivity::class.java)
            )

            finish()
            return
        }

        setContentView(R.layout.activity_login)
        buatAkunDefault()

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)

        tvRegister.setOnClickListener {
            startActivity(
                Intent(this, RegisterActivity::class.java)
            )
        }

        btnLogin.setOnClickListener {

            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            lifecycleScope.launch {

                val user = AppDatabase
                    .getInstance(this@LoginActivity)
                    .userDao()
                    .login(username, password)

                if (user != null) {

                    // Simpan status login
                    SessionManager(this@LoginActivity)
                        .saveLogin(
                            user.username,
                            user.role
                        )

                    // Pindah ke halaman utama
                    startActivity(
                        Intent(
                            this@LoginActivity,
                            MainActivity::class.java
                        )
                    )

                    finish()

                } else {

                    Toast.makeText(
                        this@LoginActivity,
                        "Username atau Password salah",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }

        }

    }

}