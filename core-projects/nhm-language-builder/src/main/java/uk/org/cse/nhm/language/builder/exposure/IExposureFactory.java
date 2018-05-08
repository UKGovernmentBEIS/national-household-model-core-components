package uk.org.cse.nhm.language.builder.exposure;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.scope.IStateAction;

public interface IExposureFactory {

    Object create(final Name id, IDwellingGroup group, IStateAction action);

}
