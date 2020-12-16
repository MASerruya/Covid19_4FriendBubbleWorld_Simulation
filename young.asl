// Initial plan to live
!live.

// Starting a new day
+new_day(young) : true <-
	.print("NEWDAY!");
	!live.
	
// Live if is infected
+!live: is_infected(young)	 
	<- .print("live: YOUNG INFECTED");
	!at(young,hospital); 
	-is_infected(young);
	!at(young,home).       

// Living if the young is recovered	
+!live: recovered(young)   
	<-  !at(young,home).                        

// Plan of living if is a weekend day                                                                                             
+!live : is_weekend
	<- .print("live: WEEKEND");
	!at(young,bar);                                                                                                 
	do_things(bar);
	!at(young,home).

// Plan of living if is a week day
+!live : is_week
	<- .print("live: WEEKDAY -- Agent located at: " );
	!at(young,park);
	do_things(park);
	!at(young,home).	
	
// Plan to move.
+!at(young,P) : at(young,P) <- true.
+!at(young,P) : not at(young,P)
	<- .print("MOVE TO: ", P); 
	move_towards(P);
	!at(young,P).

	
// ****************************************************************************
// NOT USED	
/*+!live: is_low_responsible
	<- .print("live: +++++++++++++++++++++++++++++++++++++++++ Y-RESPONSABLE: BAJO").
+!live: is_medium_responsible
	<- .print("live: +++++++++++++++++++++++++++++++++++++++++ Y-RESPONSABLE: MEDIO").
+!live: is_high_responsible                                                                            
	<- .print("live: +++++++++++++++++++++++++++++++++++++++++ Y-RESPONSABLE: ALTO").*/
	