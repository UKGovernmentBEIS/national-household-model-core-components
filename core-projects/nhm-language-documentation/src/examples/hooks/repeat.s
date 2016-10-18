(example
 class: uk.org.cse.nhm.language.definition.two.actions.XRepeatHookAction
 caption: "Finding a subsidy level using repeat"
 description: "In this example, the preserve: argument of repeat is used. When preserve: is specified, only the effects of the final repetition are eventually visible. The preserved values are the only thing kept between the repetitions. in this case the subsidy level variable is value preserved between repeats, so the subsidy level is increased until it has the effect of causing enough boilers to be installed."
 (repeat
  ;; each time we repeat, we will increase the subsidy by 10 before doing anything
  (set (def subsidy-level on:simulation default:10) (+ 10 subsidy-level))

  ;; after bumping up the subsidy level, we want to try installing lots of boilers.
  ;; we say that people's decisions are based on the net present cost of the option
  ;; where the boiler has a subsidy of subsidy-level * (delta in emissions)
  (apply
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
         ;; this is the bit controlled by the repeat - because we are using subsidy-level,
         ;; which is in the preserving: argument below, each repetition will have a greater
         ;; subsidy level, making more houses choose this option each time around.
         subsidy: (* subsidy-level (- old-emissions house.emissions)))
        )))

  ;; this is the termination condition for the repeat - we flagged our houses as having a
  ;; boiler, so we are checking whether the number of houses which got a boiler exceeds 1
  ;; million. If this fails, we will crank up the subsidy level and then try again -
  ;; however, because the preserve: argument is in effect, the model will forget all of
  ;; the boilers installed and flags changed, and retry the apply with only the subsidy
  ;; level changed. This has the effect of making sure we give everyone the same subsidy
  ;; level.
  until: (>= (summarize (aggregate.where (house.flags-match got-boiler)))
             1000000)

  ;; this is what ensures that subsidy-level is the only thing changed from one repeat to
  ;; the next
  preserving:[subsidy-level]
  ))
