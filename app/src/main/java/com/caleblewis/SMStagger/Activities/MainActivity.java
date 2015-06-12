package com.caleblewis.SMStagger.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.caleblewis.SMStagger.Adapters.MessagesViewPagerAdapter;
import com.caleblewis.SMStagger.Libs.SlidingLayoutTab;
import com.caleblewis.SMStagger.R;
import com.caleblewis.SMStagger.Utils.SnackBarAlert;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        String snackBarText = getIntent().getStringExtra("snackbarMessage");
        if(snackBarText != null){
            new SnackBarAlert(this).show(snackBarText);
        }

        set_adapters();
    }

    private void set_adapters() {
        MessagesViewPagerAdapter adapter =  new MessagesViewPagerAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        SlidingLayoutTab tabs = (SlidingLayoutTab) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingLayoutTab.TabColorizer() {
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.background);
            }
        });

        tabs.setCustomTabView(R.layout.viewpager_tab_view, R.id.viewpager_text);

        tabs.setViewPager(pager);
    }

    public void startCreateNewTextMessageActivity(View view) {
        Intent newTextMessageIntent = new Intent(this, CreateTextMessageActivity.class);
        this.startActivity(newTextMessageIntent);
    }
}