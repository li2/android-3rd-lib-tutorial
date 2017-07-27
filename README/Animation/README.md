# Animation Tutorial

[如何学习 Android Animation？](http://li2.me/2016/01/how-to-learn-android-animation.html)


## Animator & ValueAnimator & ObjectAnimator

[译: Android Animation in Honeycomb by Chet Haase (Android 3.0系统中的动画机制)](http://li2.me/2016/01/android-animation-in-honeycomb.html)

这篇文章讨论了一个问题，已经有了能实现 move, scale, rotate, and fade 这些视图动画的 android.view.animation，为什么还要在 3.0 引入新 APIs android.animation？ 新 APIs 带来了哪些新特性？ 然后进一步展示了这些新特性的强大便利之处。


## Interpolators

[Android Animation Interpolator - Android 动画插值器源码笔记](http://li2.me/2016/01/android-animation-interpolator.html)

插值器定义了动画改变的速率，比如允许 alpha, scale, translate, rotate 加速、减速、重复等。比如插值器 fast_out_slow_in， 加速开始、逐渐降速直至结束，这样的动画效果使得对象在整个运动轨迹中，在接近终点的位置耗费较多时间。而根据不同的场景，选择不同的插值器或者自定义插值器，就可以使动画更具意义，摆脱千篇一律的印象。
 
插值器用一个 0~1.0 范围的浮点数表示当前动画播放的进度，0 代表开始播放，1.0 代表播放结束。通过 getInterpolation 把当前进度映射成另一个值。动画参照的时间由此被「篡改」，动画的速度由此被改变。

> 建议取消历法，让生活模糊起来！就分以前和以后，保持时间的连续性 2015-11-19


## ViewPropertyAnimator

[译: Android ViewPropertyAnimator 介绍](http://li2.me/2016/02/introducing-android-viewpropertyanimator.html)

> 这篇文章介绍了在 3.1 中增加的和动画机制有关的类 ViewPropertyAnimator。先从三个方面做了论述，虽然 3.0 时已经有了能实现任意对象和属性动画的 ObjectAnimator，但仍然有必要提供一个新类，以提供一种更加简明易读的方式来实现 View 多个属性的并发动画。然后介绍了这个新类 ViewPropertyAnimator 的用法。并且提供了一个例子和视频来展示实际的效果。


## Material Animation

Material Animation 最好的教程是开源库 https://github.com/lgvalle/Material-Animations 的 README 文档，另一个很棒的开源库 https://github.com/hitherejoe/animate 。

> Android Transition Framework can be used for three main things:
>
> - Animate activity layout content when transitioning from one activity to another.
> - Animate shared elements (Hero views) in transitions between activities.
> - Animate view changes within same activity.

### (Material) Touch Feedback

一种波纹效果，波纹从触点开始向外扩散直至填充整个view，点击继承自 Theme.Material 的 button 就可以看到这种效果，在 XML 中加入如下定义即可：

    android:background="?android:attr/selectableItemBackground"
    
这里可能会遇到的问题（[代码link](https://github.com/li2/Fiserv_SmartMoney/search?utf8=%E2%9C%93&q=selectableItemBackground&type=)），

- 设置 `android:foreground` 实现水纹效果，包括以下几种情形：

    * `Button` 为 background 设置图片（为了使button被整个图片填充）而失去了水纹效果；
    * CardView 设置属性 `card_view:cardBackgroundColor` 也会失去水纹效果；
    * RecyclerView ViewHolder item 设置水纹效果无效时。

- 并排/列的 icon 和 text 作为1个整体，不要使用2个组件，1个Button就足够了：`android:drawableLeft`, `drawableTop` 等，对于上下并列的情况，必须定义`layout_height` 为一个固定值比如80dp，不能是 wrap_content，否则 icon 和 text 的排列会很奇怪，然后通过 padding 约束 icon 和 text 的间距。


### (Material) Transitions between Activities

在两个页面间平滑过渡，常见的场景是从列表视图切换到详情视图。
转场动画的实现需要定义 `exitTransition`, `reenterTransition`, `enterTransition`，`returnTransition` 等，动画效果可以是 `Fade`, `Slide`, `Explode`, 可以通过代码或者XML定义，一段示例代码：https://github.com/li2/Fiserv_SmartMoney/commit/b9e0943473

    Transition transition;
    
    new Explode();
    new Fade();
    new Slide();
    
    TransitionInflater.from(this).inflateTransition(android.R.transition.explode);
    TransitionInflater.from(this).inflateTransition(android.R.transition.fade);
    TransitionInflater.from(this).inflateTransition(android.R.transition.slide_right);
    
    transition.setDuration(getResources().getInteger(android.R.integer.config_longAnimTime)));
    getWindow().setEnterTransition(transition);


### (Material) Shared elements between Activities / Fragments

这是比转场动画更有意义的动画，尤其适合具有相同 UI 元素的列表-详情界面。特别要注意的是列表项的 transitionName 必须是惟一的。一段代码示例：https://github.com/li2/Fiserv_SmartMoney/commit/489687d467

Implement transition animation between RecyclerView & Fragment, that's list-detail screen.

* through shared element
* which must have same & unique transitionName,
* for recyclerView, we have to define transitionName in codes instead of XML, to give unique id to every item.
* for Detail Fragment, we have to postpone transition until shared element (image view) have loaded.


### (Material) Animate view layout elements

布局动画，TODO
Transition Framework can also be used to animate layout property changes in a view. You just need to make whatever changes you want and it will perform necessary animations for you.


### (Material) Circular Reveal

Circular Reveal is just an animation to show or hide a group of UI elements.
一个圆形扇面从触点开始向外辐射直至填充整个view，这种过渡效果常用于展示新的内容。

### (Material) Animated Vector Drawables

Vector Drawables are scalable without losing definition. The AnimatedVectorDrawable class (and AnimatedVectorDrawableCompat for backward-compatibility) lets you animate the properties of a vector drawable.
比如，浮动操作按钮的状态根据“用户是否会出席当前页面的这个会议”而改变，状态改变时伴随着动画（+号、勾号切换）。

### (Material) Animate View State Changes

The StateListAnimator class lets you define animators that run when the state of a view changes. The following example shows how to define an StateListAnimator as an XML resource:
View 状态改变时的动画。这个实例的核心是为 `android:state_pressed` 值为 true/false 定义不同的 `objectAnimator`.

------

weiyi.li @SH 2017-07-27
