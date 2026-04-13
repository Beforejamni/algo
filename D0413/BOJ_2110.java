
import java.io.*;
import java.util.*;

public class BOJ_2110 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        //given
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] houses = new int[N];
        for(int i = 0; i < N; i++){
            houses[i] = Integer.parseInt(br.readLine());
        }
        
        //when
        Arrays.sort(houses);
        int result = 0;

        int start = 1;
        int end = houses[N-1] - houses[0];
        while(start <= end){
            int mid = start + ((end - start)>>1);
            if(installable(houses, mid, M)){
                result = mid;
                start = mid + 1;
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

    static boolean installable(int[] houses, int dist, int M){
        int cnt = 1;
        int last = houses[0];
        for(int i = 1; i < houses.length; i++){
            if(houses[i] - last >= dist){
                last = houses[i];
                cnt++;
            }
        }
        return cnt >= M;
    }
}
