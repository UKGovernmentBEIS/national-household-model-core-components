package uk.org.cse.stockimport.domain.geometry.impl;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.components.fabric.types.CoveringType;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.RoofStructureType;
import uk.org.cse.stockimport.domain.geometry.IRoofDTO;

/**
 * RoofDTO.
 *
 * @author richardt
 * @version $Id: RoofDTO.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
@AutoProperty
public class RoofDTO extends AbsDTO implements IRoofDTO {
    private RoofConstructionType roofType;
    private RoofStructureType structureType;
    private CoveringType coveringType;
    private Optional<Double> insulationThickness = Optional.absent();

    @Override
	public RoofConstructionType getRoofType() {
		return roofType;
	}

	@Override
	public void setRoofType(final RoofConstructionType roofType) {
		this.roofType = roofType;
	}

	@Override
	public RoofStructureType getStructureType() {
		return structureType;
	}

	@Override
	public void setStructureType(final RoofStructureType structureType) {
		this.structureType = structureType;
	}

	@Override
	public CoveringType getCoveringType() {
		return coveringType;
	}

	@Override
	public void setCoveringType(final CoveringType coveringType) {
		this.coveringType = coveringType;
	}

	@Override
	public Optional<Double> getInsulationThickness() {
		return insulationThickness;
	}

	@Override
	public void setInsulationThickness(final Optional<Double> insulationThickness) {
		this.insulationThickness = insulationThickness;
	}

	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}

	@Override
	public boolean equals(final Object other) {
		return Pojomatic.equals(this, other);
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}
