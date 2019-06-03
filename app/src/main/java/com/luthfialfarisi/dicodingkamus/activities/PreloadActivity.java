package com.luthfialfarisi.dicodingkamus.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.luthfialfarisi.dicodingkamus.R;
import com.luthfialfarisi.dicodingkamus.models.KamusItems;
import com.luthfialfarisi.dicodingkamus.preferences.AppPreference;
import com.luthfialfarisi.dicodingkamus.utils.KamusHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PreloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preload);
        
        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {

        final String TAG = LoadData.class.getSimpleName();

        KamusHelper kamusHelper;
        AppPreference appPreference;

        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper(PreloadActivity.this);
            appPreference = new AppPreference(PreloadActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {

            Boolean firstRun = appPreference.getFirstRun();
            if (firstRun) {
                ArrayList<KamusItems> KamusItemsENID = preLoadRaw("EN");
                ArrayList<KamusItems> KamusItemsIDEN = preLoadRaw("ID");

                kamusHelper.open();

                Double progressMaxInsert = 100.0;
                int total_size = KamusItemsENID.size() + KamusItemsIDEN.size();
                Double progressDiff = (progressMaxInsert - progress) / total_size;

                kamusHelper.beginTransaction();
                try {
                    for (KamusItems model : KamusItemsENID) {
                        kamusHelper.insertTransaction(model, "EN_ID");
                    }
                    kamusHelper.setTransactionSuccess();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception");
                }
                kamusHelper.endTransaction();
                progress += progressDiff;
                publishProgress((int) progress);

                kamusHelper.beginTransaction();
                try {
                    for (KamusItems model : KamusItemsIDEN) {
                        kamusHelper.insertTransaction(model, "ID_EN");
                    }
                    kamusHelper.setTransactionSuccess();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception");
                }
                kamusHelper.endTransaction();
                progress += progressDiff;
                publishProgress((int) progress);

                kamusHelper.close();

                appPreference.setFirstRun(false);
                publishProgress((int) maxprogress);

            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);

                        this.wait(2000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(PreloadActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<KamusItems> preLoadRaw(String language) {
        int raw_data;
        if(language == "EN"){
            raw_data = R.raw.english_indonesia;
        }else {
            raw_data = R.raw.indonesia_english;
        }
        ArrayList<KamusItems> listKamus = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw = res.openRawResource(raw_data);

            reader = new BufferedReader(new InputStreamReader(raw));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                KamusItems kamusItem = new KamusItems(splitstr[0], splitstr[1]);
                listKamus.add(kamusItem);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listKamus;
    }
}
