package D0412;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class SWEA_1226_미로1{
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};
    
    static final int N = 16;



    static int[][] map;
    static boolean[][] visited;
    public static void main(String[] args) throws NumberFormatException, IOException {
        int T = 10;
        for(int t = 1; t <= T; t++){
            int testNum = Integer.parseInt(br.readLine());
            sb.append("#").append(testNum).append(" ");
            //given
            map = new int[N][N];
            visited = new boolean[N][N];

            int[] start = new int[2];
            int[] end =new int[2];

            for(int i = 0; i < N; i++){
                String line = br.readLine();
                for(int j = 0; j < N; j++){
                    map[i][j] = line.charAt(j) - '0';
                    if(map[i][j] == 2){
                        start[0] = i;
                        start[1] = j;
                    }else if(map[i][j] == 3){
                        end[0] = i;
                        end[1] = j;
                    }
                }
            }
            //when & then
            int result = bfs(start, end);

            sb.append(result).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    static int bfs(int[] start, int[] end){
        Queue<int[]> que = new LinkedList<>();

        que.offer(start);
        visited[start[0]][start[1]] = true;
        
        while(!que.isEmpty()){
            int[] cur = que.poll();
            
            if(cur[0] == end[0] && cur[1] == end[1]){
                return 1;
            }

            for(int d = 0; d < 4; d++){
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                if(nr >= 0  && nr < N && nc >= 0 && nc < N && !visited[nr][nc]){
                    if(map[nr][nc] == 1) continue;
                    visited[nr][nc] = true;
                    que.add(new int[]{nr , nc});
                }
            }
        }

        return 0;
    }
}