package com.study.emoticons.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.emoticons.R;

public class LoginAdapter extends RecyclerView.Adapter<LoginAdapter.ViewHolder> {
    private Context context;
    private Boolean status;
    private LayoutInflater inflater;

    public LoginAdapter(Context context,Boolean status){
        this.context = context;
        this.status = status;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (status){
                view = inflater.inflate(R.layout.fragment_mine_login,viewGroup,false);
        } else {
                view = inflater.inflate(R.layout.fragment_mine_unlogin,viewGroup,false);
            }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);

        }
    }
}
