package D0422;

import java.io.*;
import java.util.*;

public class SWEA_1953_탈주범검거 {
    enum Direction {
        ONE(1, new int[] { 1, 2, 3, 4 }, new int[] { 1, 2, 3, 4 }),
        TWO(2, new int[] { 1, 2 }, new int[] { 1, 2 }),
        TREE(3, new int[] { 3, 4 }, new int[] { 3, 4 }),
        FOUR(4, new int[] { 1, 4 }, new int[] { 2, 3 }),
        FIVE(5, new int[] { 2, 4 }, new int[] { 1, 3 }),
        SIX(6, new int[] { 2, 3 }, new int[] { 1, 4 }),
        SEVEN(7, new int[] { 1, 3 }, new int[] { 2, 4 }),
        DEAFULT(0, new int[] { 0 }, new int[] { 0 });

        private final int type;
        private final int[] movable;
        private final int[] inputable;

        private static final Direction[] directions = Direction.values();

        Direction(int type, int[] movable, int[] inputable) {
            this.type = type;
            this.movable = movable;
            this.inputable = inputable;
        }

        static int[] getMovable(int type) {
            for (Direction d : directions) {
                if (d.type == type) {
                    return d.movable;
                }
            }
            return null;
        }

        static int[] getInputable(int type) {
            for (Direction d : directions) {
                if (d.type == type) {
                    return d.inputable;
                }
            }
            return null;
        }
    }

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static final int[] dr = { 0, -1, 1, 0, 0 };
    static final int[] dc = { 0, 0, 0, -1, 1 };

    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");
            // given
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int R = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());
            int L = Integer.parseInt(st.nextToken());

            int[][] map = new int[N][M];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            // when
            Queue<int[]> que = new LinkedList<>();
            boolean[][] visited = new boolean[N][M];

            que.add(new int[] { R, C });
            visited[R][C] = true;
            int result = 0;
            for (int i = 0; i < L; i++) {
                int size = que.size();
                result += size;

                for (int q = 0; q < size; q++) {
                    int[] cur = que.poll();

                    int[] movable = Direction.getMovable(map[cur[0]][cur[1]]);
                    for (int d : movable) {
                        int nr = cur[0] + dr[d];
                        int nc = cur[1] + dc[d];
                        if (nr >= 0 && nr < N && nc >= 0 && nc < M && !visited[nr][nc]) {
                            int[] inputable = Direction.getInputable(map[nr][nc]);
                            for (int input : inputable) {
                                if (d == input) {
                                    visited[nr][nc] = true;
                                    que.add(new int[] { nr, nc });
                                }
                            }
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
