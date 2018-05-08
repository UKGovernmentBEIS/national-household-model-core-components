package uk.org.cse.stockimport.hom.impl.steps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.hom.people.People.Occupant;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.IHouseCaseDTO;
import uk.org.cse.stockimport.domain.IPersonDTO;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.domain.impl.OccupantDetailsDTO;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * PersonBuildStep.
 *
 * @author richardt
 * @version $Id: PersonBuildStep.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class PeopleBuildStep implements ISurveyCaseBuildStep {

    /**
     * @since 1.0
     */
    public static final String IDENTIFIER = PeopleBuildStep.class.getCanonicalName();

    /**
     * @return @see
     * uk.org.cse.stockimport.hom.ISurveyCaseBuildStep#getIdentifier()
     */
    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    /**
     * @return @see
     * uk.org.cse.stockimport.hom.ISurveyCaseBuildStep#getDependencies()
     */
    @Override
    public Set<String> getDependencies() {
        return Collections.emptySet();
    }

    /**
     * @assumption If occupant details are null: moved in date, disabled/sick
     * and on benefits will be null.
     * @assumption If number of adults or children are null: we set them to
     * zero. That is, the house is empty.
     * @param model
     * @param dtoProvider
     * @see
     * uk.org.cse.stockimport.hom.ISurveyCaseBuildStep#build(uk.org.cse.nhm.hom.SurveyCase,
     * uk.org.cse.stockimport.repository.IHouseCaseSources)
     */
    @Override
    public void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider) {
        final Optional<OccupantDetailsDTO> occupantDetails = dtoProvider.getOne(OccupantDetailsDTO.class);
        final IHouseCaseDTO houseCaseDTO = dtoProvider.requireOne(HouseCaseDTO.class);

        final People people = new People();
        if (!occupantDetails.isPresent()) {
            // TODO: What to do with null values from EHS
        } else {
            final OccupantDetailsDTO dto = occupantDetails.get();
            people.setDateMovedIn(dto.getDateMovedIn().orNull());
            people.setHasDisabledOrSickOccupant(dto.getHasDisabledOrSickOccupant().orNull());
            people.setHasOccupantOnBenefits(dto.getHasOccupantOnBenefits().orNull());
            people.setNormalWorkingHours(dto.getWorkingHours());
        }

        final List<IPersonDTO> peopleDTOs = dtoProvider.getAll(IPersonDTO.class);
        final List<Occupant> occupants = new ArrayList<>();
        for (final IPersonDTO p : peopleDTOs) {
            occupants.add(new Occupant(p.getSex(), p.getAge(), p.isSmoker()));
        }

        people.setAdults(houseCaseDTO.getAdultOccupants().or(0));
        people.setChildren(houseCaseDTO.getChildOccupants().or(0));
        people.setOccupants(occupants);

        model.setPeople(people);
    }
}
