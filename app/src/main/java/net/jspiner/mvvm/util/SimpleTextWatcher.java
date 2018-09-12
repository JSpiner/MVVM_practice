package net.jspiner.mvvm.util;

import android.text.Editable;
import android.text.TextWatcher;

public interface SimpleTextWatcher extends TextWatcher {

    void textChanged(CharSequence charSequence);

    @Override
    default void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // no-op
    }

    @Override
    default void onTextChanged(CharSequence s, int start, int before, int count) {
        textChanged(s);
    }

    @Override
    default void afterTextChanged(Editable s) {
        // no-op
    }

}
