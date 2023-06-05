package com.example.lab4_1.db

import android.provider.BaseColumns

object DBShare: BaseColumns {

    const val TABLE_NAME = "profile"
    const val COLUMT_NAME_FIGI = "figi"
    const val COLUMT_NAME_QUNTITY = "quantity"
    const val COLUMT_NAME_PRICE = "price"
    const val COLUMT_NAME_YIELD = "yield"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "Profile.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMT_NAME_FIGI TEXT, " +
            "$COLUMT_NAME_QUNTITY INT, $COLUMT_NAME_PRICE float, $COLUMT_NAME_YIELD float)"
    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

}

object DBShares: BaseColumns {

    const val TABLE_NAME = "shares"
    const val COLUMT_NAME_FIGI = "figi"
    const val COLUMT_NAME_NAME = "name"
    const val COLUMT_NAME_ISIN = "isin"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMT_NAME_FIGI TEXT, " +
                "$COLUMT_NAME_NAME TEXT, $COLUMT_NAME_ISIN TEXT)"
    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

}