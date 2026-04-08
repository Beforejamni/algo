package D0408;

import java.util.*;
import java.io.*;

public class BOJ_20058 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static StringTokenizer st;

    static int map[][];
    static boolean[][] visited;
    public static void main(String[] args) throws IOException {
        //given
        st = new StringTokenizer(br.readLine());
        // 맵 크기 2^N , 시행 횟수 Q
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        // size 최대 2^6
        int mapSize = 1<<N;
        map = new int[mapSize][mapSize];
        visited = new boolean[mapSize][mapSize];
        for(int i = 0; i < mapSize; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < mapSize; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 나눌 격자 크기 배열
        int[] arr = new int[Q];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < Q; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        //when
        for(int d : arr){
            int divlen = 1<<d;
            // 스톰
            for(int i = 0; i < map.length; i+=divlen){
                for(int j = 0; j < map[i].length; j+=divlen){
                    storm(i, j, divlen);
                }
            }
            // 파이어
            fire();
        }

        //then
        int totalIce = findTotalIce();
        int maxMass = findMaxMass();

        sb.append(totalIce)
        .append("\n")
        .append(maxMass)
        .append("\n");

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    //부분 회전 시작 r ,c , 배열 크기
    static void storm(int sr, int sc ,int divlen){
        int[][] temp = new int[divlen][divlen];
        // 90도 회전
        for(int i = 0; i < divlen; i++){
            for(int j = 0; j < divlen; j++){
                temp[j][divlen -i -1] = map[sr + i][sc + j];
            }
        }

        //적용
        for(int i = 0; i < divlen; i++){
            for(int j = 0; j < divlen; j++){
                map[sr + i][sc +j] = temp[i][j];
            }
        }
    }

    static void fire(){
        // 녹을 예정인 좌표
        List<int[]> list = new ArrayList<>();

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                //0이하의 경우 건너뛰기
                if(map[i][j] <= 0)continue;
                
                int cnt = 0;
                for(int d = 0; d < 4; d++){
                    int nr = i + dr[d];
                    int nc = j + dc[d];
                    if(nr >= 0 && nr < map.length && nc >= 0 && nc < map[i].length
                        && map[nr][nc] > 0){
                        cnt++;
                    }
                }
                //3방면 미만 이면 --
                if(cnt < 3){
                    list.add(new int[]{i, j});
                }
            }
        }        
        for(int[] m : list){
            //음수로 변경되는 것 방지
            if(map[m[0]][m[1]] <= 0) continue;
            map[m[0]][m[1]]--;
        }
    }

    static int findTotalIce(){
        int totalIce = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                //음수 더하기 방어
                if(map[i][j] > 0)
                totalIce += map[i][j];
            }
        }
        return totalIce;
    }

    static int findMaxMass(){
        int max = 0;
        for(int i = 0; i < map.length ; i++){
            for(int j = 0; j < map[i].length; j++){
                if(map[i][j] > 0 && !visited[i][j]){
                    int mass = findMass(i , j);
                    max = max<mass?mass:max;
                }
            }
        }

        return max;
    }

    static int findMass(int r , int c){
        int mass =0;
        // 방문배열
        Queue<int[]> que = new LinkedList<>();

        que.offer(new int[]{r ,c});
        visited[r][c] = true;
        mass++;

        while(!que.isEmpty()){
            int[] cur = que.poll();
            for(int d = 0; d < 4; d++){
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                if(nr >= 0 && nr < map.length && nc >= 0 && nc < map[cur[0]].length && !visited[nr][nc]){
                    visited[nr][nc] = true;
                    if(map[nr][nc] > 0){
                        mass++;
                        que.offer(new int[]{nr , nc});
                    }
                }
            }
        }
        return mass;
    }
}
