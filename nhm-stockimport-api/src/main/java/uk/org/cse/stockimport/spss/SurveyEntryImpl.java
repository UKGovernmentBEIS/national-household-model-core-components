package uk.org.cse.stockimport.spss;

import java.util.UUID;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class SurveyEntryImpl implements SurveyEntry {
	private String aacode;
	
	private String id;
	
    public SurveyEntryImpl() {
        this.id = UUID.randomUUID().toString();
    }

	@Override
	public String getAacode() {
		return aacode;
	}
	
	public void setAacode(final String aacode) {
		this.aacode = aacode;
		this.id = aacode + "_" + getClass().getSimpleName();
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

    /**
     * Return the id.
     * 
     * @return the id
     */
    @Override
	public String getId() {
        return id;
    }

    /**
     * Set the id.
     * 
     * @param id the id
     */
    public void setId(final String id) {
        this.id = id;
    }
}
