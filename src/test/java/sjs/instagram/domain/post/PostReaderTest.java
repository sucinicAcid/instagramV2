package sjs.instagram.domain.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.db.post.PostEntity;
import sjs.instagram.db.post.PostImageEntity;
import sjs.instagram.db.user.UserEntity;
import sjs.instagram.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostReaderTest {
    @Autowired
    private PostReader postReader;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("썸네일 게시물 모두 조회")
    void readThumbnailPost() {
        //given
        List<ThumbnailPost> posts = new ArrayList<>();
        UserEntity user = userRepository.save(new UserEntity("id", "pw"));
        for (int i=0; i<5; i++) {
            PostImageEntity first = new PostImageEntity(
                    "first uploadFileName" + i,
                    "first storeFileName" + i
            );
            PostImageEntity second = new PostImageEntity(
                    "second uploadFileName" + i,
                    "second storeFileName" + i
            );
            PostEntity post = new PostEntity(
                    user.getId(),
                    Arrays.asList(first, second),
                    "title"+i,
                    "content"+i
            );
            postRepository.save(post);
            ThumbnailPost thumbnailPost = new ThumbnailPost(post.getId(), "first storeFileName" + i);
            posts.add(thumbnailPost);
        }

        //when
        List<ThumbnailPost> result = postReader.readThumbnailPost(user.getId());

        //then
        assertThat(posts).isEqualTo(result);
    }
}