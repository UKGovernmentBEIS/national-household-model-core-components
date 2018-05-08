package uk.org.cse.stockimport.domain.geometry.impl;

import java.util.Collections;
import java.util.List;

import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.stockimport.domain.IBasicDTO;

/**
 * AbsElementDTO.
 *
 * @author richardt
 * @version $Id: AbsDTO.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
@AutoProperty
public abstract class AbsDTO implements IBasicDTO {

    private String aaCode;

    /**
     * @return @see
     * uk.org.cse.stockimport.domain.geometry.IElementDTO#getAacode()
     */
    @Override
    public String getAacode() {
        return aaCode;
    }

    @Override
    public void setAacode(final String aaCode) {
        this.aaCode = aaCode;
    }

    @Override
    public List<String> validate() {
        return Collections.emptyList();
    }
}
