package com.yy.baselibrary

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.yy.baselibrary.util.FunctionUtil

/**
 * @author YY
 * Created 2022/3/1
 * Description：
 */
open class BaseFragment : Fragment() {
    private val TAG = BaseFragment::class.java.simpleName
    private var mSavedState: Bundle? = null

    companion object {
        private const val TYPE_NONE = 0
        private const val TYPE_LEFT_TO_RIGHT = 1
        private const val TYPE_RIGHT_TO_LEFT = 2
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                initView()
                if (!restoreStateFromArguments()) {
                    onActivityCreatedFirstTimeLaunched()
                }
                owner.lifecycle.removeObserver(this)
            }
        })
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        view?.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP) {
                onBackPressed()
                true
            } else {
                false
            }
        }
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        saveStateToArguments()
    }

    /**
     * override用以做onActivityCreated中第一次進入動作
     */
    open fun onActivityCreatedFirstTimeLaunched() {}

    private fun initView() {
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState!!)
        saveStateToArguments()
    }

    private fun saveStateToArguments() {
        if (view == null) {
            return
        }
        mSavedState = saveState()
        if (mSavedState == null) {
            return
        }
        val bundle = arguments
        bundle?.putBundle(TAG, mSavedState)
    }

    private fun saveState(): Bundle? {
        val saveState = Bundle()
        onSavedState(saveState)
        return saveState
    }

    /**
     * override用以儲存state
     */
    protected fun onSavedState(outState: Bundle?) {}

    private fun restoreStateFromArguments(): Boolean {
        val bundle = arguments
        if (bundle == null || bundle.isEmpty) {
            return false
        }
        mSavedState = bundle.getBundle(TAG)
        if (mSavedState != null && !mSavedState!!.isEmpty) {
            restoreState()
            return true
        }
        return false
    }

    private fun restoreState() {
        if (mSavedState == null) {
            return
        }
        onRestoreState(mSavedState)
    }

    /**
     * override用以恢復state
     *
     * @param savedInstanceState
     */
    protected fun onRestoreState(savedInstanceState: Bundle?) {}

    fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val view: View? = requireActivity()?.currentFocus
            if (view is EditText) {
                val rect = Rect()
                view.getGlobalVisibleRect(rect)
                if (!rect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    view.clearFocus()
                    FunctionUtil.hideSoftKeyboard(requireActivity())
                }
            }
        }
        return false
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

    override fun startActivity(intent: Intent?) {
        requireActivity()?.overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out)
        super.startActivity(intent)
    }

    protected fun changeFragment(replaceLayoutId: Int, fragment: Fragment) {
        changeFragment(replaceLayoutId, parentFragmentManager.beginTransaction(), fragment, TYPE_NONE)
    }

    protected fun changeChildFragment(replaceLayoutId: Int, fragment: Fragment) {
        changeFragment(
            replaceLayoutId,
            childFragmentManager.beginTransaction(),
            fragment,
            TYPE_NONE
        )
    }

    protected fun changeChildFragmentWithLeftAnimation(replaceLayoutId: Int, fragment: Fragment) {
        changeFragment(
            replaceLayoutId,
            childFragmentManager.beginTransaction(),
            fragment,
            TYPE_LEFT_TO_RIGHT
        )
    }

    protected fun changeChildFragmentWithRightAnimation(replaceLayoutId: Int, fragment: Fragment) {
        changeFragment(
            replaceLayoutId,
            childFragmentManager.beginTransaction(),
            fragment,
            TYPE_RIGHT_TO_LEFT
        )
    }

    private fun changeFragment(
        replaceLayoutId: Int,
        fragmentTransactionParent: FragmentTransaction,
        fragment: Fragment,
        animationType: Int
    ) {
        val fragmentTransaction: FragmentTransaction = fragmentTransactionParent
        if (animationType == TYPE_LEFT_TO_RIGHT) {
            fragmentTransaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out)
        } else if (animationType == TYPE_RIGHT_TO_LEFT) {
            fragmentTransaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out)
        }
        fragmentTransaction.replace(replaceLayoutId, fragment)
        fragmentTransaction.addToBackStack(fragment.javaClass.name)
        fragmentTransaction.commit()
    }
}