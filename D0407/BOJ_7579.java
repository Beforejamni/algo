package D0407;

import java.util.*;
import java.io.*;
//앱
public class BOJ_7579 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int MAX = Integer.parseInt(st.nextToken());

        int[][] apps = new int[N+1][2];
        StringTokenizer memorys = new StringTokenizer(br.readLine());
        StringTokenizer costs = new StringTokenizer(br.readLine());

        int maxCost = 0;
        for(int i = 1; i <= N; i++){
            int m = Integer.parseInt(memorys.nextToken());
            int c = Integer.parseInt(costs.nextToken());
            apps[i] = new int[]{m, c};
            maxCost += c;
        }

        int[] dp = new int[maxCost+1];
        
        for(int i = 1; i <= N; i++){
            int m = apps[i][0];
            int c = apps[i][1];
            for(int j = maxCost; j >= c; j--){
                dp[j] = Math.max(dp[j] , dp[j - c] + m);
            }
        }
        int res = 0;
        for(int i = 0; i <= maxCost; i++){
            if(dp[i] >= MAX){
                res = i;
                break;
            }
        }
        sb.append(res).append("\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
