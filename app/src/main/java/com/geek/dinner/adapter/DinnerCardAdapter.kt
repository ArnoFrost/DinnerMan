package com.geek.dinner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.geek.dinner.R
import com.geek.dinner.bean.CardParams

class DinnerCardAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val cardList = mutableListOf<CardParams>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_dinner_card, parent, false)

        return DinnerCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DinnerCardViewHolder) {
            holder.bind(cardList[position])
        }
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    inner class DinnerCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cardParams: CardParams?) {
            cardParams?.let {
                with(itemView) {
                    findViewById<ImageView>(R.id.card_bg).apply {
                        background = if (cardParams.isActive) {
                            ContextCompat.getDrawable(itemView.context, R.drawable.green)
                        } else {
                            ContextCompat.getDrawable(itemView.context, R.drawable.grey)
                        }
                    }
                    findViewById<TextView>(R.id.tv_title).apply {
                        text = it.name
                    }
                    findViewById<TextView>(R.id.tv_date).apply {
                        text = it.dateStr
                    }
                    findViewById<TextView>(R.id.tv_time).apply {
                        text = it.timeStr
                        setTextColor(
                            if (cardParams.isActive) {
                                context.getColor(R.color.date_active)
                            } else {
                                context.getColor(R.color.date_deactive)
                            }
                        )
                    }
                }
            }
        }


    }
}