package me.li2.android.tutorial.BasicUI;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.li2.android.tutorial.R;

import static me.li2.android.tutorial.BasicUI.LogHelper.makeLogTag;

/**
 * Created by weiyi on 22/04/2017.
 * https://github.com/li2
 */

public class PhotoPageFragment extends Fragment {
    private static final String TAG = makeLogTag(PhotoPageFragment.class);

    private static final String ARG_KEY_PHOTO_LOAD_FROM_PATH = "arg_key_photo_load_from_path";
    private static final String ARG_KEY_PHOTO_LOAD_FROM_URI = "arg_key_photo_load_from_uri";
    private static final String ARG_KEY_PHOTO_LOAD_FROM_IMAGE_FILE_PATH = "arg_key_photo_load_from_image_file_path";
    private static final String ARG_KEY_PHOTO_LOAD_FROM_DRAWABLE_RESOURCE_ID = "arg_key_photo_load_from_drawable_resource_id";

    @BindView(R.id.photo_page_descriptionView)
    TextView mPhotoDescriptionView;

    @BindView(R.id.photo_page_imageView)
    ImageView mPhotoView;

    private String mPhotoPath;
    private Uri mPhotoUri;
    private File mPhotoFile;
    private int mPhotoResourceId;

    /**
     * Start an image request using the specified path.
     * <p>
     * This path may be a remote URL, file resource (prefixed with {@code file:}), content resource
     * (prefixed with {@code content:}), or android resource (prefixed with {@code
     * android.resource:}.
     * <p>
     * @param path
     */
    public static PhotoPageFragment newPathInstance(String path) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY_PHOTO_LOAD_FROM_PATH, path);
        PhotoPageFragment fragment = new PhotoPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Start an image request using the specified URI.
     * @param uri
     */
    public static PhotoPageFragment newUriInstance(Uri uri) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY_PHOTO_LOAD_FROM_URI, uri.toString());
        PhotoPageFragment fragment = new PhotoPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Start an image request using the specified image file.
     * @param filePath
     */
    public static PhotoPageFragment newFileInstance(String filePath) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY_PHOTO_LOAD_FROM_IMAGE_FILE_PATH, filePath);
        PhotoPageFragment fragment = new PhotoPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Start an image request using the specified drawable resource ID.
     * @param resourceId
     */
    public static PhotoPageFragment newResourceInstance(int resourceId) {
        Bundle args = new Bundle();
        args.putInt(ARG_KEY_PHOTO_LOAD_FROM_DRAWABLE_RESOURCE_ID, resourceId);
        PhotoPageFragment fragment = new PhotoPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        if (args.containsKey(ARG_KEY_PHOTO_LOAD_FROM_PATH)) {
            mPhotoPath = args.getString(ARG_KEY_PHOTO_LOAD_FROM_PATH);
        }

        if (args.containsKey(ARG_KEY_PHOTO_LOAD_FROM_URI)) {
            mPhotoUri = Uri.parse(args.getString(ARG_KEY_PHOTO_LOAD_FROM_URI));
        }

        if (args.containsKey(ARG_KEY_PHOTO_LOAD_FROM_IMAGE_FILE_PATH)) {
            mPhotoFile = new File(args.getString(ARG_KEY_PHOTO_LOAD_FROM_IMAGE_FILE_PATH));
        }

        if (args.containsKey(ARG_KEY_PHOTO_LOAD_FROM_DRAWABLE_RESOURCE_ID)) {
            mPhotoResourceId = args.getInt(ARG_KEY_PHOTO_LOAD_FROM_DRAWABLE_RESOURCE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_page, container, false);
        ButterKnife.bind(this, view);

        if (mPhotoPath != null) {
            loadImage(mPhotoPath);
        } else if (mPhotoUri != null) {
            loadImage(mPhotoUri);
        } else if (mPhotoFile != null) {
            loadImage(mPhotoFile);
        } else if (mPhotoResourceId > 0) {
            loadImage(mPhotoResourceId);
        }

        return view;
    }


    private void loadImage(String path) {
        mPhotoDescriptionView.setText("Loading Image from Path: " + path);
        Picasso.with(getActivity())
                .load(path)
                .into(mPhotoView);
    }

    private void loadImage(Uri uri) {
        mPhotoDescriptionView.setText("Loading Image from Uri: " + uri.getPath());
        Picasso.with(getActivity())
                .load(uri)
                .into(mPhotoView);
    }

    private void loadImage(File file) {
        mPhotoDescriptionView.setText("Loading Image from File: " + file.getPath());
        Picasso.with(getActivity())
                .load(file)
                .into(mPhotoView);
    }

    private void loadImage(int resourceId) {
        mPhotoDescriptionView.setText("Loading Image from resource ID : " + resourceId);
        Picasso.with(getActivity())
                .load(resourceId)
                .into(mPhotoView);
    }
}
