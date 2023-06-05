package com.example.lab4_1.db

data class Share(val figi: String, val quantity: Int, val currentPrice: Double, val expectedYield: Double)

data class Profile(val totalAmountBonds: Int, val totalAmountEtf: Double, val totalAmountCurrencies: Double,
                   val totalAmountFutures: Double, val totalAmountShares: Double, val getExpectedYield: Double,
                   val positionsSize: Int, val Shares :ArrayList<Share>)

data class Shares(val figi: String, val name:String, val isin:String)