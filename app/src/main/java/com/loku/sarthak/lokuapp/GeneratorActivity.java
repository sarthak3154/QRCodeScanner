package com.loku.sarthak.lokuapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.SpannableString;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

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
    @Bind(R.id.imageQR)
    ImageView imageViewQR;
    private GeneratorScreenContract.Presenter generatorPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);
        ButterKnife.bind(this);
        generatorPresenter = new GeneratorScreenPresenter(this);
    }

    @Override
    public void showGeneratedImage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(editTextName.getText());
        stringBuilder.append("_");
        stringBuilder.append(editTextMobile.getText());
        stringBuilder.append("_");
        stringBuilder.append(editTextEmail.getText());
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(stringBuilder.toString(), BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageViewQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
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
}
