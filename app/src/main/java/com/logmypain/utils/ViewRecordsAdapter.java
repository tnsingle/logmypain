package com.logmypain.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.logmypain.main.R;
import com.logmypain.main.record.HeadacheRecordFormActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class ViewRecordsAdapter
        extends ArrayAdapter<HeadacheRecord> {
    private Context context;
    private int layoutResourceId;
    private List<HeadacheRecord> recordList;
    private long toDelete;

    public ViewRecordsAdapter(Context paramContext, int paramInt, List<HeadacheRecord> paramList) {
        super(paramContext, paramInt, paramList);
        this.layoutResourceId = paramInt;
        this.context = paramContext;
        this.recordList = paramList;
    }

    public void deleteRecord(View paramView) {
        DatabaseHelper localDatabaseHelper = new DatabaseHelper(this.context);
        Integer localInteger = (Integer) paramView.getTag();
        this.toDelete = ((HeadacheRecord) this.recordList.get(localInteger.intValue())).getId();
        localDatabaseHelper.deleteRecord(this.toDelete);
        this.recordList.remove(localInteger.intValue());
        notifyDataSetChanged();
    }

    public String getNumHours(Calendar paramCalendar1, Calendar paramCalendar2) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("dd 'days' hh 'hours' mm 'minutes'", Locale.getDefault());
        long l = paramCalendar2.getTime().getTime() - paramCalendar1.getTime().getTime();
        GregorianCalendar localGregorianCalendar = new GregorianCalendar();
        localGregorianCalendar.setTimeInMillis(l);
        return localSimpleDateFormat.format(localGregorianCalendar.getTime());
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {

            View localView = paramView;
            final HeadacheRecord localHeadache = (HeadacheRecord) this.recordList.get(paramInt);
            if (localView == null) {
                localView = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(this.layoutResourceId, null);
            }
            localView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent localIntent = new Intent(ViewRecordsAdapter.this.context, HeadacheRecordFormActivity.class);
                    localIntent.putExtra("Record_ID", localHeadache.getId());
                    ViewRecordsAdapter.this.context.startActivity(localIntent);
                }
            });
            ImageView localImageView;
            LinearLayout localLinearLayout;
            if (localHeadache != null) {
                TextView localTextView1 = (TextView) localView.findViewById(R.id.viewRecordsDate);
                TextView localTextView2 = (TextView) localView.findViewById(R.id.viewRecordsMonth);
                localImageView = (ImageView) localView.findViewById(R.id.viewRecordsIntensity);
                localLinearLayout = (LinearLayout) localView.findViewById(R.id.viewRecordsHoursContainer);
                TextView localTextView3 = (TextView) localView.findViewById(R.id.viewRecordsHours);
                Calendar localCalendar = localHeadache.getStart();
                if ((localTextView1 != null) && (localCalendar != null)) {
                    localTextView1.setText(new SimpleDateFormat("dd", Locale.getDefault()).format(localCalendar.getTime()));
                }
                if ((localTextView2 != null) && (localCalendar != null)) {
                    localTextView2.setText(localCalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()));
                }
                if (localImageView != null) {
                    localImageView.setVisibility(View.INVISIBLE);
                    if (localHeadache.getIntensity() != -1) {
                        int i;
                        if (localHeadache.getIntensity() <= 3) {
                            i = this.context.getResources().getColor(R.color.intensity_mild);
                        }


                        if (localHeadache.getIntensity() <= 6) {
                            i = this.context.getResources().getColor(R.color.intensity_mod);
                        } else {
                            i = this.context.getResources().getColor(R.color.intensity_sev);
                        }
                        localImageView.setBackgroundColor(i);
                        localImageView.setVisibility(View.VISIBLE);
                    }

                }
                if (localTextView3 != null) {
                    new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                    if ((localHeadache.getEnd() == null) || (localHeadache.getStart() == null)) {
                        localLinearLayout.setVisibility(View.INVISIBLE);
                    }else {
                        localLinearLayout.setVisibility(View.VISIBLE);
                        localTextView3.setText(CalendarUtil.getShortDuration(localHeadache.getStart(), localHeadache.getEnd()));
                    }
                }
            }
            //for (;;) {
            ImageButton localImageButton = (ImageButton) localView.findViewById(R.id.delViewButton);
            localImageButton.setTag(Integer.valueOf(paramInt));
            localImageButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(final View paramAnonymousView) {
                    AlertDialog.Builder localBuilder = new AlertDialog.Builder(ViewRecordsAdapter.this.context);
                    localBuilder.setMessage("Are you sure you want to delete this record?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {
                            ViewRecordsAdapter.this.deleteRecord(paramAnonymousView);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {
                            paramAnonymous2DialogInterface.cancel();
                        }
                    });
                    localBuilder.create().show();
                }
            });
            return localView;


    }

}
