package D0420;

import java.io.*;
import java.util.*;

public class BOJ_1941_소문난칠공주 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static final int[] dr = { -1, 1, 0, 0 };
    static final int[] dc = { 0, 0, -1, 1 };
    static final int SIZE = 5;

    static int result;
    static int[] selected;
    static boolean[][] map, visited;

    public static void main(String[] args) throws IOException {
        map = new boolean[SIZE][SIZE];
        selected = new int[7];
        for (int i = 0; i < SIZE; i++) {
            String lines = br.readLine();
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = lines.charAt(j) == 'S' ? true : false;
            }
        }

        result = 0;
        dfs(0, 0, 0);
        sb.append(result).append("\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();

    }

    static void dfs(int depth, int start, int cnt) {
        if (depth == 7) {
            if (cnt > 3) {
                if (isLinked()) {
                    result += 1;
                }
            }
            return;
        }

        for (int i = start; i < SIZE * SIZE; i++) {
            int r = i / SIZE;
            int c = i % SIZE;

            selected[depth] = i;
            dfs(depth + 1, i + 1, map[r][c] ? cnt + 1 : cnt);
        }
    }

    static boolean isLinked() {
        int cnt = 1;
        Deque<Integer> deque = new ArrayDeque<>();
        visited = new boolean[SIZE][SIZE];

        deque.offerLast(selected[0]);
        visited[selected[0] / SIZE][selected[0] % SIZE] = true;

        while (!deque.isEmpty()) {
            int cur = deque.pollFirst();

            for (int d = 0; d < 4; d++) {
                int nr = cur / 5 + dr[d];
                int nc = cur % 5 + dc[d];
                if (nr >= 0 && nr < SIZE && nc >= 0 && nc < SIZE && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    for (int s : selected) {
                        if (nr * SIZE + nc == s) {
                            deque.offerLast(s);
                            cnt++;
                        }
                    }
                }

            }
        }

        return cnt == 7;
    }
}
