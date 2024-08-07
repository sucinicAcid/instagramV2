package sjs.instagram.controller.user;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.config.LoginUserDetails;
import sjs.instagram.db.user.UserEntity;
import sjs.instagram.domain.post.ThumbnailPost;
import sjs.instagram.domain.user.JoinUser;
import sjs.instagram.domain.user.LoginUser;
import sjs.instagram.domain.user.UserInfo;
import sjs.instagram.domain.user.UserRepository;
import sjs.instagram.service.post.PostService;
import sjs.instagram.service.user.UserService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PostService postService;

    private UserEntity createUser(String instagramId, String password) {
        UserEntity user = new UserEntity(instagramId, passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    private LoginUserDetails createLoginUserDetails(String instagramId, String password) {
        UserEntity user = createUser(instagramId, password);
        LoginUser loginUser = new LoginUser(user.getId(), user.getInstagramId(), user.getPassword(), user.getRole());
        return new LoginUserDetails(loginUser);
    }

    @Test
    @DisplayName("PUBLIC 사용자 프로필 조회")
    void publicUserInfo() throws Exception {
        //given
        LoginUserDetails loginUserDetails = createLoginUserDetails("id123456", "pw123456");
        UserEntity target = createUser("id654321", "pw654321");

        UserInfo userInfo = userService.readUserInfo(target.getId());
        List<ThumbnailPost> thumbnails = postService.readThumbnailPost(loginUserDetails.getUser().id(), target.getId());

        //when then
        mockMvc.perform(get("/users/id654321")
                        .with(SecurityMockMvcRequestPostProcessors.user(loginUserDetails)))
                .andExpect(model().attribute("userInfo", userInfo))
                .andExpect(model().attribute("thumbnails", thumbnails))
                .andExpect(status().isOk())
                .andExpect(view().name("publicUserInfo"));
    }

    @Test
    @DisplayName("PRIVATE 사용자 프로필 조회")
    void privateUserInfo() throws Exception {
        //given
        LoginUserDetails loginUserDetails = createLoginUserDetails("id123456", "pw123456");
        UserEntity target = createUser("id654321", "pw654321");
        target.changePrivacyTo(UserEntity.UserAccountPrivacy.PRIVATE);

        UserInfo userInfo = userService.readUserInfo(target.getId());

        //when then
        mockMvc.perform(get("/users/id654321")
                        .with(SecurityMockMvcRequestPostProcessors.user(loginUserDetails)))
                .andExpect(model().attribute("userInfo", userInfo))
                .andExpect(status().isOk())
                .andExpect(view().name("privateUserInfo"));
    }

    @Test
    @DisplayName("존재하지 않는 사용자 프로필 조회")
    void NoUserFound() throws Exception {
        //given
        LoginUserDetails loginUserDetails = createLoginUserDetails("id123456", "pw123456");

        //when then
        mockMvc.perform(get("/users/id654321")
                        .with(SecurityMockMvcRequestPostProcessors.user(loginUserDetails)))
                .andExpect(model().attribute("message", "존재하지 않는 사용자입니다."))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }
}