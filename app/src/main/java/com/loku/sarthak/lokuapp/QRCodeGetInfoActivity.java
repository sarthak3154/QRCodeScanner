package com.loku.sarthak.lokuapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SARTHAK on 1/28/2017.
 */

public class QRCodeGetInfoActivity extends AppCompatActivity implements QRScreenContract.View {

    @Bind(R.id.textID)
    TextView textViewID;
    @Bind(R.id.textName)
    TextView textViewName;
    @Bind(R.id.textMobile)
    TextView textViewMobile;
    @Bind(R.id.textEmail)
    TextView textViewEmail;
    @Bind(R.id.textRecognize)
    TextView textViewRecognize;
    @Bind(R.id.toolbarQRInfo)
    Toolbar toolbar;
    @Bind(R.id.buttonNo)
    AppCompatButton btnNo;
    @Bind(R.id.buttonTextYes)
    TextView textViewYes;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);
        init();
    }


    @Override
    public void init() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        intent = getIntent();
        if (intent != null) {
            String[] stringArray = intent.getStringArrayExtra("stringArray");
            textViewID.setText(stringArray[3]);
            textViewName.setText(stringArray[0]);
            textViewRecognize.setText("Do you recognize " + stringArray[0] + "?");
            textViewMobile.setText(stringArray[1]);
            textViewEmail.setText(stringArray[2]);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.buttonTextYes)
    public void onClickYes() {
        onBackPressed();
    }

    @OnClick(R.id.buttonNo)
    public void onClickNo() {
        onBackPressed();
    }
}
