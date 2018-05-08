package uk.org.cse.nhm.energycalculator.api.types.steps;

import java.util.List;

public enum Period {
    MonthlyMean {
        @Override
        public double getMonth(List<Double> data, int month) {
            return getMonthImpl(data, month);
        }

        @Override
        public double getAnnual(List<Double> data) {
            return Period.sumAnnual(data) / 12d;
        }

        @Override
        public boolean isMonthly() { return true; }

        @Override
        public String toString() { return "Monthly, or averaged over all the months in the year."; }
    },
    MonthlySum {
        @Override
        public double getMonth(List<Double> data, int month) {
            return getMonthImpl(data, month);
        }

        @Override
        public double getAnnual(List<Double> data) {
            return Period.sumAnnual(data);
        }

        @Override
        public boolean isMonthly() { return true; }

        public String toString() { return "Monthly, or summed over all the months in the year."; }
    },
    Annual {
        @Override
        public double getMonth(List<Double> data, int month) {
            throw new UnsupportedOperationException("Can't get a monthly value for an annual quantity.");
        }

        @Override
        public double getAnnual(List<Double> data) {
            return data.get(0);
        }

        @Override
        public boolean isMonthly() { return false; }

        public String toString() { return "Annual."; }
    };

    public static double getMonthImpl(List<Double> data, int month) {
        return data.get(month - 1);
    }

    public static double sumAnnual(List<Double> data) {
        double result = 0d;

        for (double d : data) {
            result += d;
        }

        return result;
    }

    abstract public String toString();

    abstract public double getMonth(List<Double> data, int month);
    abstract public double getAnnual(List<Double> data);
    abstract public boolean isMonthly();
}