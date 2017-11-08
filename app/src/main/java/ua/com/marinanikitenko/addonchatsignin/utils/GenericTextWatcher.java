package ua.com.marinanikitenko.addonchatsignin.utils;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


/**
 * Created by Marina on 12.07.2017.
 * Helper r for watch when user enter text, watches if error enabled and turns it off
 */

public class GenericTextWatcher implements TextWatcher {
    private String TAG = GenericTextWatcher.class.getSimpleName() + ":";
    private TextInputLayout mView;
    private EditText mText;

    public GenericTextWatcher(EditText email, TextInputLayout errorView) {
        this.mView = errorView;
        this.mText = email;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {
        if(mView.isErrorEnabled()) {
            mView.setErrorEnabled(false);
        }
    }
}


