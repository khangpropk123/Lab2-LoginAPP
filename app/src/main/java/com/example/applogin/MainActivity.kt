package com.example.applogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import java.math.BigInteger
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    val db : DBHelper get() = DBHelper.Instance(applicationContext)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        id_btnLogin.setOnClickListener{
            var username = id_username_server.text.toString()
            var password = id_password.text.toString()
                password = password.sha()
            val users = db.use {
                select(User.TABLE_NAME)
                    .whereArgs("${User.COLUMN_USERNAME}={e} and ${User.COLUMN_PASSWORD}={p}","e" to username, "p" to password)
                    .exec { parseList<User>(classParser()) }
            }
            if (users.size >0){
                toast("Login Thanh Cong")
                val intent =  Intent(this@MainActivity,ActivityHome::class.java)
                    intent.putExtra("username",username)
                    startActivity(intent)
            }
            else{
                toast("Sai password hoac username")
            }
        }
        id_btnRegister.setOnClickListener {

//            var username = id_username.text.toString()
//            var password = id_password.text.toString()
//                password = password.sha()
//            var user = ContentValues()
//                user.put(User.COLUMN_USERNAME,username)
//                user.put(User.COLUMN_PASSWORD,password)
//            val users = db.use {
//                insert(User.TABLE_NAME,null,user)
//            }
//            if (users!=-1L){
//                toast("Đăng Ký Thành Công ")
//            }
//            else{
//                toast("Tài khoản đã được sử dụng!!")
//            }

            val intent = Intent(this,ActivityRegister::class.java)
            startActivity(intent)


        }
        btnLoginOnline.setOnClickListener {
            val intent = Intent(this,ActivityLoginOnline::class.java)
            startActivity(intent)
        }

    }
    fun String.sha(): String {
        val md = MessageDigest.getInstance("SHA-1")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

}
