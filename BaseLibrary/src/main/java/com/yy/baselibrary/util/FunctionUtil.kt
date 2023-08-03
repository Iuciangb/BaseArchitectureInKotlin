package com.yy.baselibrary.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import com.yy.baselibrary.R
import java.io.*
import java.text.DecimalFormat

/**
 * @author YY
 * Created 2022/2/28
 * Description：
 */
object FunctionUtil {
    private const val RES_TYPE_STRING = "string"
    private const val RES_TYPE_DRAWABLE = "drawable"
    private const val RES_TYPE_MIPMAP = "mipmap"
    private const val FORMAT_TWO_DIGIT_FLOAT = "##0.00"
    private const val FORMAT_THREE_COMMA_FLOAT = ",##0.00"

    /**
     * 判斷字串是否存在
     */
    fun isStringResValid(context: Context, resId: Int): Boolean {
        return isResValid(context, resId, RES_TYPE_STRING)
    }

    /**
     * 判斷圖片是否存在
     */
    fun isDrawableResValid(context: Context, resId: Int): Boolean {
        return isResValid(context, resId, RES_TYPE_DRAWABLE) ||
                isResValid(context, resId, RES_TYPE_MIPMAP)
    }

    /**
     * 判斷resource是否存在
     */
    private fun isResValid(context: Context, resId: Int, type: String): Boolean {
        val checkExistence: Int =
            context.resources
                .getIdentifier(resId.toString(), type, context.packageName)
        return checkExistence > 0
    }

    /**
     * 隱藏鍵盤
     */
    fun hideSoftKeyboard(activity: Activity) {
        val view: View = activity.findViewById(android.R.id.content)
        if (view != null) {
            val imm: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * 隱藏鍵盤
     */
    fun hideSoftKeyboard(view: View?) {
        if (view != null) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * 把DP轉PX
     */
    fun convertDpToPixel(context: Context?, dp: Int): Int {
        return dp * getDensity(context!!).toInt()
    }

    private fun getDensity(context: Context): Float {
        return context?.resources?.displayMetrics?.density!!
    }

    /**
     * 取到小數點兩位
     */
    fun getTwoDigitFloat(number: Float): String? {
        val decimalFormat = DecimalFormat(FORMAT_TWO_DIGIT_FLOAT)
        return decimalFormat.format(number.toDouble())
    }

    fun copy(context: Context, text: String?) {
        if (TextUtils.isEmpty(text)) {
            return
        }
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(text, text)
        clipboardManager.setPrimaryClip(clipData)
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        val deviceId: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } else {
            val mTelephony: TelephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return ""
                }
            }
            assert(mTelephony != null)
            if (mTelephony.deviceId != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mTelephony.imei
                } else {
                    mTelephony.deviceId
                }
            } else {
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            }
        }
        Log.d("deviceId", deviceId)
        return deviceId
    }

    fun isEmailFormatValid(email: String): Boolean {
        if (email.isNullOrEmpty()) {
            return false
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun getFormatLeadingNumber(number: Int): String? {
        val length = number.toString().length
        val isOdd = length % 2 > 0
        if (isOdd) {
            val format = "%0" + (length + 1) + "d"
            return String.format(format, number)
        }
        return number.toString() + ""
    }

    fun getDomainPing(domain: String): Long {
        var timeOfPing: Long = 0
        val runtime: Runtime = Runtime.getRuntime()
        try {
            val a = System.currentTimeMillis() % 100000
            val ipProcess: Process = runtime.exec("/system/bin/ping -c 1 $domain")
            ipProcess.waitFor()
            val b = System.currentTimeMillis() % 100000
            timeOfPing = if (b <= a) {
                ((100000 - a) + b)
            } else {
                (b - a)
            }
        } catch (e: Exception) {
            Logger.e(e.message)
        }
        return timeOfPing
    }

    /**
     * 計算StatusBar高度
     */
    fun getStatusBarHeight(context: Context, window: Window): Int {
        val localRect = Rect()
        window.decorView.getWindowVisibleDisplayFrame(localRect)
        var mStatusBarHeight = localRect.top
        if (0 == mStatusBarHeight) {
            try {
                val localClass = Class.forName("com.android.internal.R\$dimen")
                val localObject = localClass.newInstance()
                val i5 =
                    localClass.getField("status_bar_height")[localObject].toString().toInt()
                mStatusBarHeight = context.resources.getDimensionPixelSize(i5)
            } catch (var6: ClassNotFoundException) {
                var6.printStackTrace()
            } catch (var7: IllegalAccessException) {
                var7.printStackTrace()
            } catch (var8: InstantiationException) {
                var8.printStackTrace()
            } catch (var9: NumberFormatException) {
                var9.printStackTrace()
            } catch (var10: IllegalArgumentException) {
                var10.printStackTrace()
            } catch (var11: SecurityException) {
                var11.printStackTrace()
            } catch (var12: NoSuchFieldException) {
                var12.printStackTrace()
            }
        }
        if (0 == mStatusBarHeight) {
            val resourceId: Int =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                mStatusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            }
        }
        return mStatusBarHeight
    }

    fun saveMediaToStorage(context: Context, bitmap: Bitmap?, contentResolver: ContentResolver, filename: String) {
        val fileName = "$filename.jpg"
        var outputStream: OutputStream?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentResolver.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                outputStream = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, fileName)
            outputStream = FileOutputStream(image)
        }
        outputStream?.use {
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
            ToastUtil.showToast(context, R.string.text_save_image_success)
        }
    }

    fun getShareIntent(content: String): Intent {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, content)
            type = "text/plain"
        }

        return Intent.createChooser(sendIntent, null)
    }

    /**
     * 每三位數一個逗點
     */
    fun getThreeCommaFloat(number: Float): String? {
        val decimalFormat = DecimalFormat(FORMAT_THREE_COMMA_FLOAT)
        return decimalFormat.format(number.toDouble())
    }
}