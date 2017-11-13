package servlets;

/*import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;*/
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author sv.gerasimov
 */
public class getRasp {
    /*static String tourUrlReq = "https://gigz.ru/artist/?group_id=68383956&viewer_id=0&content=upcoming&content=upcoming";
*/
    static String answer = "";


    private static final String url = "jdbc:mysql://web.nexs.me:3306/";
    private static final String user = "afisha";
    private static final String password = "Y.BiydE7N";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static String getTours(int isGet) throws IOException {



       /* Document doc = Jsoup.connect(tourUrlReq).get();

        Elements months = doc.getElementsByClass("f-s_18");
        Elements titles = doc.getElementsByClass("row f-w_b");

*/

        String query = "select date_format(a.date, '%d.%m'), a.place, a.descr_short from afisha a where a.band_id=1";

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connected SQL");

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);
            System.out.println("Execute query");

            while (rs.next()) {

              answer = "";
            }

        } catch (SQLException sqlEx) {
             sqlEx.printStackTrace();

        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }


        /*for (int i = 0; i < months.size(); i++) {
            answer += (months.get(i).text() + " " + titles.get(i).text() + "\r\n");
        }*/

        System.out.println(answer);



        return answer;
    }
}
