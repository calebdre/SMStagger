package com.caleblewis.SMStagger.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caleblewis.SMStagger.Models.TextMessage;
import com.caleblewis.SMStagger.R;

import java.util.ArrayList;
import java.util.List;


public class TextMessageListAdapter extends RecyclerView.Adapter<TextMessageListAdapter.TextMessageViewHolder> {

    private List<TextMessage> tms;

    public TextMessageListAdapter(List<TextMessage> tms) {
        this.tms = tms;
    }

    public TextMessageListAdapter() {this.tms = new ArrayList<TextMessage>();}

    public void addMessages(List<TextMessage> newMessages){
        this.tms.addAll(newMessages);
        this.notifyDataSetChanged();
    }

    public void clearMessages(){
        this.tms.clear();
        this.notifyDataSetChanged();
    }

    public TextMessage getItem(int pos){
        return this.tms.get(pos);
    }

    public void removeItem(long id){
        for (int i = 0; i < this.getItemCount(); i++) {
            if(this.tms.get(i).getId() == id) tms.remove(i);
        }

        this.notifyDataSetChanged();
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
        holder.date.setText(tm.getDate());
    }

    @Override
    public int getItemCount() {
        return tms.size();
    }

    public static class TextMessageViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;
        protected TextView phone;
        protected TextView message;
        protected TextView date;

        public TextMessageViewHolder(View v) {
            super(v);

            this.name = (TextView) v.findViewById(R.id.tmName);
            this.phone = (TextView) v.findViewById(R.id.tmPhone);
            this.message = (TextView) v.findViewById(R.id.tmMessage);
            this.date = (TextView) v.findViewById(R.id.tmDate);
        }
    }


}
