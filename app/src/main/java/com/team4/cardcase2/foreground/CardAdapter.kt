package com.team4.cardcase2.foreground

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.team4.cardcase2.R
import com.team4.cardcase2.entity.ServerCard

class CardAdapter(private val cards: List<ServerCard>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.server_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        // 在这里设置布局中的内容，例如设置文本和图像等
        holder.showName.text = card.elements[0].content
        holder.showCompany.text = card.elements[2].content
        holder.showPhone.text = card.elements[4].content
        holder.showEmail.text = card.elements[3].content
        holder.iv2.setImageResource(R.drawable.bank)
        holder.iv3.setImageResource(R.drawable.phone)
        holder.iv4.setImageResource(R.drawable.email)
        // 设置其他布局内容...
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val showName: TextView = itemView.findViewById(R.id.showName)
        val showCompany: TextView = itemView.findViewById(R.id.showCompany)
        val showPhone: TextView = itemView.findViewById(R.id.showPhone)
        val showEmail: TextView = itemView.findViewById(R.id.showEmail)
        val iv2: ImageView = itemView.findViewById(R.id.imageView2)
        val iv3: ImageView = itemView.findViewById(R.id.imageView3)
        val iv4: ImageView = itemView.findViewById(R.id.imageView4)
        // 其他布局中的视图组件...
    }
}