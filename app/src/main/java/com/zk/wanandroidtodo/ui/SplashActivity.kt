package com.zk.wanandroidtodo.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.ui.mine.LoginActivity
import com.zk.wanandroidtodo.utils.Constant
import com.zk.wanandroidtodo.utils.Preference
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit

/**
 * @description:     闪屏页面
 * @author:         zhukai
 * @date:     2018/9/4 15:01
 */
class SplashActivity : AppCompatActivity() {

    private val isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // 播放文字路径动画
        tv_splash_text.startAnimation(0f, 1f)
        Observable.timer(4, TimeUnit.SECONDS)
                .subscribe {
                    // 跳转下一页
                    jumpNextPage()
                }
    }

    fun jumpNextPage() {
        if (isLogin) {
            Intent(this, MainActivity::class.java).run {
                startActivity(this)
            }
        } else {
            Intent(this, LoginActivity::class.java).run {
                startActivity(this)
            }
        }
        finish()
    }
}