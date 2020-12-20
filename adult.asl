// Initial plan to live
!live.

// Starting a new day
+new_day : true <-
	!live.	

// Live if is infected
+!live: is_infected & not is_patient0	 
	<- !at(hospital);
	do_things(hospital).

// Live if the agent is in quarentine, stay at home
+!live: quarentine <- true.
                                                  
// Living if the young is recovered	
+!live: recovered  
	<-  !go_home
	do_things(home).                        

// Plan to move to each agent's home
+!go_home : is_home1
	<- !at(home1).
+!go_home : is_home2
	<- !at(home2).
+!go_home : is_home3
	<- !at(home3).
+!go_home : is_home4
	<- !at(home4).
	
// Plan to move.
+!at(P) : at(P) <- true.
+!at(P) : not at(P)
	<-move_towards(P);
	!at(P).

// Plans to tell the other agent that i am infected
+at(hospital): is_infected & is_home1 <- .broadcast(achieve, quarantine(home1)).
+at(hospital): is_infected & is_home2 <- .broadcast(achieve, quarantine(home2)).
+at(hospital): is_infected & is_home3 <- .broadcast(achieve, quarantine(home3)).
+at(hospital): is_infected & is_home4 <- .broadcast(achieve, quarantine(home4)).

// Plans by home when quarentine
// When the broadcast is received, in case there is house 

// If the agent is high responsible, it goes home and quarentines
+!quarantine(home1) : is_home1 & is_high_responsible <- .succeed_goal(live); add_quarentine; !go_home.
+!quarantine(home2) : is_home2 & is_high_responsible <- .succeed_goal(live); add_quarentine; !go_home.
+!quarantine(home3) : is_home3 & is_high_responsible <- .succeed_goal(live); add_quarentine; !go_home.
+!quarantine(home4) : is_home4 & is_high_responsible <- .succeed_goal(live); add_quarentine; !go_home.

// If the agent is medium responsible, it will only quarentine next da; 
+!quarantine(home1) : is_home1 & is_medium_responsible <- add_quarentine.
+!quarantine(home2) : is_home2 & is_medium_responsible <- add_quarentine.
+!quarantine(home3) : is_home3 & is_medium_responsible <- add_quarentine.
+!quarantine(home4) : is_home4 & is_medium_responsible <- add_quarentine.

// Low responsible keep movin or not the case
+!quarantine(home1) : true <- true.
+!quarantine(home2) : true <- true.
+!quarantine(home3) : true <- true.
+!quarantine(home4) : true <- true.

+!live : is_monday & r1
	<- !at(job);
	do_things(job);
	!at(sports);
	do_things(sports);
	!go_home.

+!live : is_monday & r3
	<- !at(bar);
	do_things(bar);
	!at(park);
	do_things(park);
	!go_home.		
	
+!live : is_monday
	<- !at(job);
	do_things(job);
	!at(bar);
	do_things(bar);
	!go_home.


+!live : is_tuesday & r2
	<- !at(job);
	do_things(job);
	!go_home.	
	
+!live : is_tuesday & r3
	<- !at(park);
	do_things(park);
	!at(sports);
	do_things(sports);
	!go_home.

+!live : is_tuesday
	<- !at(job);
	do_things(job);
	!at(sports);
	do_things(sports);
	!go_home.		
	
+!live : is_wednesday & r3
	<- !at(bar);
	do_things(bar);
	!at(park)
	do_things(park);
	!go_home.	
	
+!live: is_wednesday
	<- !at(job);
	do_things(job);
	!go_home.

+!live : is_thursday & r3
	<- !at(sports);
	do_things(sports);
	!at(park);
	do_things(park);
	!go_home.	
	
+!live: is_thursday & r2
	<- !at(job);
	do_things(job);
	!at(bar);
	do_things(bar);
	!go_home.

+!live: is_thursday
	<- !at(job);
	do_things(job);
	!at(park);
	do_things(park);
	!go_home.

+!live : is_friday & r3
	<- !at(sports);
	do_things(sports);
	!at(bar);
	do_things(bar);
	!go_home.	
	
+!live: is_friday & r1
	<- !at(job);
	do_things(job);
	!at(bar);
	do_things(bar);
	!go_home.

+!live: is_friday
	<- !at(job);
	do_things(job);
	!at(park);
	do_things(park);
	!go_home.

+!live : is_friday & r3
	<- !at(park);
	do_things(park);
	!at(bar);
	do_things(bar);
	!go_home.	
	
+!live: is_friday & r2
	<- !at(job);
	do_things(job);
	!at(bar);
	do_things(bar);
	!go_home.

+!live: is_friday
	<- !at(job);
	do_things(job);
	!at(park);
	do_things(park);
	!go_home.


+!live: is_saturday & r2
	<- !at(park);
	do_things(park);
	!at(sports);
	do_things(sports);
	!go_home.

+!live : is_saturday 
	<- !at(bar);
	do_things(bar);
	!at(sports);
	do_things(sports);
	!go_home.	

+!live: is_sunday & r1
	<- !at(bar);
	do_things(bar);
	!at(sports);
	do_things(sports);
	!go_home.

+!live : is_sunday 
	<- !at(park);
	do_things(park);
	!at(bar);
	do_things(bar);
	!go_home.
