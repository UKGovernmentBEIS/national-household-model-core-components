(example
 caption:"Probing the district heating measure"
 description:"In this example we use a measure probe to investigate the behaviour of a district heating measure.
						Each row in the probe report generated will contain a column containing the size of the measure, where relevant.
						Note that the report will contain a size column for the house after the measure is applied but also before;
						the sizing result before will either be n/a, or the result of any sizing calculation which was performed
						before the consideration of district heating (if, for example, DH was being used in a package with a combi boiler
						or some other bizarre combination)."

 (probe name:"Probe district heating power capacity"
		capture: (size.kw)
			   
		(measure.district-heating)))