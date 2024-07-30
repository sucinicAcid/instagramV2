package sjs.instagram.service.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import sjs.instagram.db.follow.FollowEntity;
import sjs.instagram.db.post.PostEntity;
import sjs.instagram.db.post.PostImageEntity;
import sjs.instagram.db.user.UserEntity;
import sjs.instagram.domain.follow.FollowRepository;
import sjs.instagram.domain.post.CreatePost;
import sjs.instagram.domain.post.PostRepository;
import sjs.instagram.domain.post.ThumbnailPost;
import sjs.instagram.domain.post.UpdatePost;
import sjs.instagram.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @Autowired
    private FollowRepository followRepository;
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

    private List<ThumbnailPost> createThumbnailPosts(Long userId) {
        List<ThumbnailPost> posts = new ArrayList<>();
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
                    userId,
                    Arrays.asList(first, second),
                    "title"+i,
                    "content"+i
            );
            postRepository.save(post);
            ThumbnailPost thumbnailPost = new ThumbnailPost(post.getId(), "first storeFileName" + i);
            posts.add(thumbnailPost);
        }
        return posts;
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
        UserEntity user = createUser();
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
        assertThat(find.getImages().get(0).getImage().getUploadFileName()).isEqualTo("file.png");
    }

    @Test
    @DisplayName("게시물 생성 오류: 제목 빈칸")
    void failCreatePostByBlankTitle() {
        //given
        UserEntity user = createUser();
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
        UserEntity user = createUser();
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
        UserEntity user = createUser();
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
        UserEntity user = createUser();
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

    @Test
    @DisplayName("게시물 삭제")
    void remove() {
        //given
        PostEntity post = createPost();

        //when
        postService.removePost(post.getUserId(), post.getId());

        //then
        List<PostEntity> findAll = postRepository.findAll();
        assertThat(findAll.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("게시물 삭제 오류: 존재하지 않는 게시물")
    void failRemovePostByNonExistPost() {
        //given
        UserEntity user = createUser();

        //when then
        assertThatThrownBy(() -> postService.removePost(user.getId(), 1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("존재하지 않는 게시물입니다.");
    }

    @Test
    @DisplayName("게시물 삭제 오류: 존재하지 않는 사용자")
    void failRemovePostByNonExistUser() {
        //given
        PostEntity post = createPost();

        //when then
        assertThatThrownBy(() -> postService.removePost(post.getId(), post.getUserId()+1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }

    @Test
    @DisplayName("게시물 삭제 오류: 게시물이 사용자의 것이 아님")
    void failRemovePostByPostIsNotOwnedByUser() {
        //given
        PostEntity post = createPost();
        UserEntity newUser = createUser();

        //when then
        assertThatThrownBy(() -> postService.removePost(newUser.getId(), post.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("본인 게시물만 삭제할 수 있습니다.");
    }

    @Test
    @DisplayName("게시물 수정")
    void update() {
        //given
        PostEntity post = createPost();
        String newTitle = "new " + post.getTitle();
        String newContent = "new " + post.getContent();
        UpdatePost updatePost = new UpdatePost(post.getId(), newTitle, newContent);

        //when
        postService.updatePost(post.getUserId(), updatePost);

        //then
        PostEntity find = postRepository.findById(post.getId()).get();
        assertThat(find.getId()).isEqualTo(updatePost.postId());
        assertThat(find.getTitle()).isEqualTo(updatePost.title());
        assertThat(find.getContent()).isEqualTo(updatePost.content());
        assertThat(find.getImages()).isEqualTo(post.getImages());
    }

    @Test
    @DisplayName("게시물 수정 오류: 제목 빈칸")
    void failUpdatePostByBlankTitle() {
        //given
        PostEntity post = createPost();
        String newTitle = "";
        String newContent = "new " + post.getContent();
        UpdatePost updatePost = new UpdatePost(post.getId(), newTitle, newContent);

        //when then
        assertThatThrownBy(() -> postService.updatePost(post.getUserId(), updatePost))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("제목에 빈칸이 들어갈 수 없습니다.");
    }

    @Test
    @DisplayName("게시물 수정 오류: 내용 빈칸")
    void failUpdatePostByBlankContent() {
        //given
        PostEntity post = createPost();
        String newTitle = "new " + post.getTitle();
        String newContent = "";
        UpdatePost updatePost = new UpdatePost(post.getId(), newTitle, newContent);

        //when then
        assertThatThrownBy(() -> postService.updatePost(post.getUserId(), updatePost))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("내용에 빈칸이 들어갈 수 없습니다.");
    }

    @Test
    @DisplayName("게시물 수정 오류: 존재하지 않는 게시물")
    void failUpdatePostByNonExistPost() {
        //given
        PostEntity post = createPost();
        UpdatePost updatePost = new UpdatePost(post.getId() + 1, post.getTitle(), post.getContent());

        //when then
        assertThatThrownBy(() -> postService.updatePost(post.getUserId(), updatePost))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("존재하지 않는 게시물입니다.");
    }

    @Test
    @DisplayName("게시물 수정 오류: 존재하지 않는 사용자")
    void failUpdatePostByNonExistUser() {
        //given
        PostEntity post = createPost();
        String newTitle = "new " + post.getTitle();
        String newContent = "new " + post.getContent();
        UpdatePost updatePost = new UpdatePost(post.getId(), newTitle, newContent);

        //when then
        assertThatThrownBy(() -> postService.updatePost(post.getUserId() + 1, updatePost))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }

    @Test
    @DisplayName("게시물 수정 오류: 게시물이 사용자의 것이 아님")
    void failUpdatePostByPostIsNotOwnedByUser() {
        //given
        UserEntity user = createUser();
        PostEntity post = createPost();
        String newTitle = "new " + post.getTitle();
        String newContent = "new " + post.getContent();
        UpdatePost updatePost = new UpdatePost(post.getId(), newTitle, newContent);

        //when then
        assertThatThrownBy(() -> postService.updatePost(user.getId(), updatePost))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("본인 게시물만 삭제할 수 있습니다.");
    }

    @Test
    @DisplayName("썸네일 게시물 모두 조회: 팔로우 관계일 때")
    void readThumbnailPostWhenFollowing() {
        //given
        UserEntity user = createUser();
        UserEntity target = createUser();
        target.changePrivacyTo(UserEntity.UserAccountPrivacy.PRIVATE);
        FollowEntity follow = new FollowEntity(user.getId(), target.getId());
        followRepository.save(follow);
        List<ThumbnailPost> posts = createThumbnailPosts(target.getId());

        //when
        List<ThumbnailPost> result = postService.readThumbnailPost(user.getId(), target.getId());

        //then
        assertThat(posts).isEqualTo(result);
    }

    @Test
    @DisplayName("썸네일 게시물 모두 조회: 상대 계정이 PUBLIC일 때")
    void readThumbnailPostWhenTargetAccountIsPUBLIC() {
        //given
        UserEntity user = createUser();
        UserEntity target = createUser();
        target.changePrivacyTo(UserEntity.UserAccountPrivacy.PUBLIC);
        List<ThumbnailPost> posts = createThumbnailPosts(target.getId());

        //when
        List<ThumbnailPost> result = postService.readThumbnailPost(user.getId(), target.getId());

        //then
        assertThat(posts).isEqualTo(result);
    }

    @Test
    @DisplayName("썸네일 게시물 모두 조회 오류: 존재하지 않는 사용자")
    void failReadThumbnailPostByNonExistUser() {
        //given
        UserEntity user1 = createUser();
        UserEntity user2 = createUser();
        Long user1Id = user1.getId();
        Long user2Id = user2.getId();

        //when then
        assertThatThrownBy(() -> postService.readThumbnailPost(user1Id, user1Id+user2Id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
        assertThatThrownBy(() -> postService.readThumbnailPost(user1Id+user2Id, user2Id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
        assertThatThrownBy(() -> postService.readThumbnailPost(2*user1Id+user2Id, user1Id+user2Id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }

    @Test
    @DisplayName("썸네일 게시물 모두 조회 오류: 게시물 접근 권한 없음(팔로잉 하지 않으면서 상대 계정이 PRIVATE)")
    void failReadThumbnailPostInvalidAccess() {
        //given
        UserEntity user = createUser();
        UserEntity target = createUser();
        target.changePrivacyTo(UserEntity.UserAccountPrivacy.PRIVATE);

        //when then
        assertThatThrownBy(() -> postService.readThumbnailPost(user.getId(), target.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("게시물 접근 권한이 없습니다.");
    }
}