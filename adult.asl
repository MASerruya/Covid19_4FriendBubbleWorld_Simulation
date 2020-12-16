// Initial plan to live
!live.

// Starting a new day
+new_day(adult) : true <-
	.print("NEWDAY!");
	!live.

// Live if is infected
+!live: is_infected(adult)	 
	<- .print("live: ADULT INFECTED");
	!at(adult,hospital);
	!at(adult,home).

// Live on saturday
+!live : is_saturday
	<- .print("live: SATURDAY");
	!at(adult,sports);
	!at(adult,home);
	!at(adult,bar);
	!at(adult,home).

// Live on sunday
+!live : is_sunday
	<- .print("live: SUNDAY");
	!at(adult,park);
	!at(adult,home).	

// Live during week-day	
+!live : is_week
	<- .print("live: WEEKDAY -- Agent located at: " );
		!at(adult,job);
		!at(adult,school);
		!at(adult,home).

// Plan to move.
+!at(adult,P) : at(adult,P) <- true.
+!at(adult,P) : not at(adult,P)
	<- .print("MOVE TO: ", P); 
	move_towards(P);
	!at(adult,P).

	 
// ****************************************************************************
// NOT USED
/*	
+!live: is_low_responsible
	<- .print("live: +++++++++++++++++++++++++++++++++++++++++ A-RESPONSABLE: BAJO").
+!live: is_medium_responsible
	<- .print("live: +++++++++++++++++++++++++++++++++++++++++ A-RESPONSABLE: MEDIO").
+!live: is_high_responsible
	<- .print("live: +++++++++++++++++++++++++++++++++++++++++ A-sRESPONSABLE: ALTO").
*/

/*    
+!live : is_wday(DOM)
	<- .print("live: *********************SUNDAY");
	!at(adult,park);
	!at(adult,home).*/
	
		/*
+!live : is_weekend
	<- .print("live: WEEKEND");
	!at(adult,sports);
	!at(adult,park);
	!at(adult,home).*/
