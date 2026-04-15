package D0415;

import java.io.*;
import java.util.*;

public class SWEA_1824_혁진이의프로그램검증 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;

    static final int[] dr = { -1, 1, 0, 0 };
    static final int[] dc = { 0, 0, -1, 1 };

    static int R, C;
    static char[][] map;
    static boolean[][][][] visited;

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");

            // when
            st = new StringTokenizer(br.readLine());
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            map = new char[R][C];

            for (int i = 0; i < R; i++) {
                String row = br.readLine();
                for (int j = 0; j < C; j++) {
                    map[i][j] = row.charAt(j);
                }
            }

            if (bfs()) {
                sb.append("YES");
            } else {
                sb.append("NO");
            }
            sb.append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static boolean bfs() {
        Queue<int[]> que = new LinkedList<>();
        visited = new boolean[R][C][16][4];

        int mem = 0;
        que.offer(new int[] { 0, 0, mem, 3 });
        visited[0][0][mem][3] = true;

        while (!que.isEmpty()) {
            int[] cur = que.poll();

            int cr = cur[0];
            int cc = cur[1];

            if (map[cr][cc] == '@') {
                return true;
            }

            int cmem = cur[2];
            int cd = cur[3];
            if (map[cr][cc] == '?') {
                for (int d = 0; d < 4; d++) {
                    int nr = rotationIdx(cr, dr[d], R);
                    int nc = rotationIdx(cc, dc[d], C);
                    if (nr >= 0 && nr < R && nc >= 0 && nc < C && !visited[nr][nc][cmem][d]) {
                        visited[nr][nc][cmem][d] = true;
                        que.offer(new int[] { nr, nc, cmem, d });
                    }
                }
            } else {
                int[] next = excuteCmt(cr, cc, cmem, cd);
                int nr = next[0];
                int nc = next[1];
                if (nr >= 0 && nr < R && nc >= 0 && nc < C && !visited[nr][nc][next[2]][next[3]]) {
                    visited[nr][nc][next[2]][next[3]] = true;
                    que.offer(next);
                }
            }
        }

        return false;
    }

    static int[] excuteCmt(int r, int c, int mem, int d) {
        char cmd = map[r][c];

        switch (cmd) {
            case '<':
                d = 2;
                break;
            case '>':
                d = 3;
                break;
            case '^':
                d = 0;
                break;
            case 'v':
                d = 1;
                break;
            case '_':
                d = mem == 0 ? 3 : 2;
                break;
            case '|':
                d = mem == 0 ? 1 : 0;
                break;
            case '+':
                mem = mem < 15 ? mem + 1 : 0;
                break;
            case '-':
                mem = mem > 0 ? mem - 1 : 15;
                break;
            default:
                if ('0' < cmd && cmd < '9') {
                    mem = getmeory(r, c);
                }
                break;
        }

        int nr = rotationIdx(r, dr[d], R);
        int nc = rotationIdx(c, dc[d], C);

        return new int[] { nr, nc, mem, d };
    }

    static int getmeory(int r, int c) {
        int memory = 0;
        int cmd = map[r][c] - '0';
        if (0 <= cmd && cmd <= 9) {
            memory = cmd;
        }
        return memory;
    }

    static int rotationIdx(int idx, int d, int typeMax) {
        int temp = idx + d;
        if (temp < 0) {
            temp = typeMax - 1;
        } else if (temp >= typeMax) {
            temp = 0;
        }
        return temp;
    }
}
