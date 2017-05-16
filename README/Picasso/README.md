# Picasso Tutorial

[tutorial codes](../../app/src/main/java/me/li2/android/tutorial/Picasso)

> A powerful image downloading and caching library for Android.
> Many common pitfalls of image loading on Android are handled automatically by Picasso:
>
> - Handling ImageView recycling and download cancelation in an adapter.
> - Complex image transformations with minimal memory use.
> - Automatic memory and disk caching.


## Loading images from

[PhotoPageFragment.java](../../app/src/main/java/me/li2/android/tutorial/BasicUI/PhotoPageFragment.java)

- a URL
- a specified drawable resource ID
- a File object
- a Uri

> The Picasso library is using a fluent interface, specifically implemented with the Picasso class. The Picasso class requires at least three parameters for a fully functional request (in one line of code):
 
    Picasso
        .with(context)
        .load("http://i.imgur.com/DvpvklR.png")
        .into(imageView);


## Displaying images in ListView / GridView

[ImageDisplaying.java](../../app/src/main/java/me/li2/android/tutorial/Picasso/L2ImageDisplaying/ImageDisplaying.java)

* 在 `ArrayAdapter.getView()` 中调用 `Picasso.load(urls[position])` 下载 image 并显示在 ImageView 上，和之前演示的仅仅加载一张图片的调用方法完全一致：

    > Picasso call is exactly the same as in the previously used 'regular' loading of images.

* `Picasso.fit()` 方法导致 `android:scaleType="centerInside"` 无效。

* 必须设定 ListView 中的 ImageView 为固定高度，否则 ListView 不显示任何内容。

* Picasso 替开发者解决了 ImageView 重用，以及滑动过程中加载的问题：

     > we need to re-use layouts in ListViews to create a fast & smooth scrolling experience. One awesomeness of Picasso is that it automatically takes care of the request canceling, clearing of the ImageViews, and loading the correct image into the appropriate ImageView.

* Picasso 替开发者解决了缓存的问题：

    > When loading an image, Picasso uses three sources: memory, disk and network (ordered from fastest to slowest). Once again, there is nothing you'll have to do. Picasso hides all that complexity from you, while creating intelligently sized caches for you. 

* Picasso 可以在图片加载成功前显示 Placeholders 占位图，在加载失败后显示 Errors 图片。
    
    > An expected behavior of an application is to display a placeholder until the image is loaded and processed. If the image cannot be loaded, display an error image to make it obvious to the user when something goes wrong.

* How to fix crash: java.lang.IllegalArgumentException: Path must not be empty
        
        // catch the case that the passed image URL is either null or an empty string
        if (TextUtils.isEmpty(url)) {
            // option 1: cancel Picasso request and clear ImageView
            Picasso.with(mContext)
                    .cancelRequest(imageView);
            // this avoids the situation where an incorrect image from a previous ListView item gets displayed.
            // however, it looks weird in the UI, since ImageView might still occupy the space in the UI.
            imageView.setImageDrawable(null);
    
            // option 2: load placeholder
            Picasso.with(mContext)
                    .load(R.drawable.ic_image_broken)
                    .into(imageView);
    
        } 


## Advanced Loading & Optimizations

### `.get()` synchronously loads the image and returns a Bitmap object.

    Bitmap bitmap = Picasso.with(this).load(url).get();

### Use interface `Target` as Callback Mechanism.
 
> So far, we've always used an ImageView as parameter for `.into()`. That is not the complete functionality of `.into()`. It is also possible to use an implementation of the `Target` interface.
>
> Picasso will load the image just like before, but instead of displaying it in an ImageView, it'll return the Bitmap (or the error!) through the `Target` callback.
>
> whenever you need the raw bitmap use either `.get()` or an implementation of `Target` to receive the image.

    Picasso.with(this)
            .load(ImagesData.URLS[3])
            .into(mTarget);
    
    private Target mTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {}
        @Override
        public void onBitmapFailed(Drawable errorDrawable) {}
        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {}
    };
        
### Load Images to Custom Notifications with RemoteViews

    Picasso.with(this)
            .load(ImagesData.URLS[0])
            .into(mNotificationBuilder.getBigContentView(),
                    R.id.notification_image,
                    CUSTOMIZED_NOTIFICATION_ID,
                    mNotificationBuilder.build()
            );

### Ordering Requests by Image Priority: `.priority(Priority)`

### Request Management by Grouping of Images via Tag() to cancel, pause or resume multiple images at the same time
    
> If your view changes rapidly, it'd be very useful to cancel all requests of the images on the previous, outdated screen and start the images of the new view. Picasso covers that functionality with `tag()`. [Official Picasso SampleScrollListener.java](https://github.com/square/picasso/blob/master/picasso-sample/src/main/java/com/example/picasso/SampleScrollListener.java)

    mListView.setOnScrollListener(new SampleScrollListener(this));

    Picasso.with(mContext)
            .load(url)
            .tag(this)
            .into(imageView);
    
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        final Picasso picasso = Picasso.with(context);
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            picasso.resumeTag(context);
        } else {
            picasso.pauseTag(context);
        }
    }

## References

[Picasso](http://square.github.io/picasso/)
[Future Studio - Picasso Tutorials](https://futurestud.io/tutorials/picasso-series-round-up)
