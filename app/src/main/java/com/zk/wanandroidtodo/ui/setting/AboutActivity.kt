package com.zk.wanandroidtodo.ui.setting

import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.base.activity.BaseActivity

/**
 * @description:     关于页面
 * @author:         zhukai
 * @date:     2018/9/6 11:23
 */
class AboutActivity : BaseActivity() {

    override fun getContentViewId(): Int = R.layout.activity_about

    override fun showHomeAsUp(): Boolean = true

    override fun initView() {
        super.initView()
        tvTitle.text = getString(R.string.setting_title_about)
    }
}