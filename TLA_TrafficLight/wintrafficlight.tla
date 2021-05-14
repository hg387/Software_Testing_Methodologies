---------------------------- MODULE wintrafficlight ----------------------------
EXTENDS Integers, TLC

(*--algorithm traffic
    variables
        (* Tell TLA what colors are valid for lights *)
        colors = {"red", "green", "yellow"},
        (* Model the state of the lights, as a function from light number (0 or 1)
           to color *)
        lights  = [ l \in {0,1} |-> IF l=0 THEN "green" ELSE "red"],
        (* Which light gets to go green next *)
        next = 1;
    
    (* This section is used to define logical predicates /
       temporal properties that we would like to check of the model
       
       ValidColors *should* be an invariant, because we never set either
       color to "Chartreuse" or some other non-standard traffic light
       color.  You can check this using the model checker. *)
    define
        ValidColors == (\A l \in {0,1}: lights[l] \in colors)
        Safe == ((lights[0] = "red") \/ (lights[1] = "red")) 
        EventuallyGreen == [](<>(lights[0] = "green")) /\ [](<>(lights[1] = "green"))        
        EventuallyRed == [](<>(lights[0] = "red")) /\ [](<>(lights[1] = "red"))        
        EventuallyYellow == [](<>(lights[0] = "yellow")) /\ [](<>(lights[1] = "yellow"))        
    end define;
    
    (* This block defines the code for each light.  Both run the same code,
       but "self" is bound to 0 in one light and 1 in the other.
       
       The "fair+ process proc \in {0,1}" says there should be two processes
       (roughly, threads, or simultaneously running components), one for each 
       element of the set {0,1}.
    *)
    
    fair+ process proc \in {0,1}
    begin
        (* While loops in TLA+ must be labelled (a1) *)
        a1: while (TRUE) do
                \* Advance the state of the light when appropriate
        g1:     if (lights[self] = "green") then
        g2:         lights[self] := "yellow";  
                elsif (lights[self] = "yellow") then
        y2:         lights[self] := "red";
        y3:         next := self;
                elsif ((lights[self] = "red") /\ (next = self))  then
        r2:         await next /= self;
        r3:         lights[self] := "green";
                end if;
        rpt:    skip;
        
                
        end while;
    end process;

end algorithm;*)
\* BEGIN TRANSLATION
VARIABLES colors, lights, next, pc

(* define statement *)
ValidColors == (\A l \in {0,1}: lights[l] \in colors)
Safe == ((lights[0] = "red") \/ (lights[1] = "red"))
EventuallyGreen == [](<>(lights[0] = "green")) /\ [](<>(lights[1] = "green"))
EventuallyRed == [](<>(lights[0] = "red")) /\ [](<>(lights[1] = "red"))
EventuallyYellow == [](<>(lights[0] = "yellow")) /\ [](<>(lights[1] = "yellow"))


vars == << colors, lights, next, pc >>

ProcSet == ({0,1})

Init == (* Global variables *)
        /\ colors = {"red", "green", "yellow"}
        /\ lights = [ l \in {0,1} |-> IF l=0 THEN "green" ELSE "red"]
        /\ next = 1
        /\ pc = [self \in ProcSet |-> "a1"]

a1(self) == /\ pc[self] = "a1"
            /\ pc' = [pc EXCEPT ![self] = "g1"]
            /\ UNCHANGED << colors, lights, next >>

g1(self) == /\ pc[self] = "g1"
            /\ IF (lights[self] = "green")
                  THEN /\ pc' = [pc EXCEPT ![self] = "g2"]
                  ELSE /\ IF (lights[self] = "yellow")
                             THEN /\ pc' = [pc EXCEPT ![self] = "y2"]
                             ELSE /\ IF ((lights[self] = "red") /\ (next = self))
                                        THEN /\ pc' = [pc EXCEPT ![self] = "r2"]
                                        ELSE /\ pc' = [pc EXCEPT ![self] = "rpt"]
            /\ UNCHANGED << colors, lights, next >>

g2(self) == /\ pc[self] = "g2"
            /\ lights' = [lights EXCEPT ![self] = "yellow"]
            /\ pc' = [pc EXCEPT ![self] = "rpt"]
            /\ UNCHANGED << colors, next >>

y2(self) == /\ pc[self] = "y2"
            /\ lights' = [lights EXCEPT ![self] = "red"]
            /\ pc' = [pc EXCEPT ![self] = "y3"]
            /\ UNCHANGED << colors, next >>

y3(self) == /\ pc[self] = "y3"
            /\ next' = self
            /\ pc' = [pc EXCEPT ![self] = "rpt"]
            /\ UNCHANGED << colors, lights >>

r2(self) == /\ pc[self] = "r2"
            /\ next /= self
            /\ pc' = [pc EXCEPT ![self] = "r3"]
            /\ UNCHANGED << colors, lights, next >>

r3(self) == /\ pc[self] = "r3"
            /\ lights' = [lights EXCEPT ![self] = "green"]
            /\ pc' = [pc EXCEPT ![self] = "rpt"]
            /\ UNCHANGED << colors, next >>

rpt(self) == /\ pc[self] = "rpt"
             /\ TRUE
             /\ pc' = [pc EXCEPT ![self] = "a1"]
             /\ UNCHANGED << colors, lights, next >>

proc(self) == a1(self) \/ g1(self) \/ g2(self) \/ y2(self) \/ y3(self)
                 \/ r2(self) \/ r3(self) \/ rpt(self)

(* Allow infinite stuttering to prevent deadlock on termination. *)
Terminating == /\ \A self \in ProcSet: pc[self] = "Done"
               /\ UNCHANGED vars

Next == (\E self \in {0,1}: proc(self))
           \/ Terminating

Spec == /\ Init /\ [][Next]_vars
        /\ \A self \in {0,1} : SF_vars(proc(self))

Termination == <>(\A self \in ProcSet: pc[self] = "Done")

\* END TRANSLATION

=============================================================================
