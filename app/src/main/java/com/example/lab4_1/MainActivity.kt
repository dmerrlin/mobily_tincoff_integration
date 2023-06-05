package com.example.lab4_1


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab4_1.databinding.ActivityMainBinding
import com.example.lab4_1.db.BDManeger
import com.example.lab4_1.db.DBHelper
import com.example.lab4_1.db.Shares
import com.example.lab4_1.profile.Share
import com.example.lab4_1.profile.ShareAdapter
import getDataTinkoff


class MainActivity : AppCompatActivity() {

    var final_text: TextView? = null
    lateinit var binding: ActivityMainBinding
    private val adapter = ShareAdapter()
    val BDManeger = BDManeger(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BDManeger.openDB()

        //Получаем вид с файла prompt.xml, который применим для диалогового окна:
        val li = LayoutInflater.from(this)
        val promptsView: View = li.inflate(R.layout.prompt, null)
        val mDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun getTincoffTokenDialeg(viev: View){

        //Получаем вид с файла prompt.xml, который применим для диалогового окна:
        val li = LayoutInflater.from(this)
        val promptsView: View = li.inflate(R.layout.prompt, null)
        val mainActivity: View = li.inflate(R.layout.activity_main, null)
        //Создаем AlertDialog
        val mDialogBuilder = AlertDialog.Builder(this)

        //Настраиваем prompt.xml для нашего AlertDialog:
        mDialogBuilder.setView(promptsView)

        //Настраиваем отображение поля для ввода текста в открытом диалоге:
        val userInput = promptsView.findViewById<View>(R.id.input_text) as EditText

        mDialogBuilder.setPositiveButton(android.R.string.ok){
                dialog, which ->
                var token = userInput.text.toString()
                BDManeger.onClear()

               	var api = getDataTinkoff().getConnectApi(token)

              	val share =  getDataTinkoff().getSharesData(api)

                for (i in 0 until share.size)
                {
                    BDManeger.insertToDbShare(share[i].figi, share[i].name, share[i].isin)
                }
               
            	val shares = getDataTinkoff().getPortfolioExample(api).Shares

                for (i in 0 until shares.size)
                {
                    BDManeger.insertToDbProfile(shares[i].figi, shares[i].quantity, shares[i].currentPrice, shares[i].expectedYield)
                }
                init()

        }
        mDialogBuilder.show()
    }

    private fun init() = with(binding){
        apply {
            adapter.clearShar()
            rcView.layoutManager = LinearLayoutManager(this@MainActivity)
            rcView.adapter = adapter

            val shares = BDManeger.getProfileData()
            for (i in 0 until shares.size){
                adapter.addShar(shares[i])
                Log.v("MyLogger", shares[i].image);
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BDManeger.closeDB()
    }
}




