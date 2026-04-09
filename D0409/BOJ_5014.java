package D0409;

import java.util.*;
import java.io.*;

public class BOJ_5014 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static final String MESSAGE = "use the stairs";
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int F = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());
        int G = Integer.parseInt(st.nextToken());
        int U = Integer.parseInt(st.nextToken());
        int D = Integer.parseInt(st.nextToken());

        int[] floors = new int[F+1];
        boolean[] visited =new boolean[F+1];

        Queue<Integer> que = new LinkedList<>();
        que.add(S);
        visited[S] = true;
        
        while(!que.isEmpty()){
            int cur = que.poll();

            if(cur == G){
                System.out.println(floors[cur]);
                return;
            }

            int up = cur + U;
            if(up <= F && !visited[up]){
                visited[up] = true;
                floors[up] = floors[cur] +1;
                que.add(up);
            }
            
            int down = cur - D;
            if(down > 0 && !visited[down]){
                visited[down] = true;
                floors[down] = floors[cur] +1;
                que.add(down);
            }
        }
        System.out.println(MESSAGE);
    }
}
