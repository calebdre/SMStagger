package com.caleblewis.textstagger;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        String snackBarText = getIntent().getStringExtra("snackbarMessage");
        if(snackBarText != null){
            new SnackBarAlert(this).show(snackBarText);
        }

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.text_message_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        MessagesDB db = new MessagesDB(this);
        db.onUpgrade(db.getWritableDatabase(), 1, 2); // reset the db

        List<TextMessage> messages = db.getScheduledMessages();

        CardView card = (CardView) findViewById(R.id.no_messages);

        if(messages.size() != 0){
            card.setVisibility(View.GONE);
            mRecyclerView.setAdapter(new TextMessageListAdapter(messages));
        }

    }

    public void startCreateNewTextMessageActivity(View view) {
        Intent newTextMessageIntent = new Intent(this, CreateTextMessageActivity.class);
        this.startActivity(newTextMessageIntent);
    }
}
