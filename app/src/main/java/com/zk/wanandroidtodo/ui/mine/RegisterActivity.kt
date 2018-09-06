package com.zk.wanandroidtodo.ui.mine

import android.app.AlertDialog
import android.view.View
import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.base.activity.BaseActivity
import com.zk.wanandroidtodo.bean.UserBean
import com.zk.wanandroidtodo.toast
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.dialog_register_success.*

/**
 * @description:     注册页面
 * @author:         zhukai
 * @date:     2018/8/31 14:57
 */
class RegisterActivity : BaseActivity(), RegisterContract.IRegisterView {

    private val mPresenter: RegisterPresenter by lazy {
        RegisterPresenter()
    }

    override fun getContentViewId(): Int = R.layout.activity_register

    override fun initView() {
        super.initView()
        tvTitle.text= getString(R.string.login_register)
        mPresenter.attachView(this)
    }

    override fun initData() {
        super.initData()
    }

    override fun initEvent() {
        super.initEvent()
        tv_register.setOnClickListener(onClickListener)
    }

    /**
     * 注册成功
     */
    override fun showRegisterSuccess(user: UserBean) {
//        val dialog = layoutInflater.inflate(R.layout.dialog_register_success, null)
//        val builder = AlertDialog.Builder(mContext)
//                .setView(dialog)
//        // 注册成功信息
//        tv_message.text = (getString(R.string.dialog_register_success)
//                + "\n用户名：" + user.username
//                + "\n密码：" + user.password)
//        // 确定
//        tv_submit.setOnClickListener { finish() }
//        builder.show()
        toast(getString(R.string.dialog_register_success)
                + "\n用户名：" + user.username
                + "\n密码：" + user.password)
        finish()
    }

    /**
     * 注册失败
     */
    override fun showRegisterFailed(message: String) {
        toast(message)
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

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.tv_register -> {
                // 注册
                val username = et_username.text.toString().trim()
                val password = et_password.text.toString().trim()
                val repassword = et_repassword.text.toString().trim()
                if (username.isEmpty()) {
                    toast(getString(R.string.register_username))
                } else if (password.isEmpty()) {
                    toast(getString(R.string.register_password))
                } else if (repassword.isEmpty()) {
                    toast(getString(R.string.register_repassword))
                } else if (password != repassword) {
                    toast(getString(R.string.password_different))
                } else {
                    mPresenter.doRegister(username, password, repassword)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}