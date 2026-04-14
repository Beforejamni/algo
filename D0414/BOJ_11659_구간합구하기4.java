package D0414;

import java.io.*;
import java.util.*;

public class BOJ_11659_구간합구하기4 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;

    private static int N, data[], segmentsTree[];

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        data = new int[N + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            data[i] = Integer.parseInt(st.nextToken());
        }

        init();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int result = query(start, end);
            sb.append(result).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();

    }

    public static void init() {
        segmentsTree = new int[N * 4];
        setup(1, 1, N);
    }

    public static void setup(int node, int left, int right) {

        if (left == right) {
            segmentsTree[node] = data[left];
            return;
        }

        int mid = left + (right - left) / 2;

        setup(node << 1, left, mid);
        setup(node << 1 ^ 1, mid + 1, right);

        segmentsTree[node] = segmentsTree[node << 1] + segmentsTree[node << 1 ^ 1];
    }

    static int query(int left, int right) {
        return query(1, 1, N, left, right);
    }

    static int query(int node, int start, int end, int left, int right) {
        if (end < left || right < start) {
            return 0;
        }
        if (start >= left && right >= end) {
            return segmentsTree[node];
        }
        int mid = start + ((end - start) >> 1);

        int leftsum = query(node << 1, start, mid, left, right);
        int rightsum = query(node << 1 ^ 1, mid + 1, end, left, right);
        return leftsum + rightsum;

    }
}
