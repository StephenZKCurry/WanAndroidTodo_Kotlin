package com.zk.wanandroidtodo.api

import com.zk.wanandroidtodo.bean.DataResponse
import com.zk.wanandroidtodo.bean.TodoListBean
import com.zk.wanandroidtodo.bean.UserBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @description: Retrofit请求接口
 * @author: zhukai
 * @date: 2018/8/21 14:03
 */
interface ApiService {

    /**
     * 登录
     * http://www.wanandroid.com/user/login
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @POST("/user/login")
    @FormUrlEncoded
    fun doLogin(
            @Field("username") username: String,
            @Field("password") password: String
    ): Observable<DataResponse<UserBean>>

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 确认密码
     * @return
     */
    @POST("/user/register")
    @FormUrlEncoded
    fun doRegister(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("repassword") repassword: String
    ): Observable<DataResponse<UserBean>>

    /**
     * 未完成的Todo列表
     * http://www.wanandroid.com/lg/todo/listnotdo/0/json/1
     *
     * @param page 页码,从1开始
     * @return
     */
    @POST("/lg/todo/listnotdo/0/json/{page}")
    fun getTodoList(@Path("page") page: Int): Observable<DataResponse<TodoListBean>>

    /**
     * 已完成的Todo列表
     * http://www.wanandroid.com/lg/todo/listdone/0/json/1
     *
     * @param page
     * @return
     */
    @POST("/lg/todo/listdone/0/json/{page}")
    fun getDoneList(@Path("page") page: Int): Observable<DataResponse<TodoListBean>>

    /**
     * 新增一条Todo
     * http://www.wanandroid.com/lg/todo/add/json
     *
     * @param title   标题
     * @param content 内容
     * @param date    日期
     * @param type    类型 0:只用这一个 1:工作 2:学习 3:生活,这里传0即可
     * @return
     */
    @POST("/lg/todo/add/json")
    @FormUrlEncoded
    fun addTodo(@Field("title") title: String,
                @Field("content") content: String,
                @Field("date") date: String,
                @Field("type") type: Int): Observable<DataResponse<Any>>

    /**
     * 更新一条Todo内容
     * http://www.wanandroid.com/lg/todo/update/83/json
     *
     * @param id      唯一标识
     * @param title   标题
     * @param content 详情
     * @param date    日期
     * @param status  状态，0为未完成，1为完成
     * @param type    类型 0:只用这一个 1:工作 2:学习 3:生活,这里传0即可
     * @return
     */
    @POST("/lg/todo/update/{id}/json")
    @FormUrlEncoded
    fun updateTodo(@Path("id") id: Int,
                   @Field("title") title: String,
                   @Field("content") content: String,
                   @Field("date") date: String,
                   @Field("status") status: Int,
                   @Field("type") type: Int): Observable<DataResponse<Any>>

    /**
     * 删除一条Todo
     * http://www.wanandroid.com/lg/todo/delete/83/json
     *
     * @param id 唯一标识
     * @return
     */
    @POST("/lg/todo/delete/{id}/json")
    fun deleteTodo(@Path("id") id: Int): Observable<DataResponse<Any>>

    /**
     * 更新事项完成状态
     * http://www.wanandroid.com/lg/todo/done/80/json
     *
     * @param id 唯一标识
     * @param status 状态，0为未完成，1为完成
     * @return
     */
    @POST("/lg/todo/done/{id}/json")
    @FormUrlEncoded
    fun updateTodoStatus(@Path("id") id: Int, @Field("status") status: Int): Observable<DataResponse<Any>>
}
