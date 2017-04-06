package me.li2.android.tutorial.Retrofit2.L14SendDataFormUrlencoded;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by weiyi on 06/04/2017.
 * https://github.com/li2
 */

public interface TaskService {
    /*
    04-06 02:46:11.201 4810-5644/? D/OkHttp: --> POST http://requestb.in/xvnl60xv http/1.1
    04-06 02:46:11.201 4810-5644/? D/OkHttp: Content-Type: application/x-www-form-urlencoded
    04-06 02:46:11.201 4810-5644/? D/OkHttp: Content-Length: 83
    04-06 02:46:11.201 4810-5644/? D/OkHttp: title=xxx
    04-06 02:46:11.201 4810-5644/? D/OkHttp: --> END POST (83-byte body)
     */
    @FormUrlEncoded
    @POST("http://requestb.in/xvnl60xv")
    Call<String> createTask(@Field("title") String title);

    /*
    04-06 02:57:07.511 6530-8037/? D/OkHttp: --> POST http://requestb.in/xvnl60xv http/1.1
    04-06 02:57:07.511 6530-8037/? D/OkHttp: Content-Type: application/x-www-form-urlencoded
    04-06 02:57:07.511 6530-8037/? D/OkHttp: Content-Length: 164
    04-06 02:57:07.511 6530-8037/? D/OkHttp: title=xxx&title=xxx
    04-06 02:57:07.511 6530-8037/? D/OkHttp: --> END POST (164-byte body)
     */
    @FormUrlEncoded
    @POST("http://requestb.in/xvnl60xv")
    Call<String> createTasks(@Field("title") List<String> titles);
}
