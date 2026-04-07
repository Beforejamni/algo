package D0406;

import java.util.*;
import java.io.*;

public class SWEA3282 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;
    public static void main(String[] args) throws NumberFormatException, IOException {
        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <=T; t++){
            sb.append("#").append(t).append(" ");
            st = new StringTokenizer(br.readLine());
            int N =Integer.parseInt(st.nextToken());
            int MAX = Integer.parseInt(st.nextToken());

            int[][] goods = new int[N+1][2];
            int[][] dp = new int[N+1][MAX+1];
            for(int i = 1; i <= N; i++){
                st = new StringTokenizer(br.readLine());
                int weight = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                goods[i][0] = weight;
                goods[i][1] = value;
            }
            for(int[] a : dp){
                System.out.println(Arrays.toString(a));
            }
        

            for(int i = 1; i <= N; i++){
                for(int j = 1; j <= MAX ; j++){
                    int weight = goods[i][0];
                    int value = goods[i][1];
                    if( j < weight){
                        dp[i][j] = dp[i-1][j];
                        continue;
                    }
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-weight] +value);
                }
            }
            int res = dp[N][MAX];

            for(int[] a : dp){
                System.out.println(Arrays.toString(a));
            }
            sb.append(res).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
