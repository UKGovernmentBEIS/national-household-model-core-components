(example
 caption:"Reporting on transactions from loans"
 description:"This report will produce a log of all the transactions having the
		:loan tag, for any of the houses in the simulation (since the group is group.all, by default).
		
		By default, loan-related transactions have this tag."

 (report.transactions name:loantransactions
					  tags:":loan"))
