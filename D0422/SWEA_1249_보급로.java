package D0422;

import java.io.*;
import java.util.*;

public class SWEA_1249_보급로 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static final int[] dr = { -1, 1, 0, 0 };
    static final int[] dc = { 0, 0, -1, 1 };

    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");
            int N = Integer.parseInt(br.readLine());
            int[][] map = new int[N][N];

            for (int i = 0; i < N; i++) {
                String lines = br.readLine();
                for (int j = 0; j < N; j++) {
                    map[i][j] = lines.charAt(j) - '0';
                }
            }

            PriorityQueue<int[]> que = new PriorityQueue<>(Comparator.comparingInt(n -> n[2]));
            boolean[][] visited = new boolean[N][N];
            que.add(new int[] { 0, 0, 0 });
            visited[0][0] = true;

            int result = 0;
            while (!que.isEmpty()) {
                int[] cur = que.poll();

                if (cur[0] == N - 1 && cur[1] == N - 1) {
                    result = cur[2];
                }

                for (int d = 0; d < 4; d++) {
                    int nr = cur[0] + dr[d];
                    int nc = cur[1] + dc[d];
                    if (nr >= 0 && nr < N && nc >= 0 && nc < N && !visited[nr][nc]) {
                        visited[nr][nc] = true;
                        que.add(new int[] { nr, nc, cur[2] + map[nr][nc] });
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
