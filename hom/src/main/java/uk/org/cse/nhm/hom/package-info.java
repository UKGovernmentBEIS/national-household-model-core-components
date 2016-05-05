/**
 * The NHM house object model describes all the information about a house that we are able to extract
 * from the survey. The root element in the house object model is {@link uk.org.cse.nhm.hom.SurveyCase}, which
 * represents a single survey case.
 * <p>
 * {@link uk.org.cse.nhm.hom.SurveyCase} is actually just a container for several clusters of related information,
 * each of which has its own model object; these are
 * <ol>
 * 	<li> {@link uk.org.cse.nhm.hom.BasicCaseAttributes}, which gives the top-level summary information for a house </li>
 *  <li> {@link uk.org.cse.nhm.hom.people.People}, which enumerates the people in a house </li>
 *  <li> {@link uk.org.cse.hom.money.FinancialAttributes}, which details some financial information about a family in a house </li>
 *  <li> {@link uk.org.cse.nhm.hom.structure.StructureModel}, which describes the physical structure of a house.
 *  	This is where u-values and so on are stored
 *   </li>
 *  <li>{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel}, which describes the technologies and systems installed in a house.
 *  As the technology model is quite large and complicated, see {@link uk.org.cse.nhm.hom.emf.technologies} for an overview of this.</li>
 * </ol>
 */
package uk.org.cse.nhm.hom;

