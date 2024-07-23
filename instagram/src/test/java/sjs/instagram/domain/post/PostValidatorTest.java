package sjs.instagram.domain.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostValidatorTest {
    @Autowired
    private PostValidator postValidator;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("게시물 검증 성공")
    void validate() {
        //given
        MockMultipartFile multipartFile = new MockMultipartFile("file", "file".getBytes());
        CreatePost createPost = new CreatePost(
                "title",
                "content",
                Arrays.asList(multipartFile)
        );

        //when then
        assertThatCode(() -> postValidator.validate(createPost)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("게시물 검증 오류: 제목 빈칸")
    void failValidateByBlankTitle() {
        //given
        MockMultipartFile multipartFile = new MockMultipartFile("file", "file".getBytes());
        CreatePost createPost = new CreatePost(
                " ",
                "content",
                Arrays.asList(multipartFile)
        );

        //when then
        assertThatThrownBy(() -> postValidator.validate(createPost))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("제목에 빈칸이 들어갈 수 없습니다.");
    }

    @Test
    @DisplayName("게시물 검증 오류: 내용 빈칸")
    void failValidateByBlankContent() {
        //given
        MockMultipartFile multipartFile = new MockMultipartFile("file", "file".getBytes());
        CreatePost createPost = new CreatePost(
                "title",
                " ",
                Arrays.asList(multipartFile)
        );

        //when then
        assertThatThrownBy(() -> postValidator.validate(createPost))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("내용에 빈칸이 들어갈 수 없습니다.");
    }

    @Test
    @DisplayName("게시물 검증 오류: 사진 개수 0개")
    void failValidateByZeroImage() {
        //given
        CreatePost createPost = new CreatePost(
                "title",
                "content",
                new ArrayList<>()
        );

        //when then
        assertThatThrownBy(() -> postValidator.validate(createPost))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("사진은 최소 1장 게시해야 합니다.");
    }

    @Test
    @DisplayName("게시물 검증 오류: 사진 용량 0B")
    void failValidateByEmptyImage() {
        //given
        MockMultipartFile multipartFile = new MockMultipartFile("file", "".getBytes());
        CreatePost createPost = new CreatePost(
                "title",
                "content",
                Arrays.asList(multipartFile)
        );

        //when then
        assertThatThrownBy(() -> postValidator.validate(createPost))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("빈 파일은 사용할 수 없습니다.");
    }
}