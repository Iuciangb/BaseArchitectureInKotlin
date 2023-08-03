package com.yy.baselibrary

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.yy.baselibrary.util.ActivityManager
import com.yy.baselibrary.util.FitUtil
import com.yy.baselibrary.util.FunctionUtil
import com.yy.baselibrary.util.LocaleConfigurationWrapperManager
import com.yy.baselibrary.util.LocaleConfigurationWrapperManager.wrapLocale
import com.yy.baselibrary.util.ToastUtil.showToast
import java.util.*
import kotlin.system.exitProcess


/**
 * @author YY
 * Created 2022/3/1
 * Description：
 */
abstract class BaseActivity : AppCompatActivity() {
    protected var mIsFirstShow = true
    private var exitTime: Long = 0

    private var mActivityResultLauncher: ActivityResultLauncher<Intent>? = null

    companion object {
        private const val TYPE_NONE = 0
        private const val TYPE_LEFT_TO_RIGHT = 1
        private const val TYPE_RIGHT_TO_LEFT = 2
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun attachBaseContext(newBase: Context?) {
        val localeItem: LocaleConfigurationWrapperManager.LocaleItem =
            LocaleConfigurationWrapperManager.getLocaleConfiguration(newBase)
        super.attachBaseContext(
            wrapLocale(
                newBase!!,
                Locale(localeItem.localeLanguage, localeItem.localeCountry)
            )
        )
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FitUtil.setCustomDensity(this, application)
        ActivityManager.instance?.addActivity(this)
        initView()
    }

    override fun onStart() {
        super.onStart()
        mActivityResultLauncher = registerForActivityResult(
            StartActivityForResult()
        ) { result ->
            onActivityResult(result.resultCode, result.data)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val view: View? = currentFocus
        val ret = super.dispatchTouchEvent(event)
        if (view is EditText) {
            val w: View? = currentFocus
            val scores = IntArray(2)
            w?.getLocationOnScreen(scores)
            val x: Float = event.rawX + w?.left!! - scores[0]
            val y: Float = event.rawY + w?.top!! - scores[1]
            if (event.action == MotionEvent.ACTION_UP && (x < w?.left ?: 0 || x >= w.right || y < w.top || y > w.bottom)) {
                val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(window.currentFocus!!.windowToken, 0)
            }
        }
        return ret
    }

    override fun onBackPressed() {
        finish()
        if (!isFinishToBottom()) {
            overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right)
        } else {
            overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.instance?.removeActivity(this)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK
            && event!!.action == KeyEvent.ACTION_DOWN
        ) {
            //点击两次退出登录判断
            if (isActivityExitAppTips()) {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    showToast(this, "再按一次将退出")
                    exitTime = System.currentTimeMillis()
                } else {
                    finish()
                    exitProcess(0)
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 点击返回取消正在请求的对话框
     *
     * @return
     */
    open fun isCancelRequestOnClickBack(): Boolean {
        return false
    }

    //设置是否冲底部关闭
    open fun isFinishToBottom(): Boolean {
        return false
    }

    /**
     * activity是否具有点击返回按钮提示以后退出app提示
     *
     * @return
     */
    open fun isActivityExitAppTips(): Boolean {
        return false
    }

    private fun initView() {

    }

    open fun onActivityResult(resultCode: Int, intent: Intent?) {

    }

    protected fun launchHomeButton() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)
    }

    override fun startActivity(intent: Intent?) {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
        super.startActivity(intent)
    }

    fun startActivityForResult(intent: Intent?) {
        if (mActivityResultLauncher == null) {
            return
        }
        val activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(baseContext, R.anim.slide_in_from_right, R.anim.slide_out_to_left)
        mActivityResultLauncher?.launch(intent, activityOptionsCompat)
    }

    protected fun changeFragmentWithLeftToRightAnimation(replaceLayoutId: Int, fragment: Fragment) {
        changeFragment(replaceLayoutId, fragment)
    }

    protected fun changeFragmentWithRightToLeftAnimation(replaceLayoutId: Int, fragment: Fragment) {
        changeFragment(replaceLayoutId, fragment)
    }

    protected fun changeFragment(replaceLayoutId: Int, fragment: Fragment) {
        changeFragment(replaceLayoutId, fragment, null)
    }

    protected fun changeFragment(replaceLayoutId: Int, fragment: Fragment, tag: String?) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        if (TextUtils.isEmpty(tag)) {
            fragmentTransaction.replace(replaceLayoutId, fragment)
        } else {
            fragmentTransaction.replace(replaceLayoutId, fragment, tag)
        }
        fragmentTransaction.addToBackStack(fragment.javaClass.name)
        fragmentTransaction.commitAllowingStateLoss()
    }
}