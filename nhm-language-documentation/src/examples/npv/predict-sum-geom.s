(example
    caption:"Computing the present cost of a house's future obligations"
    description:"By combining future-value with exponential-discount we compute the sum of the discounted expected values of the costs of a house. This prediction does not include the immediate costs to the house, only future costs; to include costs happening right now, you would need to add (net-cost) to this value. The prediction will predict future changes in the fuel bill, but only those changes which can be inferred by looking at date-based functions in the scenario."

    (future-value
        horizon:10 ; how many years from now to sum
        (exponential-discount
            rate: 5% ; the discount rate
            (house.annual-cost) ; the function to be predicted and discounted.
    )))
