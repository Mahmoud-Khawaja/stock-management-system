package org.example.stockmanagementsystem.helperClasses;

import javafx.scene.control.TextField;

public class InputValidator {

    public static boolean validateTextField(TextField textField) {
        String text = textField.getText().trim();
        if (text.isEmpty()) {
            setInvalidStyle(textField);
            return false;
        } else {
            setValidStyle(textField);
            return true;
        }
    }

    public static boolean validateDoubleField(TextField textField) {
        if (validateTextField(textField)) {
            try {
                double value = Double.parseDouble(textField.getText().trim());
                if (value < 0) {
                    setInvalidStyle(textField);
                    return false;
                } else {
                    setValidStyle(textField);
                    return true;
                }
            } catch (NumberFormatException e) {
                setInvalidStyle(textField);
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean validateIntegerField(TextField textField) {
        if (validateTextField(textField)) {
            try {
                int value = Integer.parseInt(textField.getText().trim());
                if (value < 0) {
                    setInvalidStyle(textField);
                    return false;
                } else {
                    setValidStyle(textField);
                    return true;
                }
            } catch (NumberFormatException e) {
                setInvalidStyle(textField);
                return false;
            }
        } else {
            return false;
        }
    }

    private static void setInvalidStyle(TextField textField) {
        textField.setStyle("-fx-border-color: red;");
    }

    private static void setValidStyle(TextField textField) {
        textField.setStyle("");
    }
}
