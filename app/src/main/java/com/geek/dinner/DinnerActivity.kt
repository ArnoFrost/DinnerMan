package com.geek.dinner

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geek.dinner.adapter.DinnerCardAdapter
import com.geek.dinner.adapter.DinnerItemAdapter
import com.geek.dinner.utils.DinnerDataHelper

class DinnerActivity : AppCompatActivity() {
    private lateinit var mCardAdapter: DinnerCardAdapter
    private lateinit var mItemAdapter: DinnerItemAdapter
    private val cardRv: RecyclerView by lazy {
        findViewById(R.id.rv_top)
    }
    private val contentRv: RecyclerView by lazy {
        findViewById(R.id.rv_content)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dinner)
        initView()
    }

    private fun initView() {
        initContent()
        initCard()
    }

    private fun initCard() {
        mCardAdapter = DinnerCardAdapter().apply {
            //反向填充数据确保合理性
            cardList.addAll(DinnerDataHelper.getDefaultCardByItem(mItemAdapter.cardItemList))
        }
        cardRv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = mCardAdapter
        }
    }

    private fun initContent() {
        mItemAdapter = DinnerItemAdapter().apply {
            cardItemList.addAll(DinnerDataHelper.getDefaultCardList(9))
        }

        contentRv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = mItemAdapter
        }
    }

    fun goBack(view: View) {
        supportFinishAfterTransition()
    }
}