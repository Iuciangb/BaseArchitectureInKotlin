package com.yy.baselibrary.util

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.HashMap

/**
 * @author YY
 * Created 2022/2/28
 * Description：
 */
class SharedPreferencesHelper private constructor() {
    private val mSet: Set<String> = HashSet()

    companion object {
        private const val PREF_SP_NAME = "pref_base_library"

        val instance: SharedPreferencesHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SharedPreferencesHelper()
        }
    }

    fun save(context: Context, key: String, value: String) {
        putSPString(context, key, value)
    }

    fun save(context: Context, key: String, value: Int) {
        putSPInt(context, key, value)
    }

    fun save(context: Context, key: String, value: Float) {
        putSPFloat(context, key, value)
    }

    fun save(context: Context, key: String, value: Boolean) {
        putSPBoolean(context, key, value)
    }

    fun save(context: Context, key: String, value: Set<String>) {
        putSPSet(context, key, value)
    }

    fun save(context: Context, key: String, list: List<Any>) {
        putSPString(context, key, Gson().toJson(list))
    }

    fun save(context: Context, key: String, value: Long) {
        putSPLong(context, key, value)
    }

    /**
     * 儲存序列化對象，
     * 但是要記得，只能存Serializable對象，不能存Parcelable對象。
     *
     * @param key
     * @param object
     */
    fun save(context: Context, key: String, `object`: Any) {
        putSPObject(context, key, `object`)
    }

    fun <K, V> save(context: Context, key: String, map: Map<K, V>?) {
        putSPString(context, key, Gson().toJson(map))
    }

    fun saveHashMap(context: Context, key: String, obj: Any) {
        val gson = Gson()
        val json = gson.toJson(obj)
        putSPString(context, key, json) // This line is IMPORTANT !!!
    }

    private fun putSPString(context: Context, key: String, value: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun putSPInt(context: Context, key: String, value: Int) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    private fun putSPFloat(context: Context, key: String, value: Float) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    private fun putSPBoolean(context: Context, key: String, value: Boolean) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun putSPLong(context: Context, key: String, value: Long) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    private fun putSPSet(context: Context, key: String, set: Set<String>) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putStringSet(key, set)
        editor.apply()
    }

    /**
     * 將序列化對象轉成16進制儲存，
     * 因為相較於JSON或XML，
     * 16進制生成的文件更小性能更快，
     * 但是要記得，只能存Serializable對象，不能存Parcelable對象。
     */
    private fun putSPObject(context: Context, key: String, `object`: Any) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(`object`)
            editor.putString(key, bytesToHexString(byteArrayOutputStream.toByteArray()))
            editor.apply()
            objectOutputStream.close()
            byteArrayOutputStream.close()
        } catch (e: IOException) {
            Logger.e(e.message)
        }
    }

    /**
     * 保存Bean(有沒有泛型都兼容)
     */
    fun <T> putSPBean(context: Context, key: String, t: T) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val gson = Gson()
        editor.putString(key, gson.toJson(t))
        editor.apply()
    }

    fun getSPString(context: Context, key: String?): String? {
        return getSPString(context, key, "")
    }

    fun getSPString(context: Context, key: String?, defValue: String?): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, defValue)
    }

    fun getSPFloat(context: Context, key: String?): Float {
        return getSPFloat(context, key, 0f)
    }

    fun getSPFloat(context: Context, key: String?, defValue: Float): Float {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getFloat(key, defValue)
    }

    fun getSPInt(context: Context, key: String?): Int {
        return getSPInt(context, key, 0)
    }

    fun getSPInt(context: Context, key: String?, defValue: Int): Int {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, defValue)
    }

    fun getSPBoolean(context: Context, key: String?): Boolean {
        return getSPBoolean(context, key, false)
    }

    fun getSPBoolean(context: Context, key: String?, defValue: Boolean): Boolean {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, defValue)
    }

    fun getSPLong(context: Context, key: String?): Long {
        return getSPLong(context, key, 0)
    }

    fun getSPLong(context: Context, key: String?, defValue: Long): Long {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getLong(key, defValue)
    }

    fun getSPSet(context: Context, key: String?): Set<String>? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getStringSet(key, mSet)
    }

    fun getSPSet(context: Context, key: String?, defValue: Set<String?>?): Set<String>? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getStringSet(key, defValue)
    }

    fun <V> getSPMap(context: Context, key: String?, vClass: Class<V>?): Map<String, V> {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        val map: MutableMap<String, V> = HashMap()
        val json = sharedPreferences.getString(key, "")
        if (TextUtils.isEmpty(json)) {
            return HashMap()
        }
        val gson = Gson()
        val jsonObject: JsonObject = JsonParser().parse(json).asJsonObject
        val entrySet: Set<Map.Entry<String, JsonElement?>> = jsonObject.entrySet()
        for ((entryKey) in entrySet) {
            val entryValue: V = gson.fromJson(jsonObject.get(entryKey), vClass)
            map[entryKey] = entryValue
        }
        return map
    }

    fun getHashMap(context: Context, key: String): HashMap<Int, String> {
        val gson = Gson()
        val json = getSPString(context, key)
        val type: Type = object : TypeToken<HashMap<Int?, String>>() {}.type
        if (json.isNullOrEmpty()) {
            return HashMap()
        }
        return gson.fromJson(json, type)
    }

    fun getSPObject(context: Context, key: String?): Any? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        var `object`: Any? = null
        if (sharedPreferences.contains(key)) {
            val hexString = sharedPreferences.getString(key, "")
            if (!TextUtils.isEmpty(hexString)) {
                val byteArray = hexStringToBytes(hexString)
                val byteArrayInputStream = ByteArrayInputStream(byteArray)
                try {
                    val objectInputStream = ObjectInputStream(byteArrayInputStream)
                    `object` = objectInputStream.readObject()
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
        return `object`
    }

    /**
     * 獲取帶泛型的Bean
     */
    fun <T> getSPGenericBean(context: Context, key: String, type: Type): T {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(key, "")
        val gson = Gson()
        return gson.fromJson(json, type)
    }

    /**
     * 獲取不帶泛型的Bean
     */
    fun <T> getBean(context: Context, key: String, classOfT: Class<T>): T {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(key, "")
        val gson = Gson()
        return gson.fromJson(json, classOfT)
    }

    /**
     * 將byteArray轉為16進制
     *
     * @param bytes
     * @return
     */
    private fun bytesToHexString(bytes: ByteArray?): String {
        if (bytes == null) {
            return ""
        }
        if (bytes.isEmpty()) {
            return ""
        }
        val stringBuffer = StringBuffer(bytes.size)
        for (i in bytes.indices) {
            val temp = Integer.toHexString(0xFF and bytes[i].toInt())
            if (temp.length < 2) {
                stringBuffer.append(0)
            }
            stringBuffer.append(temp.uppercase(Locale.getDefault()))
        }
        return stringBuffer.toString()
    }

    /**
     * 將16進制字串轉為byteArray
     *
     * @param binaryString
     * @return
     */
    private fun hexStringToBytes(binaryString: String?): ByteArray? {
        val hexString = binaryString!!.uppercase(Locale.getDefault()).trim { it <= ' ' }
        if (hexString.length % 2 != 0) {
            return null
        }
        val byteArray = ByteArray(hexString.length / 2)
        var i = 0
        while (i < hexString.length) {
            byteArray[i / 2] = hexToByte(hexString.substring(i, i + 2))
            i += 2
        }
        return byteArray
    }

    private fun hexToByte(hex: String): Byte {
        val firstDigit = convertToDigit(hex[0])
        val secondDigit = convertToDigit(hex[1])
        return ((firstDigit shl 4) + secondDigit).toByte()
    }

    private fun convertToDigit(hexChar: Char): Int {
        val digit = Character.digit(hexChar, 16)
        require(digit != -1) { "Invalid Hexadecimal Character: $hexChar" }
        return digit
    }

    fun clearSP(context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.commit()
    }
}