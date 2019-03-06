package com.study.emoticons.listener;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public interface FragementLifeCycleListener {
    void onAttach(Context context);

    void onCreate(Bundle saveInstanceState);

    void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState);

    void onActivityCreated(Bundle saveInstanceState);

    void Resume();

    void onPause();

    void onStart();

    void onResume();

    void onStop();

    void onRestart();

    void onDestroyView();

    void onDestroy();

    void onDetach();
}
