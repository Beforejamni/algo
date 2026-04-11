
import java.util.*;
import java.io.*;

public class SWEA_2814_최장경로 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;
    
    static int N, result;
    static boolean[] visited;
    static List<List<Integer>> adj;
    public static void main(String[] args) throws NumberFormatException, IOException {
        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            sb.append("#").append(t).append(" ");
            
            st = new StringTokenizer(br.readLine());
            
            //given
            N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            adj = new ArrayList<>();
            for(int i = 0; i <= N ; i++){
                adj.add(new ArrayList<>());
            }
            for(int i = 0; i < M; i++){
                st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                adj.get(start).add(end);
                adj.get(end).add(start);
            }
            //when
            result = 0;
            visited = new boolean[N+1];
            for(int i = 0; i <= N; i++){
                visited[i] = true;
                dfs(i , 1);
                visited[i] = false;
            }
            sb.append(result).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    static void dfs(int idx, int cursum){
        result = result>cursum?result:cursum;

        for(int next : adj.get(idx)){
            if(visited[next]) continue;
            visited[next] = true;
            dfs(next, cursum +1);
            visited[next] = false;
        }
        
    }
}
