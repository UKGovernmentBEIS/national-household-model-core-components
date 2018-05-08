package uk.org.cse.nhm.language.definition.batch.inputs.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import uk.org.cse.nhm.language.definition.batch.inputs.XTable;

public class TableLengthsValidator implements ConstraintValidator<TableRowLengths, XTable> {

    @Override
    public void initialize(final TableRowLengths constraintAnnotation) {
        // no-op
    }

    @Override
    public boolean isValid(final XTable value, final ConstraintValidatorContext context) {
        final int length = value.getHeader().size();
        for (final List<String> row : value.getRows()) {
            if (row.size() != length) {
                return false;
            }
        }
        return true;
    }
}
