package servlets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import org.json.*;

import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//import org.jsoup.*;
//import org.jsoup.nodes.*;
//import org.jsoup.select.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class lastuser {

         // token Креативные обложки
         //static String TOKEN="30fe43b4979b91b4375c7cb1864dd4f87a7b7e049489dcf89b975edaaea6c174ca5c0adc867fb3525dc9e";
      //  токен группы МП
        static String TOKEN = "6e9a494d126d2d0e7d3d4912094d97d27573c9cdcd00a66a6d7de5992a008ea2978712dbc1c844240c800";

      // id креативные обложки
         //static String groupID="122772291";
     //   Айди группы МП
        static String groupID="68383956";

        static  String getURL="https://api.vk.com/method/photos.getOwnerCoverPhotoUploadServer?group_id="+ groupID + "&crop_x=0&crop_y=0&crop_x2=1590&crop_y2=400&access_token=" + TOKEN + "&v=5.64";
        static String tourUrlReq = "https://gigz.ru/artist/?group_id=68383956&viewer_id=0&content=upcoming&content=upcoming";
        static String answ;


    private static ArrayList<String> months = new ArrayList<String>();
    private static ArrayList<String> titles = new ArrayList<String>();
    private static ArrayList<String> descr = new ArrayList<String>();

    private static final String url = "jdbc:mysql://web.nexs.me:3306/mp_afisha?useSSL=false";
    private static final String user = "afisha";
    private static final String password = "Y.BiydE7N";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static String writeSplash () throws IOException {

           try {
               Class.forName("com.mysql.jdbc.Driver");
               System.out.println("Driver loaded");
               BufferedImage background_image = ImageIO.read(new File("//opt//tomcat//sources//cover.jpg"));

               System.out.println("Image loaded");

               BufferedImage result = new BufferedImage(background_image.getWidth(), background_image.getHeight(), BufferedImage.TYPE_INT_RGB);
               Graphics2D g = (Graphics2D) result.getGraphics();
               g.drawImage(background_image, 0, 0, null);

               g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
               g.setPaint(Color.white);
               //шрифт для надписей
               File f = new File("//opt//tomcat//sources//tour.ttf");

               FileInputStream in = new FileInputStream(f);
               Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, in);
               Font dynamicFont14Pt = dynamicFont.deriveFont(42f);
               g.setStroke(new BasicStroke(8));



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

                       System.out.println(rs.getString(1) + " " + rs.getString(2));


                       months.add(rs.getString(1));
                       titles.add(rs.getString(2));
                       descr.add(rs.getString(3));

                       System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22");

                       System.out.println("months: " + months.size());

                       System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22");
                   }

               } catch (SQLException sqlEx) {
                   sqlEx.printStackTrace();
                   titles.add(sqlEx.getMessage());
               } finally {
                   //close connection ,stmt and resultset here
                   System.out.println("MONTH: " + months.toString());
                   try { con.close(); } catch(SQLException se) { /*can't do anything */ }
                   try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
                   try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
               }





               //отрисовка динамики
         //      Document doc = Jsoup.connect(tourUrlReq).get();

           //    Elements months = doc.getElementsByClass("f-s_18");
            //   Elements titles = doc.getElementsByClass("row f-w_b");

               for (int i = 0; i < months.size(); i++) {
                   if (i == 0) {
                       String d_fin = months.get(i) + ".2018";
                       System.out.println("!!!! d_fin " + d_fin);
                       SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                       Date d_cur = new Date();
                       System.out.println("!!!! d_cur.getTime()" + d_cur.getTime());

                       Date date2 = null;

                       date2 = format.parse(d_fin);
                       long difference = date2.getTime() - d_cur.getTime();
                       System.out.println("!!!! difference " + difference);

                       int days = (int) (difference / (24 * 60 * 60 * 1000));
                       System.out.println("!!!! days " + days);

                     //  g.setFont(dynamicFont14Pt);
                       Font dynamicFont32Pt = dynamicFont.deriveFont(52f);
                       g.setFont(dynamicFont32Pt);
                       if (days == 0) {
                           Font dynamicFont42Pt = dynamicFont.deriveFont(62f);
                           g.setFont(dynamicFont42Pt);
                           g.drawString("Cегодня: " +  titles.get(i), 208, 365);
                       } else {

                           g.drawString("До ближайшего концерта ", 208, 365);
                           Font dynamicFont42Pt = dynamicFont.deriveFont(62f);
                           g.setFont(dynamicFont42Pt);
                           g.drawString(plural(days), 821, 365);

                           g.setFont(dynamicFont14Pt);
                       }
                   }

                   g.drawString( months.get(i) + " " + descr.get(i), 208, (i * 42) + 80);
                //   g.drawString(descr.get(i), 225, (i * 42) + 160);
               }
               System.out.println("WRITING IMAGE!");

               ImageIO.write(result, "JPG", new File("//opt//tomcat//sources//cover_new.jpg"));

           }
           catch (Exception e) {
               System.out.println(e.toString());
           }

        System.out.println("SENDING IMAGE");

           sendCover("//opt//tomcat//sources//cover_new.jpg");

         return answ;
        }

    private static String plural(int days) {

        int days100 = days % 100;
        int days10 = days % 10;

        if (days100 > 10 && days100 < 20) return  String.valueOf(days) + " дней";
        if (days10 > 1 && days10  < 5) return String.valueOf(days) + " дня";
        if (days10 == 1) return String.valueOf(days) + " день";
        return  String.valueOf(days) + " дней";

    }

        public static String getJson(String sendUrl){
            URL url;
            HttpURLConnection conn;
            BufferedReader rd;
            String line;
            String result = "";
            try {
                url = new URL(sendUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = rd.readLine()) != null) {
                    result += line;
                }
                rd.close();
            } catch (Exception e) {
                e.printStackTrace();
            }



            return result;
        }


        public static void sendCover(String coverPath) {

            String coverURL = getJson(getURL);
            System.out.println("FIle: " + coverPath);
            File uploadFile = new File(coverPath);

            JSONObject obj = new JSONObject(coverURL);
            String coverSetUrl = obj.getJSONObject("response").getString("upload_url");

            StringBuilder response_sb = new StringBuilder();
            try {
                MultipartUtility multipart = new MultipartUtility(coverSetUrl, "UTF-8");

                multipart.addFilePart("photo", uploadFile);

                List<String> response = multipart.finish();

                for (String line : response) {
                    response_sb.append(line);
                }
                System.out.println(response_sb);

                JSONObject obj3 = new JSONObject(response_sb.toString());

                String hash = obj3.getString("hash");
                String photo = obj3.getString("photo");



                try{
                    answ = getJson("https://api.vk.com/method/photos.saveOwnerCoverPhoto?hash="+ hash +"&photo="+ photo +"&access_token="+ TOKEN +"&v=5.65");

                    System.out.println("IMAGE SEND!!!!!!");
                }
                catch (Exception e) {
                    System.out.println(e.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }





}
