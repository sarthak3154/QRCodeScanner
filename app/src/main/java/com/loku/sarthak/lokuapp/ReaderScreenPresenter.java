package com.loku.sarthak.lokuapp;

import android.support.annotation.RequiresPermission;

/**
 * Created by SARTHAK on 1/27/2017.
 */

public class ReaderScreenPresenter implements ReaderScreenContract.Presenter {

    ReaderScreenContract.View view;

    public ReaderScreenPresenter(ReaderScreenContract.View view) {
        this.view = view;
    }
}
