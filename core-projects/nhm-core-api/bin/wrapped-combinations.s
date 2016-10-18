[
	((generate-combinations
		template:(foo @rest)
		[a b c]
		[1 2 3]))

	((foo a 1) (foo a 2) (foo a 3) (foo b 1) (foo b 2) (foo b 3) (foo c 1) (foo c 2) (foo c 3))
]