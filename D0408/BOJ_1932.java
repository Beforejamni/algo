package D0408;

import java.util.*;
import java.io.*;

public class BOJ_1932 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;
    public static void main(String[] args) throws NumberFormatException, IOException {
        //given
        int N = Integer.parseInt(br.readLine());
        int[][] triangle = new int[N+1][N+1];
        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j <= i ; j++){
                if(!st.hasMoreTokens()) {
                    System.out.println("StringTokenzier is Null");
                }
                triangle[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        //when
        int[][] dp = new int[N+1][N+1];
        for(int i = 1; i <= N ; i++){
            for(int j = 1; j <= i; j++){
                // 이전층 좌우 최댓값
                int beforemax = dp[i-1][j-1]>dp[i-1][j]?dp[i-1][j-1]:dp[i-1][j];
                dp[i][j] = beforemax + triangle[i][j];
            }
        }

        //then
        int res = 0;
        for(int leaf : dp[N]){
            res = res>leaf?res:leaf;
        }

        sb.append(res).append("\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
        
    }
}
