package jwp.controller;

import core.db.MemoryUserRepository;
import core.mvc.Controller;
import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );

        MemoryUserRepository.getInstance().addUser(user);
        System.out.println("user 회원가입 완료");

        return "redirect:/user/list";
    }
}
//
//@WebServlet("/user/signup")
//public class CreateUserController extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        User user = new User(req.getParameter("userId"),
//                req.getParameter("password"),
//                req.getParameter("name"),
//                req.getParameter("email")
//        );
//        MemoryUserRepository.getInstance().addUser(user);
//        System.out.println("user 회원가입 완료");
//        resp.sendRedirect("/user/list");
//
//    }
//}
