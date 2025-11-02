package jwp.controller;

import jwp.dao.QuestionDao;
import jwp.model.Question;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/qna/show")
public class ShowQuestionController extends HttpServlet {
    private final QuestionDao questionDao = new QuestionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. questionId 파라미터 받기
        String questionIdParam = req.getParameter("questionId");

        if (questionIdParam == null || questionIdParam.isEmpty()) {
            // questionId가 없으면 메인 페이지로 리다이렉트
            resp.sendRedirect("/");
            return;
        }

        try {
            // 2. questionId로 질문 조회
            Long questionId = Long.parseLong(questionIdParam);
            Question question = questionDao.findByQuestionId(questionId);

            if (question == null) {
                // 질문이 없으면 메인 페이지로 리다이렉트
                resp.sendRedirect("/");
                return;
            }

            // 3. 질문 정보를 request에 설정
            req.setAttribute("question", question);

            // 4. show.jsp로 forward
            RequestDispatcher rd = req.getRequestDispatcher("/qna/show.jsp");
            rd.forward(req, resp);

        } catch (NumberFormatException e) {
            // questionId가 숫자가 아닌 경우
            resp.sendRedirect("/");
        } catch (SQLException e) {
            throw new ServletException("질문 조회 실패", e);
        }
    }
}