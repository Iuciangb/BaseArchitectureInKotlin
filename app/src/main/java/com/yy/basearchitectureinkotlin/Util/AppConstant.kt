package com.yy.basearchitectureinkotlin.Util

/**
 * @author YY
 * Created 2022/3/13
 * Description：
 */
class AppConstant {
    object RequestCode {
        const val MOBILE_QUICK = 2
        const val PERMISSION_ALL = 3
        const val PERMISSION_WRITE_EXTERNAL_STORAGE = 4
        const val PERMISSION_READ_EXTERNAL_STORAGE = 5
        const val PERMISSION_CAMERA = 6
        const val PERMISSION_ACCESS_FINE_LOCATION = 7
        const val PERMISSION_RECORD_AUDIO = 8
    }

    object ResultCode {
        const val MOBILE_CAPTCHA_SUCCESS = 2
        const val REGISTER_BACK_LOGIN = 3
        const val FIND_PASSWORD_BACK_LOGIN = 4
        const val BIND_PHONE_SUCCESS = 5
        const val BIND_EMAIL_SUCCESS = 6
    }

    object Key {
        const val PHONE = "phone"
        const val CODE = "code"
        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val DATA_LIST = "data_list"
        const val AUTHORIZATION = "Authorization"
    }

    object NotificationChannelId {
        const val WEB_SOCKET = "web_socket"

    }

    object NotificationChannelName {
        const val WEB_SOCKET = "WebSocket is running"
    }

    object ErrorCode {
        /**
         * 表單驗證失敗
         */
        const val CODE_VALIDATE_FAILURE = 1001

        /**
         * 沒有存在的 帳戶名/郵件/手機
         */
        const val NO_EXISTED_ACCOUNT_PHONE_EMAIL = 2006

        /**
         * 新密碼與舊密碼重複
         */
        const val NEW_PASSWORD_SAME_AS_OLD_PASSWORD = 2007

        /**
         * 驗證碼與簡訊不同
         */
        const val SMS_VERIFY_ERROR = 2008

        /**
         * 驗證碼逾時
         */
        const val SMS_VERIFY_TIME_ERROR = 2009

        /**
         * 用戶名稱未找到
         */
        const val USERNAME_NOT_FOUND = 2013

        /**
         * 用戶手機未找到
         */
        const val PHONE_NUMBER_NOT_FOUND = 2014

        /**
         * 用戶電子郵件未找到
         */
        const val EMAIL_NOT_FOUND = 2015
    }

    object Api {
        /**
         * 獲取已綁定設備列表
         */
        const val URL_CHECK_YOURSELF_LIST = "/api/v1/user/devices"
    }
}