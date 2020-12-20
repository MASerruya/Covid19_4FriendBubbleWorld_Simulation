// Initial plan to live
!live.

// Starting a new day
+new_day : true <-
	.print("NEWDAY for Adults");
	!live.

// Live if is infected
+!live: is_infected	 
	<- .print("live: ADULT INFECTED");
	!at(hospital);
	do_things(hospital).

// Live if the agent is in quarentine, stay at home
+!live: quarentine <- true.

// Living if the adult is recovered	
+!live: recovered  
	<-  !go_home
	do_things(home).                        

// Live on saturday
+!live : is_saturday
	<-!at(sports);
	!go_home;
	!at(bar);
	!go_home.

// Live on sunday
+!live : is_sunday
	<-!at(park);
	!go_home.	

// Live during week-day	
+!live : is_week
	<- !at(job);
	!at(school);
	!go_home.

//Plan to move to each agent's home
+!go_home : is_home1
	<- !at(home1).
+!go_home : is_home2
	<- !at(home2).
+!go_home : is_home3
	<- !at(home3).
+!go_home : is_home4
	<- !at(home4).
+!go_home : is_home5
	<- !at(home5).

// Plan to move.
+!at(P) : at(P) <- true.
+!at(P) : not at(P)
	<-move_towards(P);
	!at(P).

//Plans to tell the other agent that i am infected
+at(hospital): is_infected & is_home1 <- .broadcast(achieve, quarantine(home1)).
+at(hospital): is_infected & is_home2 <- .broadcast(achieve, quarantine(home2)).
+at(hospital): is_infected & is_home3 <- .broadcast(achieve, quarantine(home3)).
+at(hospital): is_infected & is_home4 <- .broadcast(achieve, quarantine(home4)).
+at(hospital): is_infected & is_home5 <- .broadcast(achieve, quarantine(home5)).

//Plans by home when quarentine
//When the broadcast is received, in case there is house 

//IF the agent is high responsible, it goes home and quarentines
+!quarantine(home1) : is_home1 & is_high_responsible <- .succeed_goal(live); add_quarentine; !go_home.
+!quarantine(home2) : is_home2 & is_high_responsible <- .succeed_goal(live); add_quarentine; !go_home.
+!quarantine(home3) : is_home3 & is_high_responsible <- .succeed_goal(live); add_quarentine; !go_home.
+!quarantine(home4) : is_home4 & is_high_responsible <- .succeed_goal(live); add_quarentine; !go_home.
+!quarantine(home5) : is_home5 & is_high_responsible <- .succeed_goal(live); add_quarentine; !go_home.
//If the agent is medium responsible, it will only quarentine next day
+!quarantine(home1) : is_home1 & is_medium_responsible <- add_quarentine.
+!quarantine(home2) : is_home2 & is_medium_responsible <- add_quarentine.
+!quarantine(home3) : is_home3 & is_medium_responsible <- add_quarentine.
+!quarantine(home4) : is_home4 & is_medium_responsible <- add_quarentine.
+!quarantine(home5) : is_home5 & is_medium_responsible <- add_quarentine.

//Low responsible keep movin or not the case
+!quarantine(home1) : true <- true.
+!quarantine(home2) : true <- true.
+!quarantine(home3) : true <- true.
+!quarantine(home4) : true <- true.
+!quarantine(home5) : true <- true.

