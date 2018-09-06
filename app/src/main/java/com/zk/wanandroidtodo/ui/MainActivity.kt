package com.zk.wanandroidtodo.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import com.zk.wanandroidtodo.R
import com.zk.wanandroidtodo.base.activity.BaseActivity
import com.zk.wanandroidtodo.toast
import com.zk.wanandroidtodo.ui.setting.SettingFragment
import com.zk.wanandroidtodo.ui.todo.AddTodoActivity
import com.zk.wanandroidtodo.ui.todo.TodoListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var todoFragment: TodoListFragment? = null
    private var doneFragment: TodoListFragment? = null
    private var settingFragment: SettingFragment? = null

    private val FRAGMENT_TODO = 0
    private val FRAGMENT_DONE = 1
    private val FRAGMENT_SETTING = 2

    private val fragmentManager by lazy {
        supportFragmentManager
    }

    private val SELECT_ITEM = "select_item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            todoFragment = fragmentManager.findFragmentByTag("Todo") as TodoListFragment?
            doneFragment = fragmentManager.findFragmentByTag("Done") as TodoListFragment?
            settingFragment = fragmentManager.findFragmentByTag("Setting") as SettingFragment?
            // 恢复recreate()前的状态
            rg_bottom_bar.check(savedInstanceState.getInt(SELECT_ITEM))
        } else {
            rb_todo.isChecked = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(SELECT_ITEM, rg_bottom_bar.checkedRadioButtonId)
    }

    override fun getContentViewId(): Int = R.layout.activity_main

    override fun initView() {
        super.initView()
    }

    override fun initData() {
        super.initData()
    }

    override fun initEvent() {
        super.initEvent()
        rg_bottom_bar.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_todo -> showFragment(FRAGMENT_TODO)
                R.id.rb_done -> showFragment(FRAGMENT_DONE)
                R.id.rb_setting -> showFragment(FRAGMENT_SETTING)
            }
            // 调用onPrepareOptionsMenu()
            invalidateOptionsMenu()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if (rb_todo.isChecked) {
            menu.findItem(R.id.menu_add).isVisible = true
        } else {
            menu.findItem(R.id.menu_add).isVisible = false
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            Intent(mContext, AddTodoActivity::class.java).run {
                startActivity(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showFragment(index: Int) {
        fragmentManager.beginTransaction().apply {
            hideFragment(this)
            when (index) {
                FRAGMENT_TODO -> {
                    tvTitle.text = getString(R.string.main_title_todo)
                    if (todoFragment == null) {
                        TodoListFragment.newInstance(false).let {
                            todoFragment = it
                            add(R.id.fl_container, it, "Todo")
                        }
                    } else {
                        show(todoFragment)
                    }
                }
                FRAGMENT_DONE -> {
                    tvTitle.text = getString(R.string.main_title_done)
                    if (doneFragment == null) {
                        TodoListFragment.newInstance(true).let {
                            doneFragment = it
                            add(R.id.fl_container, it, "Done")
                        }
                    } else {
                        show(doneFragment)
                    }
                }
                FRAGMENT_SETTING -> {
                    tvTitle.text = getString(R.string.main_title_setting)
                    if (settingFragment == null) {
                        SettingFragment.newInstance().let {
                            settingFragment = it
                            add(R.id.fl_container, it, "Setting")
                        }
                    } else {
                        show(settingFragment)
                    }
                }
            }
        }.commit()
    }

    /**
     * 隐藏Fragment
     */
    private fun hideFragment(ft: FragmentTransaction) {
        // 如果不为空，就先隐藏起来
        todoFragment?.let {
            ft.hide(it)
        }
        doneFragment?.let {
            ft.hide(it)
        }
        settingFragment?.let {
            ft.hide(it)
        }
    }

    // 按两次返回键退出
    private var time: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (KeyEvent.ACTION_DOWN == event.action && keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > 2000) {
                toast(getString(R.string.main_alert_back))
                time = System.currentTimeMillis()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
