package com.junpu.widget.searchbar;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * @author Junpu
 * @time 2017/12/4 13:31
 */
public class SearchBar extends FrameLayout implements OnClickListener, OnEditorActionListener {

    public static final int SEARCH_ACTION_SEARCH = 1; // 搜索状态
    public static final int SEARCH_ACTION_CANCEL = 2; // 取消状态

    private int mSearchAction = SEARCH_ACTION_CANCEL;

    private EditText mEditSearch;
    private View mBtnClear;
    private TextView mBtnSearch;

    private String mSearchKey;

    private OnSearchListener mOnSearchListener;

    public SearchBar(Context context) {
        this(context, null, 0);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int height = getResources().getDimensionPixelOffset(R.dimen.search_height);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        setLayoutParams(params);
        LayoutInflater.from(getContext()).inflate(R.layout.searchbar_layout, this);
        mEditSearch = findViewById(R.id.editSearch);
        mEditSearch.addTextChangedListener(mTextWatcher);
        mEditSearch.setOnEditorActionListener(this);

        mBtnClear = findViewById(R.id.clear);
        mBtnClear.setOnClickListener(this);
        mBtnSearch = findViewById(R.id.search);
        mBtnSearch.setOnClickListener(this);
    }

    /**
     * 编辑框文字变化
     */
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String key = s.toString();
            if (TextUtils.isEmpty(key)) {
                mBtnClear.setVisibility(View.INVISIBLE);
                mBtnSearch.setText(R.string.cancel);
                mSearchAction = SEARCH_ACTION_CANCEL;
                mSearchKey = key;
            } else {
                mBtnClear.setVisibility(View.VISIBLE);
                mBtnSearch.setText(R.string.search);
                mSearchAction = SEARCH_ACTION_SEARCH;
            }
            if (mOnSearchListener != null) {
                mOnSearchListener.onSearchKeyChanged(key);
            }
        }
    };

    /**
     * 软键盘搜索事件
     *
     * @param v
     * @param actionId
     * @param event
     * @return
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchAction();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.search) {
            if (mSearchAction == SEARCH_ACTION_SEARCH) {
                searchAction();
            } else if (mSearchAction == SEARCH_ACTION_CANCEL) {
                if (mOnSearchListener != null) {
                    mOnSearchListener.onCancel();
                }
            }
        } else if (id == R.id.clear) {
            if (!TextUtils.isEmpty(mEditSearch.getText())) {
                mEditSearch.setText("");
            }
        }
    }

    private void searchAction() {
        String key = mEditSearch.getText().toString().trim();
        if (TextUtils.isEmpty(key)) {
            mEditSearch.requestFocus();
        } else if (!key.equals(mSearchKey)) {
            mSearchKey = key;
            if (mOnSearchListener != null) {
                mOnSearchListener.onSearch(key);
            }
        }
        SearchUtils.hideInputMethod(getContext(), mEditSearch);
    }

    public EditText getEditSearch() {
        return mEditSearch;
    }

    public String getSearchKey() {
        return mSearchKey;
    }

    /**
     * 设置搜索回调
     *
     * @param listener
     */
    public void setOnSearchListener(OnSearchListener listener) {
        mOnSearchListener = listener;
    }

    public interface OnSearchListener {
        void onSearchKeyChanged(String key);
        void onSearch(String key);
        void onCancel();
    }

    public static class SimpleOnSearchListener implements OnSearchListener {

        @Override
        public void onSearchKeyChanged(String key) {
        }

        @Override
        public void onSearch(String key) {
        }

        @Override
        public void onCancel() {
        }
    }

}
