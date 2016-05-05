[
	(macro.match
		thing
		[thing one]
		[other-thing two]
		default:(macro.error "died")
	)

	one
]