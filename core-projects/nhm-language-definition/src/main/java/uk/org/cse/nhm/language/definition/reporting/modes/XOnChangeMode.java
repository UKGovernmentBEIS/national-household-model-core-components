package uk.org.cse.nhm.language.definition.reporting.modes;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;

@Doc("Report mode which creates records data every time a change is observed in the simulation.")

@Bind("mode.on-change")
public class XOnChangeMode extends XReportMode {
}
