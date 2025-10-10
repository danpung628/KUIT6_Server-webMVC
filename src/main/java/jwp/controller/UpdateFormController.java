package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/updateForm")
public class UpdateFormController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. 요청 파라미터에서 수정할 userId 받기
        String userId = req.getParameter("userId");

        // 2. 세션에서 현재 로그인한 사용자 정보 가져오기
        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("user");

        // 3. 권한 검증: 로그인한 사용자와 수정하려는 사용자가 동일한지 확인
        if (loginUser != null && loginUser.isSameUser(userId)) {
            // 4. Repository에서 사용자 정보 조회
            MemoryUserRepository repository = MemoryUserRepository.getInstance();
            User user = repository.findUserById(userId);

            // 5. 사용자 정보를 request에 설정
            req.setAttribute("user", user);

            // 6. updateForm.jsp로 포워드
            RequestDispatcher rd = req.getRequestDispatcher("/user/updateForm.jsp");
            rd.forward(req, resp);
        } else {
            // 권한 없음 - 메인 페이지로 리다이렉트
            resp.sendRedirect("/");
        }
    }
}