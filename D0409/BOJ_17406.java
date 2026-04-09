package D0409;

import java.util.*;
import java.io.*;

public class BOJ_17406 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();
    
    //우 하 좌 상
    static final int[] dr = {0, 1, 0, -1};
    static final int[] dc = {1, 0, -1, 0};

    static StringTokenizer st;

    static int N, M, K, result, map[][], backup[][],cmds[][], perm[];
    static boolean[] selected;
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        //given
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
        
        cmds = new int[K][3];
        perm = new int[K];
        selected = new boolean[K];
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            cmds[i] = new int[]{r, c, s};
        }

    
        br.close();
        //when
        result = Integer.MAX_VALUE;
        
        permutate(0);
        
        //then
        sb.append(result).append("\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    static void permutate(int depth){
        if(depth == K){
            for(int i = 0; i < N ;i++){
                System.arraycopy(backup[i], 0, map[i], 0,M);
            }
            simulate();
            int res = findminRowSum();
            result = result>res?res:result;
            return;
        }
            for(int i = 0; i < K; i++){
                if(selected[i]) continue;
                selected[i] = true;
                perm[depth] = i;
                permutate(depth + 1);
                selected[i] = false; 
            }

    }

    static void simulate(){
        for(int p : perm){
            int[] cmd = cmds[p];
            rotation(cmd[0], cmd[1], cmd[2]);
        }
    }

    static int findminRowSum(){
        int min = Integer.MAX_VALUE;
        for(int[] row : map){
            int temp = 0;
            for(int num : row){
                temp+=num;
            }
            min=min<temp?min:temp;
        }
        return min;
    }

    static void rotation(int sr , int sc, int bound){
        int size = bound<<1^1;
        // 0-base 실제 좌표
        sr = sr - bound -1;
        sc = sc - bound -1;

        int[][] temp = new int[size][size];

        boolean[][] visited = new boolean[size][size];
        
        for(int i = 0; i < bound; i++){
            Queue<int[]> que = new LinkedList<>();
            //좌측으로 시작
            int d = 0;
            que.add(new int[]{i, i});
            while(!que.isEmpty()){
                // 현재 위치
                int[] cur = que.poll();
                // 현재 방향 다음
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                // 방문하지 않았다면
                if(nr >= i && nr < size-i && nc >= i && nc < size-i && !visited[nr][nc]){
                    visited[nr][nc] = true;
                    // 임시 배열에 실제 배열의 이동 전 값 저장
                    temp[nr][nc] = map[sr + cur[0]][sc + cur[1]];
                    que.add(new int[]{nr , nc});
                //범위를 벗어났으면
                }else{
                    //시계 방향으로 회잔
                    d++;
                    que.add(new int[]{cur[0], cur[1]});
                }

                //마지막 도착시 출발점 값 저장
                if(d >= 4){
                    break;
                }
            }
        }

        //임시 배열 값 적용
        for (int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++){
                // 중앙값 덮어쓰기 제외
                if (i == bound && j == bound) continue;
                map[sr + i][sc + j] = temp[i][j];
            }
        }
    }
}
