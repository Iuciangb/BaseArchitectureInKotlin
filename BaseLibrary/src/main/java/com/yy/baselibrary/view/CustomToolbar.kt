package com.yy.baselibrary.view

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.yy.baselibrary.R
import com.yy.baselibrary.util.FunctionUtil


/**
 * @author YY
 * @create 2022/3/2
 * @Describe
 **/
class CustomToolbar : RelativeLayout {
    private var mContext: Context
    private var mLeftImageButton: ImageButton? = null
    private var mLeftTextView: TextView? = null
    private var mTitleTextView: TextView? = null
    private var mRightTextView: TextView? = null
    private var mLeftIconId = 0
    private var mTitleId = 0
    private var mTitleColor = 0
    private var mTitleSize = 0
    private var mOnToolbarListener: OnToolbarListener? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context
        initView(attrs)
    }

    private fun initView(attributeSet: AttributeSet) {
        LayoutInflater.from(mContext).inflate(R.layout.custom_toolbar, this, true)
        mLeftImageButton = findViewById(R.id.left_image_button)
        mLeftTextView = findViewById(R.id.left_text_view)
        mTitleTextView = findViewById(R.id.title_text_view)
        mRightTextView = findViewById(R.id.right_text_view)
        val typedArray: TypedArray = mContext.obtainStyledAttributes(attributeSet, R.styleable.CustomToolBar) ?: return
        mLeftIconId = typedArray.getResourceId(R.styleable.CustomToolBar_toolbar_left_icon, R.mipmap.arrow_back)
        if (FunctionUtil.isDrawableResValid(mContext, mLeftIconId)) {
            mLeftTextView?.visibility = GONE
            mLeftImageButton?.setImageResource(mLeftIconId)
        }
        mTitleId = typedArray.getResourceId(R.styleable.CustomToolBar_toolbar_title, R.string.empty)
        if (FunctionUtil.isStringResValid(mContext, mTitleId)) {
            mTitleTextView?.setText(mTitleId)
        }
        mTitleColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            typedArray.getColor(R.styleable.CustomToolBar_toolbar_title_color, resources.getColor(R.color.color_ffffff, context.theme))
        } else {
            typedArray.getColor(R.styleable.CustomToolBar_toolbar_title_color, resources.getColor(R.color.color_ffffff))
        }
        mTitleSize = typedArray.getDimensionPixelOffset(R.styleable.CustomToolBar_toolbar_title_size, 15)
        typedArray.recycle()
        mTitleTextView?.setTextColor(mTitleColor)
        mTitleTextView?.textSize = mTitleSize.toFloat()
        mLeftImageButton?.setOnClickListener(mOnClickListener)
        mLeftTextView?.setOnClickListener(mOnClickListener)
        mTitleTextView?.setOnClickListener(mOnClickListener)
        mRightTextView?.setOnClickListener(mOnClickListener)
    }

    fun setTitle(@StringRes titleId: Int) {
        mTitleTextView?.setText(titleId)
    }

    fun setTitle(title: String?) {
        mTitleTextView?.text = title
    }

    /**
     * 左邊同時有ImageButton跟TextView，只有有一個Visible
     */
    fun setLeftTextView(title: String?, @DrawableRes drawableLeft: Int) {
        mLeftImageButton?.visibility = GONE
        mLeftTextView?.visibility = VISIBLE
        mLeftTextView?.text = title
        if (FunctionUtil.isDrawableResValid(mContext, drawableLeft)) {
            mLeftTextView?.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, 0, 0, 0)
        }
    }

    /**
     * 因為右邊的TextView沒有那麼常用，
     * 所以預設隱藏，也不寫attr了。
     *
     * @param titleId
     * @param textColor
     */
    fun setRightTextView(titleId: Int, textColor: Int) {
        setRightVisibility(true)
        mRightTextView?.setText(titleId)
        mRightTextView?.setTextColor(ContextCompat.getColor(context, textColor))
    }

    fun setRightDrawable(@DrawableRes drawableId: Int) {
        if (FunctionUtil.isDrawableResValid(mContext, drawableId)) {
            setRightVisibility(true)
            mRightTextView?.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableId, 0)
        }
    }

    fun setLeftVisibility(isVisible: Boolean) {
        mLeftImageButton?.visibility = if (isVisible) VISIBLE else GONE
    }

    fun setTitleVisibility(isVisible: Boolean) {
        mTitleTextView?.visibility = if (isVisible) VISIBLE else GONE
    }

    fun setRightVisibility(isVisible: Boolean) {
        mRightTextView?.visibility = if (isVisible) VISIBLE else GONE
    }

    fun setOnToolbarListener(onToolbarListener: OnToolbarListener?) {
        mOnToolbarListener = onToolbarListener
    }

    private val mOnClickListener = OnClickListener { view ->
        if (mOnToolbarListener == null) {
            return@OnClickListener
        }
        when {
            view === mLeftImageButton -> {
                mOnToolbarListener?.onLeftClick()
            }
            view === mLeftTextView -> {
                mOnToolbarListener?.onLeftClick()
            }
            view === mTitleTextView -> {
                mOnToolbarListener?.onTitleClick()
            }
            view === mRightTextView -> {
                mOnToolbarListener?.onRightClick()
            }
        }
    }

    interface OnToolbarListener {
        fun onLeftClick()
        fun onTitleClick()
        fun onRightClick()
    }
}