package com.junpu.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 中文1个字符、英文半个字符的EditText字数限制类
 *
 * @author zhangjunpu
 * @date 2017/3/19
 */
public class MaxLengthWatcher implements TextWatcher {

    private EditText mEditText;
    private float mMaxLength;
    private String mBeforText;
    private boolean isNeedRefresh;
    private String mResult;
    private int mSelection;
    private List<InputFilter> mFilterList = new ArrayList<>();

    private OnTextChangeListener mOnTextChangeListener;

    public MaxLengthWatcher(EditText editText, int maxLength, InputFilter... filters) {
        this.mEditText = editText;
        this.mMaxLength = maxLength;
        initFilters(filters);
    }

    public MaxLengthWatcher(EditText editText, int maxLength, OnTextChangeListener listener, InputFilter... filters) {
        this.mEditText = editText;
        this.mMaxLength = maxLength;
        initFilters(filters);
        setOnTextChangeListener(listener);
    }

    public MaxLengthWatcher(EditText editText, float maxLength, OnTextChangeListener listener, InputFilter... filters) {
        this.mEditText = editText;
        this.mMaxLength = maxLength;
        initFilters(filters);
        setOnTextChangeListener(listener);
    }

    private void initFilters(InputFilter... filters) {
        Collections.addAll(mFilterList, filters);
    }

    private InputFilter[] getInputFilters(InputFilter filter) {
        InputFilter[] filters = new InputFilter[mFilterList.size() + 1];
        for (int i = 0; i < mFilterList.size(); i++) {
            filters[i] = mFilterList.get(i);
        }
        filters[mFilterList.size()] = filter;
        return filters;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mBeforText = s.toString();
        isNeedRefresh = false;
    }

    /**
     * @param s      变化后的字符
     * @param start  变化开始的位置
     * @param before 被删除的字符长度
     * @param count  增加的字符长度
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String content = s.toString();
        float length = StringUtils.getLength(content);
        if (count - before >= 0 && length > mMaxLength) {
            String changeStr = content.substring(start, start + count);
            String deleteStr = mBeforText.substring(start, start + before);
            mResult = changeString(mBeforText, deleteStr, changeStr, mMaxLength, start);
            mSelection = start + mResult.length() - mBeforText.length() + deleteStr.length();
            isNeedRefresh = true;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        String content = s.toString();
        float mCurLength = StringUtils.getLength(content);
        int length = s.length();

        InputFilter inputFilter;
        if (mCurLength > mMaxLength) {
            inputFilter = new InputFilter.LengthFilter(length);
        } else {
            inputFilter = new InputFilter.LengthFilter(Integer.MAX_VALUE);
        }
        mEditText.setFilters(getInputFilters(inputFilter));

        if (mOnTextChangeListener != null) {
            mOnTextChangeListener.onTextChange(s);
        }

        if (isNeedRefresh) {
            isNeedRefresh = false;
            mEditText.setText(mResult);
            if (mSelection > mEditText.getText().length()) {
                mSelection = mEditText.getText().length();
            }
            mEditText.setSelection(mSelection);
        }
    }

    private String changeString(String beforeStr, String deleteStr, String changeStr, float maxLength, int start) {
        float beforLength = StringUtils.getLength(beforeStr);
        float deleteLength = StringUtils.getLength(deleteStr);
        float changeLength = StringUtils.getLength(changeStr);
        if (beforLength - deleteLength + changeLength <= maxLength) {
            StringBuilder sb = new StringBuilder(beforeStr);
            sb.delete(start, start + deleteStr.length());
            sb.insert(start, changeStr);
            return sb.toString();
        } else {
            String str = changeStr.substring(0, changeStr.length() - 1);
            return changeString(beforeStr, deleteStr, str, maxLength, start);
        }
    }

    public void setOnTextChangeListener(OnTextChangeListener listener) {
        mOnTextChangeListener = listener;
    }

    public interface OnTextChangeListener {
        void onTextChange(Editable s);
    }
}
