package uk.org.cse.nhm.language.definition.action.measure.heating;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;


@Bind("measure.combi-boiler")
@Doc("Installs a combi boiler")
@Unsuitability({
	"fuel is mains gas AND dwelling is off gas grid",
	"dwelling has existing community space heating",
	"dwelling has existing community hot water"
	})
public class XCombiBoilerMeasure extends XBoilerMeasure {
	public static final class P {
		public static final String storageVolume = "storageVolume";		
	}
	
	private XNumber storageVolume = XNumberConstant.create(0);

	@BindNamedArgument("storage-volume")
	@Prop(P.storageVolume)
	@Doc("The storage volume (if zero, the boiler is taken as an instantaneous combi)")
	public XNumber getStorageVolume() {
		return storageVolume;
	}
	public void setStorageVolume(final XNumber storageVolume) {
		this.storageVolume = storageVolume;
	}
}
