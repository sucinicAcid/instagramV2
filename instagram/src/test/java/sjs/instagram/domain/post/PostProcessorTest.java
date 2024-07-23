package sjs.instagram.domain.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.db.post.PostEntity;
import sjs.instagram.db.user.UserEntity;
import sjs.instagram.domain.user.UserRepository;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class PostProcessorTest {

    @Autowired
    private PostProcessor postProcessor;
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
        Long postId = postProcessor.post(user.getId(), createPost);

        //then
        PostEntity find = postRepository.findById(postId).get();
        assertThat(find.getId()).isEqualTo(postId);
        assertThat(find.getUserId()).isEqualTo(user.getId());
        assertThat(find.getTitle()).isEqualTo(createPost.title());
        assertThat(find.getContent()).isEqualTo(createPost.content());
        assertThat(find.getImages().size()).isEqualTo(1);
        assertThat(find.getImages().get(0).getUploadFileName()).isEqualTo("file.png");
    }
}
