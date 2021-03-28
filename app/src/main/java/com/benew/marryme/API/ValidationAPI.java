package com.benew.marryme.API;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ValidationAPI {

    public static boolean validateNumber(TextInputLayout inputLayout, String noNumber, String falseNumber) {
        String number = Objects.requireNonNull(inputLayout.getEditText()).getText().toString().trim();

        if (number.isEmpty()) {
            inputLayout.setError(noNumber);
            inputLayout.requestFocus();
            return false;
        } else if (number.length() != 8) {
            inputLayout.setError(falseNumber);
            inputLayout.requestFocus();
            return false;
        } else {
            inputLayout.setError(null);
            return true;
        }
    }

    public static boolean validatePassword(TextInputLayout inputLayout, String absencePass, String moinsQue5) {
        String password = Objects.requireNonNull(inputLayout.getEditText()).getText().toString().trim();

        if (password.isEmpty()) {
            inputLayout.setError(absencePass);
            inputLayout.requestFocus();
            return false;
        } else if (password.length() <= 5) {
            inputLayout.setError(moinsQue5);
            inputLayout.requestFocus();
            return false;
        } else {
            inputLayout.setError(null);
            return true;
        }
    }
}
