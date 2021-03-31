package com.geek.dinner.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geek.dinner.R
import com.geek.dinner.bean.CardItemParams

class DinnerItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val cardItemList = mutableListOf<CardItemParams>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_dinner_item, parent, false)

        return DinnerCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DinnerCardViewHolder) {
            holder.bind(cardItemList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return cardItemList.size
    }

    inner class DinnerCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cardItemParams: CardItemParams?, position: Int) {
            cardItemParams?.let {
                with(itemView) {
                    val tvLeftTitle = findViewById<TextView>(R.id.tv_left_title).apply {
                        cardItemParams.leftName.isNotBlank().apply {
                            text = "${cardItemParams.leftName} ${cardItemParams.leftTimeStr}"
                        }

                    }
                    val tvRightTitle = findViewById<TextView>(R.id.tv_right_title).apply {
                        cardItemParams.rightName.apply {
                            text = "${cardItemParams.rightName} ${cardItemParams.rightTimeStr}"
                        }
                        if (TextUtils.isEmpty(cardItemParams.rightName)) {
                            visibility = View.INVISIBLE
                        }

                    }
                    val tvDate = findViewById<TextView>(R.id.tv_date).apply {
                        text = cardItemParams.dateStr
                    }
                    //首部隐藏
                    if (position == 0) {
                        val divider = findViewById<View>(R.id.divider).apply {
                            this.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }


    }
}