package com.zk.wanandroidtodo.ui.mine

import com.zk.wanandroidtodo.BasePresenter
import com.zk.wanandroidtodo.base.IBaseModel
import com.zk.wanandroidtodo.base.IBaseView
import com.zk.wanandroidtodo.bean.DataResponse
import com.zk.wanandroidtodo.bean.UserBean
import io.reactivex.Observable

/**
 * @description:     注册Contract
 * @author:         zhukai
 * @date:     2018/8/31 15:06
 */
interface RegisterContract {

    abstract class RegisterPresenter : BasePresenter<IRegisterView>() {
        /**
         * 用户注册
         */
        abstract fun doRegister(username: String, password: String, repassword: String)
    }

    interface IRegisterModel : IBaseModel {
        /**
         * 用户注册
         */
        fun doRegister(username: String, password: String, repassword: String): Observable<DataResponse<UserBean>>
    }

    interface IRegisterView : IBaseView {
        /**
         * 注册成功
         */
        fun showRegisterSuccess(user: UserBean)

        /**
         * 注册失败
         */
        fun showRegisterFailed(message: String)
    }
}