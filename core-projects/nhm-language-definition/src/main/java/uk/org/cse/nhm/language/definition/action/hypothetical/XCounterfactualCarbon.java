package uk.org.cse.nhm.language.definition.action.hypothetical;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


@Bind("counterfactual.carbon")
@Doc({"Sets counterfactual carbon factors for a house, if used in an under.",
	  "If a factor is unspecified, the value from page 199 of SAP 2009 is used."})
@Category(CategoryType.CARBON)
public class XCounterfactualCarbon extends XCounterfactualAction {
	private double mainsGas = 0.216;
	private double bulkLPG = 0.241;
	private double bottledLPG = 0.241;
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
	public void setMainsGas(double mainsGas) {
		this.mainsGas = mainsGas;
	}
	@Doc("The carbon factor for bulk LPG")
    
@BindNamedArgument("bulk-lpg")
	public double getBulkLPG() {
		return bulkLPG;
	}
	public void setBulkLPG(double bulkLPG) {
		this.bulkLPG = bulkLPG;
	}
	@Doc("The carbon factor for bottled LPG")
    
@BindNamedArgument("bottled-lpg")
	public double getBottledLPG() {
		return bottledLPG;
	}
	public void setBottledLPG(double bottledLPG) {
		this.bottledLPG = bottledLPG;
	}
	@Doc("The carbon factor for peak electricity")
    
@BindNamedArgument("peak-electricity")
	public double getPeakElectricity() {
		return peakElectricity;
	}
	public void setPeakElectricity(double peakElectricity) {
		this.peakElectricity = peakElectricity;
	}
	@Doc("The carbon factor for off-peak electricity")
    
@BindNamedArgument("off-peak-electricity")
	public double getOffPeakElectricity() {
		return offPeakElectricity;
	}
	public void setOffPeakElectricity(double offPeakElectricity) {
		this.offPeakElectricity = offPeakElectricity;
	}
	@Doc("The carbon factor for heating oil")
    
@BindNamedArgument("oil")
	public double getOil() {
		return oil;
	}
	public void setOil(double oil) {
		this.oil = oil;
	}
	@Doc("The carbon factor for biomass pellets")
    
@BindNamedArgument("biomass-pellets")
	public double getBiomassPellets() {
		return biomassPellets;
	}
	public void setBiomassPellets(double biomassPellets) {
		this.biomassPellets = biomassPellets;
	}
	@Doc("The carbon factor for biomass woodchip")
    
@BindNamedArgument("biomass-woodchip")
	public double getBiomassWoodchip() {
		return biomassWoodchip;
	}
	public void setBiomassWoodchip(double biomassWoodchip) {
		this.biomassWoodchip = biomassWoodchip;
	}
	@Doc("The carbon factor for biomass wood")
    
@BindNamedArgument("biomass-wood")
	public double getBiomassWood() {
		return biomassWood;
	}
	public void setBiomassWood(double biomassWood) {
		this.biomassWood = biomassWood;
	}
	@Doc("The carbon factor for photons")
    
@BindNamedArgument("photons")
	public double getPhotons() {
		return photons;
	}
	public void setPhotons(double photons) {
		this.photons = photons;
	}
	@Doc("The carbon factor for house coal")
    
@BindNamedArgument("house-coal")
	public double getHouseCoal() {
		return houseCoal;
	}
	public void setHouseCoal(double houseCoal) {
		this.houseCoal = houseCoal;
	}
	@Doc("The carbon factor for community heat")
    
@BindNamedArgument("community-heat")
	public double getCommunityHeat() {
		return communityHeat;
	}
	public void setCommunityHeat(double communityHeat) {
		this.communityHeat = communityHeat;
	}
}
