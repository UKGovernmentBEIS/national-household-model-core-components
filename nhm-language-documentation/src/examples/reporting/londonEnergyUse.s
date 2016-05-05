(example caption:"Energy use in london" 
		 description:"This example shows a simple aggregate,
 which sums up the energy usage of the whole stock.  It filters the stock down to houses in London, and then applies the sum aggregator to
 the energy use. This will produce a time series of the total energy
 use for all the houses whose region is London."

		 additional:"<formalpara xmlns=\"http://docbook.org/ns/docbook\">
			<title>
				Sample report output</title>
			<para>
				This is how the table produced from the example report would look.
			</para>
		</formalpara>
		<informaltable xmlns=\"http://docbook.org/ns/docbook\">
			<tgroup cols=\"3\">
				<thead>
					<row>
						<entry>Date</entry>
						<entry>group</entry>						
						<entry>sum-of-energy-use</entry>
					</row>
				</thead>
				<tbody>
					<row>
						<entry>01/01/2020</entry>
						<entry>london-region</entry>
						<entry>23456</entry>
					</row>
					<row>
						<entry>01/01/2021</entry>
						<entry>london-region</entry>
						<entry>34567</entry>
					</row>
				</tbody>
			</tgroup>
		</informaltable>"

		 (aggregate name: total-energy-use
					   	over: (filter 
						   	name:london-region
							(house.region-is London))
						   (aggregate.sum (house.energy-use))))
