package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ehcs10.derived.Interview_09Plus10Entry;
import uk.org.cse.nhm.ehcs10.derived.impl.Interview_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.fuel_poverty.Fuel_Poverty_Dataset_2010Entry;
import uk.org.cse.nhm.ehcs10.fuel_poverty.impl.Fuel_Poverty_Dataset_2010EntryImpl;
import uk.org.cse.nhm.ehcs10.interview.impl.PeopleEntryImpl;
import uk.org.cse.nhm.ehcs10.interview.types.Enum752;
import uk.org.cse.nhm.spss.wrap.SavEnumMapping;
import uk.org.cse.stockimport.domain.IOccupantDetailsDTO;
import uk.org.cse.stockimport.domain.impl.OccupantDetailsDTO;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;

/**
 * OccupantDetailsReader.
 *
 * @author richardt
 * @version $Id: OccupantDetailsReader.java 94 2010-09-30 15:39:21Z richardt
 * @since 1.0.1
 */
public class OccupantDetailsReader extends AbsSpssReader<IOccupantDetailsDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OccupantDetailsReader.class);
    private final DateTime surveyDate;

    /**
     * Default Constructor.
     *
     * @since 1.0
     */
    public OccupantDetailsReader(final String executionId, final IHouseCaseSourcesRepositoryFactory providerFactory,
            final DateTime surveyDate) {
        super(executionId, providerFactory);
        this.surveyDate = surveyDate;
    }

    @Override
    public List<IOccupantDetailsDTO> read(final IHouseCaseSources<Object> provider) {
        return buildOccupantDetails(provider);
    }

    protected List<IOccupantDetailsDTO> buildOccupantDetails(final IHouseCaseSources<Object> provider) {
        Double annualHouseholdIncome = null;
        Integer ageOfHrp = null;
        Boolean hasDisabledOrSickOccupant = null;
        Boolean hasOccupantOnBenefits = null;
        DateTime dateMovedIn = null;

        final Optional<Fuel_Poverty_Dataset_2010Entry> povertyEntry = provider.getOne(Fuel_Poverty_Dataset_2010Entry.class);
        final Optional<Interview_09Plus10Entry> maybeInterview = provider.getOne(Interview_09Plus10Entry.class);
        final Optional<PeopleEntryImpl> hrpPerson = getHRPersonEntry(provider);

        if (povertyEntry.isPresent()) {
            annualHouseholdIncome = povertyEntry.get().getAnnualFullHouseholdIncome___();
        } else {
            LOGGER.error("Fuel_Poverty_Dataset_2010Entry not found for aacode:{}", provider.getAacode());
        }

        if (maybeInterview.isPresent()) {
            final Interview_09Plus10Entry interview = maybeInterview.get();

            dateMovedIn = calcDateMovedIn(interview.getLengthOfResidence_Years_());
            ageOfHrp = interview.getAgeOfHRP_Continuous();

            hasOccupantOnBenefits = getHasOccupantOnBenefits(
                    interview.getHouseholdVulnerable_OnMeansTestedOrCertainDisabilityRelatedBenefits_());

            hasDisabledOrSickOccupant = getHasDisabledOrSickOccupant(
                    interview.getAnyoneInHholdHaveLessThanIllnessOrDisability_());
        } else {
            LOGGER.error("Interview_09Plus10Entry not found for aacode:{}", provider.getAacode());
        }

        // Create DTO
        final IOccupantDetailsDTO occupantDetails = new OccupantDetailsDTO();
        occupantDetails.setAacode(provider.getAacode());
        occupantDetails.setChiefIncomeEarnersAge(Optional.fromNullable(ageOfHrp));
        occupantDetails.setHouseHoldIncomeBeforeTax(Optional.fromNullable(annualHouseholdIncome));
        occupantDetails.setHasOccupantOnBenefits(Optional.fromNullable(hasOccupantOnBenefits));
        occupantDetails.setHasDisabledOrSickOccupant(Optional.fromNullable(hasDisabledOrSickOccupant));
        occupantDetails.setDateMovedIn(Optional.fromNullable(dateMovedIn));

        if (hrpPerson.isPresent()) {
            final Enum752 hoursNormallyWorked_Wk = hrpPerson.get().getHoursNormallyWorked_Wk();
            switch (hoursNormallyWorked_Wk) {
                case UpTo15Hours_:
                case _16_29Hours_:
                    SavEnumMapping enumMapping;
                    try {
                        enumMapping = Enum752.class.getField(hoursNormallyWorked_Wk.toString()).getAnnotation(SavEnumMapping.class);
                    } catch (final SecurityException e) {
                        throw new RuntimeException("Security exception looking up field on generated SPSS hoursNormallyWorked_wk enum.", e);
                    } catch (final NoSuchFieldException e) {
                        throw new RuntimeException("Exception looking up field on generated SPSS hoursNormallyWorked_wk enum.", e);
                    }
                    occupantDetails.setWorkingHours(enumMapping.value()[0].toString());
                case DoesNotApply:
                case NoAnswer:
                default:
                    break;
            }
        }

        return Arrays.asList(occupantDetails);
    }

    /**
     * Maps from {@link Enum69} to either true,false or null. Returns true if
     * {@link Enum69#Yes}, false if {@link Enum69#No} otherwise returns null.
     *
     * @param includesDisabled
     * @return
     * @since 1.0.1
     */
    protected Boolean getHasDisabledOrSickOccupant(final Enum69 includesDisabled) {
        Boolean hasDisabled = null;

        if (includesDisabled != null) {
            switch (includesDisabled) {
                case Yes:
                    hasDisabled = Boolean.TRUE;
                    break;
                case No:
                    hasDisabled = Boolean.FALSE;
                default:
                    break;
            }
        }

        return hasDisabled;
    }

    /**
     * Maps from {@link Enum10} to either true,false or null. Returns true if
     * {@link Enum10#Yes}, false if {@link Enum10#No} otherwise returns null.
     *
     * @param onBenefits
     * @return
     * @since 1.0.1
     */
    protected Boolean getHasOccupantOnBenefits(final Enum10 onBenefits) {
        Boolean isOnBenefits = null;

        if (onBenefits != null) {
            switch (onBenefits) {
                case Yes:
                    isOnBenefits = Boolean.TRUE;
                    break;
                case No:
                    isOnBenefits = Boolean.FALSE;
                default:
                    break;
            }
        }

        return isOnBenefits;
    }

    /**
     * 1. Takes survey date and subtracts lengthOfResidence from this to get
     * moved in date.<br/><br/> <b>n.b. currently only supports years
     * increments, returns null of lengthOfResidence is null.</b>
     *
     * @param lengthOfResidence
     * @return
     * @since 1.0.1
     */
    protected DateTime calcDateMovedIn(final Integer lengthOfResidence) {
        if (lengthOfResidence == null) {
            return null;
        }

        return surveyDate.plusYears(-lengthOfResidence);
    }

    private Optional<PeopleEntryImpl> getHRPersonEntry(final IHouseCaseSources<Object> dtoProvider) {
        final List<PeopleEntryImpl> people = dtoProvider.getAll(PeopleEntryImpl.class);
        if (people.isEmpty()) {
            return Optional.absent();
        }

        final List<PeopleEntryImpl> results = new ArrayList<PeopleEntryImpl>();
        for (final PeopleEntryImpl p : people) {
            if (p.getPersonIdentifier().equals(p.getPersonNumberOfHRP())) {
                results.add(p);
            }
        }
        if (results.size() == 1) {
            return Optional.of(results.get(0));
        }
        throw new RuntimeException(String.format("Found %s HRP PeopleEntries out of %s for %s.", results.size(), people.size(), dtoProvider.getAacode()));
    }

    /**
     * @return @see
     * uk.org.cse.stockimport.ehcs2010.spss.elementreader.AbsSpssReader#getSurveyEntryClasses()
     */
    @Override
    protected Set<Class<?>> getSurveyEntryClasses() {
        return ImmutableSet.<Class<?>>of(
                Fuel_Poverty_Dataset_2010EntryImpl.class,
                Interview_09Plus10EntryImpl.class,
                PeopleEntryImpl.class);
    }

    /**
     * @return @see
     * uk.org.cse.stockimport.ehcs2010.spss.elementreader.AbsSpssReader#readClass()
     */
    @Override
    protected Class<?> readClass() {
        return OccupantDetailsDTO.class;
    }
}
