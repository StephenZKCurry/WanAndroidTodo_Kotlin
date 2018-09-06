package com.zk.wanandroidtodo.manager

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @description:     管理Rxjava注册订阅和取消订阅
 * @author:         zhukai
 * @date:     2018/8/31 10:33
 */
class RxManager {

    private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    /**
     * 注册订阅
     */
    fun register(d: Disposable) {
        mCompositeDisposable.add(d)
    }

    /**
     * 取消订阅
     */
    fun unSucribe(){
        mCompositeDisposable.dispose()
    }
}