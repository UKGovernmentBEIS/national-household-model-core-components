(example 
	caption:"Heating on 9-5 at weekends"
	description:"In this example we set the heating to be on from 9-5 at weekends only"
	
	(action.set-heating-schedule
		(schedule on:Weekends
			(heating between:9 and:17)))
)