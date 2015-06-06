package com.caleblewis.textstagger;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String snackBarText = getIntent().getStringExtra("snackbarMessage");
        if(snackBarText != null){
            new SnackBarAlert(this).show(snackBarText);
        }

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.text_message_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        MessagesDB db = new MessagesDB(this);
//        db.onUpgrade(db.getWritableDatabase(), 1, 2); // reset the db

        List<TextMessage> messages = db.getMessages();

        CardView card = (CardView) findViewById(R.id.no_messages);

        if(messages.size() != 0){
            card.setVisibility(View.GONE);
            mRecyclerView.setAdapter(new TextMessageListAdapter(messages));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startCreateNewTextMessageActivity(View view) {
        Intent newTextMessageIntent = new Intent(this, CreateTextMessageActivity.class);
        this.startActivity(newTextMessageIntent);
    }
}
