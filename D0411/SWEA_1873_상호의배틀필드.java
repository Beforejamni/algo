
import java.util.*;
import java.io.*;

public class SWEA_1873_상호의배틀필드{
    //이넘으로 명령어 관리
    enum Direction{
        //명령어 / 탱크 방향 / 움직일 방향
        UP('U' , '^', 0),
        DOWN('D', 'v', 1),
        LEFT('L', '<', 2), 
        RIGHT('R', '>', 3);

        private final char cmd;
        private final char tank;
        private final int dir;
        Direction(char cmd, char tank, int dir){
            this.cmd = cmd;
            this.tank = tank;
            this.dir = dir;
        }

        static Direction fromCmd(char cmd){
            for(Direction d : Direction.values()){
                if(d.cmd == cmd){
                    return d;
                }
            }
            return null;
        }

        static Direction fromTank(char tank){
            for(Direction d : Direction.values()){
                if(d.tank == tank){
                    return d;
                }
            }
            return null;
        }
    }

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;
    // U , D , L , R
    static final int[] dr = {-1 ,1 ,0, 0};
    static final int[] dc = {0, 0, -1 ,1};

    static int N , M , tank[];
    static char map[][];
    public static void main(String[] args) throws NumberFormatException, IOException {
        int T = Integer.parseInt(br.readLine());
        for(int t = 1 ; t <= T; t++){
            sb.append("#").append(t).append(" ");
            //given
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            
            map = new char[N][M];
            tank = new int[2];
            for(int i = 0; i < N; i++){
                String line = br.readLine();
                for(int j = 0; j < M; j++){
                    map[i][j] = line.charAt(j);
                    if(map[i][j] == '<' || map[i][j] == '>' || map[i][j] =='^' || map[i][j] == 'v'){
                        tank[0] = i;
                        tank[1] = j;
                    }
                }
            }
            int cmdSize = Integer.parseInt(br.readLine());
            String cmds = br.readLine();
            
            //when
            for(int i = 0; i < cmdSize; i++){
                action(cmds.charAt(i));
            }

            //then
            for(char[] row : map){
                for(char m : row){
                    sb.append(m);
                }
                sb.append("\n");
            }
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static void action(char cmd){
        //S만 슈팅
        if(cmd == 'S'){
            shoot();
        //나머지 움직임
        }else{
            move(Direction.fromCmd(cmd));
        }
        
    }

    static void move(Direction direction){
        int r = tank[0];
        int c = tank[1];
        // 탱크 방향 변경        
        map[r][c] =direction.tank;
        
        int nr = r + dr[direction.dir];
        int nc = c + dc[direction.dir];

        //움직일 수 있는지 확인
        if(nr >= 0 && nr < N && nc >= 0 && nc < M){
            if(map[nr][nc] == '.'){
                map[r][c] = '.';
                tank[0] = nr;
                tank[1] = nc;
                map[nr][nc] = direction.tank;
            }
        }
    }

    static void shoot(){
        int r = tank[0];
        int c = tank[1];

        char tank = map[r][c];
        int d = Direction.fromTank(tank).dir;
        
        int nr = r + dr[d];
        int nc = c + dc[d];
        while(nr >= 0 && nr < N && nc >= 0 && nc < M){
            if(map[nr][nc] == '*'){
                map[nr][nc] = '.';
                return;
            }else if( map[nr][nc] == '#'){
                return;
            }
            nr += dr[d];
            nc += dc[d];
        }
    }
}