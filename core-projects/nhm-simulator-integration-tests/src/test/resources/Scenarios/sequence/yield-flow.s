(scenario
	start-date: 01/01/2014
	end-date: 01/01/2014
	stock-id: my-lovely-stock

	(def output-1 on:House)
	(def output-2 on:House)
	(def output-3 on:House)
	(def output-4 on:House)
	(def output-5 on:House)
	(def output-6 on:House)
	
	(def intermediate-1 on:Event)
	(def intermediate-2 on:Event)
	
	(on.dates scenario-start
		(apply
			(do
					(set #intermediate-1 10)		; intermediate-1 is 10
					
					(increase #intermediate-1 #intermediate-1) ; 20 
					
					; next set output 1 to 88, hiding the effect on intermediate-1
					(do hide:#intermediate-1 (set #intermediate-1 88) (set #output-1 #intermediate-1)) 
					
					(set #output-2 #intermediate-1)
			)
		)
		
		(apply		
			(do name:outer-do
				(choice name:inner-choice
					do-first:[ (set #intermediate-1 10)  (set #intermediate-2 20)  ]
					
					select:(select.minimum #intermediate-1) ; minimize intermediate-1
					
					; an option that loses
					(decrease #intermediate-1 5) 
					
					; another option that loses
					(do name:inner-do
						(set #intermediate-1 2)
						(set #intermediate-2 2)    ; want to check that this is not affected after the test
					)
					
					; the winner who sets intermediate-1 to zero
					(do
						name:winning-do
						(set #intermediate-1 0)
					)
					
				); choice
					
				; after the choice completes save some values for the assertions
				
				(set #output-3 #intermediate-1)
				(set #output-4 #intermediate-2)
			)
		)
			
		(apply
			(do
				(set #intermediate-1 10)
				(set #intermediate-2 20)
			
				(set #output-5 (under (set #intermediate-1 0) evaluate: (+ #intermediate-1 #intermediate-2)))
			
				(set #output-6 #intermediate-1)
			)
		)
	)
)