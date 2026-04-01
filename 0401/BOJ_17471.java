
import java.io.*;
import java.util.*;

public class BOJ_17471 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();
    
    static StringTokenizer st;
    static int N, population[], res;
    static List<List<Integer>> adjList;
    static boolean[] selected;
    public static void main(String[] args) throws NumberFormatException, IOException {
        N = Integer.parseInt(br.readLine());
        population = new int[N+1];
        selected = new boolean[N +1];

        st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= N; i++){
            population[i] = Integer.parseInt(st.nextToken());
        }

        adjList = new ArrayList<>();
        adjList.add(new ArrayList<>());

        for(int i = 1; i <= N; i++){
            adjList.add(new ArrayList<>());
            st = new StringTokenizer(br.readLine());
            int len = Integer.parseInt(st.nextToken());
            for(int k = 0; k < len; k++){
                adjList.get(i).add(Integer.parseInt(st.nextToken()));
            }
        }
        res = Integer.MAX_VALUE;
        combination(1);

        if(res == Integer.MAX_VALUE){
            res = -1;
        }


        sb.append(res).append("\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();

    }

    //조합 찾기
    static void combination(int  idx){
        if(idx == N + 1){
            //선택 후 학인
            int temp = bfs();
            if(temp == -1) return;

            res = Math.min(res, temp);
            return;
        }
        //선택을 하는 재귀
        selected[idx] = true;
        combination(idx + 1);

        //선택을 하지 않는 재귀
        selected[idx] = false;
        combination(idx + 1);
    }

    static int bfs(){
        // 인구 차이 변수
        int diff = 0;
        //전체 연결 확인 변수
        int cnt = 0;
        //방분 확인
        boolean[] visited = new boolean[N+1];

        Deque<Integer> deque = new ArrayDeque<>();
        // 처음 선택된 지역
        int idx = -1;
        for(int i = 0; i <= N; i++){
            if(selected[i]){
                idx = i;
                break;
            }
        }
        //모든 지역이 선택이 되지 않은 경우
        if(idx == -1) return -1;

        deque.offerFirst(idx);
        visited[idx] = true;
        while(!deque.isEmpty()){
            int cur = deque.pollFirst();
            diff += population[cur];
            cnt++;
            for(int next : adjList.get(cur)){
                if(visited[next] || !selected[next])continue;
                visited[next] = true;
                deque.offerLast(next);
            }
        }
        // 0번 인덱스는 사용하지 않음
        idx = -1;
        for (int i = 1; i <= N; i++) {
            if(!selected[i]){
                idx = i;
                break;
            }
        }
        // 모든 지역이 선택된 경우
        if(idx == -1) return -1;
        
        deque.offerFirst(idx);
        visited[idx] = true;
        while(!deque.isEmpty()){
            int cur = deque.pollFirst();
            diff -= population[cur];
            cnt++;
            for(int next : adjList.get(cur)){
                if(visited[next] || selected[next]) continue;
                visited[next] = true;
                deque.offerLast(next);
            }
        }

        if(cnt < N) return -1;

        return Math.abs(diff);
    }
}
