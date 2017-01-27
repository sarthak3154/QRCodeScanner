package com.loku.sarthak.lokuapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainScreenContract.View {

    @Bind(R.id.buttonGenerate)
    AppCompatButton btnGenerate;
    @Bind(R.id.buttonRead)
    AppCompatButton btnRead;
    private MainScreenContract.Presenter mainScreenPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void init() {
        mainScreenPresenter = new MainPresenter(this);
        ButterKnife.bind(this);
    }

    @Override
    public void showGenerator() {
        Intent intent = new Intent(this, GeneratorActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonGenerate)
    public void generateQRCode() {
        mainScreenPresenter.onClickGenerateCode();
    }

    @OnClick(R.id.buttonRead)
    public void readQRCode() {
        mainScreenPresenter.onClickReadCode();

    }

    @Override
    public void showReader() {
        Intent intent = new Intent(this, ReaderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
