package D0412;

import java.util.*;
import java.io.*;

public class SWEA_22657_차윤이의RC카{
    enum Direction{
        UP(0, 2, 3),
        DOWN(1, 3, 2), 
        LEFT(2, 1, 0),
        RIGHT(3, 0, 1),
        DEFAULT(0, 2, 3);

        final int cur;
        final int left;
        final int right;

        Direction(int cur, int left, int right){
            this.cur = cur;
            this.left =left;
            this.right = right;
        }

        static int turnLeft(int cur){
            for(Direction d : Direction.values()){
                if(d.cur == cur){
                    return d.left;
                }
            }
            throw new IllegalArgumentException("방향 전환 실패");
        }

        static int turnRight(int cur){
            for(Direction d : Direction.values()){
                if(d.cur == cur){
                    return d.right;
                }
            }
            throw new IllegalArgumentException("방향 전환 실패");
        }

        
    }

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};

    static StringTokenizer st;

    static int N, car[];
    static char[][] map;
    public static void main(String[] args) throws NumberFormatException, IOException {
        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            sb.append("#").append(t).append(" ");
            //given
            N = Integer.parseInt(br.readLine());
            map = new char[N][N];
            car = new int[2];
            for(int i = 0; i < N; i++){
                String line = br.readLine();
                for(int j = 0; j < N; j++){
                    map[i][j] = line.charAt(j);
                    if(map[i][j] == 'X'){
                        car[0] = i;
                        car[1] = j;
                    }
                }
            }
            //when
            int cmdCnt = Integer.parseInt(br.readLine());
            
            for(int i = 0; i < cmdCnt; i++){
                st = new StringTokenizer(br.readLine());
                int cnt = Integer.parseInt(st.nextToken());
                String cmds = st.nextToken();
                System.out.println("cmd");
                char dest = simulate(cnt, cmds);
                if(dest == 'Y'){
                    sb.append(1).append(" ");
                }else{
                    sb.append(0).append(" ");
                }
            }
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.readLine();
    }
    
    static char simulate(int cnt , String cmds){
        char dest = 'X';
        int curdir = 0;
        int[] moveCar = new int[2];
        moveCar[0] = car[0];
        moveCar[1] = car[1];

        for(int c = 0; c < cnt ; cnt++){
            char cmd = cmds.charAt(c);
            if(cmd == 'A'){
                int nr = moveCar[0] + dr[curdir];
                int nc = moveCar[1] + dc[curdir];
                if(nr >= 0 && nr < N && nc >= 0 && nc < N){
                    if(map[nr][nc] != 'T'){
                        moveCar[0] = nr;
                        moveCar[1] = nc;
                    }
                }
            }else if(cmd == 'L'){
                curdir = Direction.turnLeft(curdir);
            }else if(cmd == 'R'){
                curdir = Direction.turnRight(curdir);
            }
        }

        dest = map[moveCar[0]][moveCar[1]];
        return dest;
    }
}