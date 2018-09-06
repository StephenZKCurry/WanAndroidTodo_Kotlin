package com.zk.wanandroidtodo.bean

/**
 * @description: 用户信息实体类
 * @author: zhukai
 * @date: 2018/8/21 14:25
 */
data class UserBean(
        var email: String,
        var icon: String,
        var id: Int,
        var password: String,
        var type: Int,
        var username: String,
        var collectIds: List<Int>) {
}
