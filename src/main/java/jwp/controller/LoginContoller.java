package jwp.controller;

import jwp.dao.UserDao;
import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/user/login")
public class LoginContoller extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 1. 파라미터 받기
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");

        try {
            // 2. 데이터베이스에서 사용자 조회
            User user = userDao.findByUserId(userId);

            // 3. 로그인 검증
            if (user != null && user.matchPassword(password)) {
                // 로그인 성공
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                resp.sendRedirect("/");
            } else {
                // 로그인 실패
                resp.sendRedirect("/user/loginFailed.jsp");
            }
        } catch (SQLException e) {
            throw new ServletException("로그인 처리 중 오류 발생", e);
        }
    }
}