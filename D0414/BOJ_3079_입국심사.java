package D0414;

import java.util.*;
import java.io.*;

public class BOJ_3079_입국심사 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;

    static long INF = 0L;

    static int N, M;
    static long arr[];

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        // given
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new long[N];
        for (int i = 0; i < N; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }

        // when
        Arrays.sort(arr);

        INF = Math.multiplyExact(arr[0], M);

        long result = 0;
        long start = 1;
        long end = INF;
        while (start <= end) {
            long mid = start + ((end - start) >> 1);
            if (!retired(mid)) {
                result = mid;
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        sb.append(result).append("\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static boolean retired(long TTL) {
        long passCnt = 0;
        for (long t : arr) {
            if (TTL < t) {
                break;
            }
            passCnt += TTL / t;
            if (passCnt < M) {
                return true;
            }
        }
        return false;
    }
}
