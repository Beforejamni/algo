package D0414;

import java.io.*;
import java.util.*;

public class SWEA2115_벌꿀채취 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;

    static int N, M, C, map[], curMaxBenfit;

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");
            st = new StringTokenizer(br.readLine());
            // given
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());

            map = new int[N * N];
            for (int i = 0; i < map.length; i++) {
                if (i % N == 0) {
                    st = new StringTokenizer(br.readLine());
                }
                map[i] = Integer.parseInt(st.nextToken());
            }

            // when

            // 일꾼A
            int maxBenefitA = 0;
            int result = 0;

            for (int i = 0; i < map.length; i++) {
                if (isNextrows(i))
                    continue;

                curMaxBenfit = 0;
                getBenefit(0, i, 0, 0);
                // 최대값 갱신 했을 때만 B를 움직임
                if (maxBenefitA < curMaxBenfit) {
                    maxBenefitA = curMaxBenfit;
                    // 일꾼B
                    // 일꾼A가 i 시도일 때 일꾼B의 최대값
                    int maxBenefitB = 0;
                    for (int j = i + M; j < map.length; j++) {
                        if (isNextrows(j))
                            continue;
                        curMaxBenfit = 0;
                        getBenefit(0, j, 0, 0);
                        if (maxBenefitB < curMaxBenfit) {
                            maxBenefitB = curMaxBenfit;
                            if (result < maxBenefitA + maxBenefitB) {
                                result = maxBenefitA + maxBenefitB;
                            }
                        }
                    }
                }

            }
            // then
            sb.append(result).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();

    }

    static boolean isNextrows(int idx) {
        int curidx = idx / N;
        int nextidx = (idx + M - 1) / N;
        return curidx != nextidx;
    }

    // 선택할지말지 idx , 시작 인덱스 , C 비교 확인, 실제 이익
    static void getBenefit(int idx, int start, int sum, int curBenefit) {
        if (sum > C) {
            return;
        }

        if (idx == M) {
            if (curMaxBenfit < curBenefit) {
                curMaxBenfit = curBenefit;
            }
            return;
        }
        getBenefit(idx + 1, start, sum, curBenefit);

        getBenefit(idx + 1, start, sum + map[start + idx], curBenefit + map[start + idx] * map[start + idx]);
    }
}
