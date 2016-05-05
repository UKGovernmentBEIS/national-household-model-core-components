/**
<p>
  This package contains the definition of the API for the NHM's Energy
  calculator. It is a good place to start in understanding the
  high-level structure of the calculator;
</p>

<p>
  The calculator itself is specified by the {@link uk.org.cse.nhm.energycalculator.api.IEnergyCalculator} interface, but this
  only contains a single evaluate method. As that method's signature
  suggests, the real interface behaviour is defined on the two
  parameters:
  
  <ul>
    <li>{@link uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase}, which
    defines what the calculator expects to see from a house, and</li>
    <li>{@link uk.org.cse.nhm.energycalculator.api.IInternalParameters}, which
    define the conditions a house experiences from its surroundings
    (climate, and so on)</li>
  </ul>

  When the evaluate method is invoked, the calculator will collect the
  informative parts of the house which it is interested in by
  providing an {@link uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor} to the house case's #accept()
  method. The house case then presents its internals to the
  {@link uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor}, insulating the energy calculator from the house's
  structural details.
</p>
<p>
  The energy calculation has two distinct phases; in the first the
  calculator will determine the specific heat loss of the building
  from the heat loss information presented to the visitor. In the
  second, all of the {@link uk.org.cse.nhm.energycalculator.api.IEnergyTransducer}s presented to the visitor
  communicate through an {@link uk.org.cse.nhm.energycalculator.api.IEnergyState} to calculate the energy
  requirements of the house. For details on these phases, see the
  EnergyCalculator implementation.
</p>

 */
package uk.org.cse.nhm.energycalculator.api;

