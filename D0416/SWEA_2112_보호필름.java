package D0416;

import java.io.*;
import java.util.*;

public class SWEA_2112_보호필름 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static final int RMAX = 13;

    static StringTokenizer st;

    static int N, M, K, map[][], backup[][], result;

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");
            st = new StringTokenizer(br.readLine());
            // given
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            map = new int[N][M];
            backup = new int[N][M];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    backup[i][j] = map[i][j];
                }
            }
            result = RMAX;
            // when
            dfs(0, 0);
            sb.append(result).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static void dfs(int idx, int cnt) {
        if (cnt >= result)
            return;

        if (valid()) {
            result = result < cnt ? result : cnt;
            return;
        }
        if (idx == N) {
            return;
        }

        dfs(idx + 1, cnt);

        insert(idx, 0);
        dfs(idx + 1, cnt + 1);

        insert(idx, 1);
        dfs(idx + 1, cnt + 1);

        rollback(idx);

    }

    static boolean valid() {
        int validcellCnt = 0;
        for (int c = 0; c < M; c++) {
            int validcnt = 1;
            int target = map[0][c];
            for (int r = 1; r < N; r++) {
                if (target == map[r][c]) {
                    validcnt++;
                } else {
                    target = map[r][c];
                    validcnt = 1;
                }
                if (validcnt >= K) {
                    validcellCnt++;
                    break;
                }
            }
        }
        return validcellCnt == M;
    }

    static void insert(int idx, int type) {
        Arrays.fill(map[idx], type);
    }

    static void rollback(int idx) {
        System.arraycopy(backup[idx], 0, map[idx], 0, M);
    }
}
