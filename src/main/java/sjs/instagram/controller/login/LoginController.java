package sjs.instagram.controller.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sjs.instagram.domain.ValidationErrorException;
import sjs.instagram.service.user.JoinUserRequest;
import sjs.instagram.service.user.UserService;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @GetMapping(value = {"", "/"})
    public String index() {
        return "index";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/loginFail")
    public String loginFail(Model model) {
        model.addAttribute("isFail", true);
        model.addAttribute("errorMessage", "아이디 또는 비밀번호가 틀렸습니다.");
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(Model model) {
        model.addAttribute("joinUserRequest", new JoinUserRequest("", ""));
        return "joinForm";
    }

    @PostMapping("/joinProc")
    public String joinProc(@ModelAttribute JoinUserRequest joinUserRequest, BindingResult bindingResult, RedirectAttributes r) {
        try {
            userService.joinUser(joinUserRequest.toJoinUser());
            return "redirect:/loginForm";
        } catch (ValidationErrorException e) {
            e.getErrors().forEach(err -> {
                if (err.hasField())
                    bindingResult.rejectValue(err.field(), null, err.message());
                else
                    bindingResult.reject(err.message());
            });
            return "joinForm";
        }
    }
}
