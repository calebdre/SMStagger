package com.caleblewis.textstagger;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;


public class TextMessageListAdapter extends RecyclerView.Adapter<TextMessageListAdapter.TextMessageViewHolder> {

    private List<TextMessage> tms;

    public TextMessageListAdapter(List<TextMessage> tms) {
        this.tms = tms;
    }

    @Override
    public TextMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_message_view, parent, false);
        return new TextMessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TextMessageViewHolder holder, int i) {
        TextMessage tm = tms.get(i);

        holder.name.setText(tm.getName());
        holder.phone.setText(tm.getPhone());
        holder.message.setText(tm.getMessage());
    }

    @Override
    public int getItemCount() {
        return tms.size();
    }

    public static class TextMessageViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;
        protected TextView phone;
        protected TextView message;

        public TextMessageViewHolder(View v) {
            super(v);

            this.name = (TextView) v.findViewById(R.id.tmName);
            this.phone = (TextView) v.findViewById(R.id.tmPhone);
            this.message = (TextView) v.findViewById(R.id.tmMessage);
        }
    }


}