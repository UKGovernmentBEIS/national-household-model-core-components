package uk.org.cse.nhm.reporting.standard.flat;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ReportFormats {
	/**
     * Returns date formatted as yyyy/mm/dd
     * @param date
     */
    public static String getDateAsDay(DateTime date) {
    	 DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy/MM/dd");
         return fmt.print(date);
   }

    /**
     * Returns consistently formatted date, currently just the year (yyyy).
     * 
     * @param date
     */
    public static String getDateAsYear(DateTime date) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy");
        return fmt.print(date);
    }
}
