package uk.org.cse.nhm.energycalculator.api.types.steps;

public enum Units {
    Unknown(Unit.Unknown),
    Dimensionless(Unit.Dimensionless),
    DimensionlessProportion(Unit.DimensionlessProportion),
    Count(Unit.Count),
    Watt(Unit.Watts),
    MetreCubed_per_Hour(Unit.MetresCubed_per_Hour),
    Watt_per_Kelvin(Unit.Watts_per_Kelvin),
    Watt_per_MetreSquared_Kelvin(Unit.Watts_per_MetreSquared_Kelvin),
    AirChange_per_Hour(Unit.AirChanges_per_Hour),
    MetreSquared(Unit.MetresSquared),
    MetreCubed(Unit.MetresCubed),
    Metre_per_Second(Unit.Metres_per_Second),
    Kilo_Joule_per_MetreSquared_Kelvin(Unit.Kilo_Joules_per_MetreSquared_Kelvin),
    Litre_per_Day(Unit.Litres_per_Day),
    Centigrade(Unit.Centigrade),
    PoundSterling(Unit.PoundSterling),
    Day_per_Month(Unit.Days, Unit.Days_per_Month) {
        @Override
        public double convert(double nhmValue, Integer daysInMonth) {
            return nhmValue * daysInMonth;
        }
    },
    Kilo_Watt_Hour_per_Day(Unit.Watts, Unit.Kilo_Watt_Hours_per_Day) {
        @Override
        public double convert(double nhmValue, Integer daysInMonth) {
            return nhmValue * 24 / 1000;
        }
    },
    Kilo_Watt_Hour_per_Month(Unit.Watts, Unit.Kilo_Watt_Hours_per_Month) {
        @Override
        public double convert(double nhmValue, Integer daysInMonth) {
            return nhmValue * daysInMonth * 24 / 1000;
        }
    },
    Kilo_Watt_Hour_per_Litre_per_Day(Unit.Watts_per_Litre, Unit.Kilo_Watt_Hours_per_Litre_per_Day) {
        @Override
        public double convert(double nhmValue, Integer daysInMonth) {
            return nhmValue * 24 / 1000;
        }
    },
    Kilo_Watt_Hour_per_MetreSquared_per_Month(Unit.Watts_per_MetreSquared, Unit.Kilo_Watt_Hours_per_MetreSquared_per_Month) {
        @Override
        public double convert(double nhmValue, Integer daysInMonth) {
            return nhmValue * daysInMonth * 24 / 1000;
        }
    };

    private final Unit nhm;
    private final Unit sap;

    Units(Unit both) {
        this(both, both);
    }

    Units(Unit nhm, Unit sap) {
        this.nhm = nhm;
        this.sap = sap;
    }

    public double convert(double nhmValue, Integer daysInMonth) {
        return nhmValue;
    }

    @Override
    public String toString() {
        if (sap == nhm) {
            return sap.toString() + ".";
        } else {
            return nhm.toString() + " inside the NHM, converted to " + sap.toString() + ".";
        }
    }

    public enum Unit {
        Unknown,
        Dimensionless,
        DimensionlessProportion,
        Count,
        Boolean,
        Radians,
        Metres,
        MetresSquared,
        MetresCubed,
        Second,
        Hours,
        Days,
        Months,
        Watts,
        Centigrade,
        Kelvin,
        Litres,
        PoundSterling,
        AirChanges,
        Days_per_Month,
        MetresCubed_per_Hour,
        AirChanges_per_Hour,
        Watts_per_MetreSquared,
        Watts_per_Person,
        Watts_per_Kelvin,
        Watts_per_MetreSquared_Kelvin,
        Kilo_Watt_Hours_per_Day,
        Kilo_Watt_Hours_per_MetreSquared_per_Month,
        Kilo_Watt_Hours_per_Month,
        Metres_per_Second,
        Litres_per_Day,
        Watts_per_Litre,
        Kilo_Watt_Hours_per_Litre_per_Day,
        Kilo_Joules_per_MetreSquared_Kelvin,;

        @Override
        public String toString() {
            return name().replace('_', ' ');
        }
    }
}
