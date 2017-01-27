package com.loku.sarthak.lokuapp;

import android.content.Intent;

/**
 * Created by SARTHAK on 1/26/2017.
 */

public class MainPresenter implements MainScreenContract.Presenter {

    MainScreenContract.View view;

    public MainPresenter(MainScreenContract.View mainScreenView) {
        view = mainScreenView;
    }

    @Override
    public void onClickGenerateCode() {
        view.showGenerator();
    }

    @Override
    public void onClickReadCode() {
        view.showReader();
    }
}
