package Model;

import Model.ValidationErrors;

/**
 * Custom exception for reporting errors in the validation process.
 */
public class ValidationException extends Exception{

    private ValidationErrors validationErrors;

    public ValidationException(ValidationErrors validationErrors, String message) {
        super(message);
        this.validationErrors = validationErrors;
    }

    /**
     * Validation errors.
     * @return validation errors.
     */
    public ValidationErrors  getValidationErrors() {
        return validationErrors;
    }
}
