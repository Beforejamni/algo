package D0408;

import java.util.*;
import java.io.*;

// XOR(^)연산을 두 번하면 원래 대로 돌아 온다
// 1 ~ j : prefix[j] = a_1 ^ a_2 ...^a_j;
// i ~ j prefix[j]^prefix[i-1]

public class BOJ_13710 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;

    public static void main(String[] args) throws NumberFormatException, IOException {
        //given
        int N = Integer.parseInt(br.readLine());
        long[] arr = new long[N+1];
        
        //누적 XOR
        long[] prefix = new long[N+1];
        st = new StringTokenizer(br.readLine());
        for(int i = 1 ; i <= N; i++){
            arr[i] = Long.parseLong(st.nextToken());
        }

        //when
        for(int i = 1; i <= N; i++){
            prefix[i] = prefix[i-1]^arr[i];
        }
        
        long res = 0;
        //shift bit 크기
        for(int b = 0; b < 32; b++){
            long cnt0 = 0;
            long cnt1 = 0;
            
            //배열 순회
            //공집합 포함
            for(int i = 0; i <= N; i++){
                // b번째 비트가 1일 경우    
                if(((prefix[i] >> b) &  1) == 1){
                    cnt1++;
                }else{
                    cnt0++;
                }
            }
            //
            //b자리 가중치 곱하기
            res += (cnt0 * cnt1) * (1L << b);
        }
        
        
        //then
        sb.append(res).append("\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

}

//tescase

//input 
// 4
// 1 , 2 , 2 , 1

//pf[0] = 0          (0000)
//pf[1] = pf[0]^1 = 1(0001)
//pf[2] = pf[1]^2 = 3(0011)
//pf[3] = pf[2]^2 = 1(0001)
//pf[4] = pf[3]^1 = 0(0000)

//2진법의 경우의 수로 구하는 이유


//bitIdx 3 2 1 0 
//---------------------
//     0 5 5 4 2
//     1 0 0 1 3
//*      0 0 4 6 
// *2^n  0 0 8 6 = 11 

// * 는 이유는 경우의 수를 통해서 XOR 시 1이 되는 경우를 찾기 위함
// 세로 연산을 하는 이유는 비트의 독립성 
// 각 비트자리별  * 가 모든 구간에 대한 ^연산 시 1이 나오는 경우이고
// 비트 자리별 2^n을 곱해주어 더하면 연속 부분 수열의 누적합이 나온다.