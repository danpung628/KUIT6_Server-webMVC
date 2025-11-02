package jwp.controller;

import jwp.dao.QuestionDao;
import jwp.model.Question;
import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/qna/create")
public class CreateQuestionController extends HttpServlet {
    private final QuestionDao questionDao = new QuestionDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. 세션에서 로그인 사용자 정보 확인
        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("user");

        // 2. 로그인 여부 확인
        if (loginUser == null) {
            resp.sendRedirect("/user/login.jsp");
            return;
        }

        // 3. 폼 데이터 받기
        String writer = req.getParameter("writer");
        String title = req.getParameter("title");
        String contents = req.getParameter("contents");

        // 4. 작성자가 로그인한 사용자와 일치하는지 확인
        if (!loginUser.getUserId().equals(writer)) {
            resp.sendRedirect("/");
            return;
        }

        try {
            // 5. 질문 저장
            Question savedQuestion = questionDao.insert(writer, title, contents);
            System.out.println("질문 생성 완료: " + savedQuestion);

            // 6. 메인 페이지로 리다이렉트
            resp.sendRedirect("/");
        } catch (SQLException e) {
            throw new ServletException("질문 생성 실패", e);
        }
    }
}