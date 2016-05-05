(example
 caption:"Filtering all-houses"
 description:"The most common use of a filter is probably to
		filter the set of all houses to a particular set of interest. In
		this example, we filter all houses down to pick only detached houses
		in London."

 (filter
  ; the first non-named argument is a boolean test to use for filtering
  (example
   caption:"Using all to test two conditions at once"
   description:"Houses will only pass this test when they are both detached, and in London."

   (all
	(house.built-form-is Detached)
	(house.region-is London)))
	
  ; the default source group is all-houses, so this is not strictly necessary here
  (all-houses)))
