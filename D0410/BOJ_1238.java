package D0410;

import java.util.*;
import java.io.*;

public class BOJ_1238 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        //given
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());

        // 순방향
        List<List<int[]>> adj = new ArrayList<>();
        // 역방형
        List<List<int[]>> radj = new ArrayList<>();
        for(int i = 0; i <= N; i++){
            adj.add(new ArrayList<>());
            radj.add(new ArrayList<>());
        }

        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            adj.get(start).add(new int[]{end , cost});
            radj.get(end).add(new int[]{start, cost});
        }

        //when
        int[] dist = new int[N+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        // 사용하지 않는 idx 0 + 시작 위치 X
        dist[0] = 0;
        dist[X] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n[1]));
        pq.add(new int[]{X , dist[X]});
        while(!pq.isEmpty()){
            int[] cur = pq.poll();

            //이미 값보다 크다
            if(dist[cur[0]] < cur[1]) continue;

            for(int[] next : adj.get(cur[0])){
                //최솟값 갱신
                if(dist[next[0]] > cur[1] + next[1]){
                    dist[next[0]] = cur[1] + next[1];
                    pq.add(new int[]{next[0], dist[next[0]]});
                }
            }
        }

        int[] rdist = new int[N+1];
        Arrays.fill(rdist, Integer.MAX_VALUE);
        rdist[0] = 0;
        rdist[X] = 0;

        pq.add(new int[]{X , rdist[X]});
        while(!pq.isEmpty()){
            int[] cur = pq.poll();

            if(rdist[cur[0]] < cur[1]) continue;

            for(int[] next : radj.get(cur[0])){
                if(rdist[next[0]] > cur[1] + next[1]){
                    rdist[next[0]] = cur[1] + next[1];
                    pq.add(new int[]{next[0], rdist[next[0]]});
                }
            }
        }
        for(int i = 0; i <= N; i++){
            dist[i] += rdist[i];
        }

        Arrays.sort(dist);
        //then
        sb.append(dist[N]).append("\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
