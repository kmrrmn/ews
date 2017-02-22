package com.rmn.ews.services;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }
      Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        mContext=context;
Log.e("onReceive ","CALLED");
        ScheduledJob();
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void ScheduledJob() {
        ComponentName serviceName = new ComponentName(mContext, JobService.class);
        Log.e("ScheduledJob", "called");
        JobInfo jobInfo = new JobInfo.Builder(10, serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresDeviceIdle(true)
                .setRequiresCharging(false)
                .setOverrideDeadline(3600)
                .build();

        JobScheduler scheduler = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS) {
            Toast.makeText(mContext, "Job scheduled successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
