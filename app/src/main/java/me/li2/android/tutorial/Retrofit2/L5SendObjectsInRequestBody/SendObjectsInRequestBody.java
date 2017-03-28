package me.li2.android.tutorial.Retrofit2.L5SendObjectsInRequestBody;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.li2.android.tutorial.Retrofit2.L3CreatingSustainableClient.ServiceGenerator;
import me.li2.android.tutorial.SimpleOneButtonActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by weiyi on 27/03/2017.
 * https://github.com/li2
 */

public class SendObjectsInRequestBody extends SimpleOneButtonActivity {

    @Override
    protected String getButtonText() {
        return "POST";
    }

    @Override
    public void doAction() {
        sendTasks();
    }

    private void sendTasks() {
        List<Task> tasks = new ArrayList<>();
        int id=1;
        tasks.add(new Task(id++, "li2"));
        tasks.add(new Task(id++, "Breakfast"));
        tasks.add(new Task(id++, "Ride"));
        tasks.add(new Task(id++, "Programming"));

        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        taskService.createTasks(tasks).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(SendObjectsInRequestBody.this, "succeed to send tasks", Toast.LENGTH_SHORT).show();
                // result: R.drawable.retrofit_send_objects_in_request_body
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SendObjectsInRequestBody.this, "error :( " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
