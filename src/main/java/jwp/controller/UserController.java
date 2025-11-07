package jwp.controller;

import jwp.dao.UserDao;
import jwp.model.User;
import jwp.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserDao userDao;

    // ===== 회원가입 관련 =====

    @GetMapping("/form")
    public String signupForm() {
        return "user/form";
    }

    @PostMapping("/signup")
    public String createUser(@ModelAttribute User user) throws Exception {
        userDao.insert(user);
        System.out.println("user signup");
        return "redirect:/user/list";
    }

    // ===== 로그인 관련 =====

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId,
                        @RequestParam String password,
                        HttpSession session) throws Exception {
        User user = userDao.findByUserId(userId);

        if (user != null && user.isSameUser(userId, password)) {
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    @GetMapping("/loginFailed")
    public String loginFailed() {
        return "user/loginFailed";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    // ===== 회원 목록/수정 관련 =====

    @GetMapping("/list")
    public String listUsers(HttpSession session, Model model) throws Exception {
        if (!UserSessionUtils.isLogined(session)) {
            return "redirect:/user/loginForm";
        }
        model.addAttribute("users", userDao.findAll());
        return "user/list";
    }

    @GetMapping("/updateForm")
    public String updateUserForm(@RequestParam String userId,
                                 HttpSession session,
                                 Model model) throws Exception {
        User user = userDao.findByUserId(userId);
        Object sessionUser = session.getAttribute("user");

        if (user != null && sessionUser != null && user.equals(sessionUser)) {
            model.addAttribute("user", user);
            return "user/updateForm";
        }
        return "redirect:/";
    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) throws Exception {
        userDao.update(user);
        return "redirect:/user/list";
    }
}