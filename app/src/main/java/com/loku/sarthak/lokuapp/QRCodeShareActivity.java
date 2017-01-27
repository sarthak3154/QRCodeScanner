package com.loku.sarthak.lokuapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SARTHAK on 1/27/2017.
 */

public class QRCodeShareActivity extends AppCompatActivity implements QRScreenContract.View {

    @Bind(R.id.toolbarQRCode)
    Toolbar toolbar;
    @Bind(R.id.imageQR)
    ImageView imageViewQR;
    @Bind(R.id.textHiName)
    TextView textViewName;
    private QRScreenContract.Presenter presenter;
    Intent intent;
    Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrshare);
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        presenter = new QRScreenPresenter(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        intent = getIntent();
        if (intent != null) {
            String nameUser = intent.getStringExtra("stringName");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Hi, ");
            stringBuilder.append(nameUser);
            stringBuilder.append("!");
            textViewName.setText(stringBuilder.toString());
            imageViewQR.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onGlobalLayout() {
                    int width = imageViewQR.getMeasuredWidth();
                    String stringList = intent.getStringExtra("stringList");
                    imageViewQR.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(stringList, BarcodeFormat.QR_CODE, width, width);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        imageViewQR.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_share:
                if (isStoragePermissionGranted()) {
                    shareImageIntent();
                } else {

                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareImageIntent() {
        String pathofBmp = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
        Uri bmpUri = Uri.parse(pathofBmp);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, bmpUri);
        share.putExtra(Intent.EXTRA_TEXT, "Just let out! Scan the QRCode to know more");
        startActivity(Intent.createChooser(share, "Share QRCode to.."));
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                //Permission is revoked
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            //Permission is automatically granted on SDK<23 upon installation
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_qr_share, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //resume tasks needing this permission
            shareImageIntent();
        } else {
            Toast.makeText(getBaseContext(), "Allow Storage Permission to share QR Code from App Settings.", Toast.LENGTH_SHORT).show();
        }
    }
}
