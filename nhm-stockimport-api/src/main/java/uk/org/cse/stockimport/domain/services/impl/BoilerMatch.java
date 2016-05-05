package uk.org.cse.stockimport.domain.services.impl;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.base.Optional;

import uk.org.cse.stockimport.domain.services.IBoilerMatch;
/**
 * BoilerMatch.
 *
 * @author richardt
 * @version $Id: cse-eclipse-codetemplates.xml 94 2010-09-30 15:39:21Z richardt $
 * @since 1.0
 */
@AutoProperty
public class BoilerMatch implements IBoilerMatch {
	private String surveyMake;
	private String surveyModel;
	
	private Optional<Integer> sedbukRow = Optional.absent();
	private Optional<String> sedbukBrand = Optional.absent();
	private Optional<String> sedbukModel = Optional.absent();
	private Optional<String> sedbukQualifier = Optional.absent();
	
	/**
	 * @return
	 * @see uk.org.cse.stockimport.domain.services.IBoilerMatch#getSurveyMake()
	 * @since 1.0
	 */
	@Override
	public String getSurveyMake() {
		return surveyMake;
	}
	
	/**
	 * TODO.
	 * 
	 * @param surveyMake
	 * @since 1.0 
	 */
	public void setSurveyMake(String surveyMake) {
		this.surveyMake = surveyMake;
	}
	@Override
	public String getSurveyModel() {
		return surveyModel;
	}
	/**
	 * TODO.
	 * 
	 * @param surveyModel
	 * @since 1.0
	 */
	public void setSurveyModel(String surveyModel) {
		this.surveyModel = surveyModel;
	}
	@Override
	public Optional<String> getSedbukBrand() {
		return sedbukBrand;
	}
	@Override
	public Optional<String> getSedbukModel() {
		return sedbukModel;
	}
	@Override
	public Optional<String> getSedbukQualifier() {
		return sedbukQualifier;
	}
	@Override
	public Optional<Integer> getSedbukRow() {
		return sedbukRow;
	}
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
	/**
	 * TODO.
	 * 
	 * @param sedbukRow
	 * @since 1.0
	 */
	public void setSedbukRow(Integer sedbukRow) {
		this.sedbukRow = Optional.of(sedbukRow);
	}
	/**
	 * TODO.
	 * 
	 * @param sedbukBrand
	 * @since 1.0
	 */
	public void setSedbukBrand(String sedbukBrand) {
		this.sedbukBrand = Optional.of(sedbukBrand);
	}
	
	/**
	 * TODO.
	 * 
	 * @param sedbukModel
	 * @since 1.0
	 */
	public void setSedbukModel(String sedbukModel) {
		this.sedbukModel = Optional.of(sedbukModel);
	}
	/**
	 * TODO.
	 * 
	 * @param sedbukQualifier
	 * @since 1.0
	 */
	public void setSedbukQualifier(String sedbukQualifier) {
		this.sedbukQualifier = Optional.of(sedbukQualifier);
	}
	
	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}

	@Override
	public boolean equals(Object other) {
		return Pojomatic.equals(this, other);
	}
}
