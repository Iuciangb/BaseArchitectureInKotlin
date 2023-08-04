package com.yy.baselibrary.util

import android.text.TextUtils
import android.util.Log

/**
 * @author YY
 * Created 2022/2/28
 * Description：
 */
class Logger {
    companion object {
        private val TAG = Logger::class.java.simpleName
        private const val LOGGER_TAG = "/////"
        private const val SYMBOL_COLON = ":"
        private const val ROOT_PACKAGE = "com.yy.basearchitectureinkotlin"

        fun d(message: Int) {
            d(message.toString() + "")
        }

        fun d(message: String?) {
            if (TextUtils.isEmpty(message)) {
                return
            }
            Log.d(LOGGER_TAG, getTargetStackTraceElement() + message)
        }

        fun w(message: Int) {
            w(message.toString() + "")
        }

        fun w(message: String?) {
            if (TextUtils.isEmpty(message)) {
                return
            }
            Log.w(LOGGER_TAG, getTargetStackTraceElement() + message)
        }

        fun i(message: Int) {
            i(message.toString() + "")
        }

        fun i(message: String?) {
            if (TextUtils.isEmpty(message)) {
                return
            }
            Log.i(LOGGER_TAG, getTargetStackTraceElement() + message)
        }

        fun v(message: Int) {
            v(message.toString() + "")
        }

        fun v(message: String?) {
            if (TextUtils.isEmpty(message)) {
                return
            }
            Log.v(LOGGER_TAG, getTargetStackTraceElement() + message)
        }

        fun e(message: Int) {
            e(message.toString() + "")
        }

        fun e(message: String?) {
            if (TextUtils.isEmpty(message)) {
                return
            }
            Log.e(LOGGER_TAG, getTargetStackTraceElement() + message)
        }

        private fun getTargetStackTraceElement(): String {
            val stackTraceMessage = StringBuilder()
            val stackTrace = Thread.currentThread().stackTrace
            for (stackTraceElement in stackTrace) {
                val className = stackTraceElement.className
                if (className.contains(ROOT_PACKAGE)) {
                    if (className.contains(TAG)) {
                        continue
                    }
                    stackTraceMessage.insert(
                        0,
                        String.format(
                            "%s:%d:%s",
                            stackTraceElement.className,
                            stackTraceElement.lineNumber,
                            stackTraceElement.methodName
                        ) + "\n"
                    )
                }
            }
            return stackTraceMessage.toString()
        }
    }
}