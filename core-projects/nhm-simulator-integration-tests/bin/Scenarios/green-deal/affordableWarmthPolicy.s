(policy
 (target exposure: (schedule.on-group-entry)
	 name: aw
	 group: (group.all)

	 action: (finance.fully 
		  update-flags: got-aw
		  
		  (do
		   (take-snapshot 
		   	(def-snapshot under-aw-before)
		   	#eco-assumptions)
		  
		   (choice 

		    #cavity
		    #loft
		    #standard-boiler-replacement
		    #combi-boiler-replacement

		    select: (select.filter 
		    	do-first: (take-snapshot
		    		(def-snapshot under-aw-after)
		    		#eco-assumptions)

			     test: (house.value-is 
				    below: $poundPerPoint
				    above: 0

				    (do
				     (set 
				      (def aw-measure-costs)
				      (cost.sum))

				     (set
				      (def aw-points-new)
				      (*
				       (get #measure-lifetime)
				       (snapshot.delta 
						after: #under-aw-before
						before: #under-aw-after
						(house.fuel-cost))))

				     return: (/
				      (get #aw-measure-costs)
				      (get #aw-points-new))))

				    
				    selector: (select.maximum 
					       (get #aw-points-new))))

		    (increase #measure-costs (get 
					      #aw-measure-costs))
		    (increase #aw-points 
		    	(get #aw-points-new))))))

