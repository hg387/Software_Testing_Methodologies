import java.util.Random;

public class Benchmark {
    static int readCount = 0;
    static int insertCount = 0;
    static int deleteCount = 0;
    public static int OPS = 1000000;

    public static Random rand = new Random();

    public static void profile1(){
        System.out.println("\nProfile 1: A logging data structure, which mostly records new data, with occasional queries and edits (deletions)");
        int reads = 10;
        int inserts = 80;
        int deletes = 10;

        long warmupStart = System.nanoTime();
        runOps(reads, inserts, deletes);
        long warmupElapsed = System.nanoTime() - warmupStart;
        System.out.println("Warmup round:    "+String.format("%12s",warmupElapsed)+"ns");
        System.out.println("Warmup round, Reads: "
                + readCount + " , Inserts: " + insertCount +" , Deletes: " + deleteCount +
                ", Total: "+ (readCount + insertCount + deleteCount));
        readCount = 0;
        insertCount = 0;
        deleteCount = 0;

        long[] elapsed = new long[10];
        for (int i = 0; i < 10; i++) {
            long startTime = System.nanoTime();
            runOps(reads, inserts, deletes);
            long estimated = System.nanoTime() - startTime;
            elapsed[i] = estimated;
            System.out.println("Benchmark Number: " + i + " , Reads: "
                    + readCount + " , Inserts: " + insertCount +" , Deletes: " + deleteCount +
                    ", Total: "+ (readCount + insertCount + deleteCount));

            readCount = 0;
            insertCount = 0;
            deleteCount = 0;
        }

        /* Print the times, as well as the max, min, and average */
        long total = 0;
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for (int i = 0; i < 10; i++) {
            System.out.println("Benchmark run "+i+": "+String.format("%12s",elapsed[i])+"ns");
            total += elapsed[i];
            if (elapsed[i] < min) min = elapsed[i];
            if (elapsed[i] > max) max = elapsed[i];
        }
        System.out.println("Average for "+OPS+" operations: "+String.format("%12s",(total/10))+"ns");
        System.out.println("Minimum for "+OPS+" operations: "+String.format("%12s",min)+"ns");
        System.out.println("Maximum for "+OPS+" operations: "+String.format("%12s",max)+"ns");
    }

    public static void profile2() {
        System.out.println("\nProfile 2: A read-heavy database, with occasional updates and deletions.");
        int reads = 80;
        int inserts = 10;
        int deletes = 10;

        long warmupStart = System.nanoTime();
        runOps(reads, inserts, deletes);
        long warmupElapsed = System.nanoTime() - warmupStart;
        System.out.println("Warmup round:    "+String.format("%12s",warmupElapsed)+"ns");
        System.out.println("Warmup round, Reads: "
                + readCount + " , Inserts: " + insertCount +" , Deletes: " + deleteCount +
                ", Total: "+ (readCount + insertCount + deleteCount));
        readCount = 0;
        insertCount = 0;
        deleteCount = 0;
        long[] elapsed = new long[10];
        for (int i = 0; i < 10; i++) {
            long startTime = System.nanoTime();
            runOps(reads, inserts, deletes);
            long estimated = System.nanoTime() - startTime;
            elapsed[i] = estimated;
            System.out.println("Benchmark Number: " + i + " , Reads: "
                    + readCount + " , Inserts: " + insertCount +" , Deletes: " + deleteCount +
                    ", Total: "+ (readCount + insertCount + deleteCount));

            readCount = 0;
            insertCount = 0;
            deleteCount = 0;
        }

        /* Print the times, as well as the max, min, and average */
        long total = 0;
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for (int i = 0; i < 10; i++) {
            System.out.println("Benchmark run "+i+": "+String.format("%12s",elapsed[i])+"ns");
            total += elapsed[i];
            if (elapsed[i] < min) min = elapsed[i];
            if (elapsed[i] > max) max = elapsed[i];
        }
        System.out.println("Average for "+OPS+" operations: "+String.format("%12s",(total/10))+"ns");
        System.out.println("Minimum for "+OPS+" operations: "+String.format("%12s",min)+"ns");
        System.out.println("Maximum for "+OPS+" operations: "+String.format("%12s",max)+"ns");
    }

    public static void profile3() {
        System.out.println("\nProfile 3: A read-only database, with no modifications performed.");
        int reads = 100;
        int inserts = 0;
        int deletes = 0;

        long warmupStart = System.nanoTime();
        runOps(reads, inserts, deletes);
        long warmupElapsed = System.nanoTime() - warmupStart;
        System.out.println("Warmup round:    "+String.format("%12s",warmupElapsed)+"ns");
        System.out.println("Warmup round, Reads: "
                + readCount + " , Inserts: " + insertCount +" , Deletes: " + deleteCount +
                ", Total: "+ (readCount + insertCount + deleteCount));
        readCount = 0;
        insertCount = 0;
        deleteCount = 0;
        long[] elapsed = new long[10];
        for (int i = 0; i < 10; i++) {
            long startTime = System.nanoTime();
            runOps(reads, inserts, deletes);
            long estimated = System.nanoTime() - startTime;
            elapsed[i] = estimated;
            System.out.println("Benchmark Number: " + i + " , Reads: "
                    + readCount + " , Inserts: " + insertCount +" , Deletes: " + deleteCount +
                    ", Total: "+ (readCount + insertCount + deleteCount));

            readCount = 0;
            insertCount = 0;
            deleteCount = 0;
        }

        /* Print the times, as well as the max, min, and average */
        long total = 0;
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for (int i = 0; i < 10; i++) {
            System.out.println("Benchmark run "+i+": "+String.format("%12s",elapsed[i])+"ns");
            total += elapsed[i];
            if (elapsed[i] < min) min = elapsed[i];
            if (elapsed[i] > max) max = elapsed[i];
        }
        System.out.println("Average for "+OPS+" operations: "+String.format("%12s",(total/10))+"ns");
        System.out.println("Minimum for "+OPS+" operations: "+String.format("%12s",min)+"ns");
        System.out.println("Maximum for "+OPS+" operations: "+String.format("%12s",max)+"ns");
    }

    public static void runOps(int reads, int inserts, int deletes) {
        RedBlackBST<Integer,Integer> tree = new RedBlackBST<Integer,Integer>();
        int insert_bound = reads + inserts;

        int lastn = 0;


        for(int i = 0; i < OPS; i++) {
            int v = rand.nextInt(100);
            if (v < reads) {
                // do read
                tree.get(lastn);
                readCount++;
            } else if (v < insert_bound) {
                // do insertion of random key
                tree.put(lastn, lastn);
                insertCount++;
            } else {
                // do deletion of random key
                tree.delete(lastn);
                deleteCount++;
            }
            lastn = v;
        }
    }

    public static void main(String[]  args){
        profile1();
        profile2();
        profile3();
    }
}




