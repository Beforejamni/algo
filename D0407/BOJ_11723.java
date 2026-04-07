package D0407;

import java.util.*;
import java.io.*;

public class BOJ_11723 {
    static enum Command{
        ADD("add"),
        REMOVE("remove"),
        CHECK("check"),
        TOGGLE("toggle"),
        ALL("all"),
        EMPTY("empty");

        private final String original;

        Command(String original){
            this.original = original;
        }
    }

    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static final StringBuilder sb = new StringBuilder();


    public static void main(String[] args) throws NumberFormatException, IOException {
        //비트마스킹 repository
        int repo = 0;
        int max = Integer.MAX_VALUE;
        //명령어 개수
        int T = Integer.parseInt(br.readLine());
        while(T != 0){
            StringTokenizer st = new StringTokenizer(br.readLine());
            String command = st.nextToken();
            
            int value = 0;
            if(st.hasMoreTokens()){
                value = Integer.parseInt(st.nextToken());
            }
            
            if(command.equals(Command.ADD.original)){
                repo = repo | (1<<value);
            }else if(command.equals(Command.REMOVE.original)){
                repo = repo & ~(1<<value);
            }else if(command.equals(Command.CHECK.original)){
                if((repo & (1<<value)) != 0){
                    sb.append(1).append("\n");
                }else{
                    sb.append(0).append("\n");
                }
            }else if(command.equals(Command.TOGGLE.original)){
                repo = repo ^(1 << value);
            }else if(command.equals(Command.ALL.original)){
                repo = max;
            }else if(command.equals(Command.EMPTY.original)){
                repo = 0;
            }
            T--;
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
}
