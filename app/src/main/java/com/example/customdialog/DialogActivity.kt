package com.example.customdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager

import kotlinx.android.synthetic.main.activity_dialog.*

class DialogActivity : AppCompatActivity(), View.OnClickListener {


    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.dialog_top -> {
                dialogTop()
            }
            R.id.dialog_center -> {
                dialogCenter()
            }
            R.id.dialog_bottom -> {
                dialogBottom()
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        dialog_top.setOnClickListener(this)
        dialog_center.setOnClickListener(this)
        dialog_bottom.setOnClickListener(this)


    }

    private fun setDim(customDialog: CustomDialog,dimAmount : Float){
        var lp : WindowManager.LayoutParams = customDialog.window!!.attributes
        lp.dimAmount = dimAmount
        customDialog.window!!.attributes = lp
    }

    private fun setSize(customDialog: CustomDialog, width : Int, height: Int){
        var lp : WindowManager.LayoutParams = customDialog.window!!.attributes

        lp.width = width
        lp.height = height
        customDialog.window!!.attributes = lp

    }

    private fun dialogTop(){
       CustomDialog.Builder(this)
               .setTitle("타이틀")
               .setMessage("메시지")
               .setPositiveButton("예",View.OnClickListener {

               })
               .setNegativeButton("아니요",View.OnClickListener {

               })
               .show()
    }

    private fun dialogCenter(){

        CustomDialog.Builder(this)
                .setTitle("타이틀")
                .setMessage("메시지")
                .setNeutralButton("취소", View.OnClickListener {

                })
                .setPositiveButton("예", View.OnClickListener {

                })
                .setNegativeButton("아니요", View.OnClickListener {

                })
                .show()

    }


    private fun dialogBottom(){
        CustomDialog.Builder(this)
                .setTitle("타이틀")
                .setMessage("메시지")
                .setPositiveButton("확인", View.OnClickListener {

                })
                .setNegativeButton("취소", View.OnClickListener {

                })
                .isClose(false)
                .show()
    }
}
