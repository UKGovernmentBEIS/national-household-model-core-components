package uk.org.cse.nhm.language.definition.action.hypothetical;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@Bind("counterfactual.carbon")
@Doc({ "Sets counterfactual carbon factors for a house, if used in an under.",
		"If a factor is unspecified, the value from SAP 2012 Table 12 is used." })
@Category(CategoryType.CARBON)
public class XCounterfactualCarbon extends XCounterfactualAction {
	private double mainsGas = 0.216;
	private double bulkLPG = 0.241;
	private double bottledLPG = 0.241;
	private double exportedElectricity = 0.519;
	private double peakElectricity = 0.519;
	private double offPeakElectricity = 0.519;
	private double oil = 0.298;
	private double biomassPellets = 0.039;
	private double biomassWoodchip = 0.016;
	private double biomassWood = 0.019;
	private double photons = 0;
	private double houseCoal = 0.394;
	// Assuming mains gas community heat source.
	private double communityHeat = 0.216;

	@Doc("The carbon factor for mains gas")
	@BindNamedArgument("mains-gas")
	public double getMainsGas() {
		return mainsGas;
	}

	public void setMainsGas(final double mainsGas) {
		this.mainsGas = mainsGas;
	}

	@Doc("The carbon factor for bulk LPG")
	@BindNamedArgument("bulk-lpg")
	public double getBulkLPG() {
		return bulkLPG;
	}

	public void setBulkLPG(final double bulkLPG) {
		this.bulkLPG = bulkLPG;
	}

	@Doc("The carbon factor for bottled LPG")
	@BindNamedArgument("bottled-lpg")
	public double getBottledLPG() {
		return bottledLPG;
	}

	public void setBottledLPG(final double bottledLPG) {
		this.bottledLPG = bottledLPG;
	}

	@Doc("The carbon factor for exported electricity. Note that the quantity of exported electricity is 0 or negative, so this value should be positive.")
	@BindNamedArgument("exported-electricity")
	public double getExportedElectricity() {
		return exportedElectricity;
	}

	public void setExportedElectricity(final double exportedElectricity) {
		this.exportedElectricity = exportedElectricity;
	}

	@Doc("The carbon factor for peak electricity")
	@BindNamedArgument("peak-electricity")
	public double getPeakElectricity() {
		return peakElectricity;
	}

	public void setPeakElectricity(final double peakElectricity) {
		this.peakElectricity = peakElectricity;
	}

	@Doc("The carbon factor for off-peak electricity")
	@BindNamedArgument("off-peak-electricity")
	public double getOffPeakElectricity() {
		return offPeakElectricity;
	}

	public void setOffPeakElectricity(final double offPeakElectricity) {
		this.offPeakElectricity = offPeakElectricity;
	}

	@Doc("The carbon factor for heating oil")
	@BindNamedArgument("oil")
	public double getOil() {
		return oil;
	}

	public void setOil(final double oil) {
		this.oil = oil;
	}

	@Doc("The carbon factor for biomass pellets")
	@BindNamedArgument("biomass-pellets")
	public double getBiomassPellets() {
		return biomassPellets;
	}

	public void setBiomassPellets(final double biomassPellets) {
		this.biomassPellets = biomassPellets;
	}

	@Doc("The carbon factor for biomass woodchip")
	@BindNamedArgument("biomass-woodchip")
	public double getBiomassWoodchip() {
		return biomassWoodchip;
	}

	public void setBiomassWoodchip(final double biomassWoodchip) {
		this.biomassWoodchip = biomassWoodchip;
	}

	@Doc("The carbon factor for biomass wood")
	@BindNamedArgument("biomass-wood")
	public double getBiomassWood() {
		return biomassWood;
	}

	public void setBiomassWood(final double biomassWood) {
		this.biomassWood = biomassWood;
	}

	@Doc("The carbon factor for photons")
	@BindNamedArgument("photons")
	public double getPhotons() {
		return photons;
	}

	public void setPhotons(final double photons) {
		this.photons = photons;
	}

	@Doc("The carbon factor for house coal")
	@BindNamedArgument("house-coal")
	public double getHouseCoal() {
		return houseCoal;
	}

	public void setHouseCoal(final double houseCoal) {
		this.houseCoal = houseCoal;
	}

	@Doc("The carbon factor for community heat")
	@BindNamedArgument("community-heat")
	public double getCommunityHeat() {
		return communityHeat;
	}

	public void setCommunityHeat(final double communityHeat) {
		this.communityHeat = communityHeat;
	}
}
