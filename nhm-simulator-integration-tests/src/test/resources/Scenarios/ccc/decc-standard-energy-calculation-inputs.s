(no-include
    (scenario
        ; Test scenario
        start-date: 01/01/2013
        end-date: 01/01/2014
        stock-id: NIHCS2009_DTO
        
        (include name: sap-assumptions version: 4)
        
        (on.dates
            (scenario-start)
            (apply
                (do
                    (reset-walls-to-sap.with-decc-amendments))))))

(template reset-walls-to-sap.with-decc-amendments []
        ; figures based on SAP 2012 
        ; http://www.bre.co.uk/filelibrary/SAP/2009/SAP_2009_9-91_Appendix_S.pdf 
        ; u-values have been changed to be in line with the best evidence 
        ; available to the department as advised by Amy Salisbury in Technical 
        ; Energy Analysis  Details of how the u-values have been modified can be 
        ; found in U:\Chief Economist\Modelling Team\National Household 
        ; Model\Standard Assumptions  in summary: u-values of solid walls for Band
        ; A-E set to 1.5 u-values of uninsulated cavity walls for Band A-E set to 
        ; 1.4  u-values for filled cavities for bands A-F set to 0.65
        (action.reset-walls
        u-values: (decc-u-value-amendments
            #sap.walls.u-values)
        thicknesses: #sap.walls.thicknesses
        k-values: #sap.walls.k-values
        infiltrations: (sap.walls.infiltration-rates)))
        
(template decc-u-value-amendments [@1]
    (lookup-table
        name: "DECC amendments to RDSAP wall u-values (england and wales)"
        row-keys: [
            (wall.construction)
            (wall.insulation-thickness Internal External Cavity)
            (wall.insulation-thickness Cavity)
        ]
        column-key: (house.sap-age-band)
        log-warnings: false
        [ Construction Ins Cav A B C D E F G H I J K ]
        ; For the three rows below bands A-E re-set to 1.5 on advice from TEA, rest from SAP
        [ GraniteOrWhinstone 0 0 1.5 1.5 1.5 1.5 1.5 1 0.60 0.60 0.45 0.35 0.30 ]
        [ Sandstone 0 0 1.5 1.5 1.5 1.5 1.5 1 0.60 0.60 0.45 0.35 0.30]
        [ SolidBrick <50 0 1.50 1.50 1.50 1.50 1.50 1.00 0.60 0.60 0.45 0.35 0.30 ]
        
        ; Unfilled, uninsulated cavity wall. Bands A-E set to 1.4, on advice from TEA.
        [ Cavity <50 0 1.40 1.40 1.40 1.40 1.40 1.00 0.60 0.60 0.45 0.35 0.30 ]

        ; For filled cavities bands A-F reset to 0.65 on advice from TEA, rest from SAP
        [ Cavity <=50 >0 0.65 0.65 0.65 0.65 0.65 0.65 0.35 0.35 0.45 0.35 0.30 ]
        
        default: @1))