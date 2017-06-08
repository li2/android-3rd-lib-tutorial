/# Multiple Screen Tutorial #



### 使用 Layout 背景显示图片可能会导致图片被拉伸 ###

应该通过 ImageView 显示图片，利用其 scaleType 属性，保持图片的纵横比（aspect ratio），避免图片被拉伸变形。

一种错误做法，使用某种 Layout，通过设置背景来显示图片：`setBackgroundResource(int resId)`，因为图片会完全填充 layout 的背景，如果图片和 layout 的纵横比不同，则图片被拉伸。比如，layout 100x100dp，图片 120x100pixel，则图片的宽度被压缩，看着瘦长。如果这是历史遗留问题，那么解决办法是根据 layout 尺寸缩放图片；或者在 layout 里面增加一个 ImageView。[link](https://stackoverflow.com/a/9891929/2722270)


### 必须为大屏提供布局文件 ###

即使是固定为横屏的 App，也不能仅仅通过配置修饰符 `-land/` 指定布局文件，因为手机和平板的屏幕尺寸不同，**UI 元素的尺寸和位置需要根据屏幕大小设计**，所以布局文件也可能需要 2 份，以更合理地利用屏幕空间，**实践中常使用 `w<N>dp` 限定**。但如果**大屏 UI Spec 仅仅是小屏的放大**（UI 元素，间距等），位置并没有变化，那么只需要 1 份布局文件。

但不论是 1 or 2 份布局文件，dimens 是必须提供 2 份的。实践中仍是使用 `w<N>dp` 限定。密度限定符是为了图片适配，为 dimens 使用密度限定符非常多余。



### 以适当的 dp 值、wrap_content、match_parent 指定所有布局尺寸值，字体使用 sp(scale-independent pixel)

dp 已经是密度无关单位，不需要


### 作为按钮背景的图片最好提供 1:1 透明背景


