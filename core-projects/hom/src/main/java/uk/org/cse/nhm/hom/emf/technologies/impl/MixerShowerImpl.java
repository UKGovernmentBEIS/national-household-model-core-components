package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.emf.ecore.EClass;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;

import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IMixerShower;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICombiBoiler;
import uk.org.cse.nhm.hom.emf.technologies.impl.MainWaterHeaterImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mixer Shower</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class MixerShowerImpl extends ShowerImpl implements IMixerShower {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MixerShowerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.MIXER_SHOWER;
	}

	@Override
	public double hotWaterVolumePerShower() {
		final ICentralWaterSystem waterSystem = getTechnologyModel().getCentralWaterSystem();

		if (waterSystem != null) {
			final ICentralWaterHeater waterHeater = waterSystem.getPrimaryWaterHeater();

			if (waterHeater != null && waterHeater instanceof MainWaterHeaterImpl) {
				final IHeatSource heatSource = ((MainWaterHeaterImpl)waterHeater).getHeatSource();

				if (heatSource != null && heatSource instanceof ICombiBoiler) {
					return 44.4;
				}
			}
		}

		return 28.8;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	@Override
	public double solarAdjustment() {
		return 1.29;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	@Override
	public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final AtomicInteger heatingSystemCounter, final IHeatProportions heatProportions) {
		// Mixer shower does not have anything to do here.
	}

} //MixerShowerImpl