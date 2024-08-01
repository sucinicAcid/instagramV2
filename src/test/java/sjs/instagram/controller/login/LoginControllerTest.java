package sjs.instagram.controller.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.domain.user.JoinUser;
import sjs.instagram.service.user.UserService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("별도 인증 없이 접근 가능")
    void publicUrl() throws Exception {
        mockMvc.perform(get("/css/index.css")).andExpect(status().isOk());
        mockMvc.perform(get("/loginForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginForm"));
        mockMvc.perform(get("/loginFail"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginForm"));
        mockMvc.perform(get("/joinForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("joinForm"));
    }

    @Test
    @DisplayName("인증 없이 접근 불가한 곳은 /loginForm 으로 리다이렉션")
    void redirectionToLoginForm() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/loginForm"));
        mockMvc.perform(get(""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/loginForm"));
        mockMvc.perform(get("/users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/loginForm"));
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/loginForm"));
    }

    @Test
    @DisplayName("로그인")
    void login() throws Exception {
        //given
        JoinUser user = new JoinUser("id123456", "pw123456");
        userService.joinUser(user);

        //when then
        mockMvc.perform(formLogin("/loginProc")
                        .user("instagramId","id123456")
                        .password("password","pw123456"))
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("로그인 오류: 존재하지 않는 아이디")
    void failLoginByNonExistId() throws Exception {
        //given
        JoinUser user = new JoinUser("id123456", "pw123456");
        userService.joinUser(user);

        //when then
        mockMvc.perform(formLogin("/loginProc")
                        .user("instagramId","id12345678910")
                        .password("password","pw123456"))
                .andExpect(unauthenticated())
                .andExpect(redirectedUrl("/loginFail"));
    }

    @Test
    @DisplayName("로그인 오류: 틀린 비밀번호")
    void failLoginByInvalidPassword() throws Exception {
        //given
        JoinUser user = new JoinUser("id123456", "pw123456");
        userService.joinUser(user);

        //when then
        mockMvc.perform(formLogin("/loginProc")
                        .user("instagramId","id123456")
                        .password("password","pw12345678910"))
                .andExpect(unauthenticated())
                .andExpect(redirectedUrl("/loginFail"));
    }

    @Test
    @DisplayName("회원 가입")
    void join() throws Exception {
        mockMvc.perform(post("/joinProc")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("instagramId", "id123456")
                .param("password", "pw123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/loginForm"));
    }

    @Test
    @DisplayName("회원 가입 오류: 아이디가 6자 미만이거나 15자 초과")
    void failJoinByIdLength() throws Exception {
        mockMvc.perform(post("/joinProc")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("instagramId", "id")
                        .param("password", "pw123456"))
                .andExpect(status().isOk())
                .andExpect(view().name("joinForm"));
        mockMvc.perform(post("/joinProc")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("instagramId", "id123456789101112131415")
                        .param("password", "pw123456"))
                .andExpect(status().isOk())
                .andExpect(view().name("joinForm"));
    }

    @Test
    @DisplayName("회원 가입 오류: 아이디에 영문 소문자,대문자,숫자 이외게 것들이 들어감")
    void failJoinByIdCharacter() throws Exception {
        mockMvc.perform(post("/joinProc")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("instagramId", "id123!@#")
                        .param("password", "pw123456"))
                .andExpect(status().isOk())
                .andExpect(view().name("joinForm"));
    }

    @Test
    @DisplayName("회원 가입 오류: 비밀번호가 6자 미만이거나 15자 초과")
    void failJoinByPasswordLength() throws Exception {
        mockMvc.perform(post("/joinProc")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("instagramId", "id123456")
                        .param("password", "pw"))
                .andExpect(status().isOk())
                .andExpect(view().name("joinForm"));
        mockMvc.perform(post("/joinProc")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("instagramId", "id123456")
                        .param("password", "pw123456789101112131415"))
                .andExpect(status().isOk())
                .andExpect(view().name("joinForm"));
    }

    @Test
    @DisplayName("회원 가입 오류: 아이디에 영문 소문자,대문자,숫자,*,!,? 이외게 것들이 들어감")
    void failJoinByPasswordCharacter() throws Exception {
        mockMvc.perform(post("/joinProc")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("instagramId", "id123456")
                        .param("password", "pw123@#$"))
                .andExpect(status().isOk())
                .andExpect(view().name("joinForm"));
    }
}