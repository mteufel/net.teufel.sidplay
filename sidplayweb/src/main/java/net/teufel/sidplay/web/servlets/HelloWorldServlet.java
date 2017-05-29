package net.teufel.sidplay.web.servlets;

import net.teufel.sidplay.Util;
import net.teufel.sidplay.core.dao.SidDaoJdbc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/*")
public class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {

        res.getWriter().write("Hello\n\n");

        SidDaoJdbc sidDao = new SidDaoJdbc(Util.getDataSource());


        sidDao.getTypes().forEach(t -> {
            try {
                res.getWriter().write(t.getId() + " -" + t.getType() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
