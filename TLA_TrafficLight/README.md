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

More info about TLA+:

Chapters 1 & 2 of Wayne’s Practical TLA+ (39 pages, but many are
full-page figures, with larger font)

C. Newcombe, T. Rath, F. Zhang, B. Munteanu, M. Brooker, M. Deardeuff,
“How Amazon Web Services Uses Formal Methods,” Communications of the
ACM, 58(4), 2015.
