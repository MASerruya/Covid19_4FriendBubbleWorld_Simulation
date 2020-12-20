// Initial plan to live
!live.

// Starting a new day
+new_day : true <-
	.print("NEWDAY for Adults");
	!live.

// Live if is infected
+!live: is_infected	 
	<- .print("live: ADULT INFECTED");
	!at(adult,hospital);
	!go_home.

// Live if the agent is in quarentine, stay at home
+!live: quarentine <- true.

// Live on saturday
+!live : is_saturday
	<-!at(adult,sports);
	!go_home;
	!at(adult,bar);
	!go_home.

// Live on sunday
+!live : is_sunday
	<-!at(adult,park);
	!go_home.	

// Live during week-day	
+!live : is_week
	<- !at(adult,job);
	!at(adult,school);
	!go_home.

//Plan to move to each agent's home
+!go_home : is_home1
	<- !at(adult,home1).
+!go_home : is_home2
	<- !at(adult,home2).
+!go_home : is_home3
	<- !at(adult,home3).
+!go_home : is_home4
	<- !at(adult,home4).
+!go_home : is_home5
	<- !at(adult,home5).

// Plan to move.
+!at(adult,P) : at(adult,P) <- true.
+!at(adult,P) : not at(adult,P)
	<-move_towards(P);
	!at(adult,P).

	//Plans to tell the other agent that i am infected
+is_infected(adult) : is_home1 <- .broadcast(achieve, quarantine(home1)).
+is_infected(adult) : is_home2 <- .broadcast(achieve, quarantine(home2)).
+is_infected(adult) : is_home3 <- .broadcast(achieve, quarantine(home3)).
+is_infected(adult) : is_home4 <- .broadcast(achieve, quarantine(home4)).
+is_infected(adult) : is_home5 <- .broadcast(achieve, quarantine(home5)).

//Plans by home when quarentine
//When the broadcast is received, in case there is house 

//IF the agent is high responsible, it goes home and quarentines
+!quarantine(home1) : is_home1 & is_high_responsible <- add_quarentine; !go_home.
+!quarantine(home2) : is_home2 & is_high_responsible <- add_quarentine; !go_home.
+!quarantine(home3) : is_home3 & is_high_responsible <- add_quarentine; !go_home.
+!quarantine(home4) : is_home4 & is_high_responsible <- add_quarentine; !go_home.
+!quarantine(home5) : is_home5 & is_high_responsible <- add_quarentine; !go_home.

//If the agent is medium responsible, it will only quarentine next day
+!quarantine(home1) : is_home1 & is_medium_responsible <- add_quarentine.
+!quarantine(home2) : is_home2 & is_medium_responsible <- add_quarentine.
+!quarantine(home3) : is_home3 & is_medium_responsible <- add_quarentine.
+!quarantine(home4) : is_home4 & is_medium_responsible <- add_quarentine.
+!quarantine(home5) : is_home5 & is_medium_responsible <- add_quarentine.

//Low responsible keep moving
