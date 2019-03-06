package com.study.emoticons.view.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.study.emoticons.R;
import com.study.emoticons.base.BaseFragment;

import butterknife.BindView;

public class FindFragment extends BaseFragment {
    @BindView(R.id.text_toolbar)
    TextView toolbar;

    public static FindFragment newInstance(String s){
        FindFragment findFragment = new FindFragment();
        Bundle args = new Bundle();
        args.putString("args",s);
        findFragment.setArguments(args);
        return findFragment;
    }

    @Override
    protected void visibleToUser(boolean isVisible, boolean isFirstVisible) {

    }

    @Override
    protected void initConfig(Bundle savedInstanceState) {

    }

    @Override
    protected void initArguments(Bundle bundle) {
        if (bundle != null){
            String text = bundle.getString("args");
            toolbar.setText(text);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void daoBusiness() {

    }
}
