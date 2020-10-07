### Overview
In this assignment you'll be doing open-box testing of a data structure library.
Primarily you'll be thoroughly testing the Red Black Tree implementation, achieving (almost) 100% statement and branch coverage for the relevant parts of the program.
You'll also be *briefly* analyzing the testability of the code and reflecting on its use of Design
by Contract.

### Control Flow Testing
Your goal for the first part of the assignment is to achieve (almost) 100% statement and branch coverage for the
relevant portion of the provided BST implementation.  To do this, you will need to write at least some tests for every individual method
in the class under test.

To measure the code coverage of your test suite from the command line, run

```
./gradlew test jacocoTestReport
```

It is important to run *both* ```test``` and ```jacocoTestReport```.  As with the previous
assignment, this is how we'll be grading your assignments, so make sure this works for you even if
you do your work with a different environment.  Running Eclipse's "Coverage As..." on the JUnit tests *should* produce the same results, but you should check this before you submit.

After running the above command, if all of your tests pass, there will be a file
```build/reports/coverage/index.html``` which you can open in your web browser to view coverage
information.  Most useful will probably be the file ```build/reports/coverage/edu.drexel.se320/RedBlackBST.java.html```, which displays the entire file's coverage information visually.

If you're using Eclipse, you can install the EclEmma plugin [http://www.eclemma.org/](http://www.eclemma.org/).  This appears to be pre-installed in the most recent version of Eclipse.

You are expected to produce a test suite that reaches 100% statement coverage and branch coverage on the relevant
portions of the code (more detail below).  However, we still
expect your testing coverage to be thorough, independent of the coverage rate.  In principle it is possible to attain 100% coverage
with a small number of very complicated inputs --- but if those tests ever fail they'll be
extremely difficult to debug, so we expect you to write properly sized, targeted test cases as in
your previous assignment.  We recommend starting with a closed-box testing approach, then using
coverage information to identify new tests to write that weren't dictated by the closed-box approach.

Every test should be a valid unit test.  It should follow proper test structure (arrange-act-assert), and you should be able to articulate what behavior each individual test method is checking.

#### What's relevant?
The ```RedBlackBST``` class contains a number of methods that are specifically to support integrity
checking on the BST.  Specifically, this is all code below line 660 in ```RedBlackBST.java```:

- ```check()```
- ```isBST()``` (two versions)
- ```isSizeConsistent()``` (two versions)
- ```isRankConsistent()```
- ```is23()``` (two versions)
- ```isBalanced()```
- ```select()``` (two versions)

For the methods listed above (all of which are below line 660 in the source code), you don't need to
reach full coverage.  The BST implementation is intended to be correct, while the integrity checking
code necessarily includes some branches that only run when they detect the tree structure is
invalid, which shouldn't occur.

For the methods above line 660, you should achieve 100% statement and branch coverage for lines that
are not ```assert``` statements, *with the exception of lines 230, 265, and 308*. 
Every assert statement should be executed, but you don't need to
reach full branch coverage on the assertions (the only way to do this is for the assert to fail,
which they shouldn't).  Thus, these ```assert``` lines may still be yellow when viewing coverage
information visually.

All non-```assert``` lines of code above line 660 should be fully covered (green), aside from lines 230, 265, and 308 (which should be yellow).

### Analysis (Given in the hw2WriteUp.pdf)
Please briefly reflect on three aspects of this code and testing:

1. Please tell us what parts of the code seem more or less testable, taking into account both our discussions in class and your experience having tested the code.  
2. This code uses assertions in a way that exploits some ideas of Design by Contract.  Consider whether the use of Java ```assert``` statements to implement precondition checks and object invariants (i.e., the ```check()``` method) is (1) consistent, and (2) helpful for testing (as in, does it affect the amount of work you need to do to test that the BST is behaving correctly)?
3. Assuming this code is part of a larger application (so, application internals), what about the testing above makes this *open-box* testing as opposed to closed-box?  Give an answer that does not specifically rely on the fact that you used code coverage (if we removed the code coverage part of this assignment, you'd still be doing open-box testing).

Just a few sentences for each point should suffice ---
the goal is not an essay, but if you feel you have to make a choice between being clear and being
concise, err on the side of providing a clear answer.


### Scaffolding
To (possibly) simplify things for you, we're providing scaffolding for you to use the gradle Java build tool for your project.  Gradle is similar to ant, but requires a bit less work to get going.  We're providing a template project that automatically downloads JUnit for you, and manages classpaths correctly when running ```gradle test```.  For many projects, simply adding proper dependency descriptions to the file ```build.gradle``` and placing a copy of your original project's source code in src/main/java/ will be enough for gradle to compile the project.  Extending or replacing the sample test file under src/test/java/ should make it easy to get started writing unit tests.

To make grading easier *we will be running your code via gradle*.  This means your code and test
suite need to work when we run ```./gradlew test``` in the directory containing ```build.gradle```.
This means your program source code must be in files under ```src/main/java/```, and your test code
must be somewhere under ```src/test/java/```.  Beyond that we don't have a preference for how you
organize your code into packages.  The template code already respects this directory layout, so if
you just put code in the same directories as the examples, it should work without issue.

That said, you don't need to use ```gradle``` yourself as you work through the project.  Eclipse,
NetBeans, and other Java IDEs should be able to work with the directory structure just fine, and
are capable of locating and running JUnit tests, though the details vary across IDEs.  You're
welcome to do development that way, and then make sure it still works via ```gradle``` shortly
before submitting.

#### Where Do I Get Gradle?
The project was made using gradle version 4.2.1, the latest version as of this writing.  You have
two options for getting a working gradle installation if you're using the command line:

1. Use the ```gradlew``` (or ```gradlew.bat``` on Windows) script that is part of the project
   (i.e., replace uses of ```gradle``` on the command line above with ```./gradlew``` or
   ```.\gradlew.bat```).  These scripts quietly download the appropriate version of Gradle to a
   folder within your project, and handle everything locally.  This is probably the easiest thing
   to do.
2. Install it yourself.  This is relatively easy on Mac and Windows.  On Linux, most distributions
   have very outdated versions for some reason.  You could install from source, or if you're using
   a variant of Ubuntu, you can install from a PPA (this is what I do): https://launchpad.net/~cwchien/+archive/ubuntu/gradle
