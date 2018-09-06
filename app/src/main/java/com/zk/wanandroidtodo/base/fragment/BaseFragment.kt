package com.zk.wanandroidtodo.base.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zk.wanandroidtodo.rxbus.RxBus
import com.zk.wanandroidtodo.widgets.WaitPorgressDialog

/**
 * @description:     Fragment基类
 * @author:         zhukai
 * @date:     2018/8/22 15:07
 */
abstract class BaseFragment : Fragment() {

    protected lateinit var mContext: Context
    protected lateinit var mWaitPorgressDialog: WaitPorgressDialog
    /**
     * 视图是否加载完毕
     */
    private var isViewPrepared = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.get().register(this) // 注册RxBus
        mContext = activity as Context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getContentViewId(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepared = true
        // 这里将initView()放在onViewCreated()是因为在Fragment中不能在onViewCreate()中操作控件id，因为此时还没有return view
        initView()
        lazyLoad()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoad()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            lazyLoad()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unRegister(mContext) // 取消注册RxBus
    }

    /**
     * 设置布局资源id
     */
    abstract fun getContentViewId(): Int

    /**
     * 初始化页面
     */
    open protected fun initView() {
        mWaitPorgressDialog = WaitPorgressDialog(mContext)
    }

    /**
     * 初始化数据
     */
    open protected fun initData() {

    }

    /**
     * 初始化事件
     */
    open protected fun initEvent() {

    }

    /**
     * 懒加载
     */
    protected fun lazyLoad() {
        if (userVisibleHint && isViewPrepared && !hasLoadData) {
            hasLoadData = true
            initData()
            initEvent()
        }
    }
}