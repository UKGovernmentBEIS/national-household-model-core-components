(~combinations
	caption:"Using templates with the combinations macro"
	description:"When using the combinations macro within a template, it is not safe to use
				 an anonymous template as its template: argument, because the containing template
				 will either replace the @rest argument, or be invalid. In this situation, you can
				 provide the name of another template as the template: argument, and the combinations
				 macro will expand to use that template for each combination"
	
	(~combinations
		template:my-template-name
		[(insulation-1) (insulation-2)]
		[(heating-1) (heating-2) (action.do-nothing)] 
	)
)