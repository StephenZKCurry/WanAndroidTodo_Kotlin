package com.zk.wanandroidtodo.ui.mine

import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.base.MyApplication
import com.zk.wanandroidtodo.utils.Constant
import com.zk.wanandroidtodo.utils.NetworkUtils

/**
 * @description:     注册Presenter
 * @author:         zhukai
 * @date:     2018/8/31 15:16
 */
class RegisterPresenter : RegisterContract.RegisterPresenter() {

    private val mIModel: RegisterModel by lazy {
        RegisterModel()
    }

    /**
     * 用户注册
     */
    override fun doRegister(username: String, password: String, repassword: String) {
        mIView?.showLoadingDialog("请稍后...")
        mRxManager.register(mIModel.doRegister(username, password, repassword)
                .subscribe({ dataResponse ->
                    mIView?.apply {
                        if (dataResponse.errorCode == Constant.REQUEST_SUCCESS) {
                            // 注册成功
                            showRegisterSuccess(dataResponse.data)
                        } else {
                            // 注册失败
                            showRegisterFailed(dataResponse.errorMsg)
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