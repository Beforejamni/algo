package D0409;

import java.util.*;
import java.io.*;

public class BOJ_2042{
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        //given
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        long[] data = new long[N+1];
        for(int i = 1; i <= N; i++){
            data[i] = Long.parseLong(br.readLine());
        }

        SegmentTree segmentTree = new SegmentTree(data, N);

        //when
        while(M != 0 || K != 0){
            st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());

            if(cmd == 1){
                int idx = Integer.parseInt(st.nextToken());
                long value = Long.parseLong(st.nextToken());
                segmentTree.update(idx, value);
                M--;
            }
            
            if(cmd == 2){
                int left = Integer.parseInt(st.nextToken());
                int right = Integer.parseInt(st.nextToken());
                long subsum = segmentTree.query(left, right);
                sb.append(subsum).append("\n");
                K--;
            }
        }
        //then
        br.close();
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}
//세그먼트트리 구현
class SegmentTree{
    private long[] tree;
    private long[] data;
    private int n;
    
    //생성시
    SegmentTree(long[] data , int n){
        this.data = data;
        this.n = n;
        this.tree = new long[n * 4];
        //초기화
        if(n > 0){
            init(1, 1, n);
        }
    }

    private void init(int node, int start, int end){
        //같아지는 경우
        if(start == end){
            //node번쨰 tree = start번째 data
            tree[node] = data[start];
            return;
        }

        //2등분
        int mid = start + ((end - start)>>1);

        //좌측 쪼개기
        init(node<<1, start, mid);
        //우측
        init((node<<1) +1, mid+1, end);
        
        // 부모노드는 자식 노드의 합
        tree[node] = tree[node<<1] + tree[(node<<1) + 1];
    }

    public long query(int left, int right){
        return query(1, 1, n, left, right);
    }
    
    private long query(int node, int start, int end, int left, int right){
        //탐색 범위를 벗어날 경우
        if( end < left || right < start){
            return 0L;
        }

        //탐색 범위 안에 속할 경우
        if(left <= start && end <= right){
            return tree[node];
        }

        //이등분
        int mid = start + ((end - start)>>1);

        //좌측 자식 노드 탐색
        long leftsum = query(node<<1 , start, mid, left, right);
        //우측 자식 노드 탐색
        long rightsum = query((node<<1) + 1, mid +1, end, left, right);

        // 더하기
        return leftsum + rightsum;
    }

    public void update(int idx, long value){
        update(1, 1, n, idx, value);
    }

    private void update(int node, int start, int end, int idx, long value){
        // 탐색범위를 벗어날 시 backtracking
        if(idx < start || end < idx){
            return;
        }

        //같아지는 경우가 해당 leaf 노드
        if(start == end){
            tree[node] = value;
            data[idx] = value;
            return;
        }

        //이진 탐색
        int mid = start + ((end -start)>>1);
        update(node<<1 , start, mid, idx, value);
        update((node<<1) + 1, mid + 1, end, idx, value);

        // 데이터 변경 후 관련 부모 노드 업데이트
        tree[node]  = tree[node<<1] + tree[(node<<1) + 1];
    }
}