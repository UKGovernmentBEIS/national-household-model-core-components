package uk.org.cse.nhm.language.definition.action.measure.insulation;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("measure.install-draught-proofing")
@Doc( { "Set the proportion of openings on the dwelling which have draught-stripping installed."
})
@Unsuitability({
	"there is already that proportion of greater of draught proofing installed"
})
public class XDraughtProofingMeasure extends XMeasure {
    private double proportion;
	private XNumber capex;
    
	public static final class P {
		public static final String capex = "capex";
		public static final String proportion = "proportion";
	}

	/**
     * Return the proportion of draught stripping-covered openings to be installed.
     */
    @BindNamedArgument("proportion")
    @Doc("The new proportion of openings to have draught stripping installed .")
    @Prop(P.proportion)
    public double getProportion() {
        return proportion;
    }

    /**
     * Set the proportion to install.
     */
    public void setProportion(final double proportion) {
        this.proportion = proportion;
    }

	@BindNamedArgument("capex")
	@Prop(P.capex)
	@Doc("The capital cost function for installing draught proofing.")
	public XNumber getCapex() {
		return capex;
	}
	
	public void setCapex(final XNumber capex) {
		this.capex = capex;
	}
}
