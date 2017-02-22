package com.rmn.ews.services;

import android.annotation.TargetApi;
import android.app.Service;
import android.app.job.JobParameters;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.rmn.ews.data.DBContract;
import com.rmn.ews.data.DBContract.OfflineData;
import com.rmn.ews.model.Article;
import com.rmn.ews.model.Data;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobService extends android.app.job.JobService {

    public static final String TAG = JobService.class.getSimpleName();

    private UpdateAppsAsyncTask asyncTask = new UpdateAppsAsyncTask();

    public JobService() {
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.e(TAG, "onStart Job " + jobParameters.getJobId());
        asyncTask.execute(jobParameters);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.e(TAG, "O stop job " + jobParameters.getJobId());
        boolean shouldRescheduled = asyncTask.stopJob(jobParameters);
        return false;
    }

    private class UpdateAppsAsyncTask extends AsyncTask<JobParameters, Void, JobParameters[]> {

        @Override
        protected JobParameters[] doInBackground(JobParameters... jobParameterses) {

            Log.e("doInBackground ", "called");

            getApplicationContext().getContentResolver().delete(OfflineData.CONTENT_URI,null,null);

            final Vector<ContentValues> cvVector = new Vector<ContentValues>();
            NewsService service = ServiceInstance.createRetrofitService(NewsService.class, NewsService.SERVICE_ENDPOINT);
            Cursor cursor = getApplicationContext().getContentResolver().query(DBContract.SourceTable.CONTENT_URI, new String[]{DBContract.SourceTable.COLUMN_SRC_URL_PRAM}, null, null, null);
            if (cursor != null && cursor.getCount() != 0) {
                Log.e("Cursor count ", cursor.getCount() + "  COUNT");
                while (cursor.moveToNext()) {
                    String src = cursor.getString(0);
                    Log.e("SOURCE  ", src);
                    if (src != "")
                        service.getUser(src, "top")
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<Data>() {
                                    @Override
                                    public void onCompleted() {
                                        Log.e("onCompleted", "called");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("onError", "called" + e.getMessage());
                                    }

                                    @Override
                                    public void onNext(Data data) {
//                                        mAdapter.setData(data.getArticles());
                                        for (Article article : data.getArticles()) {
                                            Log.e("OnNext ", "called");
                                            ContentValues contentValues = new ContentValues();

                                            contentValues.put(OfflineData.COLUMN_TITLE, article.getTitle());
                                            contentValues.put(OfflineData.COLUMN_DESCRIPTION, article.getDescription());
                                            contentValues.put(OfflineData.COLUMN_SOURCE, data.getSource());

                                            contentValues.put(OfflineData.COLUMN_IMAGE_URL,article.getUrlToImage());

                                            try {
                                                Bitmap bitmap = downloadUrl(article.getUrlToImage());
                                                if (bitmap!=null) {
                                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                    //Log.e("Byte Array ", stream.toString());
                                                    Log.e("jhgjh","jhkjkjg");
                                                    contentValues.put(OfflineData.COLUMN_IMAGE, stream.toByteArray());
                                                }else {
                                                    Log.e("jhgjh","jhkj  kjg");
                                                }
                                                cvVector.add(contentValues);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        Log.e("onCompleted", "called");
                                        Log.e("onCompleted", "Article ---------------------------------------------  size" + data.getArticles().size());
                                        Log.e("DATA ", data.getArticles().get(0).getTitle());
                                        ContentValues contentValues[] = new ContentValues[cvVector.size()];
                                        cvVector.toArray(contentValues);
                                        int i = getContentResolver().bulkInsert(OfflineData.CONTENT_URI, contentValues);
                                        Log.e("iiiiiiiiiiii", i + "");
                                    }
                                });
                }

            }

            return new JobParameters[0];
        }

        @Override
        protected void onPostExecute(JobParameters[] jobParameterses) {
            super.onPostExecute(jobParameterses);
            for (JobParameters param : jobParameterses) {
                if (!hasJobBeenStoped(param)) {
                    Log.e(TAG, "Finishing job with id =" + param.getJobId());
                    jobFinished(param, false);
                }
            }
        }

        private boolean hasJobBeenStoped(JobParameters param) {
            return false;
        }

        public boolean stopJob(JobParameters param) {
            Log.d(TAG, "stop job id " + param.getJobId());
            return false;
        }
    }

    private Bitmap downloadUrl(String strUrl) throws IOException {
        Bitmap bitmap = null;
        InputStream iStream = null;
        try {
            URL url = new URL(strUrl);
            /** Creating an http connection to communcate with url */
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            /** Connecting to url */
            urlConnection.connect();

            /** Reading data from url */
            iStream = urlConnection.getInputStream();

            /** Creating a bitmap from the stream returned from the url */
            bitmap = BitmapFactory.decodeStream(iStream);
            iStream.close();
        } catch (Exception e) {
            Log.d("Exception whdownloadinl", e.toString());
        }
        return bitmap;
    }

}
