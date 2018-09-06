package com.zk.wanandroidtodo.utils

import android.widget.Toast

/**
 * @description:     常量
 * @author:         zhukai
 * @date:     2018/8/21 14:50
 */
object Constant {

    // 请求基地址
    const val BASE_URL = "http://wanandroid.com/"

    // 每页数量
    const val PAGE_SIZE = 20;

    // 加载数据类型
    const val TYPE_REFRESH_SUCCESS = 1;
    const val TYPE_REFRESH_ERROR = 2;
    const val TYPE_LOAD_MORE_SUCCESS = 3;
    const val TYPE_LOAD_MORE_ERROR = 4;

    // 请求数据返回码
    const val REQUEST_SUCCESS = 0;  // 请求成功
    const val REQUEST_FAIL = -1;  // 请求失败

    // 用户信息
    const val USER_ID = "user_id"; // 用户id
    const val USER_NAME = "user_name"; // 用户名
    const val USER_PASSWORD = "user_password"; // 密码
    const val LOGIN_KEY = "login_key"; // 是否登录

    // RxBus
    const val RX_BUS_CODE_REFRESH = 0; // 新增或更新清单内容，刷新列表
    const val RX_BUS_CODE_REFRESH_STATUS = 1; // 更新清单状态，刷新列表

    // 夜间模式
    const val NIGHT_MODEL = "night_model";

    // Todo
    const val IS_DONE = "is_done" // 是否已完成
    const val TODO_BEAN = "todo_bean" // 清单bean

    /**
     * Toast
     */
    @JvmField
    var showToast: Toast? = null
}