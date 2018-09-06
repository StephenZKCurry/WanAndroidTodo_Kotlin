package com.zk.wanandroidtodo.ui.mine

import com.zk.wanandroidtodo.bean.DataResponse
import com.zk.wanandroidtodo.bean.UserBean
import com.zk.wanandroidtodo.manager.RetrofitManager
import com.zk.wanandroidtodo.utils.RxSchedulers
import io.reactivex.Observable

/**
 * @description:     注册Model
 * @author:         zhukai
 * @date:     2018/8/31 15:13
 */
class RegisterModel : RegisterContract.IRegisterModel {

    /**
     * 用户注册
     */
    override fun doRegister(username: String, password: String, repassword: String): Observable<DataResponse<UserBean>> {
        return RetrofitManager.service.doRegister(username, password, repassword)
                .filter {
                    username.isNotEmpty() && password.isNotEmpty() && repassword.isNotEmpty()
                }
                .compose(RxSchedulers.applySchedulers())
    }
}