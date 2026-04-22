package D0422;

import java.io.*;
import java.util.*;

public class SWEA_2115_벌꿀채취 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;

    static int N, M, C, map[], tempBenenfit;

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());

            map = new int[N * N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i * N + j] = Integer.parseInt(st.nextToken());
                }
            }

            int benefitA = 0;
            int result = 0;
            for (int i = 0; i < N * N; i++) {
                if (isSameRows(i)) {
                    tempBenenfit = 0;
                    getBenefit(0, i, 0, 0);
                    benefitA = benefitA > tempBenenfit ? benefitA : tempBenenfit;
                }
                int benefitB = 0;
                for (int j = i + M; j < N * N; j++) {
                    if (isSameRows(j)) {
                        tempBenenfit = 0;
                        getBenefit(0, j, 0, 0);
                        benefitB = benefitB > tempBenenfit ? benefitB : tempBenenfit;
                    }
                    int tempsum = benefitA + benefitB;
                    result = result > tempsum ? result : tempsum;
                }
            }

            sb.append(result).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static void getBenefit(int depth, int idx, int sum, int benefit) {

        if (sum > C) {
            return;
        }
        if (depth == M) {
            tempBenenfit = tempBenenfit > benefit ? tempBenenfit : benefit;
            return;
        }

        getBenefit(depth + 1, idx, sum + map[idx + depth], benefit + map[idx + depth] * map[idx + depth]);
        getBenefit(depth + 1, idx, sum, benefit);
    }

    static boolean isSameRows(int idx) {
        int a = idx / N;
        int b = (idx + M - 1) / N;
        return a == b;
    }

}
