package sjs.instagram.service.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.db.post.PostEntity;
import sjs.instagram.db.user.UserEntity;
import sjs.instagram.domain.post.CreatePost;
import sjs.instagram.domain.post.PostRepository;
import sjs.instagram.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시물 생성")
    void create() {
        //given
        UserEntity user = userRepository.save(new UserEntity());
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "file.png", MediaType.IMAGE_PNG_VALUE, "file".getBytes()
        );
        CreatePost createPost = new CreatePost(
                "title",
                "content",
                Arrays.asList(multipartFile)
        );

        //when
        Long postId = postService.post(user.getId(), createPost);

        //then
        PostEntity find = postRepository.findById(postId).get();
        assertThat(find.getId()).isEqualTo(postId);
        assertThat(find.getUserId()).isEqualTo(user.getId());
        assertThat(find.getTitle()).isEqualTo(createPost.title());
        assertThat(find.getContent()).isEqualTo(createPost.content());
        assertThat(find.getImages().size()).isEqualTo(1);
        assertThat(find.getImages().get(0).getUploadFileName()).isEqualTo("file.png");
    }

    @Test
    @DisplayName("게시물 생성 오류: 제목 빈칸")
    void failCreatePostByBlankTitle() {
        //given
        UserEntity user = userRepository.save(new UserEntity());
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "file.png", MediaType.IMAGE_PNG_VALUE, "file".getBytes()
        );
        CreatePost createPost = new CreatePost(
                " ",
                "content",
                Arrays.asList(multipartFile)
        );

        //when then
        assertThatThrownBy(() -> postService.post(user.getId(), createPost))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("제목에 빈칸이 들어갈 수 없습니다.");
    }

    @Test
    @DisplayName("게시물 생성 오류: 내용 빈칸")
    void failCreatePostByBlankContent() {
        //given
        UserEntity user = userRepository.save(new UserEntity());
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "file.png", MediaType.IMAGE_PNG_VALUE, "file".getBytes()
        );
        CreatePost createPost = new CreatePost(
                "title",
                " ",
                Arrays.asList(multipartFile)
        );

        //when then
        assertThatThrownBy(() -> postService.post(user.getId(), createPost))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("내용에 빈칸이 들어갈 수 없습니다.");
    }

    @Test
    @DisplayName("게시물 생성 오류: 사진 개수 0개")
    void failValidateByZeroImage() {
        //given
        UserEntity user = userRepository.save(new UserEntity());
        CreatePost createPost = new CreatePost(
                "title",
                "content",
                new ArrayList<>()
        );

        //when then
        assertThatThrownBy(() -> postService.post(user.getId(), createPost))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("사진은 최소 1장 게시해야 합니다.");
    }

    @Test
    @DisplayName("게시물 생성 오류: 사진 용량 0B")
    void failValidateByEmptyImage() {
        //given
        UserEntity user = userRepository.save(new UserEntity());
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "file.png", MediaType.IMAGE_PNG_VALUE, "".getBytes()
        );
        CreatePost createPost = new CreatePost(
                "title",
                "content",
                Arrays.asList(multipartFile)
        );

        //when then
        assertThatThrownBy(() -> postService.post(user.getId(), createPost))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("빈 파일은 사용할 수 없습니다.");
    }

    @Test
    @DisplayName("게시물 생성 오류: 존재하지 않는 사용자")
    void failCreatePostByNonExistUser() {
        //given
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "file.png", MediaType.IMAGE_PNG_VALUE, "file".getBytes()
        );
        CreatePost createPost = new CreatePost(
                "title",
                "content",
                Arrays.asList(multipartFile)
        );

        //when then
        assertThatThrownBy(() -> postService.post(1L, createPost))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }
}