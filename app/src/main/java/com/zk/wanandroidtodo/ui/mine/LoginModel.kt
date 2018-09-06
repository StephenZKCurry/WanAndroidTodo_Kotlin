package com.zk.wanandroidtodo.ui.mine

import android.text.TextUtils
import com.zk.wanandroidtodo.bean.DataResponse
import com.zk.wanandroidtodo.bean.UserBean
import com.zk.wanandroidtodo.manager.RetrofitManager
import com.zk.wanandroidtodo.utils.RxSchedulers
import io.reactivex.Observable
import io.reactivex.functions.Predicate

/**
 * @description:     登录Model
 * @author:         zhukai
 * @date:     2018/8/31 11:26
 */
class LoginModel : LoginContract.ILoginModel {

    /**
     * 用户登录
     */
    override fun doLogin(username: String, password: String): Observable<DataResponse<UserBean>> {
        return RetrofitManager.service.doLogin(username, password)
                .filter { username.isNotEmpty() && password.isNotEmpty() }
                .compose(RxSchedulers.applySchedulers<DataResponse<UserBean>>())
    }
}