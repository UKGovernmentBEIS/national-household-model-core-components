package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.derived.types.Enum71;
import uk.org.cse.nhm.ehcs10.interview.DisabilityEntry;
import uk.org.cse.nhm.ehcs10.interview.PeopleEntry;
import uk.org.cse.nhm.ehcs10.interview.impl.DisabilityEntryImpl;
import uk.org.cse.nhm.ehcs10.interview.impl.PeopleEntryImpl;
import uk.org.cse.nhm.hom.types.SexType;
import uk.org.cse.stockimport.domain.IPersonDTO;
import uk.org.cse.stockimport.domain.impl.PersonDTO;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;

public class SpssPersonReader extends AbsSpssReader<IPersonDTO> {
    public SpssPersonReader(String executionId, IHouseCaseSourcesRepositoryFactory mongoProviderFactory) {
        super(executionId, mongoProviderFactory);
    }

    protected Set<Class<?>> getSurveyEntryClasses() {
        return ImmutableSet.<Class<?>>of(PeopleEntry.class, PeopleEntryImpl.class,
                                         DisabilityEntry.class, DisabilityEntryImpl.class);
    }
    
    protected Class<?> readClass() {
        return PersonDTO.class;
    }

    public Collection<IPersonDTO> read(final IHouseCaseSources<Object> provider) {
        final List<IPersonDTO> output = new ArrayList<>();
        final List<DisabilityEntry> disabilities = provider.getAll(DisabilityEntry.class);

        for (final PeopleEntry entry: provider.getAll(PeopleEntry.class)) {
            final Integer age = entry.getAge();
            final Enum71 sex = entry.getSex();
            // to get the smoking information, we need to get a
            // disability entry with a joining person identifier.

            // unfortunately I don't think disability is in the normal zipfile,
            // so this will be false in most cases.
            if (age != null && sex != null) {
                final PersonDTO person = new PersonDTO();
                person.setAacode(provider.getAacode());
                person.setAge(age);
                switch (sex) {
                case Female:
                    person.setSex(SexType.FEMALE);
                    break;
                case Male:
                    person.setSex(SexType.MALE);
                    break;
                case DoesNotApply:
                case NoAnswer:
                    person.setSex(SexType.UNKNOWN);
                    break;
                }

                for (final DisabilityEntry de : disabilities) {
                    final Integer dei = de.getPersonIdentifier();
                    final Integer pei = entry.getPersonIdentifier();
                    if (dei != null && dei.equals(pei)) {
                        if (Enum69.Yes == de.getSmokeCigarettesNow()) {
                            person.setSmoker(true);
                        }
                    }
                }

                output.add(person);
            }
        }
        
        return output;
    }
}


