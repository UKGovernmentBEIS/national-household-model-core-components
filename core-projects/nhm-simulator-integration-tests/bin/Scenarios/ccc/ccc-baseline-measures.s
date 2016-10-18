(no-include
    (scenario
        ; A test scenario to ensure that this validates.
        start-date: 01/01/2013
        end-date: 01/01/2014
        stock-id: EnglandAndWales_2010
        
    (include href: ccc-groups.s)
    (include href: ccc-countries.s )
    (include href: ccc-measures.s)
    (include href: sap-assumptions.s)
    (include href: decc-standard-energy-calculation-inputs.s)
    (include href: ccc-metered-fuels.s)
        
    (context.tariffs
        others: [(metered-fuels)])

    (set-income-n-tiles)
    (baseline-measures)))

(template target.whole-uk 
    [@1 @2 @3 @4] ; name, measure, group, count
    (apply
        name: @1
        @2

        to: (sample @4
            (filter @3))))

(template target.for-region
    [@1 @2 @3 @4 @5 [@test (house.region-is @4)]]
    ; name, measure, group, region name, count
    
    (macro.match @5
        [0] ; If the count is 0, don't make this target.
        
        default: (apply
            name: (join @1 "-" @4)
            @2

            to: (sample @5
                (filter
                    (all
                        @3
                        @test))))))
            
                
(template baseline-actions [@name @rest]
    (on.dates
        name: @name
        (scenario-start)
        @rest))
        
(template target.by-region
    [@1 @2 @3 @4 @5 @6 @7 @8 @9 @10 @11 @12 @13 @14 @15]
    ; name measure, group, count for each of the 12 regions
    
    (target.for-region @1 @2 @3 NorthernIreland @4)
    (target.for-region @1 @2 @3 NorthEast @5)
    (target.for-region @1 @2 @3 NorthWest @6)
    (target.for-region @1 @2 @3 YorkshireAndHumber @7)
    (target.for-region @1 @2 @3 EastMidlands @8)
    (target.for-region @1 @2 @3 WestMidlands @9)
    (target.for-region @1 @2 @3 EastOfEngland @10)
    (target.for-region @1 @2 @3 London @11)
    (target.for-region @1 @2 @3 SouthEast @12)
    (target.for-region @1 @2 @3 SouthWest @13)
    (target.for-region @1 @2 @3 Wales @14)
    (target.for-region @1 @2 @3 Scotland @15 test: (group.scotland)))

        
(template baseline-measures []
    (baseline-actions
        name: boiler-churn
        (target.whole-uk old-gas (gas-condensing-boiler) (group.old-boilers) 924000)
        (target.whole-uk means-tested-gas (gas-condensing-boiler) (group.mtb-old-boilers) 533051)
        (target.whole-uk old-oil (oil-condensing-boiler) (group.old-boilers) 96000)
        (target.whole-uk means-tested-oil (oil-condensing-boiler) (group.mtb-old-boilers) 59228))

    (baseline-actions
        name: warm-home-discount
        (target.whole-uk core (warm-homes-discount) (group.sps-core) 82513)
        (target.whole-uk wide (warm-homes-discount) (group.sps-wide) 654045))
    
    (baseline-actions name: 	CERT
    		(target.by-region	"LIF-PriorityGroup"	(loft-insulation-full)	(group.priority)	0	70210	185489	130944	103700	120708	111783	119018	117530	108139	79246	161218)
    		(target.by-region	"LIF-NonPriorityGroup"	(loft-insulation-full)	(group.non-priority)	0	53847	144866	100119	80584	93242	86520	95882	88440	82735	60057	127930)
    		(target.by-region	"CWI-NonPriorityGroup"	(cavity-wall-insulation)	(group.non-priority)	0	99088	355427	170503	120171	183434	165798	208785	195842	175661	96701	186692)
    		(target.by-region	"CWI-PriorityGroup"	(cavity-wall-insulation)	(group.priority)	0	44932	160521	78215	54616	82839	75065	91705	91505	80097	44231	84744)
    		(target.by-region	"LIF-SuperPriorityGroup"	(loft-insulation-full)	(group.super-priority)	0	10166	30745	18498	16585	18470	17362	24101	14481	15355	10400	29738	)
    		(target.by-region	"LIT-PriorityGroup"	(loft-insulation-topup)	(all (group.priority) (group.topup))	0	20534	53150	38426	29886	35022	32361	32869	35090	31711	23479	45345)
    		(target.by-region	"LIT-NonPriorityGroup"	(loft-insulation-topup)	(all (group.non-priority) (group.topup))	0	15459	40757	28842	22799	26556	24588	26057	25933	23817	17471	35360)
    		(target.by-region	"CWI-SuperPriorityGroup"	(cavity-wall-insulation)	(group.super-priority)	0	4740	17673	6818	5560	9057	7938	13679	5781	7717	4226	8773	)
    		(target.by-region	"LIT-SuperPriorityGroup"	(loft-insulation-topup)	(all (group.super-priority) (group.topup))	0	2541	7686	4624	4146	4617	4340	6025	3620	3839	2600	7435	)
    		(target.by-region	"SWI (assume external)-PriorityGroup"	(solid-wall-insulation-external)	(group.priority)	0	648	1723	1273	1085	1305	1392	2013	1750	1300	751	2698	)
    		(target.by-region	"SWI (assume external)-NonPriorityGroup"	(solid-wall-insulation-external)	(group.non-priority)	0	282	751	555	473	569	607	877	763	567	327	1176	)
    ) (baseline-actions name: 	CESP																	
    		(target.by-region	"Solid wall insulation (external)-(group.cesp)"	(solid-wall-insulation-external)	(group.cesp)	0	7399	17376	7770	6984	7718	920	5301	1475	918	7743	11651	)
    		(target.by-region	"LIF-CESP"	(loft-insulation-full)	(group.cesp)	0	2311	5427	2427	2181	2410	287	1656	461	287	2418	3639	)
    		(target.by-region	"Photovoltaics-(group.cesp)"	(solar-photovoltaic)	(group.cesp)	0	1135	2666	1192	1071	1184	141	813	226	141	1188	1788	)
    		(target.by-region	"Solid wall insulation (internal)-(group.cesp)"	(solid-wall-insulation-internal)	(group.cesp)	0	492	1155	516	464	513	61	352	98	61	515	774	)
    		(target.by-region	"CWI-CESP"	(cavity-wall-insulation)	(group.cesp)	0	295	693	310	278	308	37	211	59	37	309	464	)
    ) (baseline-actions name: 	ECO																	
    		(target.by-region	"GCB-HHCRO & Urban & Old-boiler & fuel type is gas"	(gas-condensing-boiler)	(group.hhcro-urban-old-boilers)	0	13055	35366	21376	11961	23772	5884	5398	8590	7186	13440	19118	)
    		(target.by-region	"HTTC: Cavity wall insulation solution-CERO"	(cavity-wall-insulation)	(all (group.cero) (group.httc))	0	7894	22444	8935	8028	14301	9542	10814	17442	12036	5946	16788	)
    		(target.by-region	"LIT-CSCO"	(loft-insulation-topup)	(all (group.csco) (group.topup))	0	4479	11207	6058	3357	7896	1565	5411	2132	1724	1915	3137	)
    		(target.by-region	"LIT-HHCRO"	(loft-insulation-topup)	(all (group.hhcro) (group.topup))	0	2045	5541	3349	1874	3725	922	846	1346	1126	2106	2995	)
    		(target.by-region	"Standard CWI-CSCO"	(cavity-wall-insulation)	(group.csco)	0	2314	5791	3130	1735	4080	809	2796	1102	891	990	1621	)
    		(target.by-region	"LIT-CERO"	(loft-insulation-topup)	(all (group.cero) (group.topup))	0	1080	3070	1222	1098	1956	1305	1479	2386	1646	813	2296	)
    		(target.by-region	"LIF-CSCO"	(loft-insulation-full)	(group.csco)	0	1621	4056	2193	1215	2858	566	1959	772	624	693	1135	)
    		(target.by-region	"External wall insulation-CERO & Solid brick walls, built pre 1967"	(solid-wall-insulation-external)	(group.cero-pre-1967-brick)	0	655	1863	741	666	1187	792	897	1448	999	493	1393	)
    		(target.by-region	"LIF-CERO"	(loft-insulation-full)	(group.cero)	0	653	1856	739	664	1183	789	894	1442	995	492	1388	)
    		(target.by-region	"Standard CWI-HHCRO"	(cavity-wall-insulation)	(group.hhcro)	0	728	1973	1192	667	1326	328	301	479	401	750	1066	)
    		(target.by-region	"External wall insulation-CERO & Solid non-brick walls"	(solid-wall-insulation-external)	(group.cero-solid-non-brick)	0	514	1462	582	523	932	622	704	1136	784	387	1094	)
    		(target.by-region	"LIF-HHCRO"	(loft-insulation-full)	(group.hhcro)	0	597	1617	977	547	1087	269	247	393	328	614	874	)
    		(target.by-region	"External wall insulation-CERO & Solid brick walls, built from 1967"	(solid-wall-insulation-external)	(group.cero-post-1967-brick)	0	273	776	309	278	495	330	374	603	416	206	581	)
    		(target.by-region	"External wall insulation-CSCO & Solid brick walls, built pre 1967"	(solid-wall-insulation-external)	(group.csco-pre-1967-brick)	0	119	299	162	90	211	42	144	57	46	51	84	)
    		(target.by-region	"Cavity wall insulation solution-CSCO & HTTC"	(cavity-wall-insulation)	(all (group.csco) (group.httc))	0	110	274	148	82	193	38	132	52	42	47	77	)
    		(target.by-region	"Solid wall insulation solution-CERO & HTTC"	(solid-wall-insulation-external)	(all (group.cero) (group.httc))	0	68	193	77	69	123	82	93	150	103	51	144	)
    		(target.by-region	"Standard CWI-CERO"	(cavity-wall-insulation)	(group.cero)	0	64	182	72	65	116	77	88	141	98	48	136	)
    ) (baseline-actions name: 	FIT																	
    		(target.by-region	"Photovoltaics-PV - all others"	(solar-photovoltaic)	(group.fit.pv-others)	0	7609	15611	16317	17225	13123	18584	4515	24182	27923	12420	11719	)
    		(target.by-region	"Photovoltaics-PV - wealthier detached"	(solar-photovoltaic)	(group.fit.pv-detached)	0	7609	15611	16317	17225	13123	18584	4515	24182	27923	12420	11719	)
    		(target.by-region	"Photovoltaics-FIT_SOC"	(solar-photovoltaic)	(group.fit.pv-social)	0	3805	7805	8159	8612	6562	9292	2258	12091	13961	6210	5859	)
    ) (baseline-actions name: 	GD																	
    		(target.by-region	"GCB-GD_Targeted"	(gas-condensing-boiler)	(group.gd.targetted)	0	205	1628	777	679	645	307	536	1055	782	358	211	)
    		(target.by-region	"GCB-GD_Random"	(gas-condensing-boiler)	(group.gd.random)	0	51	407	194	170	161	77	134	264	195	89	53	)
    ) (baseline-actions name: 	WarmFront
    		(target.by-region	"NEW_GAS_CH-WarmFront"	(gas-condensing-boiler)	(group.warm-front-without-central-heating)	0	507	1060	761	507	565	427	315	624	440	0	0	)
    		(target.by-region	"ELEC_CH-WarmFront"	(measure.storage-heater)	(group.warm-front-without-central-heating)	0	237	495	355	237	264	200	147	291	206	0	0	)
    		(target.by-region	"LI (assume full)-WarmFront"	(loft-insulation-full)	(group.warm-front)	0	49	327	96	101	124	117	157	198	141	0	0	)
    		(target.by-region	"CWI-WarmFront"	(cavity-wall-insulation)	(group.warm-front)	0	48	320	94	99	121	114	154	194	138	0	0	)
    ) (baseline-actions name:	ScotlandEAP																	
    		(target.by-region	"GCB-EAP4"	(gas-condensing-boiler)	(group.eap4)	0	0	0	0	0	0	0	0	0	0	0	21640	)
    		(target.by-region	"CWI-EAP4"	(cavity-wall-insulation)	(group.eap4)	0	0	0	0	0	0	0	0	0	0	0	9000	)
    		(target.by-region	"LIT-EAP4"	(loft-insulation-topup)	(all (group.eap4) (group.topup))	0	0	0	0	0	0	0	0	0	0	0	9000	)
    		(target.by-region	"OCB-EAP4"	(oil-condensing-boiler)	(group.eap4)	0	0	0	0	0	0	0	0	0	0	0	3165	)
    		(target.by-region	"SWI-EAP4"	(solid-wall-insulation-external)	(group.eap4)	0	0	0	0	0	0	0	0	0	0	0	3000	)
    		(target.by-region	"LIF-EAP3"	(loft-insulation-full)	(group.eap3)	0	0	0	0	0	0	0	0	0	0	0	2687	)
    		(target.by-region	"CWI-EAP3"	(cavity-wall-insulation)	(group.eap3)	0	0	0	0	0	0	0	0	0	0	0	1305	)
    		(target.by-region	"ASHP-EAP4"	(air-source-heat-pump)	(group.eap4)	0	0	0	0	0	0	0	0	0	0	0	142	)
    		(target.by-region	"SWI-EAP3"	(solid-wall-insulation-external)	(group.eap3)	0	0	0	0	0	0	0	0	0	0	0	122	)
    		(target.by-region	"LIF-EAP4"	(loft-insulation-full)	(group.eap4)	0	0	0	0	0	0	0	0	0	0	0	0	)
    		(target.by-region	"LIT-EAP3"	(loft-insulation-topup)	(all (group.eap3) (group.topup))	0	0	0	0	0	0	0	0	0	0	0	0	)
    ) (baseline-actions name:	WarmHomesScheme																	
    		(target.by-region	“WHS-OO-Ins-LIT”	(loft-insulation-topup)	(all (group.warm-homes.oo-ins) (group.topup))	13493	0	0	0	0	0	0	0	0	0	0	0	)
    		(target.by-region	“WHS-OO-Ins-CWI”	(cavity-wall-insulation)	(group.warm-homes.oo-ins)	7265	0	0	0	0	0	0	0	0	0	0	0	)
    		(target.by-region	“WHS-PR-Ins-LI”	(loft-insulation-full)	(group.warm-homes.pr-ins)	4808	0	0	0	0	0	0	0	0	0	0	0	)
    		(target.by-region	“WHS-PR-Ins-CWI”	(cavity-wall-insulation)	(group.warm-homes.pr-ins)	2589	0	0	0	0	0	0	0	0	0	0	0	)
    		(target.by-region	“WHS-PR-Heat”	(gas-condensing-boiler)	(all (group.warm-homes.pr-heat) (group.old-boilers))	1654	0	0	0	0	0	0	0	0	0	0	0	)
    		(target.by-region	“WHS-OO-Heat”	(gas-condensing-boiler)	(all (group.warm-homes.oo-heat) (group.old-boilers))	1549	0	0	0	0	0	0	0	0	0	0	0	)
    )
)