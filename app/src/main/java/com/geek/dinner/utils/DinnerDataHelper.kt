package com.geek.dinner.utils

import android.text.TextUtils
import android.util.Log
import com.geek.dinner.bean.CardItemParams
import com.geek.dinner.bean.CardParams
import java.text.SimpleDateFormat
import java.util.*

/**
 * Dinner data helper
 *
 * @constructor Create empty Dinner data helper
 */
class DinnerDataHelper {

    companion object {
        private const val TAG = "DinnerDataHelper"
        private const val BREAKFAST = 0
        private const val LUNCH = 1
        private const val DINNER = 2

        private val DateFormat = SimpleDateFormat("yyyy.MM.dd")
        private val TimeFormat = SimpleDateFormat("HH:mm")

        private const val BREAKFAST_STR = "早餐"//😊 trick
        private const val LUNCH_STR = "午餐"
        private const val DINNER_STR = "晚餐"

        fun getDefaultCardByItem(itemList: MutableList<CardItemParams>): MutableList<CardParams> {
            val cardList = mutableListOf<CardParams>()
            itemList.forEachIndexed { index, value ->
                //填充数据
                if (value.leftName.isNotBlank()) {
                    //region fill lunch card data
                    cardList.add(
                        CardParams(
                            value.leftName,
                            value.dateStr,
                            value.leftTimeStr,
                            LUNCH,
                            // TODO: 2021/3/31 need fix more real data
                            index == 0
                        )
                    )
                    //endregion

                    //region fill dinner card data
                    if (value.rightName.isNotBlank()) {
                        cardList.add(
                            CardParams(
                                value.rightName,
                                value.dateStr,
                                value.rightTimeStr,
                                DINNER,
                                // TODO: 2021/3/31 need fix more real data
                                index == 0
                            )
                        )
                    }
                    //endregion
                }
            }
            return cardList
        }

        /**
         * 获取下面展示列表
         */
        fun getDefaultCardList(randomValue: Int): MutableList<CardItemParams> {
            val list = mutableListOf<CardItemParams>()
            //add today data
            list.add(getDefaultCardItemParams(0, true))
            //add fake random data
            val random = Random()
            repeat(random.nextInt(randomValue)) {
                val dateItemParams = getDefaultCardItemParams(it + 1, random.nextBoolean())
                if (!TextUtils.isEmpty(dateItemParams.dateStr)) {
                    list.add(dateItemParams)
                }
            }
            return list
        }

        /**
         * Get default card item params
         *
         * @param dayCount
         * @param hasDinner
         * @return
         */
        private fun getDefaultCardItemParams(dayCount: Int, hasDinner: Boolean): CardItemParams {
            return CardItemParams(
                LUNCH_STR,
                if (hasDinner) {
                    DINNER_STR
                } else {
                    ""
                },
                getDateStr(dayCount),
                getFixCurrentTimeStr(false),
                getFixCurrentTimeStr(true)
            )
        }

        /**
         * Get default card
         *
         * @param isActive
         * @return
         */
        fun getDefaultCard(isActive: Boolean): CardParams {
            return CardParams(
                getCurrentName(),
                getCurrentDateStr(),
                getCurrentTimeStr(),
                LUNCH,
                isActive
            )
        }

        /**
         * Get current name
         *
         * @return
         */
        private fun getCurrentName(): String {
            val morning = getLevelDate(9, 0)
            val noon = getLevelDate(11, 15)
            val noonEnd = getLevelDate(13, 30)
//            val afternoon = getLevelDate(17, 45, 0)
            val currentData = Date()

            return if (currentData >= morning && currentData < noon) {
                BREAKFAST_STR
            } else if (currentData >= noon && currentData < noonEnd) {
                LUNCH_STR
            } else {
                DINNER_STR
            }
        }

        /**
         * 自动获取合适时间
         * @param isDinner 是否是晚餐
         */
        private fun getFixCurrentTimeStr(isDinner: Boolean): String {
            val level = getCurrentLevel()
            if (isDinner) {
                return if (level in 7..8) {
                    //命中晚餐 正常逻辑
                    getCurrentTimeStr()
                } else {
                    //自动填充一个晚餐
                    getTimeStrByLevel(7 + Random().nextInt(2))
                }
            } else {
                return if (level in 1..5) {
                    //命中午餐
                    getCurrentTimeStr()
                } else {
                    //自动填充一个午餐
                    getTimeStrByLevel(Random().nextInt(6))
                }
            }


        }

        /**
         * Get current level
         *
         * @return
         */
        private fun getCurrentLevel(): Int {
            val level1 = getLevel(1)
            val level2 = getLevel(2)
            val level3 = getLevel(3)
            val level4 = getLevel(4)
            val level5 = getLevel(5)
            val level6 = getLevel(6)
            val level7 = getLevel(7)
            val level8 = getLevel(8)
            val level9 = getLevel(9)
            val currentData = Date()
            var level = 0
            if (currentData >= level1 && currentData < level2) {
                level = 1
            } else if (currentData >= level2 && currentData < level3) {
                level = 2
            } else if (currentData >= level3 && currentData < level4) {
                level = 3
            } else if (currentData >= level4 && currentData < level5) {
                level = 4
            } else if (currentData >= level5 && currentData < level6) {
                level = 5
            } else if (currentData >= level7 && currentData < level8) {
                level = 7
            } else if (currentData >= level8 && currentData < level9) {
                level = 8
            }
            return level
        }

        /**
         * 获取合适时机填充字段
         */
        private fun getCurrentTimeStr(): String {
            val currentData = Date()
            return when (getCurrentLevel()) {
                1 -> getTimeStrByLevel(1)
                2 -> getTimeStrByLevel(2)
                3 -> getTimeStrByLevel(3)
                4 -> getTimeStrByLevel(4)
                5 -> getTimeStrByLevel(5)
                7 -> getTimeStrByLevel(7)
                8 -> getTimeStrByLevel(8)
                else -> "${getCardTimeStr(currentData)} - ${getCardTimeStr(Date(System.currentTimeMillis() + 1000 * 60 * 30))}"
            }
        }


        /**
         * 获取指定时间的Date
         */
        private fun getLevelDate(hour: Int, minute: Int): Date {
            return Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }.time
        }


        /**
         * Get date str
         *
         * @param dayCount
         * @return
         */
        private fun getDateStr(dayCount: Int): String {
            Log.d(TAG, "getDateStr() called with: dayCount = $dayCount")
            if (dayCount == 0) {
                return getCurrentDateStr()
            }
            val date = Calendar.getInstance().apply {
                set(
                    Calendar.DAY_OF_MONTH,
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - dayCount
                )
            }
            //如果是周六或周日
            if (date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
            ) {
                return ""
            }
            return DateFormat.run {
                format(date.time)
            }

        }

        /**
         * 获取日期字符串
         */
        private fun getCurrentDateStr(): String {
            return DateFormat.format(Date())
        }

        /**
         * 获取时间段字符串
         */
        private fun getCardTimeStr(date: Date): String {
            return TimeFormat.format(date)
        }

        /**
         * 根据level获取字符串
         */
        private fun getTimeStrByLevel(level: Int): String {
            return when (level) {
                1 -> "${getCardTimeStr(getLevel(1))} - ${getCardTimeStr(getLevel(2))}"
                2 -> "${getCardTimeStr(getLevel(2))} - ${getCardTimeStr(getLevel(3))}"
                3 -> "${getCardTimeStr(getLevel(3))} - ${getCardTimeStr(getLevel(4))}"
                4 -> "${getCardTimeStr(getLevel(4))} - ${getCardTimeStr(getLevel(5))}"
                5 -> "${getCardTimeStr(getLevel(5))} - ${getCardTimeStr(getLevel(6))}"
                7 -> "${getCardTimeStr(getLevel(7))} - ${getCardTimeStr(getLevel(8))}"
                8 -> "${getCardTimeStr(getLevel(8))} - ${getCardTimeStr(getLevel(9))}"
                else -> {
                    "${getCardTimeStr(Date())} - ${getCardTimeStr(Date(System.currentTimeMillis() + 1000 * 60 * 30))}"
                }
            }
        }

        private fun getLevel(level: Int): Date {
            return when (level) {
                1 -> getLevelDate(11, 15)
                2 -> getLevelDate(11, 45)
                3 -> getLevelDate(12, 15)
                4 -> getLevelDate(12, 20)
                5 -> getLevelDate(12, 50)
                6 -> getLevelDate(13, 30)
                7 -> getLevelDate(17, 45)
                8 -> getLevelDate(18, 30)
                else -> getLevelDate(19, 15)
            }
        }

    }
}