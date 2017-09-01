package servlets;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.*;

/**
 *
 * @author sv.gerasimov
 */
public class getRasp {
    static String tourUrlReq = "https://gigz.ru/artist/?group_id=68383956&viewer_id=0&content=upcoming&content=upcoming";
    static String answer = "";
    public static String getTours(int isGet) throws IOException {
        Document doc = Jsoup.connect(tourUrlReq).get();

        Elements months = doc.getElementsByClass("f-s_18");
        Elements titles = doc.getElementsByClass("row f-w_b");


        for (int i = 0; i < months.size(); i++) {
            answer += (months.get(i).text() + " " + titles.get(i).text() + "\r\n");
        }




        return answer;
    }
}
