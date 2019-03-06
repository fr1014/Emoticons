package com.study.emoticons.listener;

import android.os.Bundle;

public interface ActivityLifeCycleListener {
    void onCreate(Bundle saveInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onRestart();
}
