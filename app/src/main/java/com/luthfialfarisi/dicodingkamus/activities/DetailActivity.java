package com.luthfialfarisi.dicodingkamus.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.luthfialfarisi.dicodingkamus.R;

public class DetailActivity extends AppCompatActivity {

    public static String EXTRA_WORD = "EXTRA_WORD";
    public static String EXTRA_DESC = "EXTRA_DESC";

    TextView tvWord, tvDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvWord = findViewById(R.id.tvWordDetail);
        tvDesc = findViewById(R.id.tvDescDetail);

        String word = getIntent().getStringExtra(EXTRA_WORD);
        String desc = getIntent().getStringExtra(EXTRA_DESC);

        tvWord.setText(word);
        tvDesc.setText(desc);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
