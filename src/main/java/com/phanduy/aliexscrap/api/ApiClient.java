package com.phanduy.aliexscrap.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class ApiClient {
//    private static final String BASE_URL = "http://iamhere.vn:89/api/v3/pltool/";
    private static final String BASE_URL = "http://localhost:89/api/v3/pltool/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .connectTimeout(0, TimeUnit.SECONDS) // Không giới hạn timeout khi kết nối
                    .readTimeout(0, TimeUnit.SECONDS)    // Không giới hạn timeout khi đọc dữ liệu
                    .writeTimeout(0, TimeUnit.SECONDS)   // Không giới hạn timeout khi gửi dữ liệu
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client) // Gán OkHttpClient đã cấu hình
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
