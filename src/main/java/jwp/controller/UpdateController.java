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

@WebServlet("/user/update")
public class UpdateController extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. 세션에서 로그인 사용자 정보 가져오기
        HttpSession session = req.getSession();
        Object value = session.getAttribute("user");

        // 2. 로그인 여부 확인
        if (value == null) {
            resp.sendRedirect("/user/login.jsp");
            return;
        }

        User loginUser = (User) value;

        // 3. 요청 파라미터 받기
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String email = req.getParameter("email");

        // 4. 본인 정보만 수정 가능하도록 검증
        if (!loginUser.isSameUser(userId)) {
            resp.sendRedirect("/");
            return;
        }

        try {
            // 5. 사용자 정보 업데이트
            User updateUser = new User(userId, password, name, email);
            userDao.update(updateUser);

            // 6. 세션 정보 업데이트
            session.setAttribute("user", updateUser);

            // 7. 사용자 목록 페이지로 리다이렉트
            resp.sendRedirect("/user/list");
        } catch (SQLException e) {
            throw new ServletException("사용자 정보 수정 실패", e);
        }
    }
}