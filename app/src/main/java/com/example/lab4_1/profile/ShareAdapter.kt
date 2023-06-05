package com.example.lab4_1.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab4_1.R
import com.example.lab4_1.databinding.ShareItemBinding
import com.squareup.picasso.Picasso
import java.text.DecimalFormat


class ShareAdapter: RecyclerView.Adapter <ShareAdapter.ShareHolder>() {

    val shareList = ArrayList<Share>()

    class ShareHolder(item: View, context: android.content.Context): RecyclerView.ViewHolder(item) {

        val context = context
        val binding = ShareItemBinding.bind(item)
        fun bind(share: Share) = with(binding){

            Picasso.with(context)
                .load(share.image)
                .placeholder(R.drawable.ic_baseline_cloud_download_24)
                .error(R.drawable.ic_baseline_error_24)
                .resize(320,320)
                .into(im)



            tvTitle.text = share.name
            tvTitle2.text = "${DecimalFormat("#0.00").format(share.quantity)} у.е - ${DecimalFormat("#0.00").format(share.currentPrice)}"
            tvTitle3.text = DecimalFormat("#0.00").format(share.expectedYield).toString()
            tvTitle4.text = DecimalFormat("#0.00").format(share.currentPrice*share.quantity).toString()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShareHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.share_item, parent, false)

        return  ShareHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ShareHolder, position: Int) {
        holder.bind(shareList[position])
    }

    override fun getItemCount(): Int {
      return shareList.size
    }

    fun addShar(share: Share){
        shareList.add(share)
        notifyDataSetChanged()
    }

    fun clearShar(){
        shareList.clear()
    }


}