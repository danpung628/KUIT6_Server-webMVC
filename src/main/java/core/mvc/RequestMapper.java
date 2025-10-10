package core.mvc;

import jwp.controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMapper {
    private static final RequestMapper INSTANCE = new RequestMapper(); // 싱글톤
    private final Map<String, Controller> mappingController = new HashMap<>();
    // Key: URL (예: "/user/login"), Value: Controller 객체
    // 불변성 보장

    private RequestMapper() { // private 생성자
        mappingController.put("/", new HomeController());
        mappingController.put("/user/signup", new CreateUserController());
        mappingController.put("/user/list", new ListUserController());
        mappingController.put("/user/login", new LoginContoller());
        mappingController.put("/user/logout", new LogoutController());
        mappingController.put("/user/updateForm", new UpdateFormController());
        mappingController.put("/user/update", new UpdateController());
    }

    public static RequestMapper getInstance() { // 객체 생성
        return INSTANCE;
    }

    public Controller get(String path) {
        return mappingController.get(path);
    }

}