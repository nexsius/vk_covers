package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import org.json.*;




    @WebServlet("/")
    public class splasher extends HttpServlet {

        String getJson;
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            //super.doPost(request, response);
            StringBuffer jb = new StringBuffer();
            String line = null;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                jb.append(line);
            }
            } catch (Exception e) { /*report an error*/ }

            getJson = jb.toString();

            PrintWriter out = response.getWriter();

            out.println("5be640fb");


        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            response.setContentType("text/html");
            System.out.println("PREPARING SPLASH.....");

            PrintWriter out = response.getWriter();

            if (request.getQueryString().equals("update")) {
                out.println(lastuser.writeSplash());
            } else {
                out.println("Здесь ничего нет");
            }

        }
    }
