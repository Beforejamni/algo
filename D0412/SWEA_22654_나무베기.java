package D0412;

import java.util.*;
import java.io.*;

public class SWEA_22654_나무베기 {
    enum Direction{
        UP(0 ,2 , 3), 
        DOWN(1 , 3, 2), 
        LEFT(2, 1, 0), 
        RIGHT(3, 0, 1);

        final int cur;
        final int left;
        final int right;

        //메서들 호출 -> 상수 변수로 사용
        private static final Direction[] directions = Direction.values();

        Direction(int cur, int left, int right){
            this.cur =cur;
            this.left = left;
            this.right = right;
        }

        static int getLeft(int cur){
            for(Direction d : directions){
                if(d.cur == cur){
                    return d.left;
                }
            }
            throw new IllegalArgumentException();
        }

        static int getRight(int cur){
            for(Direction d : directions){
                if(d.cur == cur){
                    return d.right;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};

    static StringTokenizer st;
    
    static int N, M, min, car[], destination[],cutDownTree[];
    static char[][] map;
    static boolean[] selected;
    static List<int[]> trees;
    public static void main(String[] args) throws NumberFormatException, IOException {
        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            sb.append("#").append(t).append(" ");
            //given
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            map = new char[N][N];
            trees = new ArrayList<>();
            for(int i = 0; i < N; i++){
                String line = br.readLine();
                for(int j = 0; j < N; j++){
                    map[i][j] = line.charAt(j);
                    if(map[i][j] == 'T'){
                        trees.add(new int[]{i, j});
                    }
                    if(map[i][j] == 'X'){
                        car = new int[]{i , j};
                    }

                    if(map[i][j] == 'Y'){
                        destination = new int[]{i, j};
                    }
                }
            }
            //when
            min = Integer.MAX_VALUE;
            cutDownTree = new int[M];
            selected = new boolean[trees.size()];
            
            cutTree(0, 0);

            //then
            if(min == Integer.MAX_VALUE){
                sb.append(-1);
            }else{
                sb.append(min);
            }
            sb.append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static void cutTree(int idx, int start){
        // 안자른 것은 어차피 포함이 되어 있다
        if(idx == M){
            int tempRes = move();
            if(tempRes != -1){
                min = min<tempRes?min:tempRes;
            }
            return;
        }
        // 조합으로 자를 나무를 선택한다.
        for(int i = start; i < trees.size(); i++){
            if(selected[i])continue;
            selected[i] = true;
            cutDownTree[idx] = i;
            cutTree(idx + 1, i +1);
            selected[i] = false;
        }
    }

    static int move(){
        int totalcmd = -1;
        // 나무를 자르고 운전을 할 임시 맵을 생성
        char[][] temp = new char[N][N];
        for(int i = 0; i < N; i++){
            System.arraycopy(map[i], 0, temp[i], 0, N);
        }

        for(int cut : cutDownTree){
            int[] tree = trees.get(cut);
            temp[tree[0]][tree[1]] = 'G';
        }

        // 자동차의 방향에 따른 방문 체크를 3차원 배열로 관리
        boolean[][][] visited = new boolean[N][N][4];
        Queue<int[]> que = new LinkedList<>();
        
            // r , c , d , cnt
        que.offer(new int[]{car[0], car[1], 0, 0});
        visited[car[0]][car[1]][0] = true;

        while(!que.isEmpty()){
            int[] cur = que.poll();

            if(temp[cur[0]][cur[1]] == 'Y'){
                return cur[3];
            }

            int nr = cur[0] + dr[cur[2]];
            int nc = cur[1] + dc[cur[2]];
            if(nr >= 0 && nr < N && nc >= 0 && nc < N && !visited[nr][nc][cur[2]] && temp[nr][nc] != 'T'){
                visited[nr][nc][cur[2]] = true;
                que.add(new int[]{nr , nc , cur[2], cur[3] +1});
            }
            int left = Direction.getLeft(cur[2]);
            if(!visited[cur[0]][cur[1]][left]){
                visited[cur[0]][cur[1]][left] = true;
                que.add(new int[]{cur[0], cur[1], left, cur[3] + 1});
            }
            int right = Direction.getRight(cur[2]);
            if(!visited[cur[0]][cur[1]][right]){
                visited[cur[0]][cur[1]][right] = true;
                que.add(new int[]{cur[0], cur[1], right, cur[3] +1});
            }
        }
        return totalcmd;
    }
//  
    // static boolean hasNextTree(int r, int c, int d, char[][] temp){
    //     int nr = r + dr[d];
    //     int nc = c + dc[d];
    //     if(nr >= 0 && nr < N && nc >= 0 && nc < N){
    //         if(temp[nr][nc] == 'T'){
    //             return true;
    //         }
    //     }
    //     return false;
    // }
}

