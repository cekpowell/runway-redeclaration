package Model;

import java.util.ArrayList;

/**
 * Utility class for holding of validation errors.
 * <p>
 * This class is used internally by the ValidationException class.
 */
public class ValidationErrors {
    ArrayList<String> errorList;

    /**
     * Default parameter-less constructor.
     */
    public ValidationErrors() {
        errorList = new ArrayList<>();
    }

    /**
     * Adds message to validation errors collection.
     * @param message The message
     */
    public void add(String message){
        errorList.add(message);
    }

    /**
     * Error checking method.
     * @return true if there are validation errors, otherwise returns false.
     */
    public boolean hasErrors(){
        return !errorList.isEmpty();
    }

    /**
     * Errors list getter.
     * @return error list as array of String.
     */
    public ArrayList<String> getErrors() {
        return errorList;
    }

    /**
     * Errors string getter
     * @return error list as String.
     */
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        for (String item: errorList) {
            sb.append(item);
            sb.append("\r\n");
        }

        return sb.toString();
    }
}
