package com.example.customdialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.layout_dialog.*

class CustomDialog : Dialog, View.OnClickListener {

    private var wm: WindowManager.LayoutParams? = null

    private var title: String? = ""
    private var message: String? = ""

    private var neuBtnLabel: String? = ""
    private var neuListener: View.OnClickListener? = null
    private var posBtnLabel: String? = ""
    private var posListener: View.OnClickListener? = null
    private var negBtnLabel: String? = ""
    private var negListener: View.OnClickListener? = null

    private var isOutTouchCancel: Boolean = true

    private var isClose: Boolean = false

    var themeResId: Int = R.style.CustomDialogStyle

    private var popupStyle: Int = TYPE_CENTER
    private var titleColorString: String? = null
    private var titleColorInt: Int = 0
    private var backColorString: String? = null
    private var backColorInt: Int = 0

    private var customView: View? = null

//    private var width: Float = 0.0f
//    private var height: Float = 0.0f

    public var isDismiss: Boolean = true

    private val attr: IntArray = intArrayOf(
        android.R.attr.windowBackground,
        android.R.attr.windowMinWidthMajor,
        android.R.attr.windowMinWidthMinor,
        android.R.attr.windowNoTitle,
        android.R.attr.windowTitleStyle,
        android.R.attr.textAppearance,
        android.R.attr.buttonBarNegativeButtonStyle,
        android.R.attr.buttonBarPositiveButtonStyle
    )

    private var buttonAttr: IntArray = intArrayOf(
        android.R.attr.background,
        android.R.attr.textAppearance,
        android.R.attr.minHeight,
        android.R.attr.minWidth,
        android.R.attr.stateListAnimator,
        android.R.attr.focusable,
        android.R.attr.clickable,
        android.R.attr.gravity
    )

    private var backgroundAttr: IntArray = intArrayOf(
        android.R.attr.background,
        android.R.attr.minHeight,
        android.R.attr.minWidth,
        android.R.attr.radius
    )


    companion object {
        val TYPE_BOTTOM_TO_TOP = 0
        val TYPE_TOP_TO_BOTTOM = 1
        val TYPE_CENTER = 2
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.dialog_neu_btn ->{
                isDismiss = true
                neuListener?.let{
                    neuListener!!.onClick(v)
                }
            }
            R.id.dialog_neg_btn -> {
                isDismiss = true
                negListener?.let {
                    negListener!!.onClick(v)
                }

            }
            R.id.dialog_pos_btn -> {
                posListener?.let {
                    posListener!!.onClick(v)
                }
            }
            R.id.dialog_close -> {
                isDismiss = true
            }

        }
        if (isDismiss)
            dismiss()
    }

    constructor(context: Context) : super(context,R.style.CustomDialogStyle)
    constructor(context: Context, themeResId: Int) : super(context, themeResId){
        this.themeResId = themeResId
    }
    //constructor(context: Context, attrs : AttributeSet) : super(context, attrs, )


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAnimation()
        initDialog()
        setDialog()
        setStyle()
    }

    private fun initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_dialog)
        setCanceledOnTouchOutside(isOutTouchCancel)

    }

    private fun setDialog() {

        dialog_title.text = title
        dialog_message.text = message

        neuBtnLabel?.let { dialog_neu_btn.text = neuBtnLabel }
        posBtnLabel?.let { dialog_pos_btn.text = posBtnLabel }
        negBtnLabel?.let { dialog_neg_btn.text = negBtnLabel }

        dialog_neu_btn.setOnClickListener(this)
        dialog_pos_btn.setOnClickListener(this)
        dialog_neg_btn.setOnClickListener(this)
        dialog_close.setOnClickListener(this)

        dialog_title.visibility = View.GONE
        dialog_message.visibility = View.GONE
        dialog_view.visibility = View.GONE
        dialog_neu_btn.visibility = View.GONE
        dialog_pos_btn.visibility = View.GONE
        dialog_neg_btn.visibility = View.GONE
        dialog_close.visibility = View.GONE

        if (null != neuListener)
            dialog_neu_btn.visibility = View.VISIBLE
        if (null != posListener)
            dialog_pos_btn.visibility = View.VISIBLE
        if (null != negListener)
            dialog_neg_btn.visibility = View.VISIBLE

        if (isClose)
            dialog_close.visibility = View.VISIBLE

        if (title != null)
            dialog_title.visibility = View.VISIBLE

        if(message != null)
            dialog_message.visibility = View.VISIBLE

        if(customView != null)
            dialog_view.visibility = View.VISIBLE

        if (backColorString != null)
            rl_dialog.setBackgroundColor(Color.parseColor(backColorString))

        if (backColorInt != 0)
            rl_dialog.setBackgroundColor(backColorInt)

        if (titleColorString != null)
            dialog_title.setTextColor(Color.parseColor(titleColorString))

        if (titleColorInt != 0)
            dialog_title.setTextColor(titleColorInt)

        customView?.let {
            dialog_view.removeAllViews()
            dialog_view.addView(customView)
        }

    }

    private fun setAnimation() {

        when (popupStyle) {
            TYPE_BOTTOM_TO_TOP -> {
                window!!.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                window!!.attributes.windowAnimations = R.style.BottomDialogAnimation
                window!!.setGravity(Gravity.BOTTOM)
            }

            TYPE_CENTER -> {
            }

            TYPE_TOP_TO_BOTTOM -> {
                window!!.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                window!!.attributes.windowAnimations = R.style.TopDialogAnimation
                window!!.setGravity(Gravity.TOP)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    private fun setStyle() {
        val typedArray = this.context.theme.obtainStyledAttributes(themeResId, attr)
        //printAttr(typedArray)

        val titleTypedArray = context.theme.obtainStyledAttributes(
            typedArray.getResourceId(4, -1),
            R.styleable.TextAppearance
        )
        setTextStyle(titleTypedArray, dialog_title)
        //val titleTypedArray = context.theme.obtainStyledAttributes(typedArray.getResourceId(4,-1),R.styleable.TextAppearance)

        val positiveBtnTypedArray = context.obtainStyledAttributes(
            typedArray.getResourceId(7, -1),
            R.styleable.TextAppearance
        )
        setTextStyle(positiveBtnTypedArray, dialog_pos_btn)

        val posBtn = context.obtainStyledAttributes(
            typedArray.getResourceId(7, -1),
            buttonAttr
        )
        setButtonStyle(posBtn, dialog_pos_btn)

        val negativeBtnTypedArray = context.obtainStyledAttributes(
            typedArray.getResourceId(6, -1),
           R.styleable.TextAppearance
        )
        setTextStyle(negativeBtnTypedArray, dialog_neg_btn)

        val negBtn = context.obtainStyledAttributes(
            typedArray.getResourceId(6,-1),
            buttonAttr
        )
        setButtonStyle(negBtn, dialog_neg_btn)

        val background = context.theme.obtainStyledAttributes(
            typedArray.getResourceId(0,-1),
            backgroundAttr
        )
        setBackgroundStyle(background, rl_dialog)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    private fun setTextStyle(typedArray: TypedArray, textView: TextView) {
        if (TypedValue.TYPE_NULL != typedArray.getType(0)) {
            // 택스트 사이즈
            textView.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                typedArray.getDimensionPixelSize(0, 0).toFloat()
            )
            // 텍스트 색깔
            textView.setTextColor(typedArray.getColor(3, android.R.color.black))
            // 텍스트 스타일
            textView.setTypeface(Typeface.DEFAULT_BOLD, typedArray.getInt(2, 0))
        }
        typedArray.recycle()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    private fun setButtonStyle(typedArray: TypedArray, button: Button) {
        if (TypedValue.TYPE_NULL != typedArray.getType(0)) {
            if (typedArray.getString(0)!!.indexOf("default") == -1)
                button.setBackgroundColor(typedArray.getResourceId(0, -1))
        }
        typedArray.recycle()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    private fun setBackgroundStyle(typedArray: TypedArray, layout: RelativeLayout){
        if(TypedValue.TYPE_NULL != typedArray.getType(0)){
            if(typedArray.getString(0)!!.indexOf("default") == -1)
                layout.setBackgroundColor(typedArray.getResourceId(0,-1))
        }
        typedArray.recycle()
    }


    fun setTitle(title: String) {
        dialog_title.setText(title)
    }

    class Builder(var context: Context) {

        private var themeResId : Int = R.style.CustomDialogStyle

        constructor(context: Context, themeResId: Int) : this(context){
            this.themeResId = themeResId
        }

        private val dialog = CustomDialog(context)

//        fun setDim() : CustomDialog{
//            var wm : WindowManager.LayoutParams? = dialog.window!!.attributes
//            wm!!.dimAmount = 1.0f
//            dialog.window!!.attributes = wm
//
//            return dialog
//        }


        fun setTitle(text: String): Builder {
            dialog.title = text
            return this
        }

        fun setTitle(resId: Int): Builder {
            dialog.title = context.getText(resId).toString()
            return this
        }

        fun setMessage(text: String): Builder
        {
            dialog.message = text
            return this
        }

        fun setMessage(resId: Int) : Builder
        {
            dialog.message = context.getText(resId).toString()
            return this
        }

        fun setTitleColor(color: String): Builder {
            dialog.titleColorString = color

            return this
        }

        fun setTitleColor(resId: Int): Builder {
            dialog.titleColorInt = resId
            return this
        }

        fun setView(view: View): Builder {
            dialog.customView = view
            return this
        }

        fun setView(layoutResId: Int): Builder {
            dialog.customView = View.inflate(context, layoutResId, null)
            return this
        }

        fun setNeutralButton(
                label: CharSequence?,
                onClickListener: View.OnClickListener?
        ): Builder {

            label?.let {
                dialog.neuBtnLabel = label.toString()
            }

            onClickListener?.let {
                dialog.neuListener = onClickListener
            }
            return this
        }

        fun setPositiveButton(
            label: CharSequence?,
            onClickListener: View.OnClickListener?
        ): Builder {

            label?.let {
                dialog.posBtnLabel = label.toString()
            }

            onClickListener?.let {
                dialog.posListener = onClickListener
            }
            return this
        }

        fun setNegativeButton(
            label: CharSequence?,
            onClickListener: View.OnClickListener?
        ): Builder {

            label?.let {
                dialog.negBtnLabel = label.toString()
            }

            onClickListener?.let {
                dialog.negListener = onClickListener
            }
            return this
        }

        fun isClose(isClose: Boolean): Builder {
            dialog.isClose = isClose
            return this
        }

        fun isCancelable(flag: Boolean): Builder {
            dialog.isOutTouchCancel = flag
            return this
        }

        fun setBackgroundColor(color: String): Builder {
            dialog.backColorString = color
            return this
        }

        fun setBackgroundColor(resId: Int): Builder {
            dialog.backColorInt = resId
            return this
        }

        fun setAniMode(type: Int): Builder {
            dialog.popupStyle = type
            return this
        }

        fun create(): CustomDialog {
            return dialog
        }

        fun show(): CustomDialog {
            dialog.show()
            return dialog
        }

    }
}