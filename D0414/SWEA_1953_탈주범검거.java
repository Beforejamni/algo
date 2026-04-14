package D0414;

import java.util.*;
import java.io.*;

public class SWEA_1953_탈주범검거 {
    enum Type {
        ONE(1, new int[] { 0, 1, 2, 3 }),
        TWO(2, new int[] { 0, 2 }),
        THREE(3, new int[] { 1, 3 }),
        FOUR(4, new int[] { 0, 1 }),
        FIVE(5, new int[] { 1, 2 }),
        SIX(6, new int[] { 2, 3 }),
        SEVEN(7, new int[] { 3, 0 }),
        DEFAULT(0, new int[] {});

        private final int typeNum;
        private final int[] movable;
        private final int[] inputable;

        private static final Type[] types = Type.values();

        Type(int typeNum, int[] movable) {
            this.typeNum = typeNum;
            this.movable = movable;
            this.inputable = new int[movable.length];
            for (int i = 0; i < inputable.length; i++) {
                inputable[i] = (movable[i] + 2) % 4;
            }
        }

        public static int[] getMovable(int typeNum) {
            for (Type t : types) {
                if (t.typeNum == typeNum) {
                    return t.movable;
                }
            }
            return null;
        }

        public static boolean canMove(int d, int nextType) {
            for (Type t : types) {
                if (t.typeNum == nextType) {
                    for (int i : t.inputable) {
                        if (d == i) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static final int[] dr = { -1, 0, 1, 0, };
    static final int[] dc = { 0, 1, 0, -1, };

    static StringTokenizer st;

    static int N, M, SR, SC, TIME, map[][], result;
    static boolean[][] visited;
    static Queue<int[]> que;

    public static void main(String[] args) throws NumberFormatException, IOException {
        int T = Integer.parseInt(br.readLine().trim());
        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");
            // given
            st = new StringTokenizer(br.readLine().trim());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            SR = Integer.parseInt(st.nextToken());
            SC = Integer.parseInt(st.nextToken());
            TIME = Integer.parseInt(st.nextToken());

            map = new int[N][M];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            // when
            que = new LinkedList<>();
            visited = new boolean[N][M];

            // 처음을 넣고 시작
            result = 0;
            que.offer(new int[] { SR, SC, map[SR][SC] });
            visited[SR][SC] = true;
            dfs(1);

            sb.append(result).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();

    }

    static void dfs(int idx) {
        int curCnt = bfs();
        result += curCnt;

        if (idx == TIME) {
            return;
        }

        dfs(idx + 1);
    }

    static int bfs() {
        // 해당 시간에 있을 수 있는 탈주자 자리
        int queSize = que.size();

        for (int s = 0; s < queSize; s++) {
            int[] cur = que.poll();
            int[] movable = Type.getMovable(cur[2]);

            for (int d : movable) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                if (nr >= 0 && nr < N && nc >= 0 && nc < M && !visited[nr][nc]) {
                    if (Type.canMove(d, map[nr][nc])) {
                        visited[nr][nc] = true;
                        que.add(new int[] { nr, nc, map[nr][nc] });
                    }

                }

            }

        }
        return queSize;
    }
}