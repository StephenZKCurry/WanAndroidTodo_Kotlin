package com.zk.wanandroidtodo.ui.setting

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.base.fragment.BaseFragment
import com.zk.wanandroidtodo.ui.mine.LoginActivity
import com.zk.wanandroidtodo.utils.Constant
import com.zk.wanandroidtodo.utils.Preference
import kotlinx.android.synthetic.main.fragment_setting.*

/**
 * @description:     设置页面
 * @author:         zhukai
 * @date:     2018/8/22 10:59
 */
class SettingFragment : BaseFragment() {

    /**
     * 用户名
     */
    private var userName: String by Preference(Constant.USER_NAME, "")

    /**
     * 是否登录
     */
    private var isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    /**
     * 是否为夜间模式
     */
    private var isNight: Boolean by Preference(Constant.NIGHT_MODEL, false)

    companion object {
        fun newInstance(): SettingFragment {
            val args = Bundle()
            val fragment = SettingFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getContentViewId(): Int = R.layout.fragment_setting

    override fun initView() {
        super.initView()
        tv_username_head.text = userName.substring(0, 1)
        tv_username.text = userName
    }

    override fun initData() {
        super.initData()
    }

    override fun initEvent() {
        super.initEvent()
        rl_nightmode.setOnClickListener(onClickListener)
        rl_github.setOnClickListener(onClickListener)
        rl_about.setOnClickListener(onClickListener)
        rl_logout.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.rl_nightmode -> {
                // 切换日/夜间模式
                switchNightMode()
            }
            R.id.rl_github -> {
                // 项目主页
                Intent(mContext, ProjectActivity::class.java).run {
                    startActivity(this)
                }
            }
            R.id.rl_about -> {
                // 关于
                Intent(mContext, AboutActivity::class.java).run {
                    startActivity(this)
                }
            }
            R.id.rl_logout -> {
                // 退出登录
                MaterialDialog.Builder(mContext)
                        .content(R.string.confirm_logout)
                        .positiveText(R.string.dialog_confirm)
                        .negativeText(R.string.dialog_cancel)
                        .onPositive(MaterialDialog.SingleButtonCallback { dialog, which ->
                            // 清除本地保存的用户信息
//                            Preference.clearPreference()
                            Preference.clearPreference(Constant.USER_ID)
                            Preference.clearPreference(Constant.USER_NAME)
                            isLogin = false
                            SharedPrefsCookiePersistor(mContext).clear()
                            // 跳转登录页
                            Intent(mContext, LoginActivity::class.java).run {
                                startActivity(this)
                                activity?.finish()
                            }
                        }).show()
            }
        }
    }

    /**
     * 切换夜间模式
     */
    fun switchNightMode() {
        if (isNight) {
            // 切换为正常模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            isNight = false
        } else {
            // 切换为夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            isNight = true
        }
        activity!!.window.setWindowAnimations(R.style.WindowAnimationFadeInOut)
        activity!!.recreate()
    }
}