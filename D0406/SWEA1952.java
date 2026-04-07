package D0406;

import java.util.*;
import java.io.*;

public class SWEA1952 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws NumberFormatException, IOException {
        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            sb.append("#").append(t).append(" ");
            int[] price = new int[4];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < price.length; i++) {
                price[i] = Integer.parseInt(st.nextToken());
            }
            int[] month = new int[13];
            st = new StringTokenizer(br.readLine());
            for(int i = 1; i < month.length; i++){
                month[i] = Integer.parseInt(st.nextToken());
            }

            int[] dp = new int[13];
            for (int i = 1; i < dp.length; i++) {
                // 1일 권일 경우
                int daycost = dp[i -1] + month[i] * price[0];
                // 1달 권일 경우
                int monthcost = dp[i -1] + price[1];
                
                // 1일권과 1달권 비교
                int mincost = Math.min(daycost, monthcost);
                
                int quatercost = Integer.MAX_VALUE;
                // 3달권 가능 시점
                if(i >= 3){
                    quatercost = dp[i -3] + price[2];
                }else{
                //1 , 2월에 3달권을 사용하는 경우
                    quatercost = dp[0] + price[2];
                }

                // 3달권과 비교
                dp[i] = Math.min(mincost, quatercost);
            }

            int res = Math.min(price[3], dp[12]);
            sb.append(res).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
