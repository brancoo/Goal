package com.example.golo;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient{
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.football-data.org/v2/";

    public static Retrofit getRetrofitInstance() {
            if (retrofit == null) {
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("X-Auth-Token", "e251f2f69b2b4413aaba270a02148849")
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                });
                httpClient.addNetworkInterceptor(new StethoInterceptor());
                OkHttpClient client = httpClient.build();
                retrofit = new retrofit2.Retrofit.Builder()
                        .client(client)
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
            }
            return retrofit;
        }
}
