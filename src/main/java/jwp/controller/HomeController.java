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
import java.util.List;

@WebServlet("/")
public class HomeController extends HttpServlet {
    private final QuestionDao questionDao = new QuestionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // 1. 데이터베이스에서 모든 질문 조회
            List<Question> questions = questionDao.findAll();

            // 2. request에 질문 목록 설정
            req.setAttribute("questions", questions);

            // 3. home.jsp로 forward
            RequestDispatcher rd = req.getRequestDispatcher("/home.jsp");
            rd.forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("질문 목록 조회 실패", e);
        }
    }
}