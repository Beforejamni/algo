
import java.util.*;
import java.io.*;

public class BOJ_2805 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());

        //given
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] trees = new int[N];
        st= new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            trees[i] = Integer.parseInt(st.nextToken());
        }

        //when
        Arrays.sort(trees);
        
        long result = 0;
        
        long start = 1;
        long end = trees[N-1];
        while(start <= end){
            //최대 범위를 넘기지 않도록
            long mid = start + ((end - start) >> 1);
            if(check(trees, mid, M)){
                result = mid;
                start = mid +1;
            }else{
                end = mid -1;
            }
        }
        //then
        sb.append(result).append("\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static boolean check(int[] trees, long mid, long M){
        long sum = 0;
        for(long tree : trees){
            if(tree - mid > 0){
                sum += tree - mid;
            }
        } 
        return sum >= M;
    }
}
