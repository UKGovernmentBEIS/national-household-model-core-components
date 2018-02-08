nhm-health
==================
This module is designed to calculate the health (QALYs) and cost impacts of applying retrofits to English homes. It will form part of the National Household Model (NHM) as an interface but will still be able to run as a stand alone application.

The code produces 4 csv files in the build directory: exposures.csv, qalys.cav, morbqalys.csv and costs.csv

what does it do?
================
We (CSE) do not exactly know about the innards of this; consult Ian Hamilton for the details.

The interface to the NHM is roughly speaking:

- There is an implementation of IHealthModule, which contains a couple of functions
- The important function calculates the health impacts of changing a house in some way
- Changes to a house are characterised by a few things:

- How the house's thermostat temperature changes (before, after)
- How the house's permeability changes (before, after)
- Other stuff about the people in the house

This function is connected up to a term in the NHM language.
The user-facing twiddly bits are temperature and permeability. 
The user can supply whatever inputs they like for this.

Suggested temperature inputs can be computed by the 'standardised internal temperature'.

This predicts a house's thermostat temperature using a regression of some sort.
The regression is defined in terms of a thing called the e-value, which is just some kind of heat loss divided by some efficiency - the e-value lives inside the health module and is not interesting to the rest of the model.

The rest of the model only uses the getInternalTemperature method in IHealthModule.

You could use this in the scenario language to supply before & after temperatures to the health impact calculator.
You could equally use it to set a realistic internal temperature, to model comfort-taking.
