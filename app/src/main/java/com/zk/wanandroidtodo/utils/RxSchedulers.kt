package com.zk.wanandroidtodo.utils

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @description: 通用的Rx线程转换类
 * @author: zhukai
 * @date: 2018/8/31 13:35
 */
object RxSchedulers {

    /**
     * compose()里接收一个Transformer对象，ObservableTransformer
     * 可以通过它将一种类型的Observable转换成另一种类型的Observable。
     * 现在.subscribeOn(Schedulers.io()) .observeOn(AndroidSchedulers.mainThread())
     * 的地方可以用.compose(RxSchedulersHelper.io_main())代替。
     */
    fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}
