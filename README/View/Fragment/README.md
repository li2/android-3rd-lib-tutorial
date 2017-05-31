# Fragment Tutorial #

SimpleRecyclerFragment

## setRetainInstance(true) ##



## Container 背景透明 ##
在 Fragment.onCreateView 中设置 background color，避免对 container 调用 setVisibility。



## 实例化传递参数避免 null

对于需要初始化的数据，不要通过 setXXX 保存在成员变量中，因为不能保证 onCreate 时这些值还在，通行的做法是：


但 interface 需要 serializable。

args.putSerializable(ARG_KEY_ITEM_CLICK_LISTENER, OnItemClickListener);

可以用一个成员保存 listener，但是旋转后失效。setRetainInstance(true).
