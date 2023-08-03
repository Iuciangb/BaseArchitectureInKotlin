package com.yy.baselibrary.util

import android.content.Context

import android.text.TextUtils
import com.yy.baselibrary.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


/**
 * @author YY
 * Created 2022/3/20
 * Description：
 */
object DateFormatManager {
    private const val SYMBOL_COLON = ":"
    private const val SIMPLE_DATE_FORMAT_DASH = "yyyy-MM-dd HH:mm:ss"
    private const val SIMPLE_DATE_FORMAT_SLASH = "yyyy/MM/dd HH:mm:ss"
    private const val SIMPLE_DATE_FORMAT_WITHOUT_DASH = "yyyyMMddHHmmss"
    private const val SLASH_DATE_WITHOUT_TIME = "yyyy/MM/dd"
    private const val DASH_DATE_WITHOUT_TIME = "yyyy-MM-dd"
    private const val DOT_DATE_WITHOUT_TIME = "yyyy.MM.dd"
    private const val SLASH_DATE_WITH_DAY_WITHOUT_TIME = "yyyy/MM/dd E"
    private const val TIME_WITH_A = "a HH:mm"
    private const val ONLY_YEAR_AND_MONTH_WITH_DASH = "yyyy-MM"
    private const val ONLY_YEAR_AND_MONTH_WITH_SLASH = "yyyy/MM"
    private const val ONLY_MONTH_AND_DAY = "MM/dd"
    private const val DAY_SUNDAY = 1
    private const val DAY_MONDAY = 2
    private const val DAY_TUESDAY = 3
    private const val DAY_WEDNESDAY = 4
    private const val DAY_THURSDAY = 5
    private const val DAY_FRIDAY = 6
    private const val DAY_SATURDAY = 7

    /**
     * "yyyy-MM-dd HH:mm:ss"
     */
    fun getSimpleDateFormatDashText(dateTimeMillis: Long): String {
        return getSimpleDateFormat(SIMPLE_DATE_FORMAT_DASH).format(dateTimeMillis)
    }

    /**
     * "yyyy-MM-dd HH:mm:ss"
     */
    fun getSimpleDateFormatDashDate(dateText: String?): Date? {
        if (TextUtils.isEmpty(dateText)) {
            return Date(System.currentTimeMillis())
        }
        var date: Date?
        try {
            date = getSimpleDateFormat(SIMPLE_DATE_FORMAT_DASH).parse(dateText)
        } catch (e: ParseException) {
            date = Date(System.currentTimeMillis())
            e.printStackTrace()
        }
        return date
    }

    /**
     * "yyyy/MM/dd HH:mm:ss"
     */
    fun getSimpleDateFormatSlashText(dateTimeMillis: Long): String {
        return getSimpleDateFormat(SIMPLE_DATE_FORMAT_SLASH).format(dateTimeMillis)
    }

    /**
     * "yyyyMMddHHmmss"
     */
    fun getSimpleDateFormatWithoutDashText(dateTimeMillis: Long): String {
        return getSimpleDateFormat(SIMPLE_DATE_FORMAT_WITHOUT_DASH).format(dateTimeMillis)
    }

    /**
     * "yyyyMMddHHmmss"
     */
    fun getSimpleDateFormatWithoutDashTextFromDate(dateText: String?): Date? {
        if (TextUtils.isEmpty(dateText)) {
            return Date(System.currentTimeMillis())
        }
        var date: Date?
        try {
            date = getSimpleDateFormat(SIMPLE_DATE_FORMAT_WITHOUT_DASH).parse(dateText)
        } catch (e: ParseException) {
            date = Date(System.currentTimeMillis())
            e.printStackTrace()
        }
        return date
    }

    /**
     * "yyyy/MM/dd"
     */
    fun getSlashDateWithoutTimeText(dateTimeMillis: Long): String {
        return getSimpleDateFormat(SLASH_DATE_WITHOUT_TIME).format(dateTimeMillis)
    }

    /**
     * "yyyy/MM/dd"
     */
    fun getSlashDateWithoutTimeTextFromDate(dateText: String?): Date? {
        if (TextUtils.isEmpty(dateText)) {
            return Date(System.currentTimeMillis())
        }
        var date: Date?
        try {
            date = getSimpleDateFormat(SLASH_DATE_WITHOUT_TIME).parse(dateText)
        } catch (e: ParseException) {
            date = Date(System.currentTimeMillis())
            e.printStackTrace()
        }
        return date
    }

    /**
     * "yyyy-MM-dd"
     */
    fun getDashDateWithoutTimeText(dateTimeMillis: Long): String {
        return getSimpleDateFormat(DASH_DATE_WITHOUT_TIME).format(dateTimeMillis)
    }

    /**
     * "yyyy-MM-dd"
     */
    fun getDashDateWithoutTimeTextFromDate(dateText: String?): Date? {
        if (TextUtils.isEmpty(dateText)) {
            return Date(System.currentTimeMillis())
        }
        var date: Date?
        try {
            date = getSimpleDateFormat(DASH_DATE_WITHOUT_TIME).parse(dateText)
        } catch (e: ParseException) {
            date = Date(System.currentTimeMillis())
            e.printStackTrace()
        }
        return date
    }

    /**
     * "yyyy.MM.dd"
     */
    fun getDotDateWithoutTimeText(dateTimeMillis: Long): String {
        return getSimpleDateFormat(DOT_DATE_WITHOUT_TIME).format(dateTimeMillis)
    }

    /**
     * "yyyy.MM.dd"
     */
    fun getDotDateWithoutTimeTextFromDate(dateText: String?): Date? {
        if (TextUtils.isEmpty(dateText)) {
            return Date(System.currentTimeMillis())
        }
        var date: Date?
        try {
            date = getSimpleDateFormat(DOT_DATE_WITHOUT_TIME).parse(dateText)
        } catch (e: ParseException) {
            date = Date(System.currentTimeMillis())
            e.printStackTrace()
        }
        return date
    }

    /**
     * "yyyy/MM/dd E"
     */
    fun getSlashDateWithDayWithoutTimeText(dateTimeMillis: Long): String {
        return getSimpleDateFormat(SLASH_DATE_WITH_DAY_WITHOUT_TIME).format(dateTimeMillis)
    }

    /**
     * "yyyy/MM/dd E"
     */
    fun getSlashDateWithDayWithoutTimeTextFromDate(dateText: String?): Date? {
        if (TextUtils.isEmpty(dateText)) {
            return Date(System.currentTimeMillis())
        }
        var date: Date?
        try {
            date = getSimpleDateFormat(SLASH_DATE_WITH_DAY_WITHOUT_TIME).parse(dateText)
        } catch (e: ParseException) {
            date = Date(System.currentTimeMillis())
            e.printStackTrace()
        }
        return date
    }

    fun getTimeWithA(dateTimeMillis: Long): String {
        return getSimpleDateFormat(TIME_WITH_A).format(dateTimeMillis)
    }

    fun getYearAndMonthWithDash(dateTimeMillis: Long): String {
        return getSimpleDateFormat(ONLY_YEAR_AND_MONTH_WITH_DASH).format(dateTimeMillis)
    }

    fun getYearAndMonthWithSlash(dateTimeMillis: Long): String {
        return getSimpleDateFormat(ONLY_YEAR_AND_MONTH_WITH_SLASH).format(dateTimeMillis)
    }

    fun getMonthAndDay(dateTimeMillis: Long): String {
        return getSimpleDateFormat(ONLY_MONTH_AND_DAY).format(dateTimeMillis)
    }

    private fun getSimpleDateFormat(format: String): SimpleDateFormat {
        val simpleDateFormat = SimpleDateFormat(format)
        simpleDateFormat.timeZone = TimeZone.getDefault()
        return simpleDateFormat
    }

    fun calculateTimeInterval(firstDate: Date, secondDate: Date, timeIntervalType: TimeIntervalType?): Long {
        val millisSecondDay = (1000 * 60 * 60 * 24).toLong()
        val millisSecondHour = (1000 * 60 * 60).toLong()
        val millisSecondMinute = (1000 * 60).toLong()
        val different: Long = abs(secondDate.time - firstDate.time)
        var timeInterval: Long = 0
        when (timeIntervalType) {
            TimeIntervalType.DAY -> timeInterval = different / millisSecondDay
            TimeIntervalType.HOUR -> timeInterval = different / millisSecondHour
            TimeIntervalType.MINUTE -> timeInterval = different / millisSecondMinute
            else -> {
                0
            }
        }
        return timeInterval
    }

    fun getFirstMonthDayCalendar(calendar: Calendar): Calendar {
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        return calendar
    }

    fun getLastMonthDayCalendar(calendar: Calendar): Calendar {
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        return calendar
    }

    /**
     * 取得目標日期與現在時間相差多少分鐘
     *
     * @param activeTime
     * @return
     */
    fun getOfflineMinute(activeTime: String?): Long {
        val lastActiveCalendar: Date? = getSimpleDateFormatDashDate(activeTime)
        val currentDate = Date(System.currentTimeMillis())
        return ((currentDate.time - lastActiveCalendar?.time!!) / (1000 * 60))
    }

    fun isToday(dateText: String?): Boolean {
        val todayCalendar: Calendar = Calendar.getInstance()
        todayCalendar.set(Calendar.HOUR, 0)
        todayCalendar.set(Calendar.MINUTE, 0)
        todayCalendar.set(Calendar.SECOND, 0)
        val objectCalendar: Calendar = Calendar.getInstance()
        objectCalendar.time = getSimpleDateFormatDashDate(dateText)
        return objectCalendar.after(todayCalendar)
    }

    fun getDayText(context: Context, timeMillis: Long): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            DAY_SUNDAY -> context.getString(R.string.text_day_sunday)
            DAY_MONDAY -> context.getString(R.string.text_day_monday)
            DAY_TUESDAY -> context.getString(R.string.text_day_thursday)
            DAY_WEDNESDAY -> context.getString(R.string.text_day_wednesday)
            DAY_THURSDAY -> context.getString(R.string.text_day_thursday)
            DAY_FRIDAY -> context.getString(R.string.text_day_friday)
            else -> context.getString(R.string.text_day_saturday)
        }
    }

    fun isToday(date: Date?): Boolean {
        val todayCalendar: Calendar = Calendar.getInstance()
        val objectCalendar: Calendar = Calendar.getInstance()
        objectCalendar.time = date
        return todayCalendar.get(Calendar.YEAR) == objectCalendar.get(Calendar.YEAR) && todayCalendar.get(Calendar.MONTH) == objectCalendar.get(
            Calendar.MONTH
        ) && todayCalendar.get(Calendar.DAY_OF_MONTH) == objectCalendar.get(Calendar.DAY_OF_MONTH)
    }

    fun getDayTime(timeMillis: Long): String {
        val time = timeMillis / 1000
        val day: Long = time / (24 * 3600)
        val hour: Long = time % (24 * 3600) / 3600
        val minute: Long = time % 3600 / 60
        val second: Long = time - day * 24 * 60 * 60 - hour * 60 * 60 - minute * 60
        return FunctionUtil.getFormatLeadingNumber(day.toInt()) + SYMBOL_COLON +
                FunctionUtil.getFormatLeadingNumber(hour.toInt()) + SYMBOL_COLON +
                FunctionUtil.getFormatLeadingNumber(minute.toInt()) + SYMBOL_COLON +
                FunctionUtil.getFormatLeadingNumber(second.toInt())
    }

    enum class TimeIntervalType {
        DAY, HOUR, MINUTE
    }
}