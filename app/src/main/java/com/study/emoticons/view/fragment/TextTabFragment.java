package com.study.emoticons.view.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.study.emoticons.R;
import com.study.emoticons.base.BaseFragment;
import com.study.emoticons.view.weight.PopupWindowMenu;

import butterknife.BindView;
import butterknife.OnClick;

public class TextTabFragment extends BaseFragment implements View.OnClickListener {
    PopupWindowMenu popupWindowMenu;
    @BindView(R.id.root_view)
    RelativeLayout rootView;
    @BindView(R.id.lin_tab_home)
    LinearLayout home;
    @BindView(R.id.lin_tab_emoticons)
    LinearLayout emoticons;
    @BindView(R.id.lin_tab_find)
    LinearLayout find;
    @BindView(R.id.lin_tab_person)
    LinearLayout person;
    @BindView(R.id.tv_tab_home)
    TextView tv_home;
    @BindView(R.id.iv_tab_home)
    ImageView iv_home;
    @BindView(R.id.tv_tab_emoticons)
    TextView tv_emoticons;
    @BindView(R.id.iv_tab_emoticons)
    ImageView iv_emoticons;
    @BindView(R.id.iv_tab_find)
    ImageView iv_find;
    @BindView(R.id.tv_tab_find)
    TextView tv_find;
    @BindView(R.id.tv_tab_person)
    TextView tv_person;
    @BindView(R.id.iv_tab_person)
    ImageView iv_person;
    private HomeFragment homeFragment;
    private EmoticonsFragment emoticonsFragment;
    private FindFragment findFragment;
    private MineFragment mineFragment;

    public static TextTabFragment newInstance() {
        TextTabFragment viewPagerFragment = new TextTabFragment();
        Bundle bundle = new Bundle();
        viewPagerFragment.setArguments(bundle);
        return viewPagerFragment;
    }

    @Override
    protected void visibleToUser(boolean isVisible, boolean isFirstVisible) {

    }

    @Override
    protected void initConfig(Bundle savedInstanceState) {

    }

    @Override
    protected void initArguments(Bundle bundle) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_text_tab;
    }

    @Override
    protected void daoBusiness() {
        home.setOnClickListener(this);
        emoticons.setOnClickListener(this);
        find.setOnClickListener(this);
        person.setOnClickListener(this);
        setFaultFragment();
    }

    private void setFaultFragment() {
        setTabStates(tv_home, getColor(R.color.black), iv_home, R.drawable.ic_home2);
        switchFragment(0);
    }

    private void switchFragment(int i) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        switch (i) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance(getString(R.string.bottom_nav_home));
                }
                transaction.replace(R.id.con, homeFragment);
                break;
            case 1:
                if (emoticonsFragment == null) {
                    emoticonsFragment = EmoticonsFragment.newInstance(getString(R.string.bottom_nav_emoticons));
                }
                transaction.replace(R.id.con, emoticonsFragment);
                break;
            case 2:
                if (findFragment == null){
                    findFragment = FindFragment.newInstance(getString(R.string.bottom_nav_find));
                }
                transaction.replace(R.id.con,findFragment);
                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = MineFragment.newInstance(getString(R.string.bottom_nav_person));
                }
                transaction.replace(R.id.con, mineFragment);
                break;
        }
        transaction.commit();
    }

    private void setTabStates(TextView textView, int color, ImageView imageView, int image) {
        imageView.setImageResource(image);
        textView.setTextColor(color);
    }

    @Override
    public void onClick(View v) {
        resetTabState();
        switch (v.getId()) {
            case R.id.lin_tab_home:
                setTabStates(tv_home, getColor(R.color.black), iv_home, R.drawable.ic_home2);
                switchFragment(0);
                break;
            case R.id.lin_tab_emoticons:
                setTabStates(tv_emoticons, getColor(R.color.black), iv_emoticons, R.drawable.ic_emoticon2);
                switchFragment(1);
                break;
            case R.id.lin_tab_find:
                setTabStates(tv_find, getColor(R.color.black), iv_find, R.drawable.ic_find2);
                switchFragment(2);
                break;
            case R.id.lin_tab_person:
                setTabStates(tv_person, getColor(R.color.black), iv_person, R.drawable.ic_person2);
                switchFragment(3);
                break;
        }
    }

    private void resetTabState() {
        setTabStates(tv_home, getColor(R.color.black), iv_home, R.drawable.ic_home1);
        setTabStates(tv_emoticons, getColor(R.color.black), iv_emoticons, R.drawable.ic_emoticon1);
        setTabStates(tv_find, getColor(R.color.black), iv_find, R.drawable.ic_find1);
        setTabStates(tv_person, getColor(R.color.black), iv_person, R.drawable.ic_person1);
    }

    private int getColor(int res) {
        return ContextCompat.getColor(activity, res);
    }

    @OnClick(R.id.lin_tab_menu)
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.lin_tab_menu:
                popupWindowMenu = new PopupWindowMenu(context, rootView);
                popupWindowMenu.init();
                break;
        }
    }

}
