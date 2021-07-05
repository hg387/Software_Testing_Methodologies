1)  SE576HW4Reflection.pdf contains answers to the assignment questions.
2)  SE576 folders constains the source code of the programs.
3)  False Negative and False Positive infer execution trace are
    mentioned in their programs as comments with the explanation of bug
    and error.

Task Details:

This task gives you some hands-on experience with a non-trivial static
analysis tool. You may have already encountered this sort of tool at
work, but this assignment asks you to look at both some classes of
static analysis bugs that are less often reported, and to also look more
pointedly at false positives and false negatives than you would if you
were just fixing every analysis error that cropped up. For this
homework, you'll be using Facebook Infer, a comparatively newer static
analysis tool, and seeing how it behaves on some java code. We didn't
say much in class, but it's quickly gained some popularity since it was
open sourced a few years ago. We haven't talked about the formal
underpinnings of Infer (which are introduced gently online if you're
interested), but in terms of end results, it has a lot in common with
how KLEE and SAGE work. So most understanding of those systems'
strengths and weaknesses should apply to Infer as well.

Getting Infer There are a few ways to get Infer running locally, though
as is often the case it's harder on some platforms than others. • Macs
can install via Homebrew. • For Linux, there are instructions to install
a recent binary. • For Windows, the only "official" option for a local
install is to run infer inside a Docker container --- essentially inside
a constrained virtual machine running Debian. This is not ideal, since
files inside the container are not persistent: if you write code inside
the container, it's up to you to reliably get the code out, and this is
often quite error-prone. There are two alternatives to Docker on
Windows: 1. One alternative is to use the Try Infer in Your Browser
option, which may be preferable for those on other platforms as well,
though you should note the following: o CodeBoard is an external
service. You are not required to use it; if you're on Windows and would
rather not, I'd recommend the other alternative below. I do not know
exactly what data CodeBoard tracks on its users. o If you make an
account, it should allow you to save your own version of the Infer
project, which you can then copy into local files to submit your
homework.


o If you want to use CodeBoard, but don't want to make an account, you
could do everything as a modification of the demo project, and just be
sure to save your work locally on a regular basis. 2. You can install
Linux into a virtual machine, and use the Linux instructions. The
VirtualBox system is free if you don't already have something like
VMWare or access to Hyper-V (note that you cannot have multiple
hypervisors installed at the same time). There are a few ways to get
this running: o You can install yourself from the installation media of
a common distribution, like Ubuntu or Mint. o |'m currently in the
process of making a read-to-use virtual machine image with infer already
installed. Resources The main sources of documentation for Infer are the
Infer Docs. Of particular interest for this assignment, you might find
the following sections especially useful: • Infer Workflow • Analyzing A
ps or Projects • Checker BugTypegs, where you might be particularly
interested in the sections on: o Null dereference o Resource leaks

Getting Started Start out by getting comfortable with interpreting the
results of an Infer analysis. Start with their online demo (this is on
CodeBoard, but to just play with the demo without saving anything you
don't need to have an account). Click the "Analyze" button in the top
row, look at the errors, and see if you can figure out how to fix the
problems so the warnings go away. The docs above provide useful
background and details on the types of bugs you'll encounter there. This
part isn't graded, and if you really want to you could skip it, but I'd
strongly recommend you do this: there's only 3 bugs to fix, and the
answers are in the special bug sections called out in the resources
section above.

Running Infer

This assignment will have you running Infer on a small number of java
files, not large projects. If you use the CodeBoard, the Analyze button
will run the tool on all files. If you're running locally, assuming
you're in the director with one or more .java files, the following will
run Infer on all java files in that directory: infer -- javac \*.java
This assumes you have the regular java compiler from the SDK (not the
JRE!) installed; it shouldn't matter what version of java you're using.

The goal is to is to become familiar with using a static analysis tool.
So you might be expecting me to ask you to run the static analysis
engine on some code and fix the warnings. I've done this in the past and
most people find it pretty boring. (If you did the demo mentioned under
"Getting Started" you can imagine how much fun it would be to fix a few
hundred of those warnings.) Usually once you can interpret the error
messages from the static analysis tool, fixing them is not particularly
interesting. Instead, your task is to write code producing one false
positive from the analysis, and one false negative. Along the way,
you'll probably see a fair amount of what the analyzer does find
correctly. For example, in addition to finding the problems in the
tutorial, Infer will happily flag the null pointer dereference in the
following:

String s = "asdf'; if(((1 4) & 0x10) \>\> 4 1) { s = null; )
System.out.println(s.length());

For each false positive and false negative, the amount of code required
shouldn't be very large --- you should be able to write a handful of lin
For full credit, though, there are a couple restrictions you need to
obey: • No reflection. Reflection is a fundamentally hard problem, which
most analysis tools "solve" by basically ignoring it. You can do this
with regular java code. • Use standard java. Nothing Android-specific. I
don't care about java 8 vs. 9 vs. 10 vs. 1 1, though if you're using
something that only works on java 1 1 you should let me know so I make
sure I use the right java compiler.

False Positives

The most common usability problem static analysis tools encounter is
that they incorrectly complain about the potential for a bug that is not
possible. Infer is used at scale in a number of companies, and they have
worked very hard to minimize the occurrence of false positives. However,
all static analyses must sometimes approximate program behavior in order
to return results quickly, so there are a number of common things that
trip up static analyzers, such that they assume some impossible program
outcomes are possible. The most prevalent are:

• Loops: Most static analysis tools cannot do non-trivial reasoning
about loops, so if a program behavior depends on how many times a loop
repeats, the analysis will often over-simplify the semantics of the
loop. • Heap access: Most static analyzers, for performance reasons,
perform only very simple reasoning about heap data structures. So if you
can arrange for a program to be safe only because of some particular
details of a heap data structure, you can also confuse the tool • System
models: Static analysis tools, unlike heavyweight tools such as symbolic
executors, do not attempt to simulate behaviors of the OS. • Semantics
of data structures: Even for common data structures, tools may bake in
some knowledge of how those data work, but not full details. Yourjob is
to produce a source code file, FaIsePositive.java, that would never have
a problem when actually executed, but still causes Infer to complain
about a possible bug. I don't care what sort of false positive you find,
but I recommend focusing on causing a false positive for the null
dereference analysis; while it's one of the "smarter" analyses in Infer,
it's also one you can tackle without learning as many subtleties of some
more narrowly focused checker.

Note: It's possible to construct a false positive example by using any
one of the general weaknesses above, by itself; you don't need to
combine them, and if you try it will probably just make things harder.

False Negatives

One kind of shortcoming of static analysis tools is the false negative:
where the program does have a certain bug, but tool doesn't find it.
Write a file, FaIseNegative.java, that will dereference null, but causes
no complaints from Infer. In principle, the same things that lead to
false positives can also lead to false negatives, but in practice there
tends to be some difference. Static analysis tools broadly try to
overapproximate where bad values (e.g., null) can get to in a program;
this is what leads to false positives. However, if the overapproximation
is consistent, basically every part of a program gets flagged. So if a
data flow for a bad value is too complicated, there are points the
static analysis tool will generally give up and lose track of the bad
value. The most common places for this to happen are:

• Storing bad values in complicated heap structures • Using complex
features to cause data flows "outside" the language itself(see:
serialization) • Combining non-trivial control flow with non-trivial
data flow (e.g., heap structures + loops) to "hide" a value's movement
from the analysis Note that while I've phrased these approaches as
adversarial strategies against the analysis (since that's what they are
for this assignment), these are also things that real programs do for
good reasons. When you get a false negative working, please be sure to
add a block comment explaining what error you expect to see that Infer
is not finding.
