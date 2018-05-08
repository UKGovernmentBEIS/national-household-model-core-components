package uk.org.cse.nhm.language.definition.fuel;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@Category(CategoryType.TARIFFS)
@Doc("Defines a method of payment")
public enum XMethodOfPayment {
    DirectDebit,
    PrepayMeter,
    StandardCredit,
    Free
}
