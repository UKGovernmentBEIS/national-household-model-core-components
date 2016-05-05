package uk.org.cse.nhm.simulator.state.dimensions.energy;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.IHeatingBehaviour;
import uk.org.cse.nhm.simulator.state.dimensions.weather.IWeather;

public interface IEnergyCalculatorBridge {
	IPowerTable evaluate(IWeather weather,
			StructureModel structure, ITechnologyModel technology,
			BasicCaseAttributes attributes, People people, IHeatingBehaviour behaviour);
}
