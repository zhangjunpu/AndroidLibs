//package com.junpu.adapter.loadmore;
//
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.RecyclerView.LayoutManager;
//import android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.junpu.adapter.RecyclerViewHolder;
//import java.util.ArrayList;
//
///**
// * 加载更多adapter
// *
// * @author zhangjunpu
// * @date 15/12/15
// */
//public abstract class LoadMoreRecyclerAdapter<T> extends BaseRecyclerAdapter<T> implements RecyclerViewHolder.OnRecyclerItemClickListener {
//
//    public List<RecyclerViewHolder.OnRecyclerItemClickListener> footerListeners;
//
//    public void addOnFooterItemClickListener(RecyclerViewHolder.OnRecyclerItemClickListener mListener) {
//        if (footerListeners == null) {
//            footerListeners = new ArrayList<>();
//        }
//        footerListeners.add(mListener);
//    }
//
//    public static final int TYPE_ITEM = 0;
//    public static final int TYPE_FOOTER = 1;
//
//    protected boolean isFooterHide;
//
//    protected LoadMoreStatus loadMoreStatus = LoadMoreStatus.LOADING;
//    protected String loadMoreText;
//
//    public enum LoadMoreStatus {
//        LOADING, FINISH, FAILURE
//    }
//
//    @Override
//    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == TYPE_FOOTER) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(com.junpu.adapter.R.layout.loadmore_view, parent, false);
//            return new FooterViewHolder(view, this);
//        }
//        return onCreateHolder(parent, viewType);
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerViewHolder h, int position) {
//        if (isFooter(position)) {
//            FooterViewHolder holder = (FooterViewHolder) h;
//            holder.bindData();
//            return;
//        }
//        onBindHolder(h, position);
//    }
//
//    public abstract RecyclerViewHolder onCreateHolder(ViewGroup parent, int viewType);
//
//    public abstract void onBindHolder(RecyclerViewHolder h, int position);
//
//    @Override
//    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
//        if (isFailureStatus()) {
//            setLoadingStatus();
//            if (footerListeners != null && !footerListeners.isEmpty()) {
//                for (RecyclerViewHolder.OnRecyclerItemClickListener listener : footerListeners) {
//                    listener.onItemClick(recyclerView, itemView, position);
//                }
//            }
//        }
//    }
//
//    @Override
//    public T getItem(int position) {
//        if (position == getItemCount() - 1) {
//            return null;
//        }
//        return super.getItem(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return super.getItemCount() + 1;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (isFooter(position)) {
//            return TYPE_FOOTER;
//        }
//        return TYPE_ITEM;
//    }
//
//    public boolean isFooter(int position) {
//        return position == getItemCount() - 1;
//    }
//
//    /**
//     * 当前是否是错误状态
//     */
//    public boolean isFailureStatus() {
//        return loadMoreStatus == LoadMoreStatus.FAILURE;
//    }
//
//    /**
//     * 加载失败时显示文字
//     */
//    public void setFailureStatus(String msg) {
//        loadMoreStatus = LoadMoreStatus.FAILURE;
//        loadMoreText = msg + "\n点击此处重新加载";
//        notifyItemChanged(getItemCount() - 1);
//    }
//
//    /**
//     * 这是为loading状态
//     */
//    public void setLoadingStatus() {
//        loadMoreStatus = LoadMoreStatus.LOADING;
//        notifyItemChanged(getItemCount() - 1);
//    }
//
//    /**
//     * 设置是否已加载全部，变更加载更多状态
//     */
//    public void setNoMore(boolean hasNoMore) {
//        loadMoreStatus = hasNoMore ? LoadMoreStatus.FINISH : LoadMoreStatus.LOADING;
//        loadMoreText = "没有更多了";
//    }
//
//    /**
//     * 是否隐藏Footer
//     */
//    public void setFooterHide(boolean footerHide) {
//        isFooterHide = footerHide;
//    }
//
//    class FooterViewHolder extends RecyclerViewHolder {
//
//        public View rootView;
//        public TextView message;
//        public View progress;
//
//        public FooterViewHolder(View itemLayoutView, OnRecyclerItemClickListener listener) {
//            super(itemLayoutView, listener);
//        }
//
//        @Override
//        public void onBindView(View itemLayoutView) {
//            rootView = itemLayoutView;
//            message = (TextView) itemLayoutView.findViewById(com.junpu.adapter.R.id.loading_text);
//            progress = itemLayoutView.findViewById(com.junpu.adapter.R.id.loading);
//        }
//
//        public void bindData() {
//            if (loadMoreStatus == LoadMoreStatus.LOADING) {
//                progress.setVisibility(View.VISIBLE);
//                message.setVisibility(View.INVISIBLE);
//            } else {
//                progress.setVisibility(View.INVISIBLE);
//                message.setVisibility(View.VISIBLE);
//                message.setText(loadMoreText);
//            }
//            rootView.getLayoutParams().height = isFooterHide ? 0 : ViewGroup.LayoutParams.WRAP_CONTENT;
//        }
//    }
//
//    /**
//     * 重写此方法可以使瀑布流布局加载更多Item可以横向充满
//     */
//    @Override
//    public void onViewAttachedToWindow(RecyclerViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
//        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
//        if (lp != null && lp instanceof LayoutParams
//                && holder.getLayoutPosition() == getItemCount() - 1) {
//            ((LayoutParams) lp).setFullSpan(true);
//        }
//    }
//
//    /**
//     * 重写此方法可以使网格布局加载更多Item可以横向充满全屏
//     */
//    @Override
//    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        LayoutManager lm = recyclerView.getLayoutManager();
//        if (lm instanceof GridLayoutManager) {
//            final GridLayoutManager manager = (GridLayoutManager) lm;
//            manager.setSpanSizeLookup(new SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    if (isFooter(position)) {
//                        return manager.getSpanCount();
//                    }
//                    return 1;
//                }
//            });
//        }
//    }
//}
