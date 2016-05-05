package uk.org.cse.nhm.energycalculator.api.impl;

import java.util.HashMap;
import java.util.Map;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;

public class GraphvizEnergyState implements IEnergyState {
	private final IEnergyState delegate;
	
	private final Map<EnergyType, Integer> etNodeNames = new HashMap<EnergyType, Integer>();
	
	private final Map<String, Integer> sNodeNames = new HashMap<String, Integer>();
	
	private final StringBuffer edges = new StringBuffer();
	
	private DoubleMap<EnergyType> thisRound = new DoubleMap<EnergyType>(EnergyType.class, 2);
	
	private String currentServiceType = null;
	
	private void emit(final EnergyType et, final double amount, final StringBuffer edges) {
		if (amount == 0) return;
		
		final int eti = getNode(et, etNodeNames);
		final int sti = getNode(currentServiceType, sNodeNames);
		
		edges.append(String.format("\tS%d -> E%d [label=\"%.1f\"];\n", sti, eti, amount));
	}
	
	private void consume(final EnergyType et, final double amount, final StringBuffer edges) {
		if (amount == 0) return;
		
		final int eti = getNode(et, etNodeNames);
		final int sti = getNode(currentServiceType, sNodeNames);
		
		edges.append(String.format("\tE%d -> S%d [label=\"%.1f\"];\n", eti, sti, amount));
	}
	
	private String currentRoundToEdges() {
		final StringBuffer sb = new StringBuffer();
		
		for (final EnergyType et : thisRound.keySet()) {
			emit(et, thisRound.get(et, 0), sb);
			consume(et, thisRound.get(et, 1), sb);
		}
		
		return sb.toString();
	}
	
	public String toDotFile() {
		final StringBuffer sb = new StringBuffer();
		final String finalEdges = currentRoundToEdges();
		sb.append("digraph energystate {\n");
		
		for (final EnergyType et : etNodeNames.keySet()) {
			sb.append(String.format("\tE%d [label=\"%s\"];\n", etNodeNames.get(et), et.toString()));
		}
		
		for (final String st : sNodeNames.keySet()) {
			sb.append(String.format("\tS%d [shape=box label=\"%s (%d)\"];\n", sNodeNames.get(st), st.toString(), sNodeNames.get(st)));
		}
		
		sb.append("\n\n");
		
		sb.append(edges.toString());
		
		sb.append(finalEdges);
		
		sb.append("}\n");
		
		return sb.toString();
	}
	
	private <T> int getNode(final T obj, final Map<T, Integer> nodes) {
		Integer i = nodes.get(obj);
		if (i == null) {
			i = nodes.size();
			nodes.put(obj, i);
		}
		return i;
	}
	
	public GraphvizEnergyState(final IEnergyState delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public void increaseSupply(final EnergyType energy, final double amount) {
//		emit(energy, amount);
		thisRound.increment(energy, 0, amount);
		delegate.increaseSupply(energy, amount);
	}

	@Override
	public void increaseDemand(final EnergyType energy, final double amount) {
//		consume(energy, amount);
		thisRound.increment(energy, 1, amount);
		delegate.increaseDemand(energy, amount);
	}
	
	@Override
	public double getUnsatisfiedDemand(final EnergyType energy) {
		return delegate.getUnsatisfiedDemand(energy);
	}

	@Override
	public double getTotalSupply(final EnergyType energy) {
		return delegate.getTotalSupply(energy);
	}

	@Override
	public double getTotalSupply(final EnergyType energy, final ServiceType service) {
		return delegate.getTotalSupply(energy, service);
	}

	@Override
	public double getTotalDemand(final EnergyType energyType, final ServiceType serviceType) {
		return delegate.getTotalDemand(energyType, serviceType);
	}

	@Override
	public double getTotalDemand(final EnergyType energyType) {
		return delegate.getTotalDemand(energyType);
	}

	@Override
	public void increaseElectricityDemand(final double highRateFraction, final double amount) {
		increaseDemand(EnergyType.FuelPEAK_ELECTRICITY, highRateFraction * amount);
		increaseDemand(EnergyType.FuelOFFPEAK_ELECTRICITY, (1 - highRateFraction) * amount);
	}
	
	@Override
	public double getBoundedTotalDemand(final EnergyType energy,
			final double proportionOfTotal) {
		return delegate.getBoundedTotalDemand(energy, proportionOfTotal);
	}
	
	@Override
	public double getBoundedTotalHeatDemand(final double proportionOfTotal) {
		return delegate.getBoundedTotalHeatDemand(proportionOfTotal);
	}
	
	@Override
	public double getExcessSupply(final EnergyType energyType) {
		return delegate.getExcessSupply(energyType);
	}

	@Override
	public void setCurrentServiceType(final ServiceType serviceType, final String name) {
		edges.append(currentRoundToEdges());
		thisRound = new DoubleMap<EnergyType>(EnergyType.class, 2);
		
		this.currentServiceType = name;
		delegate.setCurrentServiceType(serviceType, name);
	}
	
	@Override
	public void meetSupply(final EnergyType et, final double delta) {
		delegate.meetSupply(et, delta);
	}

	@Override
	public String toString() {
		return delegate.toString();
	}
}
