package D0406;


import java.io.*;
import java.util.*;

public class SWEA2383_점심식사시간 {
    static class Node{
        int r, c;
        Node(int r, int c){
            this.r = r;
            this.c = c;
        }
    }
    
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();
    
    static final int MAX_QUE = 3;
    static int N, map[][], size, res;
    static List<Node> people;
    static Node  A , B;
    static boolean[] selected;

    public static void main(String[] args) throws NumberFormatException, IOException {
        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            sb.append("#").append(t).append(" ");
            res = Integer.MAX_VALUE;
            N = Integer.parseInt(br.readLine());
            map = new int[N][N];

            people = new ArrayList<>();
            A = null;
            B = null;
            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    if(map[i][j] == 1){
                        people.add(new Node(i, j));
                    }else if(map[i][j] != 0){
                        if(Objects.isNull(A)){
                            A = new Node(i, j);
                        }else{
                            B = new Node(i, j);
                        }
                    }
                }
            }

            size = people.size();
            selected = new boolean[size];

            combination(0);
            sb.append(res).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    static void combination(int idx){
        if(idx == size){
            int cur = solve();
            res = Math.min(res, cur);
            return;
        }
        selected[idx] = true;
        combination(idx+1);
        selected[idx] = false;
        combination(idx+1);

    }

    static int solve(){
        PriorityQueue<Integer> queA = new PriorityQueue<>();
        PriorityQueue<Integer> queB = new PriorityQueue<>();
        for(int i = 0; i < size; i++){
            Node person = people.get(i);
            if(selected[i]){
                int time = Math.abs(person.r - A.r) + Math.abs(person.c - A.c);
                queA.add(time);
            }else{
                int time = Math.abs(person.r - B.r) + Math.abs(person.c - B.c);
                queB.add(time);
            }
        }

        int maxA = maxTime(queA, map[A.r][A.c]);
        int maxB = maxTime(queB, map[B.r][B.c]);

        return Math.max(maxA, maxB);
    }

    static int maxTime(Queue<Integer> que, int downTime){
        // 사용하지 않는 경우
        if(que.isEmpty()){
            return 0;
        }
        Deque<Integer> deque = new ArrayDeque<>();

        while(!que.isEmpty()){
            //계단에 도착해서 진입
            int startTime = que.poll() + 1;

            //근데 3명이 내려가고 있으면?
            if(deque.size() == MAX_QUE){
                int firstTime = deque.pollFirst();
                // 도착해서 진입하는 시간보다 1번 대기열 사람이 늦게 나오면?
                if(startTime < firstTime){
                    //그 시간 만큼 대기
                    startTime = firstTime;
                }
            }
            // 최종 걸리는 시간
            int finalTime = startTime + downTime;

            deque.offerLast(finalTime);
        }

        return deque.pollLast();
    }
}
