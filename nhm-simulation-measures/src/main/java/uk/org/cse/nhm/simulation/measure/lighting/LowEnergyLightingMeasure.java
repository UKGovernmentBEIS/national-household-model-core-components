package uk.org.cse.nhm.simulation.measure.lighting;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.ISizingResult;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.impl.SizingResult;
import uk.org.cse.nhm.simulator.measure.impl.TechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.Payment;

public class LowEnergyLightingMeasure extends AbstractMeasure {
	private final IDimension<ITechnologyModel> techDimension;
	private final IDimension<StructureModel> structureDimension;
	private final ITechnologiesFactory techFactory;
	private final double threshold;
	private final double proportion;
	private final IComponentsFunction<Number> capex;
	
	private final Comparator<ILight> byEnergyAscending = new Comparator<ILight>(){

		@Override
		public int compare(final ILight a, final ILight b) {
			return Double.compare(a.getEfficiency(), b.getEfficiency());
		}
	};

	@AssistedInject
	public LowEnergyLightingMeasure(
			@Assisted("threshold") final double threshold,
			@Assisted("proportion") final double proportion,
			@Assisted final IComponentsFunction<Number> capex,
			final IDimension<ITechnologyModel> techDimension,
			final IDimension<StructureModel> structureDimension
			) {
		this.structureDimension = structureDimension;
		this.techFactory = ITechnologiesFactory.eINSTANCE;;
		this.capex = capex;
		if (threshold > proportion) {
			this.threshold = proportion; 
		} else {
			this.threshold = threshold;
		}
		this.proportion = proportion;
		this.techDimension = techDimension;
	}

	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets)
			throws NHMException {
		
		final double floorArea = scope.get(structureDimension).getFloorArea();

		if (!isSuitable(scope, lets)) {
			return false;
		}
		
		scope.modify(techDimension, new IModifier<ITechnologyModel>(){
			@Override
			public boolean modify(final ITechnologyModel tech) {
				final SortedSet<ILight> standardLights = new TreeSet<>(byEnergyAscending);
				
				double standardP = 0.0;
				double lowEnergyP = 0.0;
				
				for (final ILight l : tech.getLights()) {
					if (l.getEfficiency() <= ILight.CFL_EFFICIENCY) {
						lowEnergyP += l.getProportion(); 
					} else {
						standardLights.add(l);
						standardP += l.getProportion();
					}
				}
				
				final double normalizingFactor = standardP + lowEnergyP;
				final double normalisedLowEnergyP = lowEnergyP / normalizingFactor;
				
				if (normalisedLowEnergyP > threshold 
						|| normalisedLowEnergyP == proportion
						|| standardLights.size() == 0) {
					return false;
				}
				
				final double lightingToInstallNormalised = proportion - normalisedLowEnergyP;
				  
				final ILight lowEnergy = techFactory.createLight();
				
				final double lightingToInstall = lightingToInstallNormalised * normalizingFactor;
				
				double installed = 0;
				while (installed < lightingToInstall 
						&& standardLights.size() > 0) {
					
					final ILight standard = standardLights.first();
					final double currentP = standard.getProportion();
					final double change = Math.max(currentP, lightingToInstall);
					
					standard.setProportion(currentP - change);
					installed += change;
					lowEnergy.setProportion(lowEnergy.getProportion() + lightingToInstall);
					
					if (standard.getProportion() == 0) {
						standardLights.remove(standard);
						tech.getLights().remove(standard);
					}
				}
				
				lowEnergy.setProportion(lowEnergy.getProportion() + installed);
				
				final double size = (installed / normalizingFactor) * floorArea;
				final ISizingResult sizingResult = SizingResult.suitable(size, Units.SQUARE_METRES);
				scope.addNote(sizingResult);
				
				final double capexResult = capex.compute(scope, lets).doubleValue();
				
				scope.addNote(new TechnologyInstallationDetails(LowEnergyLightingMeasure.this, TechnologyType.lowEnergyLighting(), sizingResult.getSize(), sizingResult.getUnits(), capexResult, 0));
				scope.addTransaction(Payment.capexToMarket(capexResult));
				
				return true;
			}
		});
		
		return true;
	}
	
	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		final ITechnologyModel tech = scope.get(techDimension);
		
		double standard = 0.0;
		double lowEnergy = 0.0;
		
		boolean someCapacity = false;
		
		for (final ILight l : tech.getLights()) {
			if (l.getEfficiency() <= ILight.CFL_EFFICIENCY) {
				lowEnergy+= l.getProportion();
			} else {
				standard += l.getProportion();
				someCapacity = true;
			}
		}

		return someCapacity && (lowEnergy / (standard + lowEnergy)) <= threshold; 
	}
	
	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}
}
