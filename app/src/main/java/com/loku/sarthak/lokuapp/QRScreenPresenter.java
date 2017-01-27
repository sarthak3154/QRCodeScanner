package com.loku.sarthak.lokuapp;

/**
 * Created by SARTHAK on 1/27/2017.
 */

public class QRScreenPresenter implements QRScreenContract.Presenter {

    QRScreenContract.View view;

    public QRScreenPresenter(QRScreenContract.View view) {
        this.view = view;
    }
}
