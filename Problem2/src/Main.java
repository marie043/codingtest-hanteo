import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	private static int N;
	private static int[] coins;
	private static int[][] dp;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer tok = new StringTokenizer(br.readLine());
		String t = "";
		for(int i = 0; i < 3; i++){
			t = tok.nextToken();
		}
		N = Integer.parseInt(t.substring(0, t.length() - 1));
		for(int i = 0; i < 3; i++){
			t = tok.nextToken();
		}
		List<Integer> list = new ArrayList<>();
		tok = new StringTokenizer(t.substring(1, t.length() - 1), ",");
		while(tok.hasMoreTokens()){
			list.add(Integer.parseInt(tok.nextToken()));
		}
		coins = new int[list.size()];
		for(int i = 0; i < list.size(); i++){
			coins[i] = list.get(i);
		}
		Arrays.sort(coins);
		dp = new int[N + 1][coins.length];
		for(int i = 0; i < coins.length; i++){
			dp[coins[i]][i] = 1;
		}
		for(int i = 1; i <= N; i++ ){
			for(int j = 0; j < coins.length; j++){
				int c = coins[j];
				if(i - c >= 0){
					for(int k = 0; k <= j; k++){
						dp[i][j] += dp[i - c][k];
					}
				}else{
					break;
				}
			}
		}
		int sum = 0;
		for(int i = 0; i < coins.length; i++){
			sum += dp[N][i];
		}
		System.out.println(sum);
	}
}
