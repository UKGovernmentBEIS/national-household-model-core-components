(~lookup-table
	caption:"A simple lookup table"
	description:"
		Each lookup table has an argument for <code>row-keys:</code>,
		which can take a list of any functions, and an argument called
		<code>column-key:</code>, which takes a single function. These
		keys functions are used to identify a cell in the lookup table,
		whose value will be used for the result. For example:
		
		<table frame=\"all\">
		<title>An example lookup table</title>
		<tgroup cols=\"4\" align=\"left\">
		  <thead>
			<row>
			  <entry>Region</entry>
			  <entry>Built Form</entry>
			  <entry>On Gas</entry>
			  <entry>Off Gas</entry>
			</row>
		  </thead>
		  <tbody>
			<row>
			  <entry>London</entry>
			  <entry>Flat</entry>
			  <entry>1</entry>
			  <entry>2</entry>
			</row>
			<row>
			  <entry>Scotland</entry>
			  <entry>Bungalow</entry>
			  <entry>3</entry>
			  <entry>4</entry>
			</row>
		  </tbody>
		</tgroup>
	  </table>

	  <para>
		If we wanted to convert the preceding table into a
		<code>lookup-table</code> function, we can write it out almost
		explicitly, and the result will be converted into a suitable lookup.
	  </para>
	"
	
	(~lookup-table name:my-table
	  row-keys:[(house.region) (house.built-form)]
	  column-key:(house.is-on-gas)
	  [Region    Form    true    false]
	  [London    Flat     1       2   ]
	  [Scotland  Bungalow 3       4   ])
)
