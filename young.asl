// Initial plan to live
!live.

// Starting a new day
+new_day(young) : true <-
	.print("NEWDAY!");
	!live.
			
// Live if is infected: High responsible
+!live: is_infected(young) & not is_patient0 //& is_high_responsible	 
	<- .print("live: YOUNG HR INFECTED");
	!at(young,hospital);
	do_things(hospital).

// Live if the agent is in quarentine, stay at home
+!live: quarentine <- true.
                                                  
	
// Living if the young is recovered	
+!live: recovered  
	<-  !go_home
	do_things(home).                        

// Plan of living if is a weekend day                                                                                             
+!live : is_weekend
	<- //.print("live: WEEKEND");
	!at(young,bar);                                                                                                 
	do_things(bar);
	!go_home.

// Plan of living if is a week day
+!live : is_week
	<- //.print("live: WEEKDAY -- Agent located at: " );
	!at(young,park);
	do_things(park);
	!go_home.	

//Plan to move to each agent's home
+!go_home : is_home1
	<- !at(young,home1).
+!go_home : is_home2
	<- !at(young,home2).
+!go_home : is_home3
	<- !at(young,home3).
+!go_home : is_home4
	<- !at(young,home4).
+!go_home : is_home5
	<- !at(young,home5).
	
// Plan to move.
+!at(young,P) : at(young,P) <- true.
+!at(young,P) : not at(young,P)
	<- //.print("MOVE TO: ", P); 
	move_towards(P);
	!at(young,P).

//Plans to tell the other agent that i am infected
+is_infected(young) : is_home1 <- .broadcast(achieve, quarantine(home1)).
+is_infected(young) : is_home2 <- .broadcast(achieve, quarantine(home2)).
+is_infected(young) : is_home3 <- .broadcast(achieve, quarantine(home3)).
+is_infected(young) : is_home4 <- .broadcast(achieve, quarantine(home4)).
+is_infected(young) : is_home5 <- .broadcast(achieve, quarantine(home5)).

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


// ****************************************************************************
// NOT USED	
/*+!live: is_low_responsible
	<- .print("live: +++++++++++++++++++++++++++++++++++++++++ Y-RESPONSABLE: BAJO").
+!live: is_medium_responsible
	<- .print("live: +++++++++++++++++++++++++++++++++++++++++ Y-RESPONSABLE: MEDIO").
+!live: is_high_responsible                                                                            
	<- .print("live: +++++++++++++++++++++++++++++++++++++++++ Y-RESPONSABLE: ALTO").*/
	
	
/*
limit_low_responsible(infection_days,1).
limit_medium_responsible(infection_days,2).
limit_high_responsible(infection_days,3).*/	



/*
// Live if is infected: Low responsible
+!live: is_infected(young) //& is_low_responsible	 
	<- .print("live: YOUNG LR INFECTED");
	.count(new_day(young),DAYS) 
	.print("DIAS................................................" );
	.print(DAYS);
	!at(young,hospital);
	.random(X); .wait(X*5000+2000);
	-is_infected(young);
	!at(young,home).  */    
/*	
// Live if is infected: Medium responsible
+!live: is_infected(young) & is_medium_responsible
	<- .print("live: YOUNG MR INFECTED");
	!at(young,hospital);
	.random(X); .wait(X*5000+2000);
	-is_infected(young);
	!at(young,home).  */   
	
