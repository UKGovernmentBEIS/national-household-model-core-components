(example
 caption: "Choice With External Evidence"
 description: "In this example, we show how to make a choice based on some external evidence which is not included as standard in the NHM. If we use set to annotate a measure with our evidence, then we can use it when we are selecting which option to pick."
 class: uk.org.cse.nhm.language.definition.action.choices.XChoiceAction

 (choice

  ; our first option is some popular cavity wall insulation
  (example
   caption: "Annotating Measures With Set"
   description: "In this example, we run a measure and then use set to annotate it with some extra information. This is then available to use outside of the yield."
   class: uk.org.cse.nhm.language.definition.sequence.XSetAction

   (do
    ; 1. the measure is applied to the house
    (measure.wall-insulation 
               type: Cavity 
               thickness: 50 
               resistance: 0.1)

    ; 2. after the measure has been applied we set the values we want to keep
    (set (def measure-popularity on:Event) 10)
         
  ; 3. #measure-popularity will be now available on this particular house until the current target ends.
  ))

  ; our second option is some unpopular internal solid wall insulation
  (do
   (measure.wall-insulation
              type: Internal
              thickness: 30
              resistance: 0.15)
   
   (set #measure-popularity 1))

    select: (select.weighted
           ; 4. we make our choice based on measure-popularity
           weight: #measure-popularity
           )))