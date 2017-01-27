package com.loku.sarthak.lokuapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.os.Build.VERSION.SDK;

/**
 * Created by SARTHAK on 1/26/2017.
 */
public class ReaderActivity extends AppCompatActivity implements ReaderScreenContract.View {

    @Bind(R.id.qrCodeView)
    CompoundBarcodeView barcodeView;
    @Bind(R.id.toolbarReader)
    Toolbar toolbar;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ReaderActivity.this,
                        Manifest.permission.CAMERA)) {

                    ActivityCompat.requestPermissions(ReaderActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(ReaderActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
            } else {

                startQRCodeScanning();
                barcodeView.resume();
            }
        } else {

            startQRCodeScanning();
            barcodeView.resume();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    private void startQRCodeScanning() {
        barcodeView.setVisibility(View.VISIBLE);

        barcodeView.decodeSingle(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                Log.i("Result", result.getResult().getText());
                String[] splitString = result.getText().split("_");
                if (splitString.length == 4) {
//                    Toast.makeText(getBaseContext(), "Welcome " + splitString[0] + "!", Toast.LENGTH_SHORT).show();
                    Intent detailsIntent = new Intent(getBaseContext(), QRCodeGetInfoActivity.class);
                    detailsIntent.putExtra("stringArray", splitString);
                    startActivity(detailsIntent);
//                    finish();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startQRCodeScanning();
                    barcodeView.resume();
                } else {
                    Toast.makeText(getBaseContext(), "Camera Permission Required! Kindly change from App Settings", Toast.LENGTH_SHORT).show();
                }
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
}
