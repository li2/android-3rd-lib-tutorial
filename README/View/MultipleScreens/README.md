/# Multiple Screen Tutorial #


### 使用 Layout 背景显示图片可能会导致图片被拉伸 ###

应该通过 ImageView 显示图片，利用其 scaleType 属性，保持图片的纵横比（aspect ratio），避免图片被拉伸变形。

一种错误做法，使用某种 Layout，通过设置背景来显示图片：`setBackgroundResource(int resId)`，因为图片会完全填充 layout 的背景，如果图片和 layout 的纵横比不同，则图片被拉伸。比如，layout 100x100dp，图片 120x100pixel，则图片的宽度被压缩，看着瘦长。如果这是历史遗留问题，那么解决办法是根据 layout 尺寸缩放图片；或者在 layout 里面增加一个 ImageView。[link](https://stackoverflow.com/a/9891929/2722270)

### include 包含的 layout 是一个自定义 View，findViewById() 返回 null
    // layout/activity_main.xml
    <include
         layout="@layout/view_custom"/>

    // layout/view_custom.xml
    <me.li2.android.CustomView>
        <ImageView
            android:scaleType="fitCenter"/>
    </me.li2.android.CustomView>    

    public class CustomView extends LinearLayout {
        public CustomView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            // 即使此处调用 inflate() 仍然 null
            mImageView = (ImageView) findViewById(R.id.imageView);
            // is null
        }
    
        // Called after a view and all of its children has been inflated from XML.
        @Override
        protected void onFinishInflate() {
            super.onFinishInflate();
            mImageView = (ImageView) findViewById(R.id.imageView);
            // not null
        }
    }

另一种解决办法是把 `<me.li2.android.CustomView>` 直接放到 `layout/activity_main.xml` 中，

    public class CustomView extends LinearLayout {
        public CustomView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            LayoutInflater.from(getContext()).inflate(R.layout.view_custom, this);
            mImageView = (ImageView) findViewById(R.id.imageView);
            // not null
        }
    }

### 使用 ImageView & ImageButton 保证图片不被拉伸

这2点可以保证图片不被拉伸，即使背景和图片纵横比不同：

- `layout_width` = `layout_height` 只需定义一个 dimens，不必根据图片的纵横比定义两个 dimens.
- `android:scaleType="fitCenter"`
- `ImageView.setImageResource(int resId)`

一般情况下，ImageButton 纵横比 1:1，对于新切的图，最好也提供纵横比(aspect ratio)为 1:1、背景透明的图片作为按钮的背景。

但如果过去切的图包含背景色、边框色、纵横比为 1.2:1，那么这种情况下，除非重新切图，否则只能保持 layout 和图片纵横比一致。


### 必须为大屏提供布局文件 ###

即使是固定为横屏的 App，也不能仅仅通过配置修饰符 `-land/` 指定布局文件，因为手机和平板的屏幕尺寸不同，**UI 元素的尺寸和位置需要根据屏幕大小设计**，所以布局文件也可能需要 2 份，以更合理地利用屏幕空间，**实践中常使用 `w<N>dp` 限定**。但如果**大屏 UI Spec 仅仅是小屏的放大**（UI 元素，间距等），位置并没有变化，那么只需要 1 份布局文件。

但不论是 1 or 2 份布局文件，dimens 是必须提供 2 份的。实践中仍是使用 `w<N>dp` 限定。密度限定符是为了图片适配，为 dimens 使用密度限定符非常多余。



### 以适当的 dp 值、wrap_content、match_parent 指定所有布局尺寸值，字体使用 sp(scale-independent pixel)

dp 已经是密度无关单位，不需要在 values-mdpi/dimens.xml 等中定义。如果涉及到 Phone & Tablet，只需要在 values 和 values-w820dp 定义就可以了。




