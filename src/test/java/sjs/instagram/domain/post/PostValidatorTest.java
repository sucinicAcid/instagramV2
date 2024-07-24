package sjs.instagram.domain.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.db.post.PostEntity;
import sjs.instagram.db.post.PostImage;
import sjs.instagram.db.user.UserEntity;
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
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    private PostEntity createPost() {
        UserEntity user = userRepository.save(new UserEntity());
        PostEntity post =  new PostEntity(
                user.getId(),
                Arrays.asList(new PostImage("uploadFileName1", "storeFileName1")),
                "title1",
                "content1"
        );
        return postRepository.save(post);
    }

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

    @Test
    @DisplayName("게시물 존재 검증")
    void validateExist() {
        //given
        PostEntity post = createPost();

        //when then
        assertThatNoException().isThrownBy(() ->
                postValidator.validateExist(post.getId()));
    }

    @Test
    @DisplayName("게시물 존재 검증 오류")
    void failValidateExist() {
        assertThatThrownBy(() -> postValidator.validateExist(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("존재하지 않는 게시물입니다.");
    }

    @Test
    @DisplayName("게시물이 해당 사용자의 것인지 검증")
    void isOwnedByUser() {
        //given
        PostEntity post = createPost();

        //when then
        assertThatNoException().isThrownBy(() ->
                postValidator.isOwnedByUser(post.getId(), post.getUserId()));
    }

    @Test
    @DisplayName("게시물이 해당 사용자의 것인지 검증 오류")
    void failIsOwnedByUser() {
        //given
        PostEntity post = createPost();

        //when then
        assertThatThrownBy(() ->
                    postValidator.isOwnedByUser(post.getId(), post.getUserId()+1)
                )
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("본인 게시물만 삭제할 수 있습니다.");
    }
}