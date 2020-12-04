//// initially, I believe that there is the start of the week
////!at(young,home).
//
//// Initially, I believe that there is the start of the week
//routine(young, 0).
//
//// We add the goal: 7 days cycle
//!routine(young, 7).
//
///* Plans */
//
//// Moving plan
//+!at(young,P) : at(young,P) <- true.
//+!at(young,P) : not at(young,P)
//  <- move_towards(P);
//     !at(young,P).
//
//// Routine plan
//+!routine(young, 7)
//	: routine(young, 0) 
//	<-  !at(young, home); 
//		!at(young, job); 
//		!at(young, bar).

!live.
                                                                                                                                  
//Plan to live depending on the week day.


+!live: is_infected(young)	 
	<- .print("live: INFECTED");!at(young,hospital); !at(young,home).
                                                                                                                                    
+!live : is_weekend
	<- .print("live: WEEKEND");!at(young,bar); !at(young,home).



+!live : is_week
	<- .print("live: WEEKDAY -- Agent located at: " );!at(young,job);
		!at(young,home).

//Plan to move.
+!at(young,P) : at(young,P) <- true.//true.
+!at(young,P) : not at(young,P)
  <- .print("MOV TO: ", P); move_towards(P);
     !at(young,P).

//New day
+new_day(young) : true <- .print("NEWDAY!"); !live.