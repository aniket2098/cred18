package com.asdsoft.reg_app_18;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiClient {
    public static final String BASE_URL = "http://webone.tk/regapp/";

    @FormUrlEncoded
    @POST("restapi.php")
    Call<List<DataRecv>> sendData(@Field("email") String email,
                                  @Field("phone") String phone);
}
