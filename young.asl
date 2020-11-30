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
+!live : is_day(WEEK) 
	<- !at(young,job);
		!at(young,home).
	                                                           
                                                                                                                                    
+!live : is_day(WEEKEND) <- !at(young,bar).

//Plan to move.
+!at(young,P) : at(young,P) <- true.
+!at(young,P) : not at(young,P)
  <- move_towards(P);
     !at(young,P).
