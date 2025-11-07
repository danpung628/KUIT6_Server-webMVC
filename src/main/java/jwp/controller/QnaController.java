package jwp.controller;

import jwp.dao.QuestionDao;
import jwp.model.Question;
import jwp.model.User;
import jwp.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {

    private final QuestionDao questionDao;

    @GetMapping("/form")
    public String createQuestionForm(HttpSession session) {
        if (!UserSessionUtils.isLogined(session)) {
            return "redirect:/user/loginForm";
        }
        return "qna/form";
    }

    @PostMapping("/create")
    public String createQuestion(@RequestParam String title,
                                 @RequestParam String contents,
                                 HttpSession session) throws Exception {
        if (!UserSessionUtils.isLogined(session)) {
            return "redirect:/user/loginForm";
        }

        User loginUser = UserSessionUtils.getUserFromSession(session);

        Question question = new Question(
                loginUser.getUserId(),
                title,
                contents,
                0
        );

        Question savedQuestion = questionDao.insert(question);
        System.out.println("saved question id= " + savedQuestion.getQuestionId());
        return "redirect:/";
    }

    @GetMapping("/show")
    public String showQuestion(@RequestParam int questionId, Model model) throws Exception {
        Question question = questionDao.findByQuestionId(questionId);
        model.addAttribute("question", question);
        return "qna/show";
    }

    @GetMapping("/updateForm")
    public String updateQuestionForm(@RequestParam int questionId,
                                     HttpSession session,
                                     Model model) throws Exception {
        if (!UserSessionUtils.isLogined(session)) {
            return "redirect:/user/loginForm";
        }

        Question question = questionDao.findByQuestionId(questionId);
        User user = UserSessionUtils.getUserFromSession(session);

        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException("본인의 글만 수정할 수 있습니다.");
        }

        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PostMapping("/update")
    public String updateQuestion(@RequestParam int questionId,
                                 @RequestParam String title,
                                 @RequestParam String contents,
                                 HttpSession session) throws Exception {
        if (!UserSessionUtils.isLogined(session)) {
            return "redirect:/user/loginForm";
        }

        User user = UserSessionUtils.getUserFromSession(session);
        Question question = questionDao.findByQuestionId(questionId);

        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException("본인의 글만 수정할 수 있습니다.");
        }

        question.updateTitleAndContents(title, contents);
        questionDao.update(question);
        return "redirect:/";
    }
}