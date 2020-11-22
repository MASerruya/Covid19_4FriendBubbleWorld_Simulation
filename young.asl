
!at(young,bar).

+!at(young,P) : at(young,P) <- true.
+!at(young,P) : not at(young,P)
  <- move_towards(P);
     !at(young,P).
