package com.example.applogin

import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.email

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.toast
import java.math.BigInteger
import java.security.MessageDigest

class ActivityRegister : AppCompatActivity() {
    val db : DBHelper get() = DBHelper.Instance(applicationContext)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btnRegister.setOnClickListener {
            var email = edEmail.text.toString()
            var username = edUsername.text.toString()
            var password = edPassword.text.toString()
                password = password.sha()
            var user = ContentValues()
                user.put(User.COLUMN_USERNAME,username)
                user.put(User.COLUMN_PASSWORD,password)
                user.put(User.COLUMN_EMAIL,email)
            val users = db.use {
                insert(User.TABLE_NAME,null,user)
            }
            if (users!=-1L){
                toast("Đăng Ký Thành Công ")
                finish()
            }
            else{
                toast("Tài khoản đã được sử dụng!!")
            }



        }

    }
    fun String.sha(): String {
        val md = MessageDigest.getInstance("SHA-1")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }
}