package com.example.applemarketapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarketapp.data.Item
import com.example.applemarketapp.databinding.ItemRecyclerViewBinding

class ItemAdapter(private val mItems: MutableList<Item>) :
    RecyclerView.Adapter<ItemAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
        fun onLongClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    inner class Holder(binding: ItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val layout = binding.itemLayout
        val imageItem = binding.itemIv
        val nameItem = binding.nameTv
        val addressItem = binding.addressTv
        val priceItem = binding.priceTv
        val chatItem = binding.chatTv
        val likeItem = binding.likeTv
        val heartIv = binding.heartIv
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
        holder.priceItem.text = item.price
        holder.chatItem.text = item.chat.toString()
        holder.likeItem.text = item.like.toString()
        if(item.isCheckedLike) {
            holder.heartIv.setImageResource(R.drawable.img_all_like)
        }
        else{
            holder.heartIv.setImageResource(R.drawable.img_all_no_like)
        }

        //클릭시 이벤트 처리
        holder.layout.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        //길게 클릭시 이벤트 처리
        holder.layout.setOnLongClickListener {
            itemClick?.onLongClick(it,position)
            true
        }
    }
}