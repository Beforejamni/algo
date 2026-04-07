package D0401;

import java.util.*;
import java.io.*;

public class SWEA_1249_보급로 {
    static class Node{
        int r, c, cost;
        Node(int r, int c, int cost){
            this.r = r;
            this.c = c;
            this.cost = cost;
        }
    }

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};

    static StringTokenizer st;
    public static void main(String[] args) throws NumberFormatException, IOException {
        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            //given
            sb.append("#").append(t).append(" ");
            int N = Integer.parseInt(br.readLine());
            int[][] map = new int[N][N];

            for (int i = 0; i < N; i++) {
                String line = br.readLine();
                for (int j = 0; j < N; j++) {
                    map[i][j] = line.charAt(j) - '0';
                }
            }
            //when
            PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
            boolean[][] visited = new boolean[N][N];
            pq.add(new Node(0, 0, map[0][0]));
            visited[0][0] = true;

            int res = 0;
            while(!pq.isEmpty()){
                Node cur = pq.poll();

                if(cur.r == N-1 && cur.c == N-1){
                    res = cur.cost;
                    break;
                }

                for(int d = 0; d < 4; d++){
                    int nr = cur.r + dr[d];
                    int nc = cur.c + dc[d];
                    if(nr >= 0 && nr < N && nc >= 0 && nc < N && !visited[nr][nc]){
                        visited[nr][nc] = true;
                        pq.add(new Node(nr , nc , cur.cost + map[nr][nc]));
                    } 
                }
            }

            sb.append(res).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
