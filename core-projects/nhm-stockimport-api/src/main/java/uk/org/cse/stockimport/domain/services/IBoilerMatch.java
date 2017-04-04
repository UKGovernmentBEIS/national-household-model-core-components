package uk.org.cse.stockimport.domain.services;

import com.google.common.base.Optional;

/**
 * @since 1.0
 */
public interface IBoilerMatch {
    /**
     * @since 1.0
     */
    public String getSurveyMake();
    /**
     * @since 1.0
     */
    public String getSurveyModel();
	
    /**
     * @since 1.0
     */
    public Optional<String> getSedbukBrand();
    /**
     * @since 1.0
     */
    public Optional<String> getSedbukModel();
	/**
     * @since 1.0
     */
    /**
     * @since 1.0
     */
    public Optional<String> getSedbukQualifier();
    /**
     * @since 1.0
     */
    public Optional<Integer> getSedbukRow();
}
