
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Login")
public class MyLogin extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String Myuser = req.getParameter("username1");
        String Mypass = req.getParameter("password1");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/FirstDB", "root", "Subh123@");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            ps.setString(1, Myuser);
            ps.setString(2, Mypass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("username", Myuser);
                resp.setContentType("text/html");
                PrintWriter out = resp.getWriter();
                out.println("<h1>Welcome: " + Myuser + "</h1>");
                out.println("<a href='Logout'>Logout</a>");
            } else {
                resp.setContentType("text/html");
                PrintWriter out = resp.getWriter();
                out.println("<h1>Invalid Username or Password</h1>");
                out.println("<a href='Login.html'>Try Again</a>");
            }
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            out.println("<h1 style='color:red'>Login Failed</h1>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service(req, resp);
    }
}

