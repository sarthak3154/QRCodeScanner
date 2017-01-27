package com.loku.sarthak.lokuapp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by SARTHAK on 1/27/2017.
 */

public class GeneratorScreenPresenter implements GeneratorScreenContract.Presenter {

    GeneratorScreenContract.View view;

    public GeneratorScreenPresenter(GeneratorScreenContract.View generatorView) {
        view = generatorView;
    }

    @Override
    public void setGeneratedImage() {
        view.hideKeyboard();
        if (view.getNameEditTextEmpty()) {
            Toast.makeText((Context) view, "Enter a valid Name!", Toast.LENGTH_SHORT).show();
        } else if (view.getMobileEditTextEmpty()) {
            Toast.makeText((Context) view, "Enter a valid Mobile No!", Toast.LENGTH_SHORT).show();
        } else if (view.getEmailEditTextEmpty()) {
            Toast.makeText((Context) view, "Enter a valid Email!", Toast.LENGTH_SHORT).show();
        } else {
            view.showGeneratedImage();
        }
    }
}
