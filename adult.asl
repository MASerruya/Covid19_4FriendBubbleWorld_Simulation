!live.
                                                                                                                                  
//Plan to live depending on the week day.
/*
+!live: is_infected(adult)	 
	<- .print("live: ADULT INFECTED");
	!at(adult,hospital);
	!at(adult,home).*/
/*    
+!live : is_wday(DOM)
	<- .print("live: *********************SUNDAY");
	!at(adult,park);
	!at(adult,home).*/

+!live : is_saturday
	<- .print("live: SATURDAY");
	!at(adult,sports);
	!at(adult,home);
	!at(adult,bar);
	!at(adult,home).

+!live : is_sunday
	<- .print("live: SUNDAY");
	!at(adult,park);
	!at(adult,home).	
	/*
+!live : is_weekend
	<- .print("live: WEEKEND");
	!at(adult,sports);
	!at(adult,park);
	!at(adult,home).*/
	
+!live : is_week
	<- .print("live: WEEKDAY -- Agent located at: " );
		!at(adult,job);
		!at(adult,school);
		!at(adult,home).

// Plan to move.
+!at(adult,P) : at(adult,P) <- true.//true.
+!at(adult,P) : not at(adult,P)
  <- .print("MOVE TO: ", P); move_towards(P);
     !at(adult,P).

// New day
+new_day(adult) : true <- 
	.print("NEWDAY!"); 
	!live.
