package uk.org.cse.stockimport.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the errors that occurred during the house construction phase of
 * the stock import. This captures the errors that will have prevented survey
 * cases from being constructed and stored.
 *
 * @since 4.0.0
 */
public class HousingStockConstructionErrors {

    private static final int MAX_NUMBER_OF_ERRORS = 300;
    private List<HousingStockConstructionError> errors = new ArrayList<>();

    public void add(final String id, final String message, final String details) {
        if (errors.size() == MAX_NUMBER_OF_ERRORS) {
            getErrors().add(new HousingStockConstructionError("", "More than " + MAX_NUMBER_OF_ERRORS + " have happened", "Further errors will be supressed"));
        } else if (errors.size() < MAX_NUMBER_OF_ERRORS) {
            getErrors().add(new HousingStockConstructionError(id, message, details));
        }
    }

    public List<HousingStockConstructionError> getErrors() {
        return errors;
    }

    public void setErrors(final List<HousingStockConstructionError> errors) {
        this.errors = errors;
    }
}
