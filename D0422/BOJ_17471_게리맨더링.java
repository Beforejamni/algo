package D0422;

import java.io.*;
import java.util.*;

public class BOJ_17471_게리맨더링 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;

    static int N, population[], result;
    static List<List<Integer>> adj;
    static boolean[] selected;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        population = new int[N + 1];
        selected = new boolean[N + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            population[i] = Integer.parseInt(st.nextToken());
        }

        adj = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int eCnt = Integer.parseInt(st.nextToken());
            for (int j = 0; j < eCnt; j++) {
                adj.get(i).add(Integer.parseInt(st.nextToken()));
            }
        }

        result = Integer.MAX_VALUE;
        solve(1);
        if (result == Integer.MAX_VALUE) {
            result = -1;
        }

        sb.append(result).append("\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static void solve(int idx) {
        if (idx == N + 1) {
            if (checked()) {
                int diff = getDiff();
                result = result < diff ? result : diff;
            }
            return;
        }

        selected[idx] = true;
        solve(idx + 1);
        selected[idx] = false;
        solve(idx + 1);
    }

    static boolean checked() {
        int rsA = getRegionSize(true);
        int rsB = getRegionSize(false);

        return rsA + rsB == N;
    }

    static int getRegionSize(boolean type) {
        int startIdx = 0;
        for (int i = 1; i <= N; i++) {
            if (selected[i] == type) {
                startIdx = i;
                break;
            }
        }
        if (startIdx == 0)
            return 0;

        Queue<Integer> que = new LinkedList<>();
        boolean[] visited = new boolean[N + 1];

        que.add(startIdx);
        visited[startIdx] = true;

        int cnt = 1;
        while (!que.isEmpty()) {
            int cur = que.poll();

            for (int next : adj.get(cur)) {
                if (!visited[next] && selected[next] == type) {
                    visited[next] = true;
                    que.add(next);
                    cnt++;
                }
            }
        }

        return cnt;
    }

    static int getDiff() {
        int pA = getPopulation(true);
        int pB = getPopulation(false);

        return Math.abs(pA - pB);
    }

    static int getPopulation(boolean type) {
        int popul = 0;
        for (int i = 1; i <= N; i++) {
            if (selected[i] == type) {
                popul += population[i];
            }
        }
        return popul;
    }
}
