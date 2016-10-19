package uk.org.cse.nhm.reporting.standard;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor.Type;

public class ZippingStandaloneFileStreamFactory extends ZippingFileStreamFactory {

	@Override
	protected List<CompletedOutput> filter(List<CompletedOutput> outputs) {
		Builder<CompletedOutput> builder = ImmutableList.builder();
		
		for (CompletedOutput o : outputs) {
            if (o.getDescriptor() != null && (o.getDescriptor().getType() == Type.Chart)) {
				// Exclude charts
			} else if (o.getName().endsWith(".jsonp")) {
				// Exclude .jsonp files (which are only useful in the web interface).
			} else {
				builder.add(o);
			}
		}
		
		return builder.build();
	}
}
