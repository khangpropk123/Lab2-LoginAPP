package com.example.applogin

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login_online.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.toast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


class ActivityLoginOnline : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_online)

       id_login.setOnClickListener {
           val apiService by lazy { ApiService.create() }
           var disposable: Disposable? = null
           val username = id_username_server.text.toString()
           val password = id_password_server.text.toString()
           disposable = apiService.getAccessToken(username,password)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(
                   { result -> toast("Welcome! "+id_username_server.text.toString())},
                   {error ->toast("401 Unauthorized!")},
                   {
                       //toast("Welcome! "+id_username_server.text.toString())
                       val intent =  Intent(this,ActivityHome::class.java)
                       intent.putExtra("username",username)
                       startActivity(intent)
                   }
               )
       }

    }
}

const val BASE_URL = "https://cybersvn.team/api/"
const val LOGIN: String = "login"
interface ApiService {
    @POST(LOGIN)
    @Multipart
    fun getAccessToken(
        @Part("userId") userName: String,
        @Part("password") password: String
    ): Observable<User>

    companion object {
        private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}