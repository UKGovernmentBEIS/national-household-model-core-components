package uk.org.cse.stockimport.domain;

import java.util.List;

import uk.org.cse.stockimport.domain.schema.DTOField;

/**
 * IBasicDTO.
 *
 * @author richardt
 * @version $Id: IBasicDTO.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public interface IBasicDTO {
    public static final String AACODE = "aacode";
	/**
     * @since 1.0
     */
	@DTOField(AACODE)
    String getAacode();
    /**
     * @since 2.0
     */
    void setAacode(String aacode);
    
    List<String> validate();
}
