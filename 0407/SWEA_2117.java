import java.util.*;
import java.io.*;

public class SWEA_2117 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws NumberFormatException, IOException {
        //testCase 개수
        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            sb.append("#").append(t).append(" ");
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            // Input start
            int[][] city = new int[N][N];
            List<int[]> houses = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    city[i][j] = Integer.parseInt(st.nextToken());
                    if(city[i][j] == 1){
                        houses.add(new int[]{i , j});
                    }
                }
            }
            // Intput end
            
            //result 변수
            int resCnt = 0;
            
            // 홈 서비스 시작 위치 - 완전 탐색
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    // k 만큼 범위 증가
                    // 중앙에서 전체를 커버하는 비용 < 배열 가장자리에서 전체를 커버하는 비용
                    // 모든 집을 덮는 MAX == N+1
                    for(int k = 1; k <= N+1; k++){
                        // 현재 비용
                        int curCost = k * k + (k-1)*(k-1);
                        
                        //범위 내 집의 수
                        int isUsedCnt = 0;
                        for(int[] cur : houses){
                            int r = cur[0];
                            int c = cur[1];

                            //서비스 위치로부터 집의 거리
                            int dist = Math.abs(i - r) + Math.abs(j -c);

                            // 반경 안에 있는 경우
                            if(dist < k){
                                isUsedCnt++;
                            }
                        }
                        // 이익
                        int profit = isUsedCnt * M;

                        // 이익이 비용보다 크거나 같을 경우
                        if(profit >= curCost){
                            resCnt = Math.max(isUsedCnt, resCnt);
                        }
                        
                    }
                }
            }

            sb.append(resCnt).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
