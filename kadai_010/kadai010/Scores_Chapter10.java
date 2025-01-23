package kadai010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Scores_Chapter10 {

	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement statementUP = null;
		
		try {
			//DBへ接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"password"
					);
			
			String sqlUpdate = "UPDATE scores SET score_math = ? ,score_english = ? WHERE id = 5;";
			statementUP = con.prepareStatement(sqlUpdate);
			System.out.println("データベース接続成功:" + con.toString());

			// SQLクエリの「?」部分をリストのデータに置き換え
			statementUP.setInt(1, 95); 
			statementUP.setInt(2, 80); 
			
			//SQLクエリを実行(DBMSに送信)
			System.out.println("レコード更新を実行します");
			
			int rowCnt = statementUP.executeUpdate(sqlUpdate); //sqlUpdateが入るとSQLにエラー
			//You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near '? ,score_english = ? WHERE id = 5' at line 1
			System.out.println(rowCnt + "件のレコードが更新されました");
			System.out.println("数学・英語の点数が高い順に並べ替えました");
			Statement statement = con.createStatement();
			String sqlSelect = "SELECT * FROM scores ORDER BY score_math DESC , score_english DESC;";
			
			ResultSet result = statement.executeQuery(sqlSelect);
            
	         // SQLクエリの実行結果を抽出
	            while(result.next()) {
	                int id = result.getInt("id");
	                String name = result.getString("name");
	                int scoreMath = result.getInt("score_math");
	                int scoreEnglish = result.getInt("score_english");
	               
	                System.out.println(result.getRow() + "件目：生徒ID=" + id  + "／氏名=" + name  + "／数学=" + scoreMath  + "／英語=" + scoreEnglish);
	            }
		}catch(SQLException e) {
			System.out.println("エラー発生:" + e.getMessage());
		}finally {
			//使用したオブジェクトを解放
			if(statementUP != null) {
				try { statementUP.close(); }catch (SQLException ignore){}
			}
			if(con != null) {
				try {con.close();}catch(SQLException ignore) {}
			}
		}

	}

}
