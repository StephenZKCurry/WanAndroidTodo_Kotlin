package com.zk.wanandroidtodo

import com.zk.wanandroidtodo.base.IBaseView
import com.zk.wanandroidtodo.manager.RxManager

/**
 * @description:     Presenter基类
 * @author:         zhukai
 * @date:     2018/8/31 10:31
 */
abstract class BasePresenter<V : IBaseView> {

    var mIView: V? = null
    protected val mRxManager: RxManager by lazy { RxManager() }

    open fun attachView(v: V) {
        mIView = v
    }

    open fun detachView() {
        mRxManager.unSucribe()
        mIView = null
    }
}