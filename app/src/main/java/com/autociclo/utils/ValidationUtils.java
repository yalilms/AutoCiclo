package com.autociclo.utils;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.util.regex.Pattern;

/**
 * Utilidad para validación de campos con feedback visual vistoso
 * @author Yalil Musa Talhaoui
 */
public class ValidationUtils {

    // Estilos directos para validación (SIN CSS - para Entrega 2)
    private static final String ERROR_STYLE = "-fx-border-color: red; -fx-border-width: 2px;";
    private static final String SUCCESS_STYLE = "-fx-border-color: green; -fx-border-width: 2px;";
    private static final String NORMAL_STYLE = "-fx-border-color: gray; -fx-border-width: 1px;";

    // Patrones de validación
    private static final Pattern MATRICULA_PATTERN = Pattern.compile("^\\d{4}-[A-Z]{3}$");

    /**
     * Valida que un TextField no esté vacío
     * @param field TextField a validar
     * @param errorLabel Label donde mostrar el mensaje de error
     * @param fieldName Nombre del campo para el mensaje
     * @return true si es válido, false si está vacío
     */
    public static boolean validateNotEmpty(TextField field, Label errorLabel, String fieldName) {
        String value = field.getText().trim();

        if (value.isEmpty()) {
            showError(field, errorLabel, fieldName + " es obligatorio");
            shakeAnimation(field);
            return false;
        }

        showSuccess(field, errorLabel);
        return true;
    }

    /**
     * Valida que un ComboBox tenga una selección
     */
    public static boolean validateComboBox(ComboBox<?> comboBox, Label errorLabel, String fieldName) {
        if (comboBox.getValue() == null) {
            showError(comboBox, errorLabel, "Debe seleccionar " + fieldName);
            shakeAnimation(comboBox);
            return false;
        }

        showSuccess(comboBox, errorLabel);
        return true;
    }

    /**
     * Valida que un TextArea no esté vacío
     */
    public static boolean validateTextArea(TextArea area, Label errorLabel, String fieldName) {
        String value = area.getText().trim();

        if (value.isEmpty()) {
            showError(area, errorLabel, fieldName + " es obligatorio");
            shakeAnimation(area);
            return false;
        }

        showSuccess(area, errorLabel);
        return true;
    }

    /**
     * Valida formato de matrícula española (1234-ABC)
     */
    public static boolean validateMatricula(TextField field, Label errorLabel) {
        String value = field.getText().trim().toUpperCase();

        if (value.isEmpty()) {
            showError(field, errorLabel, "La matrícula es obligatoria");
            shakeAnimation(field);
            return false;
        }

        if (!MATRICULA_PATTERN.matcher(value).matches()) {
            showError(field, errorLabel, "Formato inválido. Use: 1234-ABC");
            shakeAnimation(field);
            return false;
        }

        // Actualizar el campo con mayúsculas
        field.setText(value);
        showSuccess(field, errorLabel);
        return true;
    }

    /**
     * Valida que un campo sea numérico entero
     */
    public static boolean validateInteger(TextField field, Label errorLabel, String fieldName) {
        String value = field.getText().trim();

        if (value.isEmpty()) {
            showError(field, errorLabel, fieldName + " es obligatorio");
            shakeAnimation(field);
            return false;
        }

        try {
            Integer.parseInt(value);
            showSuccess(field, errorLabel);
            return true;
        } catch (NumberFormatException e) {
            showError(field, errorLabel, fieldName + " debe ser un número entero");
            shakeAnimation(field);
            return false;
        }
    }

    /**
     * Valida que un campo sea numérico entero y esté en un rango
     */
    public static boolean validateIntegerRange(TextField field, Label errorLabel, String fieldName, int min, int max) {
        String value = field.getText().trim();

        if (value.isEmpty()) {
            showError(field, errorLabel, fieldName + " es obligatorio");
            shakeAnimation(field);
            return false;
        }

        try {
            int numero = Integer.parseInt(value);

            if (numero < min || numero > max) {
                showError(field, errorLabel, fieldName + " debe estar entre " + min + " y " + max);
                shakeAnimation(field);
                return false;
            }

            showSuccess(field, errorLabel);
            return true;
        } catch (NumberFormatException e) {
            showError(field, errorLabel, fieldName + " debe ser un número entero");
            shakeAnimation(field);
            return false;
        }
    }

    /**
     * Valida que un campo sea numérico decimal
     */
    public static boolean validateDouble(TextField field, Label errorLabel, String fieldName) {
        String value = field.getText().trim().replace(",", ".");

        if (value.isEmpty()) {
            showError(field, errorLabel, fieldName + " es obligatorio");
            shakeAnimation(field);
            return false;
        }

        try {
            Double.parseDouble(value);
            field.setText(value); // actualizar con punto decimal
            showSuccess(field, errorLabel);
            return true;
        } catch (NumberFormatException e) {
            showError(field, errorLabel, fieldName + " debe ser un número decimal");
            shakeAnimation(field);
            return false;
        }
    }

    /**
     * Valida que un campo sea numérico decimal y mayor o igual a un mínimo
     */
    public static boolean validateDoubleMinimum(TextField field, Label errorLabel, String fieldName, double min) {
        String value = field.getText().trim().replace(",", ".");

        if (value.isEmpty()) {
            showError(field, errorLabel, fieldName + " es obligatorio");
            shakeAnimation(field);
            return false;
        }

        try {
            double numero = Double.parseDouble(value);

            if (numero < min) {
                showError(field, errorLabel, fieldName + " debe ser mayor o igual a " + min);
                shakeAnimation(field);
                return false;
            }

            field.setText(value); // actualizar con punto decimal
            showSuccess(field, errorLabel);
            return true;
        } catch (NumberFormatException e) {
            showError(field, errorLabel, fieldName + " debe ser un número decimal");
            shakeAnimation(field);
            return false;
        }
    }

    /**
     * Valida que un Spinner tenga un valor válido
     */
    public static boolean validateSpinner(Spinner<?> spinner, Label errorLabel, String fieldName) {
        if (spinner.getValue() == null) {
            showError(spinner, errorLabel, fieldName + " es obligatorio");
            shakeAnimation(spinner);
            return false;
        }

        showSuccess(spinner, errorLabel);
        return true;
    }

    /**
     * Muestra error visual en el campo
     */
    private static void showError(Control field, Label errorLabel, String message) {
        field.setStyle(ERROR_STYLE);

        if (errorLabel != null) {
            errorLabel.setText("⚠ " + message);
            errorLabel.setTextFill(Color.web("#e74c3c"));
            errorLabel.setVisible(true);
        }

        // Tooltip con el error
        Tooltip tooltip = new Tooltip(message);
        field.setTooltip(tooltip);
    }

    /**
     * Muestra éxito visual en el campo
     */
    private static void showSuccess(Control field, Label errorLabel) {
        field.setStyle(SUCCESS_STYLE);

        if (errorLabel != null) {
            errorLabel.setText("");
            errorLabel.setVisible(false);
        }

        field.setTooltip(null);
    }

    /**
     * Resetea el estilo del campo a normal
     */
    public static void resetStyle(Control field, Label errorLabel) {
        field.setStyle(NORMAL_STYLE);

        if (errorLabel != null) {
            errorLabel.setText("");
            errorLabel.setVisible(false);
        }

        field.setTooltip(null);
    }

    /**
     * Animación de shake (temblor) para campos con error
     */
    private static void shakeAnimation(Control field) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(50), field);
        tt.setFromX(0);
        tt.setByX(10);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.play();
    }

    /**
     * Muestra un mensaje de alerta de error
     */
    public static void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Muestra un diálogo de confirmación
     * @return true si el usuario confirma, false si cancela
     */
    public static boolean showConfirmation(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    /**
     * Muestra un mensaje de éxito
     */
    public static void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de error
     */
    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Valida que un código de pieza no esté vacío y tenga formato alfanumérico
     */
    public static boolean validateCodigoPieza(TextField field, Label errorLabel) {
        String value = field.getText().trim().toUpperCase();

        if (value.isEmpty()) {
            showError(field, errorLabel, "El código de pieza es obligatorio");
            shakeAnimation(field);
            return false;
        }

        // Código debe ser alfanumérico con guión opcional
        if (!value.matches("^[A-Z0-9\\-]+$")) {
            showError(field, errorLabel, "Solo letras, números y guión (-) permitidos");
            shakeAnimation(field);
            return false;
        }

        field.setText(value);
        showSuccess(field, errorLabel);
        return true;
    }

    /**
     * Limpia todos los estilos de validación de múltiples campos
     */
    public static void clearAllValidations(Control... fields) {
        for (Control field : fields) {
            resetStyle(field, null);
        }
    }
}
