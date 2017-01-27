package com.loku.sarthak.lokuapp;

import android.graphics.Bitmap;

/**
 * Created by SARTHAK on 1/27/2017.
 */

public interface GeneratorScreenContract {

    interface View {
        void showGeneratedImage();

        void hideKeyboard();

        boolean getNameEditTextEmpty();

        boolean getEmailEditTextEmpty();

        boolean getMobileEditTextEmpty();
    }

    interface Presenter {
        void setGeneratedImage();
    }
}
