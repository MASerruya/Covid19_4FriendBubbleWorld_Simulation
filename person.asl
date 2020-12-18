// Initial plan to live
!live.

// Starting a new day
+new_day(person) : true <-
	.print("NEWDAY!");
	!live.
			
// Live if is infected: High responsible
+!live: is_infected(person) //& is_high_responsible	 
	<- .print("live: YOUNG HR INFECTED");
	!at(person,hospital);
	do_things(hospital).
                                                  
	
// Living if the young is recovered	
+!live: recovered  
	<-  !go_home
	do_things(home).                        

// Plan of living if is a weekend day                                                                                             
+!live : is_weekend
	<- //.print("live: WEEKEND");
	!at(person,bar);                                                                                                 
	do_things(bar);
	!go_home.

// Plan of living if is a week day
+!live : is_week
	<- //.print("live: WEEKDAY -- Agent located at: " );
	!at(person,park);
	do_things(park);
	!go_home.	

//Plan to move to each agent's home
+!go_home : is_home1
	<- !at(person,home1).
+!go_home : is_home2
	<- !at(person,home2).
+!go_home : is_home3
	<- !at(person,home3).
+!go_home : is_home4
	<- !at(person,home4).
+!go_home : is_home5
	<- !at(person,home5).
	
// Plan to move.
+!at(person,P) : at(person,P) <- true.
+!at(person,P) : not at(person,P)
	<- //.print("MOVE TO: ", P); 
	move_towards(P);
	!at(person,P).

	
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
	
