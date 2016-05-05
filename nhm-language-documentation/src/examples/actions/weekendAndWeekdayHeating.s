(example 
	caption: "Heating on 9-5 at weekends, and weekday mornings"
	description: "In this example we set the heating to be on from 9-5 at
		weekends, and from 8-10 on weekdays"
	
	(action.set-heating-schedule
		(schedule on: Weekends
			(heating between:9 and:17))
		(schedule on:Weekdays
			(heating between:8 and:10)))
)