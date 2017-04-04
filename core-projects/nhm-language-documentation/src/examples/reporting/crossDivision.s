(example
 caption:"Dividing by quantized energy use and morphology"
 description:"In this example, a divide.by is used to create
		divisions in an aggregate. The report produced will count
		houses broken down by quantized energy use house region."
 
 (aggregate name:"count by energy use and region"
				   divide-by: (function.steps
							   steps: [1000 2000 3000]
							   value:(house.energy-use))))
