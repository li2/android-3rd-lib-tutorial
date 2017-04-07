package me.li2.android.tutorial.Retrofit2.L15UploadFiles;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Refer to
 *
 * Is it possible to show progress bar when upload image via Retrofit 2
 * http://stackoverflow.com/a/33384551/2722270
 *
 * Is it possible to show progress bar (percent) when downloading file in retrofit 2
 * https://github.com/square/retrofit/issues/1759
 *
 * Created by weiyi on 07/04/2017.
 * https://github.com/li2
 */

public class ProgressRequestBody extends RequestBody {
    private MediaType mContentType;
    private File mFile;
    private String mPath;
    private UploadCallbacks mListener;

    private static final int DEFAULT_BUFFER_SIZE = 2048;

    public interface UploadCallbacks {
        void onProgressUpdate(int percentage, long uploaded, long total);
        void onError();
        void onFinish();
    }

    public ProgressRequestBody(final MediaType contentType, final File file, final UploadCallbacks listener) {
        mContentType = contentType;
        mFile = file;
        mListener = listener;
    }

    @Override
    public MediaType contentType() {
        return mContentType;
    }

    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;

        try {
            int read;
            while ((read = in.read(buffer)) != -1) {
                uploaded += read;
                sink.write(buffer, 0, read);
                notifyProgress(uploaded, fileLength);
            }
        } finally {
            in.close();
        }
    }

    // to avoid duplicated notification
    private int mLastProgress;

    private void notifyProgress(final long uploaded, final long total) {
        if (mListener != null) {
            // update progress on UI thread
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    int progress = Math.round(100.0f * uploaded / total);
                    if (mLastProgress != progress) {
                        mLastProgress = progress;
                        mListener.onProgressUpdate(progress, uploaded, total);
                    }
                }
            });
        }
    }
}