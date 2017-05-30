# RecyclerView Tutorial

[SimpleRecyclerFragment.java](../../../app/src/main/java/me/li2/android/tutorial/BasicWidget/SimpleRecyclerView/SimpleRecyclerFragment.java)
[SimpleRecyclerAdapter.java](../../../app/src/main/java/me/li2/android/tutorial/BasicWidget/SimpleRecyclerView/SimpleRecyclerAdapter.java)

## 点击事件 ##

在 `RecyclerView.ViewHolder` constructor 中注册监听器。

## 更改 ViewHolder 宽、高等 layout 参数

在 `RecyclerView.Adapter.onCreateViewHolder` 中设置布局参数:

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(android.R.layout.simple_list_item_1, viewGroup, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);

        return new ViewHolder(v);
    }
