(no-include
    (scenario
        ; Test scenario to check it validates
        start-date: 01/01/2013
        end-date: 01/01/2014
        stock-id: UnitedKingdomStock
        
        (include href: ccc-metered-fuels.s)
        
        (context.tariffs
            others: [(metered-fuels)])
        
        (set-income-n-tiles)
        
        (on.dates
            (scenario-end)
            (aggregate
                divide-by: [
                    ; TODO: split up silly combination groups
                
                    ; Boiler churn
                    (group.old-boilers)
                    (group.mtb-old-boilers)
                    
                    ; Warm homes discount
                    (group.sps-core)
                    (group.sps-wide)
                    
                    ; CERT & CESP
                    (group.priority)
                    (group.super-priority)
                    (group.non-priority)
                    (group.cesp)
                    
                    ; ECO
                    (group.cero)
                    (group.csco)
                    (group.hhcro)
                    (group.hhcro-urban-old-boilers)
                    
                    (group.cero-pre-1967-brick) 
                    (group.cero-solid-non-brick)
                    (group.cero-post-1967-brick)
                    (group.csco-pre-1967-brick)
                    
                    ; Green Deal
                    (group.gd.targetted)
                    (group.gd.random)
                    
                    ; Feed in tariff
                    (group.fit.pv-others)
                    (group.fit.pv-detached)
                    (group.fit.pv-social)
                    
                    ; Warm Front
                    (group.warm-front)
                    (group.warm-front-without-central-heating)
                    
                    ; Scotland EAP
                    (group.eap3)
                    (group.eap4)
                    
                    ; Northern Ireland Warm Homes Scheme
                    (group.warm-homes.oo-ins)
                    (group.warm-homes.oo-heat)
                    (group.warm-homes.pr-ins)
                    (group.warm-homes.pr-heat)
                    
                    ; Other
                    (group.httc)
                    (group.topup)
                ]
                
                (aggregate.count)))))
            

; This scenario provides templates for the group criteria defined in PolicyBaselineMeasures.xlsx
; If was designed to be used in conjunction with ccc-baseline-measures.
; When using this scenario, start by invoking (set-income-n-tiles) to set up some required variables.

; These criteria don't check for measure suitability or region. These should be sorted out in the baseline scenario.

(template not-implemented [] (any))

(template ehs.is-true [@1]
    (=
        (house.static-number @1)
        1))

(template group.old-boilers []
        (all
            (house.has-boiler)
            (>
                (house.space-heatingsystem-age)
                12)))
                
(template group.mtb []
    (ehs.is-true MTB))

(template group.mtb-old-boilers []
    (all
        (group.old-boilers)
        (group.mtb)))
        
(template group.income-below [@1]
    (>
        @1
        (house.household-income)))
                
(template group.cert.low-income []
    (group.income-below 16190))
    
(template person.age []
    (house.static-number agehrpx_hh))

(template group.older-than [@1]
    (>=
        @1))
    
(template group.aged-between [@1 @2]
    (>=
        @1
        (person.age)
        @2))

(template group.child-under-5 []
    (ehs.is-true ChildrenUnder5))

(template group.pension-credit []
    (ehs.is-true BnPenCrd))

(template group.super-priority []
    (any
        (all
            (group.cert.low-income)
            (group.ctc))
        (all
            (group.mtb)
            (group.child-under-5))
        (group.pension-credit)))
            
(template group.ctc []
    (ehs.is-true BnCTC))

(template group.wtc []
    (ehs.is-true BnWTC))

(template group.priority []
    (all
        ; Test their eligibility for this group
        (any
            (group.mtb)
            (all
                (group.ctc)
                (group.cert.low-income))
            (all
                (group.wtc)
                (group.cert.low-income))
            (group.older-than 70))

        (none
            ; Exclude the super-priority-group
            (group.super-priority))))
        
(template group.non-priority []
    (none
        ; Exclude the priority and super-priority-groups
        (group.priority)
        (group.super-priority)))
        
(template group.income-n-tile-below [@1]
    (<
        (join #income-n-tile- @1)
        (house.household-income)))
        
(template group.income-n-tile-above [@1]
    (>=
        (join #income-n-tile- @1)
        (house.household-income)))
        
(template group.cesp []
    ; Supposed to be lowest 10% on IMD, but using income as a proxy.
    (group.income-n-tile-below 0.1))
    
(template group.cero []
    (all
        (any
            (house.tenure-is OwnerOccupied)
            (house.tenure-is PrivateRented))
        ; 
        (group.httc)))
        
(template group.pre-1967-brick []
    (all
        (<
            (house.buildyear)
            1967)
        (house.has-wall with-construction: SolidBrick)))
        
(template group.cero-pre-1967-brick []
    (all
        (group.cero)
        (group.pre-1967-brick)))

(template group.cero-post-1967-brick []
    (all
        (group.cero)
        (>=
            (house.buildyear)
            1967)
        (house.has-wall with-construction: SolidBrick)))

(template group.cero-solid-non-brick []
    (all
        (group.cero)
        (house.has-wall with-construction: AnySolid)
        (none
            (house.has-wall with-construction: SolidBrick))))
        
(template group.csco []
    (group.income-n-tile-below 0.15))
    
(template group.csco-pre-1967-brick []
    (all
        (group.csco)
        (group.pre-1967-brick)))

(template group.eco.low-income []
    (group.income-below 15860))
    
(template group.child-under-16 []
    (>=
        (house.number-of-children)
        1))

(template group.esa []
    (ehs.is-true BnESA))

(template group.jsa-esa-is []
    (any
        (ehs.is-true BnJSA)
        (group.esa)
        (ehs.is-true BnIncSup)))

(template group.pensioner-premium []
    ; We have no information on this, so assuming it's the same as pension-credit.
    (group.pension-credit))

(template group.disability-premium []
    (ehs.is-true BnSDA))

(template group.hhcro []
    (any
        (group.pension-credit)
        (all
            (group.ctc)
            (group.eco.low-income))
        (all
            (group.jsa-esa-is)
            (any
                (group.child-under-16)
                (group.disability-premium)))
        (all
            (group.wtc)
            (any
                (group.child-under-16)
                (group.eco.low-income)))))
                
(template group.hhcro-urban-old-boilers []
    (all
        (group.hhcro)
        (group.old-boilers)
        (house.main-heating-fuel-is MainsGas)))

(template group.fit.pv-owner-occupied []
    (all
        (group.aged-between 40 65)
        (house.tenure-is OwnerOccupied)
        (group.income-n-tile-above 0.5)))
            
(template group.fit.consumer []
    (all
        (group.aged-between 35 64)
    
        (house.built-form-is Detached)
        (any
            (house.morphology-is HamletsAndIsolatedDwellings)
            (house.morphology-is Village))
        (house.tenure-is OwnerOccupied)
        (group.income-n-tile-above 0.8)))

(template group.fit.pv-social []
    (any
        (house.tenure-is HousingAssociation)
        (house.tenure-is LocalAuthority)))

(template group.fit.pv-detached []
    (all
        (group.fit.pv-owner-occupied)
        (house.built-form-is Detached)))
        
            
(template group.fit.pv-others []
    ; Not-detached, but still owner occupied
    (all
        (group.fit.pv-owner-occupied)
        (none
            (group.fit.pv-detached))))
        
(template group.houses []
    (any
        (house.built-form-is EndTerrace)
        (house.built-form-is MidTerrace)
        (house.built-form-is SemiDetached)
        (house.built-form-is Detached)
        (house.built-form-is Bungalow)))

(template group.sap-band [@1 @2]
    ; Use the EHS sap rating. Only for use in installing baseline measures.
    (<=
        @1
        (house.static-number sap09)
        @2))
            
(template group.sap.g []
    (group.sap-band 1 20))
    
(template group.sap.f []
    (group.sap-band 21 38))
    
(template group.sap.e []
    (group.sap-band 39 54))

(template group.sap.d []
    (group.sap-band 55 68))

(template group.sap.c []
    (group.sap-band 69 80))

(template group.sap.b []
    (group.sap-band 81 91))

(template group.sap.a []
    (group.sap-band 92 1000))
    

(template group.gd.targetted []
    (all
        ; A house which is owner occupied, on gas, and in SAP band D or E.
        (group.houses)
        (any
            (group.sap.d)
            (group.sap.e))
        (house.tenure-is OwnerOccupied)
        (house.is-on-gas)

        ; Not cero, csco or hhcro
        (none
            (group.cero)
            (group.csco)
            (group.hhcro))))

(template group.gd.random []
    (none
        (group.cero)
        (group.csco)
        (group.hhcro)
        (group.gd.targetted)))
        
(template group.nest []
    (all
        (house.tenure-is PrivateRented)
        (any
            (group.sap.f)
            (group.sap.g))
        (any
            (group.mtb)
            (all
                (group.income-below 16010)
                (any
                    (group.wtc)
                    (group.ctc))))))

(template group.rhpp.age-and-income []
    (all
        (group.aged-between 35 64)
        (group.income-n-tile-above 0.8)))
        
(template group.off-gas []
    (none
        (house.is-on-gas)))


(template group.rhpp.gshp []
    (all
        (house.built-form-is Detached)
        (group.rhpp.age-and-income)
        (group.off-gas)))

(template group.rhpp.biomass []
    (all
        (house.built-form-is Detached)
        (group.rhpp.age-and-income)
        (group.off-gas)
        (>= house.number-of-bedrooms 4)
        ))

(template group.rhpp.ashp []
    (all
        (house.built-form-is Detached)
        (group.rhpp.age-and-income)
        (group.off-gas)
        (>= house.number-of-bedrooms 3)
        ))
        
(template group.rhpp.solar []
    (all
        (any
            (house.built-form-is Detached)
            (house.built-form-is SemiDetached))
        (group.rhpp.age-and-income)
        (>= house.number-of-bedrooms 3)
        ))
        
(template group.eap.low-income []
    ; Note that this is the same as ECO low income.
    (group.income-below 15860))
        
(template group.eap3 []
    (all
        (house.tenure-is PrivateRented)
        (any
            (all
                (group.child-under-16)
                (any
                    (group.jsa-esa-is)
                    (all
                        (group.wtc)
                        (group.eap.low-income))))
            (all
                (group.ctc)
                (group.eap.low-income))
            (group.pension-credit)
            (all
                (group.wtc)
                (group.older-than 60)
                (group.eap.low-income)))))
                
(template group.low-sap []
    (<= (house.static-number sap09) 54))


(template group.carers-allowance []
    (ehs.is-true BnICA))

(template group.eap4 []
    (all
        (house.tenure-is PrivateRented)
        (none (group.eap3)
        (any
            (all
                (group.older-than 60)
                (none
                    (house.has-central-heating)))
            (all
                (group.low-sap)
                (any
                    (all
                        (group.older-than 60)
                        (group.mtb))
                    (all
                        (group.child-under-16)
                        (group.mtb))
                    ; (group.terminally-ill) Not implemented because we have no information on this - probably insignificant.
                    (group.carers-allowance)))))))
                    
(template group.is-jsa-ir-esa []
    ; ir-esa is income-related esa.
    ; Delegating to normal esa because the difference is probably insignificant.
    (group.jsa-esa-is))
    
(template group.warm-front []
    (any
        (group.pension-credit)
        (group.esa)
        (all
            (group.is-jsa-ir-esa)
            (any
                (group.child-under-5)
                (group.disability-premium)
                (group.pensioner-premium))
                (group.sap-band 0 55))))
                
(template group.warm-front-without-central-heating []
    (all
        (group.warm-front)
        (none
            (house.has-central-heating))))

(template group.housing-benefit []
    (ehs.is-true housbenx_hh))

(template group.warm-homes.benefits []
    (any
            (group.housing-benefit)
            (group.is-jsa-ir-esa)
            (group.pension-credit)
            (group.wtc)))
            
(template group.warm-homes.fuels []
    (any
        (house.main-heating-fuel-is HouseCoal)
        (house.main-heating-fuel-is BottledLPG)
        (group.economy7) ; Defined in ccc-metered-fuels
        (none
            (house.has-central-heating))))

(template group.warm-homes.oo-ins []
    (all
        (house.tenure-is OwnerOccupied)
        (group.mtb)))


(template group.warm-homes.oo-heat []
    (all
        (house.tenure-is OwnerOccupied)
        (group.warm-homes.benefits)
        (group.warm-homes.fuels)))
        
(template group.warm-homes.pr-ins []
    (all
        (house.tenure-is PrivateRented)
        (group.mtb)))
        
(template group.warm-homes.pr-heat []
    (all
        (house.tenure-is PrivateRented)
        (group.warm-homes.benefits)
        (group.warm-homes.fuels)))

(template group.sps-core []
    (group.pension-credit))
    
(template group.sps-wide []
    (all
        (group.income-n-tile-below 0.1)
        (none
            (group.sps-core))))

(template set.income-n-tile[@1]
    (set
        (def (join "income-n-tile-" @1) on: Simulation)
        (summarize
            group: (group.all)
            (aggregate.n-tile
                n: @1
                (house.household-income)))))

(template set-income-n-tiles []
    (on.dates
        (scenario-start)
        
        (set.income-n-tile 0.1)
        (set.income-n-tile 0.15)
        (set.income-n-tile 0.5)
        (set.income-n-tile 0.8)))
        
(template group.topup []
    (<
        0
        (house.loft-insulation-thickness)  
        150))
        
(template group.httc []
    (house.flags-match hard-to-treat-cavity))