package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/login")
public class LoginContoller extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 파라미터 받기
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");

        // 2. 사용자 조회
        MemoryUserRepository repository = MemoryUserRepository.getInstance();
        User user = repository.findUserById(userId);

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
    }
}
