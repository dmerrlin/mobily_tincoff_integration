package com.example.lab4_1.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import kotlin.text.toDouble as toDouble1

class BDManeger(val context: Context) {
    val DBHelper = com.example.lab4_1.db.DBHelper(context)
    var db: SQLiteDatabase? = null

    fun openDB(){
        db = DBHelper.writableDatabase
    }

    fun insertToDbProfile(figi: String, quantity:Int, price: Double, yield: Double){

            val value = ContentValues().apply {
                put(DBShare.COLUMT_NAME_FIGI, figi)
                put(DBShare.COLUMT_NAME_QUNTITY, quantity)
                put(DBShare.COLUMT_NAME_PRICE, price)
                put(DBShare.COLUMT_NAME_YIELD, yield)
            }
        db?.insert(DBShare.TABLE_NAME, null, value)
    }

    fun insertToDbShare(figi: String, name:String, isin:String){

        val value = ContentValues().apply {
            put(DBShares.COLUMT_NAME_FIGI, figi)
            put(DBShares.COLUMT_NAME_NAME, name)
            put(DBShares.COLUMT_NAME_ISIN, isin)

        }
        db?.insert(DBShares.TABLE_NAME, null, value)
    }

    @SuppressLint("Range")
    fun readDbData(): ArrayList<Share>{
        val dataList = ArrayList<Share>()

        val cursor = db?.query(DBShare.TABLE_NAME, null, null, null, null, null, null)

        while (cursor?.moveToNext()!!){
            val share = Share(cursor?.getString(cursor.getColumnIndex(DBShare.COLUMT_NAME_FIGI)).toString(),
                cursor?.getString(cursor.getColumnIndex(DBShare.COLUMT_NAME_QUNTITY)).toString().toInt(),
                cursor?.getString(cursor.getColumnIndex(DBShare.COLUMT_NAME_PRICE)).toString().toDouble1(),
                cursor?.getString(cursor.getColumnIndex(DBShare.COLUMT_NAME_YIELD)).toString().toDouble1()
            )
            dataList.add(share)
        }
        cursor.close()
        return dataList
    }

    @SuppressLint("Range")
    fun getProfileData(): ArrayList<com.example.lab4_1.profile.Share>{
        val dataList = ArrayList<com.example.lab4_1.profile.Share>()
        val sql = "SELECT p.${DBShare.COLUMT_NAME_PRICE}, p.${DBShare.COLUMT_NAME_YIELD}, " +
                "p.${DBShare.COLUMT_NAME_QUNTITY}, s.${DBShares.COLUMT_NAME_NAME}, s.${DBShares.COLUMT_NAME_ISIN}" +
                " FROM ${DBShare.TABLE_NAME} p " +
                "LEFT JOIN ${DBShares.TABLE_NAME} s ON s.${DBShare.COLUMT_NAME_FIGI} = p.${DBShares.COLUMT_NAME_FIGI}"

        val cursor = db?.rawQuery(sql, null)

        while (cursor?.moveToNext()!!){
            val share = com.example.lab4_1.profile.Share("https://invest-brands.cdn-tinkoff.ru/${cursor?.getString(cursor.getColumnIndex(DBShares.COLUMT_NAME_ISIN)).toString()}x160.png",
                cursor?.getString(cursor.getColumnIndex(DBShares.COLUMT_NAME_NAME)).toString(),
                cursor?.getString(cursor.getColumnIndex(DBShare.COLUMT_NAME_QUNTITY)).toString().toInt(),
                cursor?.getString(cursor.getColumnIndex(DBShare.COLUMT_NAME_PRICE)).toString().toDouble1(),
                cursor?.getString(cursor.getColumnIndex(DBShare.COLUMT_NAME_YIELD)).toString().toDouble1()
                )
            dataList.add(share)
        }
        cursor.close()
        return dataList
    }

    fun closeDB(){
        DBHelper.close()
    }

    fun onClear() {
        db?.delete(DBShare.TABLE_NAME, null, null)
        db?.delete(DBShares.TABLE_NAME, null, null)

    }
}

