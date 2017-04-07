package me.li2.android.tutorial.Retrofit2.L15UploadFiles;

import android.content.Intent;
import android.net.Uri;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import net.rdrei.android.dirchooser.DirectoryChooserActivity;
import net.rdrei.android.dirchooser.DirectoryChooserConfig;

import java.io.File;
import java.net.URLConnection;

import me.li2.android.tutorial.BasicUI.SimpleOneButtonActivity;
import me.li2.android.tutorial.R;
import me.li2.android.tutorial.Retrofit2.L3CreatingSustainableClient.ServiceGenerator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.li2.android.tutorial.BasicUI.LogHelper.LOGD;
import static me.li2.android.tutorial.BasicUI.LogHelper.LOGE;
import static me.li2.android.tutorial.BasicUI.LogHelper.makeLogTag;

/**
 * Created by weiyi on 07/04/2017.
 * https://github.com/li2
 */

public class UploadFile extends SimpleOneButtonActivity {
    private static final String TAG = makeLogTag(UploadFile.class);
    @Override
    protected String getButtonText() {
        return "Choose file to upload";
    }

    @Override
    public void doAction() {
        chooseFile();
    }

    private void uploadFile(Uri fileUri) {
        FileUploadService service = ServiceGenerator.createService(FileUploadService.class);

        // get the actual file by fileUri
        File file = new File(fileUri.getPath());

        // create request body instance from file
        // the method requires two parameters: first, the media type, and second, the actual data.
        // The media type should ideally be the actual content-type, eg, a PNG image should have image/png.

        String type = URLConnection.guessContentTypeFromName(file.getName());
        /*
         type = getContentResolver().getType(fileUri);
         NullPointerException: Attempt to invoke interface method 'int java.lang.CharSequence.length()' on a null object reference
         at okhttp3.MediaType.parse(MediaType.java:51)
         */
        RequestBody requestFile =RequestBody.create(MediaType.parse(type), file);

        // add the file wrapped into a MultipartBody.Part instance, which is used to appropriately
        // upload files from client-side, also the actual file name.
        MultipartBody.Part body = MultipartBody.Part.createFormData("File To Upload", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "hello, this is li2 speaking";
        RequestBody description = RequestBody.create(MultipartBody.FORM, descriptionString);

        // finally, execute the request
        Call<ResponseBody> call = service.upload(description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                LOGD(TAG, "upload success " + response.body().charStream().toString());
                /**
                 * Refer to the image for result:
                 */
                int image = R.drawable.retrofit_upload_file;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LOGE(TAG, "upload failed : " + t.getMessage(), t);
            }
        });
    }

    //-------- choose file ----------------------------------------------------

    private static final int REQUEST_FILE = 21;

    // dirchooser:library can only choose directory
    private void chooseDir() {
        Intent intent = new Intent(this, DirectoryChooserActivity.class);
        DirectoryChooserConfig config = DirectoryChooserConfig.builder()
                .newDirectoryName("DirectoryChooseSample")
                .allowReadOnlyDirectory(true)
                .allowNewDirectoryNameModification(true)
                .build();
        intent.putExtra(DirectoryChooserActivity.EXTRA_CONFIG, config);
        startActivityForResult(intent, REQUEST_FILE);
    }

    private void chooseFile() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(REQUEST_FILE)
                .withHiddenFiles(false)
                //.withFilter(Pattern.compile(".*\\.jpg$")) // Filtering files and directories by file name using regexp
                //.withFilterDirectories(false) // Set directories filterable (false by default)
                .start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FILE && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            LOGD(TAG, "file path " + filePath);
            uploadFile(Uri.parse(filePath));
        }
    }
}

/*
OkHttp Log

04-07 14:18:47.160 19859-21303/me.li2.android.tutorial D/OkHttp: --> POST http://requestb.in/r2k92yr2 http/1.1
04-07 14:18:47.160 19859-21303/me.li2.android.tutorial D/OkHttp: Content-Type: multipart/form-data; boundary=637f6e04-ce63-4fb2-a86d-b07e784a0b35
04-07 14:18:47.160 19859-21303/me.li2.android.tutorial D/OkHttp: Content-Length: 537
04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: --637f6e04-ce63-4fb2-a86d-b07e784a0b35
04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: Content-Disposition: form-data; name="description"
04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: Content-Transfer-Encoding: binary
04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: Content-Type: multipart/form-data; charset=utf-8
04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: Content-Length: 27
04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: hello, this is li2 speaking
04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: --637f6e04-ce63-4fb2-a86d-b07e784a0b35
04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: Content-Disposition: form-data; name="File To Upload"; filename="li2.me.android.tutorial.txt"
04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: Content-Type: text/plain
04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: Content-Length: 82
04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: this is a demo file to be uploaded through Retrofit 2.
04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: weiyi.li
04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: https://li2.me
04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: --637f6e04-ce63-4fb2-a86d-b07e784a0b35--
04-07 14:18:47.170 19859-21303/me.li2.android.tutorial D/OkHttp: --> END POST (537-byte body)
04-07 14:19:07.970 19859-21303/me.li2.android.tutorial D/OkHttp: <-- 200 OK http://requestb.in/r2k92yr2 (20798ms)
04-07 14:19:07.970 19859-21303/me.li2.android.tutorial D/OkHttp: Date: Fri, 07 Apr 2017 06:19:07 GMT
04-07 14:19:07.970 19859-21303/me.li2.android.tutorial D/OkHttp: Content-Type: text/html; charset=utf-8
04-07 14:19:07.970 19859-21303/me.li2.android.tutorial D/OkHttp: Transfer-Encoding: chunked
04-07 14:19:07.970 19859-21303/me.li2.android.tutorial D/OkHttp: Connection: keep-alive
04-07 14:19:07.970 19859-21303/me.li2.android.tutorial D/OkHttp: Set-Cookie: __cfduid=d43712e9472e1ef59b3d490e8ad149ee91491545947; expires=Sat, 07-Apr-18 06:19:07 GMT; path=/; domain=.requestb.in; HttpOnly
04-07 14:19:07.970 19859-21303/me.li2.android.tutorial D/OkHttp: Sponsored-By: https://www.runscope.com
04-07 14:19:07.970 19859-21303/me.li2.android.tutorial D/OkHttp: Via: 1.1 vegur
04-07 14:19:07.970 19859-21303/me.li2.android.tutorial D/OkHttp: Server: cloudflare-nginx
04-07 14:19:07.970 19859-21303/me.li2.android.tutorial D/OkHttp: CF-RAY: 34badf9a565a3349-HKG
04-07 14:19:07.970 19859-21303/me.li2.android.tutorial D/OkHttp: ok
04-07 14:19:07.970 19859-21303/me.li2.android.tutorial D/OkHttp: <-- END HTTP (2-byte body)
 */
