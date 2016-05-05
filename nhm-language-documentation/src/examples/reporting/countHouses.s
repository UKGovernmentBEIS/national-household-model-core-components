(example
 caption:"Counting all the houses"
 description:"This example shows a very simple aggregate, which
		just counts all the houses in the simulation. It applies to all the houses in the stock. It uses the count aggregator to produce the output value. This will produce a time series of the number of houses in the
		simulation."

 additional:"<additional>
		<formalpara xmlns=\"http://docbook.org/ns/docbook\">
			<title>Sample report output</title>
			<para>
				This is how the table produced from the example report would
				look.
			</para>
		</formalpara>
		<informaltable xmlns=\"http://docbook.org/ns/docbook\">
			<tgroup cols=\"2\">
				<thead>
					<row>
						<entry>
							Date</entry>
						<entry>
							count of (all-houses)</entry>
					</row>
				</thead>
				<tbody>
					<row>
						<entry>
							01/01/2020</entry>
						<entry>
							100000</entry>
					</row>
					<row>
						<entry>
							01/01/2021</entry>
						<entry>
							120000</entry>
					</row>
				</tbody>
			</tgroup>
		</informaltable>
	</additional>"

 (aggregate name:count-houses
				   over: (all-houses)
				   (aggregate.count)))
