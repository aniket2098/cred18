package com.asdsoft.reg_app_18;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiClient {
    public static final String BASE_URL = "http://webone.tk/regapp/";

    @FormUrlEncoded
    @POST("restapi.php")
    Call<List<DataRecv>> sendData(
                                @Field("name1") String name,
                                @Field("name2") String name2,
                                @Field("name3") String name3,
                                @Field("name4") String name4,
                                @Field("email") String email,
                                @Field("phone") String phone,
                                @Field("date") String date,
                                @Field("total") int total,
                                @Field("college") String college,
                                @Field("ieee") Boolean ieee,
                                @Field("BPlan") int BPlan,
                                @Field("Contraption") int Contraption,
                                @Field("Clash") int Clash,
                                @Field("Cretronix") int Cretronix,
                                @Field("Datawiz") int Datawiz,
                                @Field("Enigma") int Enigma,
                                @Field("NTH") int NTH,
                                @Field("paperPresentation") int paperPresentation,
                                @Field("Pixelate") int Pixelate,
                                @Field("Roboliga") int Roboliga,
                                @Field("Reverse_Coding") int Reverse_Coding,
                                @Field("Quiz") int Quiz,
                                @Field("Software_Development") int Software_Development,
                                @Field("WebWeaver") int WebWeaver,
                                @Field("WallStreet") int WallStreet,
                                @Field("Xodia") int Xodia);
}
