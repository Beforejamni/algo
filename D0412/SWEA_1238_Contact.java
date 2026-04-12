package D0412;

import java.io.*;
import java.util.*;

public class SWEA_1238_Contact{
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;
    public static void main(String[] args) throws IOException {
        int T = 10;
        for(int t = 1; t <= T; t++){
            sb.append("#").append(t).append(" ");

            //given
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int S = Integer.parseInt(st.nextToken());
            
            List<List<Integer>> adj = new ArrayList<>();
            
            for(int i = 0 ; i <= 100; i++){
                adj.add(new ArrayList<>());
            }

            st = new StringTokenizer(br.readLine());
            while(st.hasMoreTokens()){
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                adj.get(from).add(to);
            }

            //when

            Queue<Integer> que = new LinkedList<>();
            int[] visited = new int[101];
            que.offer(S);
            visited[S] = 0;
            
            while(!que.isEmpty()){
                int cur = que.poll();

                for(int next : adj.get(cur)){
                    if(visited[next] != 0) continue;
                    visited[next] = visited[cur] + 1;
                    que.offer(next);
                }
            }

            int max = 0;
            int res = 0;
            for(int i = 0; i <= 100; i++){
                if(max <= visited[i]){
                    max = visited[i];
                    res = i;
                }
            }

            //then
            sb.append(res).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

}