
### Overview
The goal for this assignment is to expose you to concepts and tools related to performance testing.

Your assignment is to choose reasonable operational profiles, explain the key ideas in provided benchmarking code, measure times for some operational profiles, and explain the consequences in terms of how much load a hypothetical system might handle.

### Experiments
You've been provided with an implementation of a red-black binary search tree, which should look familiar from a previous homework.

Attached to this assignment, you'll find ```RedBlackBST.java``` (the red-black tree), and ```Benchmark.java```, which contains a simple benchmarking harness.  In ```Benchmark```, given a hard-coded operational profile for a mix of lookups, insertions, and deletions from the tree, the code performs a warmup run, then 10 times it will run a random mix of 1,000,000 (one million) operations distributed roughly according to the operational profile.  When Benchmark.main() is run, it will print to the console the running time for each iteration (including warmup), and the min, max, and average of the non-warmup runs.

### Submission
For this assignment, your submission will only be a write-up --- no code.
You'll need to provide, in one PDF, RTF, or TXT: (Given in the hg387Hw4.pdf)

1. A short (one paragraph) explanation of why the benchmark harness is structured as it is.  Why is there an initial run that isn't counted in the min/max/average?  Why are we measuring multiple runs?
2. Reasonable operational profiles for use of the RedBlackBST in the following use cases.  Each of these profiles, for our purposes, is simply a percentage of insert/delete/lookup operations (3 numbers that sum to 100 in each case).
    a. A logging data structure, which mostly records new data, with occasional queries and edits (deletions)
    b. A read-heavy database, with occasional updates and deletions.
    c. A read-only database, with no modifications performed.
3. A description of what steps you took in preparation for your performance measurements.  For example, what other software is or isn't running on your machine?  (For this assignment, you may ignore our discussion of frequency scaling's effect on performance tests, since disabling it is quite inconvenient and many of you are probably using laptops.)
4. For each of your operational profiles, report the following, which should be produced by Benchmark.main() once you update the hard-coded operational profile.
    a. Warmup time
    b. Minimum iteration time
    c. Maximum iteration time
    d. Average iteration time
5. Which of your operational profiles has higher *throughput* (i.e., performs work work per unit
   time)?  What about the red-black tree might explain this outcome?
6. Are your warmup times noticeably different from the "main" iteration times?  Most likely yes, but either way, why might we *expect* a significant difference?
7. The numbers for operational profile (c) aren't actually that useful.  Look a the benchmarking code, and explain why those numbers, as reported, tell us nothing about the performance of using the BST in a real application for a read-only workload.
8. Assume each run (each call to ```runOps```) simulates the activity of one remote request.  Based on the average execution time for each operational profile, what would the throughput be for each of your profiles?  (i.e., requests/second)
9. Assuming each remote user makes 5 requests/minute, your program's resource usage scales linearly, and we are only interested in CPU execution time, how many concurrent remote users could you support on your machine (again, do this for each operational profile) without degrading performance or overloading the system?
10. What aspects of load are we not testing, which could possibly reduce the capacity of your machine to service requests?

