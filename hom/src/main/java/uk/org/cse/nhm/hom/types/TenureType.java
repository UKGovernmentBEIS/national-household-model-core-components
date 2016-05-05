package uk.org.cse.nhm.hom.types;

/**
 * TenureType.
 *
 * @author richardt
 * @version $Id: TenureType.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public enum TenureType {
    OwnerOccupied("Owner Occupied"),
    PrivateRented("Private Rented"),
    HousingAssociation("Housing Association"),
    LocalAuthority("Local Authority");

    private String friendlyName;

    private TenureType(final String friendlyName) {
        this.friendlyName = friendlyName;
    }
}
