package D0401;

import java.util.*;
import java.io.*;

public class BOJ_2458 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();
    
    static StringTokenizer st;
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        boolean[][] checked = new boolean[N+1][N+1];
        //내 위치를 확인했다고 정의
        for(int i = 1; i <= N; i++){
            for(int j = 1; j <= N; j++){
                if(i == j) checked[i][j]= true;
            }
        }

        //키의 크기를 확인
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            checked[a][b] = true;
        }

        //추론할 수 있는 위치 확인
        // j 와 k가 비교 가능하고 k와 i가 비교 가능하면 i와 j를 비교할 수 있다.
        for(int k = 1; k <= N; k++){
            for(int j = 1; j <= N; j++){
                if(!checked[j][k]) continue;
                for(int i = 1 ; i <= N; i++){
                    if(checked[j][k] && checked[k][i]) checked[j][i] = true;
                }
            }
        }

        int res = 0;
        // i 와 비교한 j의 개수가 N 개면 i의 위치를 확인할 수 있다.
        for(int i = 1; i <= N; i++){
            int cnt = 0;
            for(int j = 1; j <= N; j++){
                if(checked[i][j] || checked[j][i]) cnt++;
            }
            if(cnt == N){
                res++;
            }
        }

        sb.append(res).append("\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
