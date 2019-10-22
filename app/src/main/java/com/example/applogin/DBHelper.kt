package com.example.applogin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.PRIMARY_KEY
import org.jetbrains.anko.db.TEXT
import org.jetbrains.anko.db.createTable

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx,name = "users.db",factory = null,version = 1) {
    companion object{
        private var instance : DBHelper? = null
        @Synchronized
        fun Instance(context: Context) : DBHelper {
            if (instance == null){
                instance = DBHelper(context.applicationContext)
            }
            return instance as DBHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
       //To change body of created functions use File | Settings | File Templates.
        db.createTable(
            User.TABLE_NAME,
            true,
            User.COLUMN_EMAIL to TEXT,
            User.COLUMN_USERNAME to TEXT + PRIMARY_KEY,
            User.COLUMN_PASSWORD to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //To change body of created functions use File | Settings | File Templates.
    }
}
