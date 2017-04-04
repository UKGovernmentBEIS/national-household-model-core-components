(example
 class: uk.org.cse.nhm.language.definition.two.actions.XRepeatHookAction
 caption: "Travelling up a MAC curve using repeat"
 description: "This examples uses repeat without a preserve: argument, so each repetition is applied on top of the effects of the previous one. We increase the subsidy-level variable each repeat, until we have installed 1 million boilers; each round of people offered a boiler get shown a higher subsidy level."
 (repeat
  ;; each time we repeat, we will increase the subsidy by 10 before doing anything
  (set (def subsidy-level on:simulation default:10) (+ 10 subsidy-level))

  ;; after bumping up the subsidy level, we want to try installing lots of boilers.
  ;; we say that people's decisions are based on the net present cost of the option
  ;; where the boiler has a subsidy of subsidy-level * (delta in emissions)
  (apply
   ;; each time around we want to operate on the people who don't have a new boiler yet
   ;; and offer them more and more money until they do what we want.
   to: (filter (house.flags-match !got-boiler))
   (do
       ;; record old emissions, so we can work out the delta
       (set (def old-emissions on:event) house.emissions)
       ;; now do the choice
       (choice
        ;; the basis for our choice - if the subsidy and 1-year fuel bill reduction balance the
        ;; capital cost, we will choose the boiler.
        select:(select.minimum (+ net-cost house.annual-cost))
        ;; first option is to do nothing
        (action.do-nothing)
        ;; second option is to take our boiler with a subsidy
        (finance.with-subsidy
         (measure.standard-boiler update-flags: got-boiler winter-efficiency:95% capex:2000)
         ;; the subsidy-level goes up each time around, increasing the subsidy provided.
         subsidy: (* subsidy-level (- old-emissions house.emissions)))
        )))

  ;; this is the termination condition for the repeat - we flagged our houses as having a
  ;; boiler, so we are checking whether the number of houses which got a boiler exceeds 1
  ;; million. If this fails, we will crank up the subsidy level and then try again.
  until: (>= (summarize (aggregate.where (house.flags-match got-boiler)))
             1000000)
  ))
