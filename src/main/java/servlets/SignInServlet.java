package servlets;

import entity.AccountService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SignInServlet extends HttpServlet {

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        response.setContentType("text/html;charset=utf-8");
        AccountService accountService = getAccountServiceFromDb(login, password);
        if (accountService != null) {
            response.setStatus(200);
            response.getWriter().println("Authorized: " + accountService.getLogin());
        } else {
            response.setStatus(401);
            response.getWriter().println("Unauthorized");
        }
    }

    private AccountService getAccountServiceFromDb(String login, String password) {
        AccountService accountService = null;
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                session1.beginTransaction();
                Query query = session1.createQuery("from AccountService accountService where accountService.login = :login and accountService.password = :password");
                query.setParameter("login", login);
                query.setParameter("password", password);
                List<AccountService> list = query.list();
                if (!list.isEmpty()) {
                    accountService = list.get(0);
                }
                session1.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountService;
    }

}
