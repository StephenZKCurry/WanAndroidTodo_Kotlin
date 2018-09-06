package com.zk.wanandroidtodo

import android.content.Context
import android.widget.Toast
import com.zk.wanandroidtodo.utils.Constant
import java.text.SimpleDateFormat
import java.util.*

/**
 * @description:     扩展函数
 * @author:         zhukai
 * @date:     2018/8/22 10:11
 */

/**
 * 显示一个toast提示
 */
fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Constant.showToast?.apply {
        setText(text)
        show()
    } ?: run {
        Toast.makeText(this.applicationContext, text, duration).apply {
            Constant.showToast = this
        }.show()
    }
}

/**
 * 格式化当前日期
 */
fun formatCurrentDate(): String = SimpleDateFormat("yyyy-MM-dd").format(Date())