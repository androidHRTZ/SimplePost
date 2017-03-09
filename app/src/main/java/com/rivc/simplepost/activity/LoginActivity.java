package com.rivc.simplepost.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.rivc.simplepost.R;
import com.rivc.simplepost.bean.User;
import com.rivc.simplepost.rest.ApiService;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    public static final String BASE_URL = "https://api.bmob.cn/1/classes/";

    @BindView(R.id.tool_bar)
    protected Toolbar toolBar;
    @BindView(R.id.edit_username)
    protected TextView userName;
    @BindView(R.id.edit_password)
    protected TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        toolBar.setTitle("登录|注册");
    }

    @OnClick(R.id.btn_register)
    protected void onClickRegister() {

        if (TextUtils.isEmpty(userName.getText().toString().trim()) || TextUtils.isEmpty(password.getText().toString().trim())) {
            Toast.makeText(LoginActivity.this, "请填写用户名或密码", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(userName.getText().toString().trim(), password.getText().toString().trim());

        new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class)
                .createUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: " + d);
                    }

                    @Override
                    public void onNext(User value) {
                        Log.e(TAG, "onNext: " + value);
                        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                        Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor();
        mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(mHttpLoggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("X-Bmob-Application-Id", "95e32d0339d11876b9ee5e949034e9fe")
                                .addHeader("X-Bmob-REST-API-Key", "81db29f4d935374727e38227decda765")
                                .addHeader("Content-Type", "application/json")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();
        return httpClient;
    }
}
