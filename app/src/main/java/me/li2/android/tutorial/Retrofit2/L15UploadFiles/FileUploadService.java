package me.li2.android.tutorial.Retrofit2.L15UploadFiles;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by weiyi on 07/04/2017.
 * https://github.com/li2
 */

public interface FileUploadService {
    /*
    04-07 14:18:47.160 19859-21303/me.li2.android.tutorial D/OkHttp: --> POST http://requestb.in/r2k92yr2 http/1.1
    04-07 14:18:47.160 19859-21303/me.li2.android.tutorial D/OkHttp: Content-Type: multipart/form-data;
    04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: Content-Disposition: form-data; name="description"
    04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: hello, this is li2 speaking
    04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: --> END POST (537-byte body)
     */
    @Multipart
    @POST("http://requestb.in/r2k92yr2")
    Call<ResponseBody> upload(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );
}
