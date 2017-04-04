(macro.match
	bad
	[thing one]
	[other-thing two]
	default:(macro.error "died")
)
