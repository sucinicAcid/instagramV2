package sjs.instagram.domain.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.db.post.PostEntity;
import sjs.instagram.db.post.PostImage;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    private PostEntity createPost() {
        return new PostEntity(1L,
                Arrays.asList(new PostImage("uploadFileName1", "storeFileName1")),
                "title1",
                "content1");
    }

    @Test
    @DisplayName("게시물 생성")
    void create() {
        //given
        PostEntity post = createPost();

        //when
        PostEntity saved = postRepository.save(post);

        //then
        assertThat(saved)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(post);
    }

    @Test
    @DisplayName("게시물 조회")
    void read() {
        //given
        PostEntity post = createPost();
        PostEntity saved = postRepository.save(post);

        //when
        PostEntity find = postRepository.findById(saved.getId()).get();

        //then
        assertThat(saved)
                .usingRecursiveComparison()
                .isEqualTo(find);
    }
}
