package com.loku.sarthak.lokuapp;

/**
 * Created by SARTHAK on 1/26/2017.
 */

public interface MainScreenContract {

    interface View {
        void init();

        void showGenerator();

        void showReader();
    }

    interface Presenter {

        void onClickGenerateCode();

        void onClickReadCode();
    }
}
