(example
 caption:"Total energy usage"
 description:"This example shows a simple aggregate report, which sums
		up the energy usage of the whole stock.  It aggregates over the whoel housing stock, and then applies the sum aggregator to the
		energy use. This will produce a time series of the total
		energy use for all the houses in the simulation."
 additional:"<formalpara xmlns=\"http://docbook.org/ns/docbook\">
			<title>
				Sample report output</title>
			<para>
				This is how the table produced from the example report would look.
			</para>
		</formalpara>
		<informaltable xmlns=\"http://docbook.org/ns/docbook\">
			<tgroup cols=\"2\">
				<thead>
					<row>
						<entry>
							Date</entry>
						<entry>
							sum of energy use for (all-houses)</entry>
					</row>
				</thead>
				<tbody>
					<row>
						<entry>
							01/01/2020</entry>
						<entry>
							123456</entry>
					</row>
					<row>
						<entry>
							01/01/2021</entry>
						<entry>
							234567</entry>
					</row>
				</tbody>
			</tgroup>
		</informaltable>"

 (aggregate name:count-houses
				   over: (all-houses)
				   (aggregate.sum (house.energy-use))))
