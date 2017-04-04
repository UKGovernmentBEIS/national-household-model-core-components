package uk.org.cse.nhm.reporting.standard.jsonp;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.org.cse.nhm.reporting.report.IReportOutput;

public class JSONPOutputUtility {
	public static String asJSONP(IReportOutput report, String data) {
		return String.format("nhmChartDataLoaded(\"%s\", %s);", report.getPath(), data);
	}

	public static void writeAsJSONP(IReportOutput report, Object data, OutputStream out) throws IOException {
		PrintWriter writer = new PrintWriter(out);
		writer.write(asJSONP(report, new ObjectMapper().writeValueAsString(data)));
		writer.flush();
	}
}
