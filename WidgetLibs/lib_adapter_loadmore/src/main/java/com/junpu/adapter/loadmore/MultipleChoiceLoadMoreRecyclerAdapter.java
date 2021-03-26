//package com.junpu.adapter.loadmore;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author zhangjunpu
// * @date 2017/2/14
// */
//public abstract class MultipleChoiceLoadMoreRecyclerAdapter<T> extends LoadMoreRecyclerAdapter<T> {
//
//    protected List<T> checkedList = new ArrayList<>();
//
//    public void setCheckedPosition(T object) {
//        if (exist(object)) {
//            checkedList.remove(object);
//        } else {
//            checkedList.add(object);
//        }
//        notifyDataSetChanged();
//    }
//
//    /**
//     * 判断是否存在
//     *
//     * @param object 对象
//     * @return
//     */
//    protected boolean exist(T object) {
//        return checkedList.contains(object);
//    }
//
//    /**
//     * 获取选择列表
//     *
//     * @return
//     */
//    public List<T> getCheckedList() {
//        return checkedList;
//    }
//
//    /**
//     * 清空所有已选择的数据
//     */
//    public void clearCheckedList() {
//        checkedList.clear();
//    }
//
//    /**
//     * 获取已选择的数量
//     *
//     * @return
//     */
//    public int getCheckedCount() {
//        return checkedList.size();
//    }
//
//    /**
//     * 选择或取消选择全部
//     *
//     * @param flag
//     */
//    public void checkedAll(boolean flag) {
//        clearCheckedList();
//        if (flag) {
//            for (T t : getCollection()) {
//                checkedList.add(t);
//            }
//        }
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
//        setCheckedPosition(getItem(position));
//
//        if (listener != null) {
//            listener.onItemClick(recyclerView, itemView, position);
//        }
//    }
//
//}
