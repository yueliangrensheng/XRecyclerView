# XRecyclerView

## decoration

- SpacingItemDecoration  
> RecyclerView的item宽度计算需要减去 item的 left 和 right 值，因此 每个item的 left + right 之和必须一致，否则 渲染出来的item宽度会不一致
> 就需要结合 RecyclerView的padding来处理边缘item多余的间距

