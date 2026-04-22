package D0422;

import java.io.*;
import java.util.*;

public class SWEA_5648_원자소멸시뮬레이션 {
    static class Atomic {
        int r, c, d, e;
        boolean isDead;

        Atomic(int r, int c, int d, int e, boolean isDead) {
            this.r = r;
            this.c = c;
            this.d = d;
            this.e = e;
            this.isDead = isDead;
        }

        static Atomic createAtomic(int r, int c, int d, int e) {
            return new Atomic(r, c, d, e, false);
        }

        static Atomic createDeadAtomic(int r, int c, int d, int e) {
            return new Atomic(r, c, d, e, true);
        }
    }

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();
    // y 증가(상) y 감소(하) x감소(좌) x증가(우)
    static final int[] dr = { 1, -1, 0, 0 };
    static final int[] dc = { 0, 0, -1, 1 };

    static StringTokenizer st;

    static int N, result;
    static Atomic[] atomics;

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");

            // given
            int N = Integer.parseInt(br.readLine());
            atomics = new Atomic[N];
            int[][] map = new int[4001][4001];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                // x y - > c, r 로 변경
                int c = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());
                atomics[i] = Atomic.createAtomic((r + 1000) << 1, (c + 1000) << 1, d, e);
            }
            // when
            result = 0;
            // 최대 4000초 이동 가능
            time: for (int i = 0; i < 4000; i++) {
                int deadCnt = 0;

                // 원자 탐색
                for (int a = 0; a < N; a++) {
                    Atomic curAtomic = atomics[a];

                    // 죽은 원자 카운트
                    if (curAtomic.isDead) {
                        deadCnt++;
                        continue;
                    }
                    // 다 죽었으면 시뮬 종료 break;
                    if (deadCnt == N) {
                        break time;
                    }

                    // 이동 전에 원자 에너지 지우기
                    if (map[curAtomic.r][curAtomic.c] == curAtomic.e) {
                        map[curAtomic.r][curAtomic.c] = 0;
                    }

                    curAtomic.r = curAtomic.r + dr[curAtomic.d];
                    curAtomic.c = curAtomic.c + dc[curAtomic.d];

                    // 이동 시키기
                    if (curAtomic.r >= 0 && curAtomic.r < 4001 && curAtomic.c >= 0 && curAtomic.c < 4001) {
                        // 이동 시킨 맵에 에너지 더하기
                        map[curAtomic.r][curAtomic.c] += curAtomic.e;
                    } else {
                        // 범위 밖이면 뒤짐
                        curAtomic.isDead = true;
                    }
                }

                // 충돌 확인
                for (int c = 0; c < N; c++) {
                    Atomic curAtomic = atomics[c];
                    // 죽은 원자 건너뛰기
                    if (curAtomic.isDead)
                        continue;

                    // map에 찍힌 에너지가 지금 에너지보다 크면
                    if (map[curAtomic.r][curAtomic.c] > curAtomic.e) {
                        // 결과에 더하가
                        result += map[curAtomic.r][curAtomic.c];
                        // 그리고 죽이기
                        curAtomic.isDead = true;
                        // 에너지 소멸
                        map[curAtomic.r][curAtomic.c] = 0;
                        // 이미 소멸 시킨 것
                    } else if (map[curAtomic.r][curAtomic.c] == 0) {
                        // 죽이기
                        curAtomic.isDead = true;
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
