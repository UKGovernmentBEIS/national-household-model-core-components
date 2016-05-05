(example
 caption: "Weight by household case weight"
 description: "A scenario which weights houses based on the household case weight survey variable.
This scenario also produces a report on the number of houses in the housing stock following this re-weighting."
 
 (scenario
  start-date: 01/01/2015
  end-date: 01/01/2016
  stock-id: my-stock

  ;; weight-by can take any number, any other numeric property of a house, or any other numeric function of a house
  ;; house.static-number is looking up a survey variable which we know to be present in the stock
  ;; aagph1112 is the household case weight for the English Housing Survey 2012
  weight-by: (house.static-number aagph1112)

  (on.dates
   [(scenario-start)]

   ;; This report produces a count of the total number of houses after we have imported and re-weighted them.
   (aggregate
    name: count

    (aggregate.count name: count)))))