package D0416;

import java.io.*;
import java.util.*;

public class SWEA_4012_요리사 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;

    static int N, matrials[][], result;

    static boolean[] selelcted;

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");
            N = Integer.parseInt(br.readLine());
            matrials = new int[N][N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    matrials[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            result = Integer.MAX_VALUE;
            selelcted = new boolean[N];
            dfs(0, 0);

            sb.append(result).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static void dfs(int depth, int idx) {
        if (depth == (N >> 1)) {
            int tempDiff = findDiff();
            result = result > tempDiff ? tempDiff : result;
            return;
        }

        for (int i = idx; i < N; i++) {
            if (selelcted[i])
                continue;
            selelcted[i] = true;
            dfs(depth + 1, i + 1);
            selelcted[i] = false;
            dfs(depth + 1, i + 1);
        }
    }

    static int findDiff() {
        int foodA = cook(true);
        int foodB = cook(false);

        return Math.abs(foodA - foodB);
    }

    static int cook(boolean flag) {
        int favor = 0;
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            if (selelcted[i] == flag) {
                list.add(i);
            }
        }
        for (int a : list) {
            for (int b : list) {
                favor += matrials[a][b];
            }
        }
        return favor;
    }
}
