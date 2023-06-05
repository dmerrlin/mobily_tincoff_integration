package com.example.lab4_1.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DBShare.DATABASE_NAME, null, DBShare.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DBShare.CREATE_TABLE)
        db?.execSQL(DBShares.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(DBShare.SQL_DELETE_TABLE)
        db?.execSQL(DBShares.SQL_DELETE_TABLE)
        onCreate(db)
    }

}