package com.logmypain.tasks.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.logmypain.R;
import com.logmypain.main.record.HeadacheRecordFormActivity;
import com.logmypain.utils.CalendarUtil;
import com.logmypain.utils.DatabaseHelper;
import com.logmypain.utils.Models.HeadacheRecord;

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
            final HeadacheRecord record = (HeadacheRecord) this.recordList.get(paramInt);
            if (localView == null) {
                localView = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(this.layoutResourceId, null);
            }
            localView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent localIntent = new Intent(ViewRecordsAdapter.this.context, HeadacheRecordFormActivity.class);
                    localIntent.putExtra("Record_ID", record.getId());
                    ViewRecordsAdapter.this.context.startActivity(localIntent);
                }
            });

            
            if (record != null) {
                TextView dateTextView = (TextView) localView.findViewById(R.id.viewRecordsDate);
                TextView monthTextView = (TextView) localView.findViewById(R.id.viewRecordsMonth);
                ImageView intensityImageView = (ImageView) localView.findViewById(R.id.viewRecordsIntensity);
                LinearLayout hoursContainerLinearLayout = (LinearLayout) localView.findViewById(R.id.viewRecordsHoursContainer);
                TextView hoursTextView = (TextView) localView.findViewById(R.id.viewRecordsHours);
                TextView notesTextView = (TextView) localView.findViewById(R.id.viewRecordsNotes);
                Calendar localCalendar = record.getStart();
                if ((dateTextView != null) && (localCalendar != null)) {
                    dateTextView.setText(new SimpleDateFormat("dd", Locale.getDefault()).format(localCalendar.getTime()));
                }
                if ((monthTextView != null) && (localCalendar != null)) {
                    monthTextView.setText(localCalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()));
                }
                if (intensityImageView != null) {
                    intensityImageView.setVisibility(View.INVISIBLE);
                    if (record.getIntensity() != -1) {
                        int i;
                        if (record.getIntensity() <= 3) {
                            i = this.context.getResources().getColor(R.color.intensity_mild);
                        }else if (record.getIntensity() <= 6) {
                            i = this.context.getResources().getColor(R.color.intensity_mod);
                        } else {
                            i = this.context.getResources().getColor(R.color.intensity_sev);
                        }
                        intensityImageView.setBackgroundColor(i);
                        intensityImageView.setVisibility(View.VISIBLE);
                    }

                }
                if (hoursTextView != null) {
                    new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                    if ((record.getEnd() == null) || (record.getStart() == null)) {
                        hoursContainerLinearLayout.setVisibility(View.INVISIBLE);
                    }else {
                        hoursContainerLinearLayout.setVisibility(View.VISIBLE);
                        hoursTextView.setText(CalendarUtil.getShortDuration(record.getStart(), record.getEnd()));
                    }
                }

                if(notesTextView != null){
                    if(!TextUtils.isEmpty((record.getNotes()))) {
                        notesTextView.setText(record.getNotes());
                        notesTextView.setVisibility(View.VISIBLE);
                    }else{
                       notesTextView.setVisibility(View.GONE);

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
