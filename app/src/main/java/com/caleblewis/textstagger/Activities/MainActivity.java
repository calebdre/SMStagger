package com.caleblewis.textstagger.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caleblewis.textstagger.MessagesDB;
import com.caleblewis.textstagger.R;
import com.caleblewis.textstagger.Utils.SnackBarAlert;
import com.caleblewis.textstagger.Models.TextMessage;
import com.caleblewis.textstagger.Adapters.TextMessageListAdapter;

import java.util.List;


public class MainActivity extends Activity {

    private TextMessageListAdapter textMessageListAdapter;
    private LinearLayout scheduleMenuItem;
    private LinearLayout sentMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String snackBarText = getIntent().getStringExtra("snackbarMessage");
        if(snackBarText != null){
            new SnackBarAlert(this).show(snackBarText);
        }

        set_fields();
        set_event_listeners();

        textMessageListAdapter = new TextMessageListAdapter();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.message_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(textMessageListAdapter);

        showScheduledMessages();
    }

    private void set_event_listeners() {
        scheduleMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showScheduledMessages();
            }
        });

        sentMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSentMessages();
            }
        });
    }

    private void set_fields() {
        scheduleMenuItem = (LinearLayout) findViewById(R.id.scheduled_menu_item);
        sentMenuItem = (LinearLayout) findViewById(R.id.sent_menu_item);
    }

    public void startCreateNewTextMessageActivity(View view) {
        Intent newTextMessageIntent = new Intent(this, CreateTextMessageActivity.class);
        this.startActivity(newTextMessageIntent);
    }

    public void showScheduledMessages() {
        showMessages(getScheduledMessage());

        TextView r = (TextView) findViewById(R.id.menu_title);
        r.setText("Scheduled Messages");

        TextView t = (TextView) findViewById(R.id.no_messages_text);
        t.setText("You haven't schedule any texts yet. Click the button below to start!");
    }

    public void showSentMessages(){
        showMessages(getSentMessage());

        TextView r = (TextView) findViewById(R.id.menu_title);
        r.setText("Sent Messages");

        TextView t = (TextView) findViewById(R.id.no_messages_text);
        t.setText("No messages have been sent yet. Check back later!");
    }

    public void showMessages(List<TextMessage> messages){
        CardView card = (CardView) findViewById(R.id.no_messages);

        if(messages.size() != 0){
            card.setVisibility(View.GONE);
            textMessageListAdapter.clearMessages();
            textMessageListAdapter.addMessages(messages);
        } else{
            textMessageListAdapter.clearMessages();
            card.setVisibility(View.VISIBLE);
        }
    }

    public List<TextMessage> getSentMessage(){
        MessagesDB db = new MessagesDB(this);
        return db.getSentMessages();
    }

    public List<TextMessage> getScheduledMessage(){
        MessagesDB db = new MessagesDB(this);
        return db.getScheduledMessages();
    }
}