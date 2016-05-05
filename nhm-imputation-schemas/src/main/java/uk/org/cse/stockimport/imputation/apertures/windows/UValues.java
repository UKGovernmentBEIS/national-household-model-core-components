package uk.org.cse.stockimport.imputation.apertures.windows;

import java.util.EnumMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.hom.components.fabric.types.FrameType;
import uk.org.cse.nhm.hom.components.fabric.types.GlazingType;
import uk.org.cse.nhm.hom.components.fabric.types.WindowInsulationType;

import com.google.common.collect.ImmutableMap;

public class UValues implements IWindowUValues {
	private static final Logger log = LoggerFactory.getLogger(UValues.class);
	/**
		 * The R-Value for curtains
		 */
		private double curtainEffectFactor;
		
		private Map<FrameType, Double> singleGlazingByFrameType = new EnumMap<FrameType, Double>(FrameType.class);
		private Map<FrameType, Double> secondaryGlazingByFrameType = new EnumMap<FrameType, Double>(FrameType.class);
		
		
		private Map<WindowInsulationType, Map<FrameType, Double>> doubleGlazingByFrameAndInsulationType = 
				new EnumMap<WindowInsulationType, Map<FrameType, Double>>(WindowInsulationType.class);
		
		
		private Map<WindowInsulationType, Map<FrameType, Double>> tripleGlazingByFrameAndInsulationType = 
				new EnumMap<WindowInsulationType, Map<FrameType, Double>>(WindowInsulationType.class);
		
		public UValues(){
			this(true);
		}
		
		/* (non-Javadoc)
		 * @see uk.org.cse.stockimport.imputation.apertures.windows.IWindowUValues#addSingleGlazingUValue(uk.org.cse.nhm.hom.components.fabric.types.FrameType, java.lang.Double)
		 */
		@Override
		public void addSingleGlazingUValue(FrameType frameType, Double uValue){
			singleGlazingByFrameType.put(frameType, uValue);
		}
		
		/* (non-Javadoc)
		 * @see uk.org.cse.stockimport.imputation.apertures.windows.IWindowUValues#addSecondaryGlazingUValue(uk.org.cse.nhm.hom.components.fabric.types.FrameType, java.lang.Double)
		 */
		@Override
		public void addSecondaryGlazingUValue(FrameType frameType, Double uValue){
			secondaryGlazingByFrameType.put(frameType, uValue);
		}
		
		/* (non-Javadoc)
		 * @see uk.org.cse.stockimport.imputation.apertures.windows.IWindowUValues#addDoubleGlazing(uk.org.cse.nhm.hom.components.fabric.types.FrameType, uk.org.cse.nhm.hom.components.fabric.types.WindowInsulationType, java.lang.Double)
		 */
		@Override
		public void addDoubleGlazing(FrameType frameType, WindowInsulationType insulationType, Double uValue){
			
			Map<FrameType, Double> map = doubleGlazingByFrameAndInsulationType.get(insulationType);
			if (map == null){
				map = new EnumMap<FrameType, Double>(FrameType.class);
				doubleGlazingByFrameAndInsulationType.put(insulationType, map);
			}
			
			map.put(frameType, uValue);
		}
		
		/* (non-Javadoc)
		 * @see uk.org.cse.stockimport.imputation.apertures.windows.IWindowUValues#addTripleGlazing(uk.org.cse.nhm.hom.components.fabric.types.FrameType, uk.org.cse.nhm.hom.components.fabric.types.WindowInsulationType, java.lang.Double)
		 */
		@Override
		public void addTripleGlazing(FrameType frameType, WindowInsulationType insulationType, Double uValue){
			
			Map<FrameType, Double> map = tripleGlazingByFrameAndInsulationType.get(insulationType);
			if (map == null){
				map = new EnumMap<FrameType, Double>(FrameType.class);
				tripleGlazingByFrameAndInsulationType.put(insulationType, map);
			}
			
			map.put(frameType, uValue);
		}
		
		/* (non-Javadoc)
		 * @see uk.org.cse.stockimport.imputation.apertures.windows.IWindowUValues#setCurtainEffectFactor(double)
		 */
		@Override
		public void setCurtainEffectFactor(double curtainEffectFactor){
			this.curtainEffectFactor = curtainEffectFactor;
		}
		
		public UValues(boolean useRdSAPValues){
			if (useRdSAPValues){
				setCurtainEffectFactor(0.04d);
				
				singleGlazingByFrameType.putAll(ImmutableMap.of(
						FrameType.Wood,  4.8, 
						FrameType.Metal, 5.7,
						FrameType.uPVC,  4.8));
			
				secondaryGlazingByFrameType.putAll(ImmutableMap.of(
						FrameType.Wood, 2.4, 
						FrameType.uPVC, 2.4));
				
				Map<FrameType,  Double> map = new EnumMap<FrameType, Double>(FrameType.class);
				map.putAll(ImmutableMap.of(
						FrameType.Wood,  3.1,
						FrameType.Metal, 3.7,
						FrameType.uPVC,  3.1));
				doubleGlazingByFrameAndInsulationType.put(WindowInsulationType.Air, map);
				
				map = new EnumMap<FrameType, Double>(FrameType.class);
				map.putAll(ImmutableMap.of(
						FrameType.Wood,  2.7,
						FrameType.Metal, 3.3,
						FrameType.uPVC,  2.7));
				doubleGlazingByFrameAndInsulationType.put(WindowInsulationType.LowEHardCoat, map);
				
				map = new EnumMap<FrameType, Double>(FrameType.class);
				map.putAll(ImmutableMap.of(
						FrameType.Wood,  2.6,
						FrameType.Metal, 3.2,
						FrameType.uPVC,  2.6));
				doubleGlazingByFrameAndInsulationType.put(WindowInsulationType.LowESoftCoat, map);
				
				map = new EnumMap<FrameType, Double>(FrameType.class);
				map.putAll(ImmutableMap.of(
						FrameType.Wood,  2.4,
						FrameType.Metal, 2.9,
						FrameType.uPVC,  2.4));
				tripleGlazingByFrameAndInsulationType.put(WindowInsulationType.Air, map);
				
				map = new EnumMap<FrameType, Double>(FrameType.class);
				map.putAll(ImmutableMap.of(
						FrameType.Wood,  2.1,
						FrameType.Metal, 2.6,
						FrameType.uPVC,  2.1));
				tripleGlazingByFrameAndInsulationType.put(WindowInsulationType.LowEHardCoat, map);
				
				map = new EnumMap<FrameType, Double>(FrameType.class);
				map.putAll(ImmutableMap.of(
						FrameType.Wood,  2.0,
						FrameType.Metal, 2.5,
						FrameType.uPVC,  2.0));
				tripleGlazingByFrameAndInsulationType.put(WindowInsulationType.LowESoftCoat, map);
			}
		}
		
		/**
		 * @param uValue
		 * @return the U value passed in, with the curtain effect factor applied
		 */
		private double addCurtains(final double uValue) {
			return 1/((1/uValue) + curtainEffectFactor);
		}
		
		/* (non-Javadoc)
		 * @see uk.org.cse.stockimport.imputation.apertures.windows.IWindowUValues#getUValue(uk.org.cse.nhm.hom.components.fabric.types.FrameType, uk.org.cse.nhm.hom.components.fabric.types.GlazingType, uk.org.cse.nhm.hom.components.fabric.types.WindowInsulationType)
		 */
		@Override
		public double getUValue(FrameType frameType, GlazingType glazingType, WindowInsulationType insulationType) {
			final double result;
			
        if (frameType == null) {
            frameType = FrameType.uPVC;
        }

        if (glazingType == null) {
            glazingType = GlazingType.Single;
        }

        if (insulationType == null) {
            insulationType = WindowInsulationType.Air;
        }

			switch (glazingType) {
			case Single:
				// single glazing has no relevant insulation type
				result = singleGlazingByFrameType.get(frameType);
				break;
			case Double:
                result = doubleGlazingByFrameAndInsulationType.get(insulationType).get(frameType);
				break;
			case Triple:
				// double and triple glazing should have an insulation type.
                result = tripleGlazingByFrameAndInsulationType.get(insulationType).get(frameType);
				break;
			case Secondary:
				result = secondaryGlazingByFrameType.get(frameType);
				break;
			default:
				log.error("Unknown glazing type {}", glazingType);
				return 0;
			}
			
			return addCurtains(result);
		}
	}
