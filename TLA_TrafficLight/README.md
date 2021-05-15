This project explores model and specification development with TLA+, the
same toolkit that is used at Amazon, Microsoft, and othe places.

The file trafficlight.tla is a model of two traffic light, each watching
how the other behaves, waiting its turm to advance. Each light is
modeled as an independently executing process. This intially contains
two bugs, task is to find these bugs using TLA+ by writing properties.

Properties should be true for any reasonable two-way traffic light and
run TLC (TLA+ model checker) to see if the properties you want to be
true are voilated by this implementation.

More analysis is given in the reflection pdf.

General Advice

TLA+ is a tool where most of the efort to improve its capabilities not
its interface. There are many other languages such as PlusCal or Algol68
but these need to be translated into TLA so TLA+ is a better choice over
these for this project.

Getting Started

If you haven't already, download and install the TLA+ Toolbox, which is essentially an 
Eclipse-based IDE for writing TLA+ models and specifications.

Windows and Linux Things should work just fine. Mac OS
Make sure you look at this github issue to deal with an application signing problem: https://github 
com/tIapIus/tIapIus/issues/320
Loading the Project
File > Open Spec > Add New Spec... will give a dialog box. Navigate to where you unpacked the 
homework zip file, and select trafficlight.tIa.
Place your cursor inside the (*--algorithm ... end algorithm;*) block, and press Ctrl+T (or 
Command+T  on Mac) to translate the PlusCal code to TLA. The toolbox will emit a bunch of code into 
the buffer below the block you just translated. Every time you define or change a property, or 
tweak the code to fix a bug, you'll need to re-translate.
Now create a model checking configuration. Go to TLC Model Checker > New Model..., and give it a 
name (the name is for you to read, not the tool, so pick at will). This will create a new tab in 
the Toolbox. On the model overview tab, make sure to select the following:
•   Under "What is the behavior spec?" make sure "Temporal Formula" is selected, and the textbox 
below it says "Spec" (this should already be the default, just make sure)
•   Under "What to Check?":
o   Make sure "Deadlock" is checked (it should be by default). This way TLC will ensure the traffic 
light implementation doesn't get stuck.
o   Under "Invariants" click the "Add" button and type "ValidColors" (the name of a predicate 
defined in the code).
Now run this model, via the menu (TLC Model Checker > Run model), key (F1 1), or the green play/run 
button at the top of the Model Overview tab. You should see it do a bunch of work, and once the 
pop-up disappears you will see a Model Checking Results tab with a bunch of information. Among 
other things, at this point you should see "Errors Detected: No errors". 

Properties to Check
For each of the following properties, define the invariant or temporal formula, and run the model 
check to see if it's true of the traffic light model. See below for a recap of the difference 
between an invariant and a temporal formula.
1. Define an Invarlant Safe that requires one of the light directions to be red (i.e., either 
lights[0] is red, or lights[1] is red). (Using this as an invariant for the model checker will 
ensure it is true in all states, so one direction will always have red lights).
2. Define a temporal formula EventuallyGreen ensuring that each light is always eventually green.
3. Define a temporal formula EventuallyYellow ensuring that each light is always eventually yellow.
4. Define a temporal formula EventuallyRed ensuring that each light is always eventually red. 5
This was much harder than intended, so ignore it and
I'll give you the points.

The PlusCal code contains one or two mistakes depending on what you decide to change. It is 
possible that one of the bugs makes multiple of the above properties fail. When you know which 
properties are false, fix the model so the model checker succeeds in checking all invariants and 
temporal formulas. Each fix is a small change to a single line of code, so you'll make small 
changes to one or two lines of code.

Feeding them to the Model Checker

TLC considers two classes of formulas: invariants, and temporal properties.
An invariant is something that should be true in any state, and therefore only uses normal logical 
operators (and, or, not, implies) and predicates on state like checking if a value is in some set, 
as in the definition of ValidColors:

ValidColors == (\A 1 \in (0,1}: lights[1] \in colors)

This defines ValidColors as a predicate that is true when for all (\A) values I in (\in) the set 
(0,1}, the value of lights[I] is in (\in) the set colors, defined earlier as the normal set of US 
traffic light colors.
To have TLC check an invariant, add it to the same list you added ValidColors to earlier.
TLC considers any formula that uses a temporal operator --- such as always ([]) or eventually (<>) 
--- to be a temporal formula, because it talks about the state of the program over time 
(specifically, relating one state of the program to future states of the program).
Note: this suggests the property you must write as an invariant should not use temporal operators!
You could for example define the temporal formula requiring that light 0 is eventually green (this 
is similar to, but not quite the same as, the second specification you're asked for):


To check a temporal property with TLC, in the "Model Overview" tab, below where you add invariants, 
there's a section called "Properties".

Noa one property name you”a use  o cnecx just as you aia for invariants \maxe sure iz s cnecxea, as 
in, mere is a marx in one cnecx DOx for
it). You'll see an already-provided "Termination" property listed, which should not be checked, and 
for this example won't pass the model checker (since the traffic light should run forever).
TLC can check all of these properties simultaneously, which is handy if you're confident they're 
all true (which should be the case at the end of this assignment). But as you're adding new 
properties, it's best to check one additional property at a time, so if something breaks you know 
which property is not true of the system.

If  a  Property Fails

If one of your properties causes TLA+ to report an error, there are two possibilities:
•   You found a bug! or
•   You wrote the property incorrectly!
If TLC reports an error, the first thing you should do is convince yourself(again) that you have 
expressed the correct property --- the one   you intended. If you believe you have written the 
formal property definition that matches your intended meaning, look at the error trace   TLC 
produces, and see if the sequence of program states reported corresponds to a validation of the 
property you intended to write. (If you think you translated the property to TLA correctly, but 
don't think the erroneous trace should be a problem for what you were trying to check, either your 
translation or your understanding of the property you're checking needs to be refined.) If you're 
convinced the trace from the failure report is indeed a violation of the property, this is a good 
sign that you've understood the property, translated it to a formal specification correctly, and 
you can look for the bug that caused the property to fail.

More info about TLA+:

Chapters 1 & 2 of Wayne’s Practical TLA+ (39 pages, but many are
full-page figures, with larger font)

C. Newcombe, T. Rath, F. Zhang, B. Munteanu, M. Brooker, M. Deardeuff,
“How Amazon Web Services Uses Formal Methods,” Communications of the
ACM, 58(4), 2015.
