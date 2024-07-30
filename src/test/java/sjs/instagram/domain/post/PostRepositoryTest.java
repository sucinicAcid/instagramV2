package sjs.instagram.domain.post;

import org.junit.jupiter.api.BeforeEach;
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

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    private String userInstagramId = "id" + 0;

    private PostEntity createPost() {
        UserEntity user = createUser();
        PostEntity post =  new PostEntity(
                user.getId(),
                Arrays.asList(new PostImageEntity("uploadFileName1", "storeFileName1")),
                "title1",
                "content1"
        );
        return postRepository.save(post);
    }

    private UserEntity createUser() {
        return userRepository.save(new UserEntity(nextUserInstagramId(), "pw"));
    }

    private String nextUserInstagramId() {
        String numStr = userInstagramId.substring(2, userInstagramId.length());
        int numInt = Integer.parseInt(numStr);
        return "id" + (numInt+1);
    }

    @BeforeEach
    void setUserInstagramIdToid0() {
        userInstagramId = "id" + 0;
    }

    @Test
    @DisplayName("게시물 생성")
    void create() {
        //given
        UserEntity user = userRepository.save(new UserEntity("id", "pw"));
        PostEntity post =  new PostEntity(user.getId(),
                Arrays.asList(new PostImageEntity("uploadFileName1", "storeFileName1")),
                "title1",
                "content1");

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

        //when
        PostEntity find = postRepository.findById(post.getId()).get();

        //then
        assertThat(post)
                .usingRecursiveComparison()
                .isEqualTo(find);
    }

    @Test
    @DisplayName("게시물 수정")
    void update() {
        //given
        PostEntity post = createPost();
        String newTitle = "new " + post.getTitle();
        String newContent = "new " + post.getContent();

        //when
        post.update(newTitle, newContent);

        //then
        PostEntity find = postRepository.findById(post.getId()).get();
        assertThat(find.getId()).isEqualTo(post.getId());
        assertThat(find.getUserId()).isEqualTo(post.getUserId());
        assertThat(find.getTitle()).isEqualTo(newTitle);
        assertThat(find.getContent()).isEqualTo(newContent);
        assertThat(find.getImages()).isEqualTo(post.getImages());
    }

    @Test
    @DisplayName("게시물 삭제")
    void delete() {
        //given
        PostEntity post = createPost();

        //when
        postRepository.deleteById(post.getId());

        //then
        List<PostEntity> findAll = postRepository.findAll();
        assertThat(findAll.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("썸네일 게시물 모두 조회")
    void findThumbnailPost() {
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
        List<ThumbnailPost> result = postRepository.findThumbnailPost(user.getId());

        //then
        assertThat(posts).isEqualTo(result);
    }
}
