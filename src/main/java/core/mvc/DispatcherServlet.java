package core.mvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String requestUri = req.getRequestURI();

        // 싱글톤 인스턴스 사용
        Controller controller = RequestMapper.getInstance().get(requestUri);

        if (controller == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            String viewName = controller.execute(req, resp);
            move(viewName, req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        if (viewName.startsWith("redirect:")) {
            String redirectUrl = viewName.substring("redirect:".length());
            resp.sendRedirect(redirectUrl);
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher(viewName);
            dispatcher.forward(req, resp);
        }
    }
}