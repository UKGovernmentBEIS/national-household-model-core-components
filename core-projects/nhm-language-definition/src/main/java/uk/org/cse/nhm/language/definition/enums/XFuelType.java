package uk.org.cse.nhm.language.definition.enums;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@Doc("These fuel types define all the different kinds of fuel understood by the energy calculator.")
@Category(CategoryType.CATEGORIES)
public enum XFuelType {
    MainsGas,
    BulkLPG,
    BottledLPG,
    @Doc({
        "Internally, the energy calculation uses peak and off-peak electricity for pricing and consumption purposes.",
        "The electricity fuel type is used to specify or test the kind of fuel used by a device, whereas the peak/offpeak",
        "electricity fuel types are used for the calculation of the operating cost of a particular device.",
        "Consequently when specifying a measure which installs an electrical device, the Electricity fuel type is appropriate",
        "but when defining the prices of different kinds of electricity, only the peak or off-peak sorts are relevant."
    })
    Electricity,
    PeakElectricity,
    OffPeakElectricity,
    @Doc({"Exported electricity is whatever electricity is provided to the grid rather than being consumed in the house.",
        "This will be a non-positive quantity, because it is always a supply to the grid."
    })
    ExportedElectricity,
    Oil,
    BiomassPellets,
    BiomassWoodchip,
    BiomassWood,
    Photons,
    HouseCoal,
    @Doc({
        "Community heat is a special kind of fuel; because the NHM is a household model",
        "it only really understands energy from the point of view of a house. For district heat",
        "in houses, the house just consumes CommunityHeat, and has no understanding of the primary",
        "energy used to generate the community heat. This is analogous to how the fuel mix used to produce",
        "electricity is unknown to a house."
    })
    CommunityHeat;
}
