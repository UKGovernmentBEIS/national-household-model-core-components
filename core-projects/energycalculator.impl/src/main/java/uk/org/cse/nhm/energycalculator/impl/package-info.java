/**
 * This package contains the moving parts for the implementation of the calculation part of
 * the NHM energy calculator.
 * <p>
 * {@link uk.org.cse.nhm.energycalculator.impl.EnergyCalculatorCalculator} is the entry point for the energy calculation.
 * Most of the surrounding classes are either uninteresting, or auxiliary classes used within the calculator.
 * <p>
 * The more complex ones of these deserve a quick introduction below:
 * <ul>
 * <li>
 * 		{@link uk.org.cse.nhm.energycalculator.impl.GainLoadRatioAdjuster}
 * 		This implements the part of the calculation where a gain/load ratio happens
 * 		and useful gains are determined
 * </li>
 * <li>{@link uk.org.cse.nhm.energycalculator.impl.StructuralInfiltrationAccumulator}
 * 		This accumulates structural infiltration values and determines the overall air change rate
 * 		for the house.
 * </li>
 * <li>{@link uk.org.cse.nhm.energycalculator.impl.Visitor}
 * 		This is the {@link uk.org.cse.nhm.IEnergyCalculatorParameters.api.IEnergyCalculatorVisitor} which {@link uk.org.cse.nhm.energycalculator.impl.EnergyCalculatorCalculator}
 * 		uses to gather up the items that are required for the heat loss calculation.
 * </li>
 * </ul>
 * 
 * Other adjuncts include the {@link uk.org.cse.nhm.energycalculator.impl.EnergyCalculationResult}, and {@link uk.org.cse.nhm.energycalculator.impl.SpecificHeatLosses},
 * which are simple objects for holding return values, {@link uk.org.cse.nhm.energycalculator.impl.InternalParameters} which adapts
 * an {@link uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters} to an {@link uk.org.cse.nhm.IEnergyCalculatorParameters.api.IInternalParameters}, and
 * {@link uk.org.cse.nhm.energycalculator.impl.HumanVentilationSystem} which implements the ventilation behaviour BREDEM defines for houses
 * without a mechanical ventilation system.
 */
package uk.org.cse.nhm.energycalculator.impl;

