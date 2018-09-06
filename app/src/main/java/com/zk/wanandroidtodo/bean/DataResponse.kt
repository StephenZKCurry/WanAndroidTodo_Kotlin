package com.zk.wanandroidtodo.bean

/**
 * @description: 返回数据实体类
 * @author: zhukai
 * @date: 2018/8/21 14:20
 */
class DataResponse<T>(
        var errorCode: Int,
        var errorMsg: String,
        var data: T
)
