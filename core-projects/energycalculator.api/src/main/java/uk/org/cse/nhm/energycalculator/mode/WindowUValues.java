package uk.org.cse.nhm.energycalculator.mode;

import java.util.EnumMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.WindowGlazingAirGap;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;

/**
 * This contains the default SAP 2012 u-values for windows from Table 6e,
 * including the curtain adjustment factor.
 *
 * We do not implement any of the footnotes.
 *
 * Separate instances are used by the windows imputation in the stock import
 * (which may override these values), and by the energy calculator's SAP
 * u-values lookup.
 */
public class WindowUValues {

    private static final Logger log = LoggerFactory.getLogger(WindowUValues.class);
    /**
     * The R-Value for curtains
     */
    private double curtainEffectFactor;

    private Map<FrameType, Double> singleGlazingByFrameType = new EnumMap<FrameType, Double>(FrameType.class);
    private Map<FrameType, Double> secondaryGlazingByFrameType = new EnumMap<FrameType, Double>(FrameType.class);

    private Map<WindowInsulationType, Map<FrameType, Map<WindowGlazingAirGap, Double>>> doubleGlazingByFrameAndInsulationType
            = new EnumMap<WindowInsulationType, Map<FrameType, Map<WindowGlazingAirGap, Double>>>(WindowInsulationType.class);

    private Map<WindowInsulationType, Map<FrameType, Map<WindowGlazingAirGap, Double>>> tripleGlazingByFrameAndInsulationType
            = new EnumMap<WindowInsulationType, Map<FrameType, Map<WindowGlazingAirGap, Double>>>(WindowInsulationType.class);

    public WindowUValues() {
        this(true);
    }

    /* (non-Javadoc)
		 * @see uk.org.cse.stockimport.imputation.apertures.windows.IWindowUValues#addSingleGlazingUValue(uk.org.cse.nhm.energycalculator.api.types.FrameType, java.lang.Double)
     */
    public void addSingleGlazingUValue(FrameType frameType, Double uValue) {
        singleGlazingByFrameType.put(frameType, uValue);
    }

    /* (non-Javadoc)
		 * @see uk.org.cse.stockimport.imputation.apertures.windows.IWindowUValues#addSecondaryGlazingUValue(uk.org.cse.nhm.energycalculator.api.types.FrameType, java.lang.Double)
     */
    public void addSecondaryGlazingUValue(FrameType frameType, Double uValue) {
        secondaryGlazingByFrameType.put(frameType, uValue);
    }

    /* (non-Javadoc)
		 * @see uk.org.cse.stockimport.imputation.apertures.windows.IWindowUValues#addDoubleGlazing(uk.org.cse.nhm.energycalculator.api.types.FrameType, uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType, java.lang.Double)
     */
    public void addDoubleGlazing(FrameType frameType, WindowInsulationType insulationType, Double uValue, WindowGlazingAirGap airGap) {

        Map<FrameType, Map<WindowGlazingAirGap, Double>> map = doubleGlazingByFrameAndInsulationType.get(insulationType);
        if (map == null) {
            map = new EnumMap<FrameType, Map<WindowGlazingAirGap, Double>>(FrameType.class);
            doubleGlazingByFrameAndInsulationType.put(insulationType, map);
        }
        Map<WindowGlazingAirGap, Double> uValues = new EnumMap<>(WindowGlazingAirGap.class);
        uValues.put(airGap, uValue);

        map.put(frameType, ImmutableMap.of(airGap, uValue));
    }

    /* (non-Javadoc)
		 * @see uk.org.cse.stockimport.imputation.apertures.windows.IWindowUValues#addTripleGlazing(uk.org.cse.nhm.energycalculator.api.types.FrameType, uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType, java.lang.Double)
     */
    public void addTripleGlazing(FrameType frameType, WindowInsulationType insulationType, Double uValue, WindowGlazingAirGap airGap) {

        Map<FrameType, Map<WindowGlazingAirGap, Double>> map = tripleGlazingByFrameAndInsulationType.get(insulationType);
        if (map == null) {
            map = new EnumMap<FrameType, Map<WindowGlazingAirGap, Double>>(FrameType.class);
            tripleGlazingByFrameAndInsulationType.put(insulationType, map);
        }
        Map<WindowGlazingAirGap, Double> uValues = new EnumMap<>(WindowGlazingAirGap.class);
        uValues.put(airGap, uValue);

        map.put(frameType, uValues);
    }

    /* (non-Javadoc)
		 * @see uk.org.cse.stockimport.imputation.apertures.windows.IWindowUValues#setCurtainEffectFactor(double)
     */
    public void setCurtainEffectFactor(double curtainEffectFactor) {
        this.curtainEffectFactor = curtainEffectFactor;
    }

    public WindowUValues(boolean useRdSAPValues) {
        if (useRdSAPValues) {
            setCurtainEffectFactor(0.04d);
            buildSingleGlazingSAPDictionaries();
            buildSecondaryGlazingSAPDictionaries();
            buildDoubleGlazingSAPDictionairies();
            buildTripleGlazingSAPDictionaries();
        }
    }

    protected void buildSingleGlazingSAPDictionaries() {
        singleGlazingByFrameType.putAll(ImmutableMap.of(
                FrameType.Wood, 4.8,
                FrameType.Metal, 5.7,
                FrameType.uPVC, 4.8));
    }

    protected void buildSecondaryGlazingSAPDictionaries() {
        secondaryGlazingByFrameType.putAll(ImmutableMap.of(
                FrameType.Wood, 2.4,
                FrameType.uPVC, 2.4));
    }

    protected void buildDoubleGlazingSAPDictionairies() {
        Map<FrameType, Map<WindowGlazingAirGap, Double>> map = new EnumMap<FrameType, Map<WindowGlazingAirGap, Double>>(FrameType.class);

        //Boring old air
        map.putAll(ImmutableMap.of(
                FrameType.Wood, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 3.1, WindowGlazingAirGap.gapOf12mm, 2.8, WindowGlazingAirGap.gapOf16mmPlus, 2.7),
                FrameType.Metal, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 3.7, WindowGlazingAirGap.gapOf12mm, 3.4, WindowGlazingAirGap.gapOf16mmPlus, 3.3),
                FrameType.uPVC, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 3.1, WindowGlazingAirGap.gapOf12mm, 2.8, WindowGlazingAirGap.gapOf16mmPlus, 2.7)));

        doubleGlazingByFrameAndInsulationType.put(WindowInsulationType.Air, map);

        map = new EnumMap<FrameType, Map<WindowGlazingAirGap, Double>>(FrameType.class);
        map.putAll(ImmutableMap.of(
                FrameType.Wood, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.7, WindowGlazingAirGap.gapOf12mm, 2.2, WindowGlazingAirGap.gapOf16mmPlus, 2.0),
                FrameType.Metal, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 3.3, WindowGlazingAirGap.gapOf12mm, 2.7, WindowGlazingAirGap.gapOf16mmPlus, 2.5),
                FrameType.uPVC, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.7, WindowGlazingAirGap.gapOf12mm, 2.2, WindowGlazingAirGap.gapOf16mmPlus, 2.0)));
        doubleGlazingByFrameAndInsulationType.put(WindowInsulationType.LowEHardCoat, map);

        map = new EnumMap<FrameType, Map<WindowGlazingAirGap, Double>>(FrameType.class);
        map.putAll(ImmutableMap.of(
                FrameType.Wood, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.6, WindowGlazingAirGap.gapOf12mm, 2.1, WindowGlazingAirGap.gapOf16mmPlus, 1.9),
                FrameType.Metal, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 3.2, WindowGlazingAirGap.gapOf12mm, 2.6, WindowGlazingAirGap.gapOf16mmPlus, 2.4),
                FrameType.uPVC, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.6, WindowGlazingAirGap.gapOf12mm, 2.1, WindowGlazingAirGap.gapOf16mmPlus, 1.9)));
        doubleGlazingByFrameAndInsulationType.put(WindowInsulationType.LowESoftCoat, map);

        //Argon
        map = new EnumMap<FrameType, Map<WindowGlazingAirGap, Double>>(FrameType.class);
        map.putAll(ImmutableMap.of(
                FrameType.Wood, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.9, WindowGlazingAirGap.gapOf12mm, 2.7, WindowGlazingAirGap.gapOf16mmPlus, 2.6),
                FrameType.Metal, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 3.5, WindowGlazingAirGap.gapOf12mm, 3.3, WindowGlazingAirGap.gapOf16mmPlus, 3.2),
                FrameType.uPVC, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.9, WindowGlazingAirGap.gapOf12mm, 2.7, WindowGlazingAirGap.gapOf16mmPlus, 2.6)));
        doubleGlazingByFrameAndInsulationType.put(WindowInsulationType.Argon, map);

        map = new EnumMap<FrameType, Map<WindowGlazingAirGap, Double>>(FrameType.class);
        map.putAll(ImmutableMap.of(
                FrameType.Wood, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.5, WindowGlazingAirGap.gapOf12mm, 2.1, WindowGlazingAirGap.gapOf16mmPlus, 2.9),
                FrameType.Metal, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 3.0, WindowGlazingAirGap.gapOf12mm, 2.6, WindowGlazingAirGap.gapOf16mmPlus, 2.5),
                FrameType.uPVC, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.5, WindowGlazingAirGap.gapOf12mm, 2.1, WindowGlazingAirGap.gapOf16mmPlus, 2.9)));
        doubleGlazingByFrameAndInsulationType.put(WindowInsulationType.ArgonLowEHardCoat, map);

        map = new EnumMap<FrameType, Map<WindowGlazingAirGap, Double>>(FrameType.class);
        map.putAll(ImmutableMap.of(
                FrameType.Wood, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.3, WindowGlazingAirGap.gapOf12mm, 1.9, WindowGlazingAirGap.gapOf16mmPlus, 1.8),
                FrameType.Metal, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.9, WindowGlazingAirGap.gapOf12mm, 2.4, WindowGlazingAirGap.gapOf16mmPlus, 2.3),
                FrameType.uPVC, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.3, WindowGlazingAirGap.gapOf12mm, 1.9, WindowGlazingAirGap.gapOf16mmPlus, 1.8)));
        doubleGlazingByFrameAndInsulationType.put(WindowInsulationType.ArgonLowESoftCoat, map);
    }

    protected void buildTripleGlazingSAPDictionaries() {
        Map<FrameType, Map<WindowGlazingAirGap, Double>> map = new EnumMap<FrameType, Map<WindowGlazingAirGap, Double>>(FrameType.class);

        //Boring old Air
        map.putAll(ImmutableMap.of(
                FrameType.Wood, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.4, WindowGlazingAirGap.gapOf12mm, 2.1, WindowGlazingAirGap.gapOf16mmPlus, 2.0),
                FrameType.Metal, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.9, WindowGlazingAirGap.gapOf12mm, 2.6, WindowGlazingAirGap.gapOf16mmPlus, 2.5),
                FrameType.uPVC, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.4, WindowGlazingAirGap.gapOf12mm, 2.1, WindowGlazingAirGap.gapOf16mmPlus, 2.0)));
        tripleGlazingByFrameAndInsulationType.put(WindowInsulationType.Air, map);

        map = new EnumMap<FrameType, Map<WindowGlazingAirGap, Double>>(FrameType.class);
        map.putAll(ImmutableMap.of(
                FrameType.Wood, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.1, WindowGlazingAirGap.gapOf12mm, 1.7, WindowGlazingAirGap.gapOf16mmPlus, 1.6),
                FrameType.Metal, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.6, WindowGlazingAirGap.gapOf12mm, 2.1, WindowGlazingAirGap.gapOf16mmPlus, 2.0),
                FrameType.uPVC, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.1, WindowGlazingAirGap.gapOf12mm, 1.7, WindowGlazingAirGap.gapOf16mmPlus, 1.6)));
        tripleGlazingByFrameAndInsulationType.put(WindowInsulationType.LowEHardCoat, map);

        map = new EnumMap<FrameType, Map<WindowGlazingAirGap, Double>>(FrameType.class);
        map.putAll(ImmutableMap.of(
                FrameType.Wood, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.0, WindowGlazingAirGap.gapOf12mm, 1.6, WindowGlazingAirGap.gapOf16mmPlus, 1.5),
                FrameType.Metal, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.5, WindowGlazingAirGap.gapOf12mm, 2.0, WindowGlazingAirGap.gapOf16mmPlus, 1.9),
                FrameType.uPVC, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.0, WindowGlazingAirGap.gapOf12mm, 1.6, WindowGlazingAirGap.gapOf16mmPlus, 1.5)));
        tripleGlazingByFrameAndInsulationType.put(WindowInsulationType.LowESoftCoat, map);

        //Argumentative Argon
        map = new EnumMap<FrameType, Map<WindowGlazingAirGap, Double>>(FrameType.class);
        map.putAll(ImmutableMap.of(
                FrameType.Wood, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.2, WindowGlazingAirGap.gapOf12mm, 2.0, WindowGlazingAirGap.gapOf16mmPlus, 1.9),
                FrameType.Metal, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.8, WindowGlazingAirGap.gapOf12mm, 2.5, WindowGlazingAirGap.gapOf16mmPlus, 2.4),
                FrameType.uPVC, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.2, WindowGlazingAirGap.gapOf12mm, 2.0, WindowGlazingAirGap.gapOf16mmPlus, 1.9)));
        tripleGlazingByFrameAndInsulationType.put(WindowInsulationType.Argon, map);

        map = new EnumMap<FrameType, Map<WindowGlazingAirGap, Double>>(FrameType.class);
        map.putAll(ImmutableMap.of(
                FrameType.Wood, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 1.9, WindowGlazingAirGap.gapOf12mm, 1.6, WindowGlazingAirGap.gapOf16mmPlus, 1.5),
                FrameType.Metal, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.3, WindowGlazingAirGap.gapOf12mm, 2.0, WindowGlazingAirGap.gapOf16mmPlus, 1.9),
                FrameType.uPVC, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 1.9, WindowGlazingAirGap.gapOf12mm, 1.6, WindowGlazingAirGap.gapOf16mmPlus, 1.5)));
        tripleGlazingByFrameAndInsulationType.put(WindowInsulationType.ArgonLowEHardCoat, map);

        map = new EnumMap<FrameType, Map<WindowGlazingAirGap, Double>>(FrameType.class);
        map.putAll(ImmutableMap.of(
                FrameType.Wood, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 1.8, WindowGlazingAirGap.gapOf12mm, 1.5, WindowGlazingAirGap.gapOf16mmPlus, 1.4),
                FrameType.Metal, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 2.2, WindowGlazingAirGap.gapOf12mm, 1.9, WindowGlazingAirGap.gapOf16mmPlus, 1.8),
                FrameType.uPVC, ImmutableMap.of(WindowGlazingAirGap.gapOf6mm, 1.8, WindowGlazingAirGap.gapOf12mm, 1.5, WindowGlazingAirGap.gapOf16mmPlus, 1.4)));
        tripleGlazingByFrameAndInsulationType.put(WindowInsulationType.ArgonLowESoftCoat, map);
    }

    /**
     * @param uValue
     * @return the U value passed in, with the curtain effect factor applied
     */
    private double addCurtains(final double uValue) {
        return 1 / ((1 / uValue) + curtainEffectFactor);
    }

    /* (non-Javadoc)
		 * @see uk.org.cse.stockimport.imputation.apertures.windows.IWindowUValues#getUValue(uk.org.cse.nhm.energycalculator.api.types.FrameType, uk.org.cse.nhm.energycalculator.api.types.GlazingType, uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType)
     */
    public double getUValue(FrameType frameType, GlazingType glazingType, WindowInsulationType insulationType, WindowGlazingAirGap airGap) {
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

        if (airGap == null) {
            airGap = WindowGlazingAirGap.gapOf6mm;
        }

        switch (glazingType) {
            case Single:
                // single glazing has no relevant insulation type
                result = singleGlazingByFrameType.get(frameType);
                break;
            case Double:
                result = doubleGlazingByFrameAndInsulationType.get(insulationType).get(frameType).get(airGap);
                break;
            case Triple:
                // double and triple glazing should have an insulation type.
                result = tripleGlazingByFrameAndInsulationType.get(insulationType).get(frameType).get(airGap);
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
