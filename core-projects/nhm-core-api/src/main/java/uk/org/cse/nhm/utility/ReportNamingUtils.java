package uk.org.cse.nhm.utility;

import java.util.regex.Pattern;

import org.joda.time.DateTime;

/**
 * Utility class for creating names for reports
 *
 * @author richardt
 * @since 3.6.2
 */
public class ReportNamingUtils {

    public static final String NAME_SEPERATOR = "_";
    public static final Pattern ILLEGAL_CHARS = Pattern.compile("[\\/:*?\"<>|&£$%]", Pattern.CASE_INSENSITIVE);
    public static final String SPACE_CHAR = " ";
    public static final String CHAR_REPLACEMENT = "";
    public static final String ZIPFILE_EXTENSION = ".zip";

    public static final String getReportFileName(
            final String scenarioName,
            final String filePrefix,
            final String fileExtension,
            final String initiatingUser,
            final DateTime timeInitiated
    ) {

        final String nameWithoutIllegalChars = ILLEGAL_CHARS.matcher(scenarioName).replaceAll(CHAR_REPLACEMENT);
        final String nameWithoutSpacesOrIllegalChars = nameWithoutIllegalChars.replaceAll(SPACE_CHAR, NAME_SEPERATOR);

        final StringBuilder reportName = new StringBuilder()
                .append(filePrefix)
                .append(nameWithoutSpacesOrIllegalChars).append(NAME_SEPERATOR)
                .append(timeInitiated.getYear()).append(NAME_SEPERATOR)
                .append(timeInitiated.getMonthOfYear()).append(NAME_SEPERATOR)
                .append(timeInitiated.getDayOfMonth()).append(NAME_SEPERATOR)
                .append(timeInitiated.getHourOfDay()).append(NAME_SEPERATOR)
                .append(timeInitiated.getMinuteOfHour()).append(NAME_SEPERATOR)
                .append(timeInitiated.getSecondOfMinute()).append(NAME_SEPERATOR)
                .append(initiatingUser)
                .append(fileExtension);

        return reportName.toString();
    }
}
