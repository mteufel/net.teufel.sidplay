package net.teufel.sidplay.servlets;

import net.teufel.sidplay.dao.SidDaoJdbc;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/servlets/*")
public class HelloWorldServlet extends HttpServlet {

    @Inject
    SidDaoJdbc sidDao;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {

        res.getWriter().write("Hello World!\n\n");


        sidDao.getTypes().forEach(t -> {
            try {
                res.getWriter().write(t.getId() + " -" + t.getType() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
