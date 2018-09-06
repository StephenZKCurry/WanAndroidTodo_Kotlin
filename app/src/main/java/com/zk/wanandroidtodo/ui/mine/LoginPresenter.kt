package com.zk.wanandroidtodo.ui.mine

import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.base.MyApplication
import com.zk.wanandroidtodo.utils.Constant
import com.zk.wanandroidtodo.utils.NetworkUtils

/**
 * @description:     登录Presenter
 * @author:         zhukai
 * @date:     2018/8/31 11:25
 */
class LoginPresenter : LoginContract.LoginPresenter() {

    private val mIModel: LoginModel by lazy {
        LoginModel()
    }

    /**
     * 用户登录
     */
    override fun doLogin(username: String, password: String) {
        mIView?.showLoadingDialog("请稍后...")
        mRxManager.register(mIModel.doLogin(username, password)
                .subscribe({ dataResponse ->
                    mIView?.apply {
                        if (dataResponse.errorCode == Constant.REQUEST_SUCCESS) {
                            // 登录成功
                            showLoginSuccess(dataResponse.data)
                        } else {
                            // 登录失败
                            showToast(dataResponse.errorMsg)
                        }
                        hideLoadingDialog()
                    }
                }, { t ->
                    mIView?.apply {
                        val available = NetworkUtils.isNetworkAvailable(MyApplication.mContext)
                        if (!available) {
                            showToast(MyApplication.mContext.getString(R.string.no_network_connection))
                        } else {
                            showToast(t.message
                                    ?: MyApplication.mContext.getString(R.string.request_error))
                        }
                        hideLoadingDialog()
                    }
                }))
    }
}