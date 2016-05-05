[
	((generate-combinations
		template:my-template
		[a b c]
		[1 2 3]))

	((my-template a 1) (my-template a 2) (my-template a 3) (my-template b 1) (my-template b 2) (my-template b 3) (my-template c 1) (my-template c 2) (my-template c 3))
]