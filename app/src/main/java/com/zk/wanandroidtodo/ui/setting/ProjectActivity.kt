package com.zk.wanandroidtodo.ui.setting

import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.base.activity.BaseActivity

/**
 * @description:     项目主页页面（WebView）
 * @author:         zhukai
 * @date:     2018/9/6 11:19
 */
class ProjectActivity : BaseActivity() {

    override fun getContentViewId(): Int = R.layout.activity_project

    override fun showHomeAsUp(): Boolean = true

    override fun initView() {
        super.initView()
        tvTitle.text = getString(R.string.setting_title_github)
    }

    override fun initData() {
        super.initData()
    }

    override fun initEvent() {
        super.initEvent()
    }
}