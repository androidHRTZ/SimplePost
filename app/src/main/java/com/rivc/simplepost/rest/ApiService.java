package com.rivc.simplepost.rest;

import com.rivc.simplepost.bean.User;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Riven on 2017/3/1.
 * Email: 1819485687@qq.com
 */

public interface ApiService {
//    @Headers({"X-Bmob-Application-Id:95e32d0339d11876b9ee5e949034e9fe", "X-Bmob-REST-API-Key:81db29f4d935374727e38227decda765", "Content-Type:application/json"})
    @POST("User")
    Observable<User> createUser(@Body User user);
}
