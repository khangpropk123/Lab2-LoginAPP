package com.example.applogin

class User(var username : String, var password : String, var email: String) {
    companion object{
        val TABLE_NAME = "users"
        val COLUMN_EMAIL = "email"
        val COLUMN_USERNAME = "username"
        val COLUMN_PASSWORD = "password"

    }
}