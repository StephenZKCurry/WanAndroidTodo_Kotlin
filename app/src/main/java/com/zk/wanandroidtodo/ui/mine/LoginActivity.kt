package com.zk.wanandroidtodo.ui.mine

import android.content.Intent
import android.view.View
import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.base.activity.BaseActivity
import com.zk.wanandroidtodo.bean.UserBean
import com.zk.wanandroidtodo.toast
import com.zk.wanandroidtodo.ui.MainActivity
import com.zk.wanandroidtodo.utils.Constant
import com.zk.wanandroidtodo.utils.Preference
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @description:     登录页面
 * @author:         zhukai
 * @date:     2018/8/31 11:29
 */
class LoginActivity : LoginContract.ILoginView, BaseActivity() {

    private val mPresenter: LoginPresenter by lazy {
        LoginPresenter()
    }

    /**
     * 用户id
     */
    private var userId: String by Preference(Constant.USER_ID, "")

    /**
     * 用户名
     */
    private var userName: String by Preference(Constant.USER_NAME, "")

    /**
     * 是否登录
     */
    private var isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    override fun getContentViewId(): Int = R.layout.activity_login

    override fun initView() {
        super.initView()
        mPresenter.attachView(this)
    }

    override fun initData() {
        super.initData()
    }

    override fun initEvent() {
        super.initEvent()
        tv_login.setOnClickListener(onClickListener)
        tv_register.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.tv_login -> {
                // 登录
                val username = et_username.text.toString().trim()
                val password = et_password.text.toString().trim()
                if (username.isEmpty()) {
                    toast(getString(R.string.register_username))
                } else if (password.isEmpty()) {
                    toast(getString(R.string.register_password))
                } else {
                    mPresenter.doLogin(username, password)
                }
            }
            R.id.tv_register -> {
                // 注册
                Intent(mContext, RegisterActivity::class.java).run {
                    startActivity(this)
                }
            }
        }
    }

    /**
     * 登录成功
     */
    override fun showLoginSuccess(user: UserBean) {
        toast(getString(R.string.login_success))
        userId = user.id.toString()
        userName = user.username
        isLogin = true
        Intent(mContext, MainActivity::class.java).run {
            finish()
            startActivity(this)
        }
    }

    override fun showLoadingDialog(message: String) {
        if (mWaitPorgressDialog.isShowing) {
            mWaitPorgressDialog.dismiss()
        }
        mWaitPorgressDialog.setMessage(message)
        mWaitPorgressDialog.show()
    }

    override fun hideLoadingDialog() {
        mWaitPorgressDialog.dismiss()
    }

    override fun showToast(message: String) {
        toast(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}