package com.example.applemarketapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarketapp.data.Item
import com.example.applemarketapp.databinding.ActivityMainBinding
import com.example.applemarketapp.databinding.ItemRecyclerViewBinding

class ItemAdapter(private val mItems: MutableList<Item>) :
    RecyclerView.Adapter<ItemAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    lateinit var itemClick: ItemClick

    inner class Holder(binding: ItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val layout = binding.itemLayout
        val imageItem = binding.itemIv
        val nameItem = binding.nameTv
        val addressItem = binding.addressTv
        val priceItem = binding.priceTv
        val chatItem = binding.chatTv
        val likeItem = binding.likeTv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val binding =
            ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: Holder, position: Int) {


        //해당 인스턴스에 값을 넣어줌
        val item = mItems[position]
        holder.imageItem.setImageResource(item.imgResource)
        holder.nameItem.text = item.name
        holder.addressItem.text = item.address
        holder.priceItem.text = item.price.toString()
        holder.chatItem.text = item.chat.toString()
        holder.likeItem.text = item.like.toString()

        //클릭시 detailActivity로 이동
        holder.layout.setOnClickListener {
            itemClick?.onClick(it,position)
        }
    }
}