These projects are implemented to learn n-depth coverage of testing and
other software validation techniques intended to produce reliable,
correct software. Topics include formal and informal specification, core
testing techniques, approaches to comparing quality of test suites,
principles of reasoning about software correctness, guided automatic
test generation, designing software to support validation, and using
current and future tools to automatically validate or find bugs in
software.

These strikes a balance between teaching principles of reasoning about
programs, techniques in current use, and providing a basis for
understanding cutting-edge techniques still in early stages of adoption.

Some projects use TLA+ involving formal specification and model
checking. The toolkit itself is fairly mature, has a freely available
book (Specifying Systems: The TLA+ Language and Tools for Hardware and
Software Engineers), and has a nice tutorial site (Learn TLA+) written
by the author of another nice (but not free) book. Both of these
technologies, while new to most students, are truly and increasingly
used in industry. We will read about how TLA+ has been used prominently
in Amazon Web Services, and from there it has spread widely among
distributed systems developers.

things we will discuss are actually common practice in Scala codebases.
TLA+ has been around for years, but the past 6-8 years have seen a sharp
increase in industry usage.

Achieved Objectives: -\> Clearly specify what correctness means for a
given software system, and modularly for its components.

-\> Design software in ways that make fine-grained testing and informal
reasoning about correctness easier than alternative designs.

-\> Write (manually) suites of tests that validate specific portions of
specifications.

-\> Understand the strengths and weaknesses of various automated
validation techniques, such as test generation and static analysis

-\> Select appropriate validation methods for a given project

-\> Clearly and persuasively argue that some validation technique (e.g.,
test suite) adequately exercises both the relevant program component and
its specification.

-\> Understand the core principles of reasoning about whether software
is correct.

-\> Effectively apply advanced analysis tools that semi-automatically
generate tests to validate a specification, or systematically search for
a violation of a specification.
