package com.loku.sarthak.lokuapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.detector.MathUtils;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SARTHAK on 1/26/2017.
 */
public class GeneratorActivity extends AppCompatActivity implements GeneratorScreenContract.View {

    @Bind(R.id.buttonGenerateQR)
    AppCompatButton btnGenerate;
    @Bind(R.id.editTextName)
    EditText editTextName;
    @Bind(R.id.editTextMobile)
    EditText editTextMobile;
    @Bind(R.id.editTextEmail)
    EditText editTextEmail;
    @Bind(R.id.toolbarGenerator)
    Toolbar toolbar;
    private GeneratorScreenContract.Presenter generatorPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);
        ButterKnife.bind(this);
        generatorPresenter = new GeneratorScreenPresenter(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void showGeneratedImage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(editTextName.getText());
        stringBuilder.append("_");
        stringBuilder.append(editTextMobile.getText());
        stringBuilder.append("_");
        stringBuilder.append(editTextEmail.getText());
        Random random = new Random();
        Integer randomNo = random.nextInt(1000 + 1);
        stringBuilder.append("_");
        stringBuilder.append(randomNo.toString());
        Intent intent = new Intent(this, QRCodeShareActivity.class);
        intent.putExtra("stringList", stringBuilder.toString());
        intent.putExtra("stringName", editTextName.getText().toString());
        startActivity(intent);

    }

    @Override
    public void hideKeyboard() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (getCurrentFocus() != null) {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }

    @Override
    public boolean getNameEditTextEmpty() {
        return (editTextName.getText().toString().isEmpty());
    }

    @Override
    public boolean getEmailEditTextEmpty() {
        return (editTextEmail.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString()).matches());
    }

    @Override
    public boolean getMobileEditTextEmpty() {
        return (editTextMobile.getText().toString().isEmpty() || editTextMobile.getText().toString().length() != 10);
    }


    @OnClick(R.id.buttonGenerateQR)
    public void onGenerateQRButton() {
        generatorPresenter.setGeneratedImage();
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
}
