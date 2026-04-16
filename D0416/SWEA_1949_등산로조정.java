package D0416;

import java.io.*;
import java.util.*;

public class SWEA_1949_등산로조정 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static final int[] dr = { -1, 1, 0, 0 };
    static final int[] dc = { 0, 0, -1, 1 };

    static StringTokenizer st;

    static int N, K, map[][], result;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");
            // given
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            map = new int[N][N];
            visited = new boolean[N][N];
            int maxhigh = 0;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    maxhigh = maxhigh > map[i][j] ? maxhigh : map[i][j];
                }
            }

            // when
            result = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    // 최대 높이면 dfs 진입
                    if (map[i][j] == maxhigh) {
                        visited[i][j] = true;
                        // 시작길이 1 , 깎았냐? nope
                        dfs(i, j, maxhigh, false, 1);
                        visited[i][j] = false;
                    }
                }
            }

            sb.append(result).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static void dfs(int r, int c, int curhigh, boolean status, int len) {
        // 매번 최대값 갱신
        result = result > len ? result : len;

        // 4방탐색을 하고
        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            if (nr >= 0 && nr < N && nc >= 0 && nc < N && !visited[nr][nc]) {
                // 그냥 가능하면
                if (curhigh > map[nr][nc]) {
                    visited[nr][nc] = true;
                    // 연결 해서 넘기기
                    dfs(nr, nc, map[nr][nc], status, len + 1);
                    visited[nr][nc] = false;
                    // 깎아서 가능하면
                } else if (curhigh > map[nr][nc] - K) {
                    // 임마가 이미 깎인 길인가? nope
                    if (!status) {
                        // 현재 높이보다 크지만, 최소 현재보다 1작으면 가능
                        visited[nr][nc] = true;
                        dfs(nr, nc, curhigh - 1, true, len + 1);
                        visited[nr][nc] = false;
                    }
                }
            }
        }
    }

}