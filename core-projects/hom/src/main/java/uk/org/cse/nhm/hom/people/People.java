package uk.org.cse.nhm.hom.people;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import uk.org.cse.nhm.hom.ICopyable;
import uk.org.cse.nhm.hom.types.SexType;

/**
 * People.
 *
 * @author richardt
 * @version $Id: People.java 94 2010-09-30 15:39:21Z richardt
 * @since 1.0.0
 */
@AutoProperty
public class People implements ICopyable<People> {

    private int children;
    private int adults;

    private Boolean hasOccupantOnBenefits;
    private Boolean hasDisabledOrSickOccupant;
    private DateTime dateMovedIn;
    private String normalWorkingHours;
    private Double occupancy;

    /**
     * Added to support NHM health calculations; this supersedes adults and
     * children but for backwards compatibility if there are no occupants and
     * the number of children or number of adults is positive we will use that.
     *
     * Whether a person is a child may mean dependent child?
     */
    private List<Occupant> occupants = new ArrayList<>(4);

    @AutoProperty
    public static class Occupant {

        private SexType sex;
        private int age;
        private boolean smoker;

        public Occupant() {

        }

        public Occupant(final SexType sex, final int age) {
            this.sex = sex;
            this.age = age;
        }

        public Occupant(final SexType sex, final int age, final boolean smoker) {
            this(sex, age);
            this.setSmoker(smoker);
        }

        public boolean isSmoker() {
            return smoker;
        }

        public void setSmoker(final boolean smoker) {
            this.smoker = smoker;
        }

        public SexType getSex() {
            return sex;
        }

        public void setSex(SexType sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Occupant copy() {
            return new Occupant(sex, age);
        }

        @Override
        public int hashCode() {
            return Pojomatic.hashCode(this);
        }

        @Override
        public boolean equals(final Object other) {
            return Pojomatic.equals(this, other);
        }
    }

    /**
     * @since 1.0.0
     */
    public People() {
    }

    /**
     * Copy constructor used by {@link #copy()} method.
     *
     * @param adults2
     * @param children2
     * @param dateMovedIn2
     */
    People(final int adults2, final int children2, final DateTime dateMovedIn2, final Boolean hasOccupantOnBenefits2, final Boolean hasDiscabledOrSickOccupant2, final String normalWorkingHours2, final Double occupancy, final List<Occupant> occupants) {
        setAdults(adults2);
        setChildren(children2);
        setDateMovedIn(dateMovedIn2);
        setHasOccupantOnBenefits(hasOccupantOnBenefits2);
        setHasDisabledOrSickOccupant(hasDiscabledOrSickOccupant2);
        setNormalWorkingHours(normalWorkingHours2);
        if (occupancy == null) {
            clearOccupancy();
        } else {
            setOccupancy(occupancy);
        }
        setOccupants(occupants);
    }

    /**
     * @since 1.0.0
     */
    public int getChildren() {
        return children;
    }

    /**
     * @since 1.0.0
     */
    public void setChildren(final int children) {
        this.children = children;
    }

    /**
     * @since 1.0.0
     */
    public int getAdults() {
        return adults;
    }

    /**
     * @since 1.0.0
     */
    public void setAdults(final int adults) {
        this.adults = adults;
    }

    /**
     * @since 1.0.0
     */
    public int getNumberOfPeople() {
        return getChildren() + getAdults();
    }

    /**
     * @since 1.3.11
     * @return either an occupancy poked in by {@link #setOccupancy(double)}, or
     * {@link #getNumberOfPeople()} if no occupancy has been set.
     */
    @JsonIgnore
    public double getOccupancy() {
        if (occupancy == null) {
            return getNumberOfPeople();
        } else {
            return occupancy;
        }
    }

    /**
     * See {@link #getOccupancy()}; {@link #clearOccupancy()} undoes this
     *
     * @since 1.3.11
     * @param occupancy
     */
    public void setOccupancy(final double occupancy) {
        this.occupancy = occupancy;
    }

    /**
     * See {@link #getOccupancy()}
     *
     * @since 1.3.11
     */
    public void clearOccupancy() {
        this.occupancy = null;
    }

    /**
     * @since 1.1.0
     */
    public Boolean hasOccupantOnBenefits() {
        return hasOccupantOnBenefits;
    }

    /**
     * Set the hasOccupantOnBenefits.
     *
     * @since 1.1.0
     */
    public void setHasOccupantOnBenefits(final Boolean hasOccupantOnBenefits) {
        this.hasOccupantOnBenefits = hasOccupantOnBenefits;
    }

    /**
     * @since 1.1.0
     */
    public Boolean hasDisabledOrSickOccupant() {
        return hasDisabledOrSickOccupant;
    }

    /**
     * Set the hasDisabledOrSickOccupant.
     *
     * @since 1.1.0
     */
    public void setHasDisabledOrSickOccupant(final Boolean hasDisabledOrSickOccupant) {
        this.hasDisabledOrSickOccupant = hasDisabledOrSickOccupant;
    }

    /**
     * Return the dateMovedIn.
     *
     * @return the dateMovedIn
     * @since 1.1.0
     */
    public DateTime getDateMovedIn() {
        return dateMovedIn;
    }

    /**
     * @since 1.2.1
     * @return A description of the number of hours normally worked weekly by
     * the head of household.
     */
    public String getNormalWorkingHours() {
        return normalWorkingHours;
    }

    /**
     * @since 1.2.1
     * @param normalWorkingHours Set a description of number of hours normally
     * worked by the head of household.
     */
    public void setNormalWorkingHours(final String normalWorkingHours) {
        this.normalWorkingHours = normalWorkingHours;
    }

    /**
     * Set the dateMovedIn.
     *
     * @param dateMovedIn the dateMovedIn
     * @since 1.1.0
     */
    public void setDateMovedIn(final DateTime dateMovedIn) {
        this.dateMovedIn = dateMovedIn;
    }

    public List<Occupant> getOccupants() {
        return occupants == null ? Collections.<Occupant>emptyList() : occupants;
    }

    public void setOccupants(final List<Occupant> occupants) {
        if (this.occupants == null) {
            this.occupants = new ArrayList<>(occupants);
        } else {
            this.occupants.clear();
            this.occupants.addAll(occupants);
        }
    }

    /**
     * @since 1.2.1
     */
    @Override
    public People copy() {
        return new People(getAdults(), getChildren(), getDateMovedIn(), hasOccupantOnBenefits(), hasDisabledOrSickOccupant(), getNormalWorkingHours(), occupancy, copyOccupants(occupants));
    }

    private static List<Occupant> copyOccupants(List<Occupant> in) {
        final List<Occupant> out = new ArrayList<>();
        if (in == null) {
            return out;
        }
        for (final Occupant o : in) {
            out.add(o.copy());
        }
        return out;
    }

    public Boolean getHasOccupantOnBenefits() {
        return hasOccupantOnBenefits;
    }

    public Boolean getHasDisabledOrSickOccupant() {
        return hasDisabledOrSickOccupant;
    }

    public void setOccupancy(final Double occupancy) {
        this.occupancy = occupancy;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + adults;
        result = prime * result + children;
        result = prime * result
                + ((dateMovedIn == null) ? 0 : dateMovedIn.hashCode());
        result = prime
                * result
                + ((hasDisabledOrSickOccupant == null) ? 0
                        : hasDisabledOrSickOccupant.hashCode());
        result = prime
                * result
                + ((hasOccupantOnBenefits == null) ? 0 : hasOccupantOnBenefits
                                .hashCode());
        result = prime
                * result
                + ((normalWorkingHours == null) ? 0 : normalWorkingHours
                                .hashCode());
        result = prime * result
                + ((occupancy == null) ? 0 : occupancy.hashCode());

        result = prime
                * result
                + (occupants == null ? 0 : occupants.hashCode());

        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final People other = (People) obj;
        if (adults != other.adults) {
            return false;
        }
        if (children != other.children) {
            return false;
        }
        if (dateMovedIn == null) {
            if (other.dateMovedIn != null) {
                return false;
            }
        } else if (!dateMovedIn.equals(other.dateMovedIn)) {
            return false;
        }
        if (hasDisabledOrSickOccupant == null) {
            if (other.hasDisabledOrSickOccupant != null) {
                return false;
            }
        } else if (!hasDisabledOrSickOccupant
                .equals(other.hasDisabledOrSickOccupant)) {
            return false;
        }
        if (hasOccupantOnBenefits == null) {
            if (other.hasOccupantOnBenefits != null) {
                return false;
            }
        } else if (!hasOccupantOnBenefits.equals(other.hasOccupantOnBenefits)) {
            return false;
        }
        if (normalWorkingHours == null) {
            if (other.normalWorkingHours != null) {
                return false;
            }
        } else if (!normalWorkingHours.equals(other.normalWorkingHours)) {
            return false;
        }
        if (occupancy == null) {
            if (other.occupancy != null) {
                return false;
            }
        } else if (!occupancy.equals(other.occupancy)) {
            return false;
        }

        if (occupants == null) {
            if (other.occupants != null) {
                return false;
            }
        } else if (!occupants.equals(other.occupants)) {
            return false;
        }
        return true;
    }
}
