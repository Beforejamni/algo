package D0415;

import java.io.*;
import java.util.*;

public class SWEA_2805_농작물수학하기 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static final int[] dr = { -1, 1, 0, 0 };
    static final int[] dc = { 0, 0, -1, 1 };

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");
            // given
            int N = Integer.parseInt(br.readLine());
            int[][] map = new int[N][N];
            for (int i = 0; i < N; i++) {
                String rows = br.readLine();
                for (int j = 0; j < N; j++) {
                    map[i][j] = rows.charAt(j) - '0';
                }
            }
            int result = 0;
            // when
            int mid = N >> 1;

            Queue<int[]> que = new LinkedList<>();
            boolean[][] visited = new boolean[N][N];

            que.offer(new int[] { mid, mid });
            visited[mid][mid] = true;
            result += map[mid][mid];

            while (!que.isEmpty()) {
                int[] cur = que.poll();

                for (int d = 0; d < 4; d++) {
                    int nr = cur[0] + dr[d];
                    int nc = cur[1] + dc[d];
                    if (nr >= 0 && nr < N && nc >= 0 && nc < N && !visited[nr][nc]) {
                        visited[nr][nc] = true;
                        int dist = Math.abs(mid - nr) + Math.abs(mid - nc);
                        if (dist <= mid) {
                            que.offer(new int[] { nr, nc });

                            result += map[nr][nc];
                        }

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

}
