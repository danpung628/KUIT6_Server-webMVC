package jwp.controller;

import core.db.MemoryUserRepository;
import core.mvc.Controller;
import jwp.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

public class ListUserController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 1. 세션에서 로그인 사용자 정보 가져오기
        HttpSession session = request.getSession();
        Object value = session.getAttribute("user");

        // 2. 로그인 여부 확인
        if (value == null) {
            // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/user/login.jsp";
        }

        // 3. 로그인한 경우 사용자 목록 조회
        User loginUser = (User) value;
        MemoryUserRepository repository = MemoryUserRepository.getInstance();
        Collection<User> users = repository.findAll();

        // 4. 사용자 목록을 request에 설정
        request.setAttribute("users", users);

        // 5. list.jsp로 포워드
        return "/user/list.jsp";
    }
}

//@WebServlet("/user/list")
//public class ListUserController extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//
//        // 1. 세션에서 로그인 사용자 정보 가져오기
//        HttpSession session = req.getSession();
//        Object value = session.getAttribute("user");
//
//        // 2. 로그인 여부 확인
//        if (value == null) {
//            // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
//            resp.sendRedirect("/user/login.jsp");
//            return;
//        }
//
//        // 3. 로그인한 경우 사용자 목록 조회
//        User loginUser = (User) value;
//        MemoryUserRepository repository = MemoryUserRepository.getInstance();
//        Collection<User> users = repository.findAll();
//
//        // 4. 사용자 목록을 request에 설정
//        req.setAttribute("users", users);
//
//        // 5. list.jsp로 포워드
//        RequestDispatcher rd = req.getRequestDispatcher("/user/list.jsp");
//        rd.forward(req, resp);
//    }
//}