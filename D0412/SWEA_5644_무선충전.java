package D0412;

import java.io.*;
import java.util.*;

public class SWEA_5644_무선충전{
    static final BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;

    static final int N  = 10;
    static final char A = 'A' , B = 'B';

    static final int[] startuserA = {1, 1};
    static final int[] startuserB = {10, 10};
    // default, up, right, down, left
    static final int[] dr = {0, -1, 0, 1, 0};
    static final int[] dc = {0, 0, 1, 0, -1};


    static int M, userA[], userB[], C, batteryCharger[][], result;
    public static void main(String[] args) throws NumberFormatException, IOException {
        int T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            sb.append("#").append(t).append(" ");
            st = new StringTokenizer(br.readLine());

            //given
            M = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());

            userA = new int[M];
            userB = new int[M];

            String pathA = br.readLine();
            savePath(userA, pathA);
            String pathB = br.readLine();
            savePath(userB, pathB);

            batteryCharger = new int[C][4];
            for(int i = 0; i < C; i++){
                String info = br.readLine();
                saveCharger(i, info);
            }
            //when
            result = 0;
            dfs(0, 0, startuserA, startuserB);

            sb.append(result).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    static void dfs(int idx, int sum, int[] curUserA, int[] curUserB){
        //매개변수로 반아 온 user A, B의 값을 계산 후
        int timemax = 0;

        //2중for문으로 모든 경우의 순을 계산
        for(int a = 0; a < C; a++){
            for(int b = 0; b < C; b++){
                int chargeA = getCharge(curUserA, batteryCharger[a]);
                int chargeB = getCharge(curUserB, batteryCharger[b]);

                int curCharge = 0;
                //같은 충전소를 사용할 경우
                if(a == b){
                    curCharge = chargeA>chargeB?chargeA:chargeB;
                //다른 충전소를 사용할 경우
                }else{
                    curCharge = chargeA + chargeB;
                }
                //그 중 최댓값
                timemax = timemax>curCharge?timemax:curCharge;
            }
        }
        // 시간 최대값을 더해주고
        sum += timemax;

        //배열 검사
        //기저가 위에 있을 경우 마지막을 계산 user 위치를 계산 하지 못함
        if(idx == M){
            result = sum;
            return;
        }

        int[] nextUserA = moveUser(curUserA, idx, A);
        int[] nextUserB = moveUser(curUserB, idx, B);

        //다음 위치를 넘겨주기
        dfs(idx +1, sum , nextUserA, nextUserB);
    }

    static int[] moveUser(int[] user, int time, char userType){
        int[] nextUser =new int[2];
        
        int r = user[0];
        int c = user[1];

        int nr = 0, nc = 0;

        if(userType == 'A'){
            nr = r + dr[userA[time]];
            nc = c + dc[userA[time]];
        }else if(userType == 'B'){
            nr = r + dr[userB[time]];
            nc = c + dc[userB[time]];
        }else{
            throw new IllegalArgumentException("타입 정보 실패");
        }
        
        nextUser[0] = nr;
        nextUser[1] = nc;

        return nextUser;    
    }

    static int getCharge(int[] user, int[] charger){
        int charge = 0;
        int r = user[0];
        int c = user[1];

        int coverage = charger[2];
        int bound = Math.abs(r - charger[0]) + Math.abs(c - charger[1]);
        if(bound <= coverage){
            charge = charger[3];
        }

        return charge;
    }


    static void saveCharger(int idx , String info){
        st = new StringTokenizer(info);
        int c = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int coverage = Integer.parseInt(st.nextToken());
        int performance = Integer.parseInt(st.nextToken());
        batteryCharger[idx] = new int[]{r , c, coverage, performance};
    }

    static void savePath(int[] user, String path){
        st = new StringTokenizer(path);
        for(int i = 0; i < M; i++){
            user[i] = Integer.parseInt(st.nextToken());
        }
    }
}