package jwp.controller;

import jwp.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/qna/form")
public class CreateQuestionFormController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. 세션에서 로그인 사용자 정보 확인
        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("user");

        // 2. 로그인 여부 확인
        if (loginUser == null) {
            // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
            resp.sendRedirect("/user/login.jsp");
            return;
        }

        // 3. 로그인한 경우 질문 작성 폼으로 이동
        RequestDispatcher rd = req.getRequestDispatcher("/qna/form.jsp");
        rd.forward(req, resp);
    }
}