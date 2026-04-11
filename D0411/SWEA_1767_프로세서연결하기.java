
import java.util.*;
import java.io.*;

public class SWEA_1767_프로세서연결하기{
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};

    static StringTokenizer st;
    
    static int N, res, maxCoreCnt ,map[][];
    static List<int[]> cores;
    static boolean linked[][];
    public static void main(String[] args) throws NumberFormatException, IOException {
        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            sb.append("#").append(t).append(" ");
            //given
            N = Integer.parseInt(br.readLine());
            map = new int[N][N];
            linked = new boolean[N][N];

            cores = new ArrayList<>();
            for(int i = 0; i < N; i++){
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < N; j++){
                    map[i][j] = Integer.parseInt(st.nextToken());
                    if(map[i][j] == 1){
                        cores.add(new int[]{i , j});
                        linked[i][j] = true;
                    }
                }
            }

            res = Integer.MAX_VALUE;
            maxCoreCnt = 0;
            //when
            simulate(0, 0, 0);

            //then
            sb.append(res).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    //dfs 
    static void simulate(int idx, int sum, int coreCnt){
        if(coreCnt + (cores.size() - idx) < maxCoreCnt){
            return;
        }
        
        if(idx == cores.size()){
            if(maxCoreCnt < coreCnt){
                maxCoreCnt = coreCnt;
                res = sum;
                return;
            }else if(maxCoreCnt == coreCnt){
                res = res > sum ? sum : res;
            }
            return;
        }

            for(int d = 0; d < 4; d++){
                // 현재 idx 코어가 연결 가능한가?
                if(linkable(idx, d)){
                    //가능하면 연결을 하며 길이 계산
                    int len = findLenAndLink(idx, d, true);
                    //다음 코어 확인
                    simulate(idx + 1, sum + len , coreCnt +1);
                    //원복
                    findLenAndLink(idx, d ,false);
                }
            }
        // 연결하지 않고 넘기기
        simulate(idx+1 ,sum, coreCnt);
    }
    static boolean linkable(int coreIdx , int d){
        int[] core = cores.get(coreIdx);
        int nr = core[0] + dr[d];
        int nc = core[1] + dc[d];
        while(nr >= 0 && nr < N && nc >= 0 && nc < N){
            if( linked[nr][nc]) return false;
            nr += dr[d];
            nc += dc[d];
        }
        return true;
    }


    //길이 반환
    //status로 연결 여부 변경
    static int findLenAndLink(int coreIdx  , int d, boolean status){
        int len = 0;
        int[] core = cores.get(coreIdx);
        int nr = core[0] + dr[d];
        int nc = core[1] + dc[d];
        while(nr >= 0 && nr < N && nc >= 0 && nc < N){
            linked[nr][nc] = status;
            len++;
            nr += dr[d];
            nc += dc[d];
        }

        return len;
    }
}