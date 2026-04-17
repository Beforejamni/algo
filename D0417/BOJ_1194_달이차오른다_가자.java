package D0417;

import java.util.*;
import java.io.*;

public class BOJ_1194_달이차오른다_가자 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static final int[] dr = { -1, 1, 0, 0 };
    static final int[] dc = { 0, 0, -1, 1 };

    static StringTokenizer st;

    static int N, M;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        char[][] map = new char[N][M];
        boolean[][][] visited = new boolean[N][M][64];
        int[] start = new int[2];

        for (int i = 0; i < N; i++) {
            String lines = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = lines.charAt(j);
                if (map[i][j] == '0') {
                    start = new int[] { i, j };
                }
            }
        }

        Queue<int[]> que = new LinkedList<>();
        // r, c, time , key
        que.add(new int[] { start[0], start[1], 0, 0 });
        visited[start[0]][start[1]][0] = true;

        int result = 0;

        while (!que.isEmpty()) {
            int[] cur = que.poll();

            if (map[cur[0]][cur[1]] == '1') {
                result = cur[2];
                break;
            }

            int keys = cur[3];

            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                if (nr >= 0 && nr < N && nc >= 0 && nc < M && map[nr][nc] != '#') {
                    int status = map[nr][nc];
                    if (visited[nr][nc][keys])
                        continue;

                    // 열쇠가 자리
                    if ('a' <= status && status <= 'f') {
                        keys = keys | (1 << status);
                        visited[nr][nc][keys] = true;
                        que.add(new int[] { nr, nc, cur[2], keys });
                    }

                    // 문 자리
                    if ('A' <= status && status <= 'F') {
                        if ((keys & 1 << status) == 1) {
                            visited[nr][nc][keys] = true;
                            que.add(new int[] { nr, nc, cur[2] + 1, keys });
                        }
                    }

                    if (status == '.') {
                        visited[nr][nc][keys] = true;
                        que.add(new int[] { nr, nc, cur[2] + 1, keys });
                    }
                }
            }

        }
        sb.append(result).append("\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
