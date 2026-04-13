
import java.io.*;
import java.util.*;

public class BOJ_1654{
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        long[] lenlines = new long[N];
        for(int i = 0; i < N; i++){
            lenlines[i] = Integer.parseInt(br.readLine());
        }
        
        Arrays.sort(lenlines);

        long result = 0;

        long left = 1;
        long right= lenlines[N-1];
 
        while(left <= right){
            long mid = (left + right)/2;
            if(checked(lenlines, mid, M)){
                result = mid;
                left = mid +1;
            }else {
                right = mid -1;
            }
        }

        sb.append(result).append("\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static boolean checked(long[] lens , long len, int M){
        long cnt = 0;
        for(long l : lens){
            cnt += (l / len);
        }
        return cnt >= M; 
    }

}