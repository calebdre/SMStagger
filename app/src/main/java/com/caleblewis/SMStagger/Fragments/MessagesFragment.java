package com.caleblewis.SMStagger.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caleblewis.SMStagger.Adapters.TextMessageListAdapter;
import com.caleblewis.SMStagger.MessagesDB;
import com.caleblewis.SMStagger.Models.TextMessage;
import com.caleblewis.SMStagger.R;

import java.util.List;


public class MessagesFragment extends Fragment {

    TextMessageListAdapter textMessageListAdapter = new TextMessageListAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.viewpager_messages_tab,container,false);

        Bundle bundle = getArguments();
        String messageType = getArguments().getString("type");

        MessagesDB db = new MessagesDB(getActivity());
        List<TextMessage> messages;

        if(messageType.equals("scheduled")){
            messages = db.getScheduledMessages();
        }else{
            messages = db.getSentMessages();
        }

        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.message_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(textMessageListAdapter);

        CardView card = (CardView) v.findViewById(R.id.no_messages);

        TextView t = (TextView) v.findViewById(R.id.no_messages_text);
        t.setText("No messages have been sent yet. Check back later!");

        if(messages.size() != 0){
            card.setVisibility(View.GONE);
            textMessageListAdapter.clearMessages();
            textMessageListAdapter.addMessages(messages);
        } else{
            textMessageListAdapter.clearMessages();
            card.setVisibility(View.VISIBLE);
        }

        return v;
    }
}
