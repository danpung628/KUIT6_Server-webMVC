package jwp.controller;

import jwp.dao.QuestionDao;
import jwp.model.Question;
import jwp.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final QuestionDao questionDao;

    @GetMapping
    public String listQuestions(Model model) throws Exception {

        model.addAttribute("questions", questionDao.findAll());
        return "/";


    }
}
