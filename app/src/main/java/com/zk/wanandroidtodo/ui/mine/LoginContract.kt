package com.zk.wanandroidtodo.ui.mine

import com.zk.wanandroidtodo.BasePresenter
import com.zk.wanandroidtodo.base.IBaseModel
import com.zk.wanandroidtodo.base.IBaseView
import com.zk.wanandroidtodo.bean.DataResponse
import com.zk.wanandroidtodo.bean.UserBean
import io.reactivex.Observable

/**
 * @description:     登录Contract
 * @author:         zhukai
 * @date:     2018/8/31 11:18
 */
interface LoginContract {

    abstract class LoginPresenter : BasePresenter<ILoginView>() {
        /**
         * 用户登录
         */
        abstract fun doLogin(username: String, password: String)
    }

    interface ILoginModel : IBaseModel {
        /**
         * 用户登录
         */
        fun doLogin(username: String, password: String): Observable<DataResponse<UserBean>>
    }

    interface ILoginView : IBaseView {
        /**
         * 登录成功
         */
        fun showLoginSuccess(user: UserBean)
    }
}