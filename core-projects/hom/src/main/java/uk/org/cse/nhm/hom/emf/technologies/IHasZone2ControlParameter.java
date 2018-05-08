package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.common.util.EList;

import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.types.Zone2ControlParameter;

public interface IHasZone2ControlParameter {

    Zone2ControlParameter getZoneTwoControlParameter(IInternalParameters parameters, EList<HeatingSystemControlType> controls, EmitterType emitterType);
}
