package com.loku.sarthak.lokuapp;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SARTHAK on 1/26/2017.
 */
public class ReaderActivity extends AppCompatActivity implements ReaderScreenContract.View {

    @Bind(R.id.qrCodeView)
    CompoundBarcodeView barcodeView;
    private ReaderScreenContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        presenter = new ReaderScreenPresenter(this);
        barcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                Log.i("Result", result.getResult().getText());
                String[] splitString = result.getText().split("_");
                if (splitString.length == 3) {
                    Toast.makeText(getBaseContext(), "Welcome " + splitString[0] + "!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "QR Code Scan Success!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }
}
