package uk.org.cse.nhm.language.definition.action;

import uk.org.cse.nhm.language.definition.Doc;

@Doc({
	"Any function of time has a degree of predictability, which indicates what level of knowledge",
	"is needed to be able to forecast its future values.",
	"This categorical variable defines the different categories for which a prediction can have foresight.",
	"All functions of time have an argument which allows you to specify the foresight category required to be able to predict them.",
	"In addition, if you don't specify the foresight level required for a temporal function, the foresight level will be determined from the context",
	"in which the function is being used.",
	"To give an example, if a house's tariffs contain (a) a time-varying unit price, and (b) a price per unit of carbon emitted",
	"If the value is forecast with foresight of carbon factors, but not with tariffs, the carbon part of the tariff will be projected but the unit price will not."
})
public enum XForesightLevel {
	@Doc("Any function of time used in defining the weather will by default be predictable when allowing this kind of foresight.")
	Weather,
	@Doc("Any function of time used in defining the simulation carbon factors will by default by predictable when allowing this kind of foresight.")
	CarbonFactors,
	@Doc("Any function of time used in defining the energy calculator calibration rules will by default by predictable when allowing this kind of foresight.")
	Calibration,
	@Doc("Any function of time used in computing tariff levels will by default by predictable when allowing this kind of foresight.")
	Tariffs,
	
	@Doc("Any function of time which is not in one of the above categories will have this required foresight level by default.")
	Default,
	
	@Doc("A spare foresight level for your own user-defined behaviour. You can use these to make arbitrary parts of a timeseries predictable in different situations.")
	Alpha,
	@Doc("Another spare foresight level.")
	Beta,
	@Doc("Another spare foresight level.")
	Gamma,
	@Doc("Another spare foresight level.")
	Delta,
	
	@Doc("Anything requiring this level of foresight can never be predicted, no matter what happens.")
	Never,
	
	@Doc("Anything requiring this level of foresight can always be predicted, no matter what happens.")
	Always
}
