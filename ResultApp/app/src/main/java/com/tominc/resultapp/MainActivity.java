package com.tominc.resultapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    ViewPager pager;
    VIewPagerAdapter adapter;
    SlidingTabLayout tabs;
    String[] titles = {
            "Result", "Rank List"
    };
    int numTabs = 2;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new VIewPagerAdapter(getSupportFragmentManager(), titles, numTabs);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
//        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//            @Override
//            public int getIndicatorColor(int position) {
//                return getResources().getColor(R.color.tabsScrollColor, getApplicationContext().getTheme());
//            }
//        });

        tabs.setSelectedIndicatorColors(Color.WHITE);
        tabs.setViewPager(pager);

        fab = (FloatingActionButton) findViewById(R.id.search_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(in);
            }
        });

    }
}
