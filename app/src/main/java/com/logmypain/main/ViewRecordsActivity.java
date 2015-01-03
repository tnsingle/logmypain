package com.logmypain.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;

import com.logmypain.tasks.ViewCalendarFragment;
import com.logmypain.tasks.ViewListFragment;
import com.logmypain.tasks.ViewRecordsTabListener;

public class ViewRecordsActivity
        extends ActionBarActivity
{
    ActionBar.Tab calendarTab;
    ActionBar.Tab listTab;
    Fragment viewCalendarFragment = new ViewCalendarFragment();
    Fragment viewListFragment = new ViewListFragment();

    public void onBackPressed()
    {
        Intent localIntent = new Intent(this, MainActivity.class);
        startActivity(localIntent);
        localIntent.setFlags(67108864);
        finish();
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_view_records_tabs);
        ActionBar localActionBar = getSupportActionBar();
        localActionBar.setNavigationMode(2);
        this.listTab = localActionBar.newTab().setText("List");
        this.calendarTab = localActionBar.newTab().setText("Calendar");
        //this.listTab.setIcon(2130837600);
        //this.calendarTab.setIcon(2130837599);
        this.listTab.setTabListener(new ViewRecordsTabListener(this.viewListFragment));
        this.calendarTab.setTabListener(new ViewRecordsTabListener(this.viewCalendarFragment));
        localActionBar.addTab(this.listTab);
        localActionBar.addTab(this.calendarTab);
        localActionBar.setSelectedNavigationItem(getIntent().getIntExtra("ActiveTab", 0));
    }

    /*public boolean onCreateOptionsMenu(Menu paramMenu)
    {
        //getMenuInflater().inflate(2131558404, paramMenu);
        //return true;
    }*/

    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {
        paramMenuItem.getItemId();
        return super.onOptionsItemSelected(paramMenuItem);
    }

    public void viewNextMonth(View paramView)
    {
        ((ViewCalendarFragment)this.viewCalendarFragment).viewNextMonth(paramView);
    }

    public void viewPrevMonth(View paramView)
    {
        ((ViewCalendarFragment)this.viewCalendarFragment).viewPrevMonth(paramView);
    }
}
