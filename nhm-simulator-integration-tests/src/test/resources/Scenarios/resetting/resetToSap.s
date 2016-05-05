(include href:sapDoors.s)
(include href:sapFloors.s)
(include href:sapRoofs.s)
(include href:sapWalls.s)
(include href:sapWindows.s)

(template reset-to-sap []
	(do all:true 
		(reset-walls-to-sap)
		
		(reset-floors-to-sap)
		
		(reset-roofs-to-sap)
		
		(reset-glazing-to-sap)
		
		(reset-doors-to-sap)
	)
)