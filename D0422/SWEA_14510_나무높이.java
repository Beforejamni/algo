package D0422;

import java.io.*;
import java.util.*;

public class SWEA_14510_나무높이 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();

    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");
            int N = Integer.parseInt(br.readLine());
            int[] trees = new int[N];
            st = new StringTokenizer(br.readLine());
            int maxTree = 0;
            for (int i = 0; i < N; i++) {
                trees[i] = Integer.parseInt(st.nextToken());
                maxTree = maxTree > trees[i] ? maxTree : trees[i];
            }

            for (int i = 0; i < trees.length; i++) {
                trees[i] = maxTree - trees[i];
            }

            int oddDays = 0;
            int evenDays = 0;
            for (int tree : trees) {
                oddDays += tree % 2;
                evenDays += tree / 2;
            }

            while (evenDays > oddDays + 1) {
                evenDays--;
                oddDays += 2;
            }

            if (evenDays >= oddDays) {
                sb.append(evenDays * 2);
            } else if (oddDays > evenDays) {
                sb.append(oddDays * 2 - 1);
            }
            sb.append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
