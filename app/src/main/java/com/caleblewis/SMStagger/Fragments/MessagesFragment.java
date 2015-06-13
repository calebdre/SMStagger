package com.caleblewis.SMStagger.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.caleblewis.SMStagger.Adapters.TextMessageListAdapter;
import com.caleblewis.SMStagger.Libs.RecyclerItemClickListener;
import com.caleblewis.SMStagger.MessagesDB;
import com.caleblewis.SMStagger.Models.TextMessage;
import com.caleblewis.SMStagger.R;
import com.caleblewis.SMStagger.SMS.SMSScheduler;
import com.caleblewis.SMStagger.Utils.SnackBarAlert;
import com.rey.material.widget.SnackBar;

import java.util.List;


public class MessagesFragment extends Fragment {

    TextMessageListAdapter textMessageListAdapter = new TextMessageListAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v =inflater.inflate(R.layout.viewpager_messages_tab,container,false);

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

        TextView t = (TextView) v.findViewById(R.id.no_messages_text);

        if(messageType.equals("scheduled")) {
            t.setText(getString(R.string.no_scheduled_texts_text_view));
            mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    TextMessage message = textMessageListAdapter.getItem(position);
                    showCancelMessageDialog(message, v, getActivity().getString(R.string.cancel_dialog_message),"No, leave it", "Yes, cancel it");
                }
            }));
        }

        if(messageType.equals("sent")){
            t.setText(getString(R.string.no_sent_texts_text_view));
            mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    TextMessage message = textMessageListAdapter.getItem(position);
                    showCancelMessageDialog(message, v, "Delete saved message?", "Keep it", "Yes, remove it");
                }
            }));
        }

        CardView card = (CardView) v.findViewById(R.id.no_messages);



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

    private void showCancelMessageDialog(final TextMessage message, final View v,String titleMessage, String cancelMessage, String positiveMessage) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Cancel Scheduled Message")
                .setMessage(titleMessage)
                .setCancelable(true)
                .setPositiveButton(positiveMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new SMSScheduler().cancel(getActivity(), message);
                        textMessageListAdapter.removeItem(message.getId());

                        new MessagesDB(getActivity()).deleteMessage(message);
                        new SnackBarAlert(getActivity()).show("The message was cancelled.");

                        if (textMessageListAdapter.getItemCount() == 0)
                            v.findViewById(R.id.no_messages).setVisibility(View.VISIBLE);
                    }
                })
                .setNegativeButton(cancelMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }
}
