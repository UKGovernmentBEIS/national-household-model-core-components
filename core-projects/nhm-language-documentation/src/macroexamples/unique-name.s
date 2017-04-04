(~unique
	caption:"Using a unique name for a temporary value in a template"
	description:"Variables can only be declared once, so a template which needs a temporary variable
				 must either have an associated set of declarations, or it can only be used once. The ~unique
				 macro allows you to create a globally unique name for a variable, because whenever it is expanded
				 it produces a new result."
	
	(template my-template 
		[
			[@:unique-name ; our variable has an internal name of unique-name, but no external name 
				(~unique)  ; and it has a default value of (~unique) - because the template will expand
						   ; its arguments before substituting them into the body, this will be replaced
						   ; with a unique name
			]
		]
		(do (set (def @unique-name on:Event) 100)
			; .. do more stuff ..
			(join # @unique-name) ; access the variable defined under unique-name later on
		)
	)
)