

public class CombinationLock {

    protected int[] lock;

    public CombinationLock(int[] input) {
        lock = new int[input[0]];
        for(int i=0;i<input[0];i++){
            lock[i] = i;
        }
    }

    public static void main(String[] args) {
        System.out.println(printSolution(5,1,2,3));
    }



    public static int printSolution(int N, int A, int B, int C) {
        return 3*N+A + (A>B ? A-B : A+N-B) +
                (C>B ? C-B : C+N-B);
    }

}