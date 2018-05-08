package uk.org.cse.nhm.language.definition.enums;

import uk.org.cse.nhm.language.definition.Doc;

@Doc("Determines the relationship between the house's occupants and its owner.")
public enum XTenureType {
    OwnerOccupied,
    PrivateRented,
    HousingAssociation,
    LocalAuthority;
}
