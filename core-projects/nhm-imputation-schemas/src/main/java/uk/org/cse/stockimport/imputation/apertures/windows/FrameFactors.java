package uk.org.cse.stockimport.imputation.apertures.windows;

import java.util.EnumMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.energycalculator.api.types.FrameType;

/**
 * Frame factor lookup, as defined by RD-SAP.
 * @since 1.0
 */
public class FrameFactors implements IWindowFrameFactor {
	private final Map<FrameType, Double> values = new EnumMap<FrameType, Double>(FrameType.class);
	
	public FrameFactors(){
		values.putAll(ImmutableMap.of(
				FrameType.Wood, 0.7,
				FrameType.Metal, 0.8,
				FrameType.uPVC, 0.7));
	}
	
	/* (non-Javadoc)
	 * @see uk.org.cse.stockimport.imputation.apertures.windows.IWindowFrameFactor#getFrameFactor(uk.org.cse.nhm.energycalculator.api.types.FrameType)
	 */
    @Override
	public double getFrameFactor(final FrameType frameType) {
		return values.get(frameType);
	}
    
    /* (non-Javadoc)
	 * @see uk.org.cse.stockimport.imputation.apertures.windows.IWindowFrameFactor#setFrameFactor(uk.org.cse.nhm.energycalculator.api.types.FrameType, java.lang.Double)
	 */
    @Override
	public void setFrameFactor(final FrameType frameType, final Double factor){
    	values.put(frameType, factor);
    }
}
