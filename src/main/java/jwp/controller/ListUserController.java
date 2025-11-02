package jwp.controller;

import jwp.dao.UserDao;
import jwp.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/user/list")
public class ListUserController extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. 세션에서 로그인 사용자 정보 가져오기
        HttpSession session = req.getSession();
        Object value = session.getAttribute("user");

        // 2. 로그인 여부 확인
        if (value == null) {
            resp.sendRedirect("/user/login.jsp");
            return;
        }

        try {
            // 3. 데이터베이스에서 사용자 목록 조회
            List<User> users = userDao.findAll();

            // 4. 사용자 목록을 request에 설정
            req.setAttribute("users", users);

            // 5. list.jsp로 포워드
            RequestDispatcher rd = req.getRequestDispatcher("/user/list.jsp");
            rd.forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("사용자 목록 조회 실패", e);
        }
    }
}