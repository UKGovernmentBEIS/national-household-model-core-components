package uk.org.cse.nhm.language.definition;

import uk.org.cse.nhm.language.adapt.IAdaptable;
import uk.org.cse.nhm.language.visit.IVisitable;

public interface IScenarioElement<T extends IVisitable<T>> extends IVisitable<T>, IAdaptable {

}
