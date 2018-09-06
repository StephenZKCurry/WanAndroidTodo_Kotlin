package com.zk.wanandroidtodo.base.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.rxbus.RxBus
import com.zk.wanandroidtodo.widgets.WaitPorgressDialog

/**
 * @description:     普通Activity基类
 * @author:         zhukai
 * @date:     2018/8/21 14:41
 */
abstract class BaseActivity : AppCompatActivity() {

    protected val TAG = javaClass.simpleName
    protected lateinit var mContext: Context
    protected lateinit var mWaitPorgressDialog: WaitPorgressDialog
    protected lateinit var mToolbar: Toolbar
    protected lateinit var tvTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()")
        RxBus.get().register(this) // 注册RxBus
        mContext = this
        setContentView(getContentViewId())
        initView()
        initData()
        initEvent()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
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
        initToolBar()
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
     * 初始化toolbar
     */
    private fun initToolBar() {
        mToolbar = findViewById(R.id.toolbar)
        tvTitle = findViewById(R.id.tv_title)
        setSupportActionBar(mToolbar);
        supportActionBar?.setDisplayHomeAsUpEnabled(showHomeAsUp())
        supportActionBar?.setDisplayShowTitleEnabled(false)
        // toolbar除掉阴影
        supportActionBar?.elevation = 0f
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.elevation = 0f
        }
    }

    /**
     * toolbar是否显示返回键，默认不显示
     */
    open protected fun showHomeAsUp(): Boolean = false

    /**
     * 点击toolbar返回键
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }
}