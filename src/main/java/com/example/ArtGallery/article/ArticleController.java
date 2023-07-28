package com.example.ArtGallery.article;


import com.example.ArtGallery.article.post.PostEntity;
import com.example.ArtGallery.article.post.PostService;
import com.example.ArtGallery.follow.FollowService;
import com.example.ArtGallery.user.UserEntity;
import com.example.ArtGallery.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final UserService userService;
    private final PostService postService;
    private final FollowService followService;

    @GetMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public String uploadnavbar(Model model, Authentication authentication, RedirectAttributes redirectAttributes){

        // 인증된 사용자의 nickname 가져오기
        String nicknameConfirm = null;
        String userEmail = null;
        if (authentication != null && authentication.isAuthenticated()) {
            // UserService에서 로그인 유저 닉네임 반환하는 메소드 호출
            nicknameConfirm = userService.getAuthNickname(userEmail, nicknameConfirm, authentication);

        } else {
            return "index";
        }

        // 해당 nickname을 사용하여 유저 정보 가져오기
        UserEntity loginUser = userService.getUserNick(nicknameConfirm);
        model.addAttribute("loginUser", loginUser);

        return "upload_form";     // 로그인한 상태일 때는 로그인한 유저의 데이터 객체가 인덱스로 넘어감

    }

    @GetMapping("/article/details/{nickname}/{postId}")
    public String articleDetail(Model model, Authentication authentication, @PathVariable String nickname, @PathVariable int postId){

        String nicknameConfirm = null;
        String userEmail = null;
        if (authentication != null && authentication.isAuthenticated()) {
            // UserService에서 로그인 유저 닉네임 반환하는 메소드 호출
            nicknameConfirm = userService.getAuthNickname(userEmail, nicknameConfirm, authentication);
        }

        // 해당 게시물 주인의 userEntity를 'user'로 템플릿에서 활용할 수 있게 반환
        UserEntity userEntity = this.userService.getUserNick(nickname);
        model.addAttribute("user", userEntity);

        // 해당 게시물의 정보(postEntity)를 'post'로 템플릿에 활용할 수 있게 반환
        PostEntity post = this.postService.getPost(postId);
        model.addAttribute("post", post);

        if(nicknameConfirm == null) {   // 비로그인 유저들을 위한 조건 추가

        } else {
            // 현재 로그인한 유저의 닉네임과 해당 프로필의 유저의 닉네임을 비교해서 isCurrentUser에 true, false를 반환
            model.addAttribute("isCurrentUser", nicknameConfirm.equals(nickname));
            
            // 현재 로그인한 유저의 정보를 'loginUser'로 템플릿에서 활용할 수 있게 반환
            UserEntity loginUserEntity = this.userService.getUserNick(nicknameConfirm);
            model.addAttribute("loginUser", loginUserEntity);

            boolean isFollowing = followService.isFollowing(loginUserEntity, userEntity);
            model.addAttribute("isFollowing", isFollowing);

            // 현재 접속한 유저가 해당 게시물을 이미 저장했는지 판별
            boolean savedByCurrentUser = userService.checkIfSavedByCurrentUser(loginUserEntity.getNickname(), postId);
            model.addAttribute("savedByCurrentUser", savedByCurrentUser);

            // 해당 게시물에 좋아요를 누른 유저인지 아닌지 판별
            boolean isLiked = postService.isLikedByCurrentUser(postId, loginUserEntity.getNickname());
            model.addAttribute("isLiked", isLiked);
        }

        // 게시물을 올린 유저의 팔로워 수 템플릿에 반환
        int followerCount = followService.getFollowerCount(userEntity.getId());
        model.addAttribute("followerCount", followerCount);

        // 해당 게시물을 저장한 유저의 수
        int savedUserCount = userService.getSavedUserCount(postId);
        model.addAttribute("savedUserCount", savedUserCount);

        // 해당 게시물에 저장된 태그들 반환
        List<String> hashtags = postService.getTagsByPostId(postId);
        model.addAttribute("hashtags", hashtags);

        return "article_details_form";
    }

    @GetMapping("/by_topic/{category}")
    public String bytopic(Model model, Authentication authentication, @RequestParam(name = "sortingOption", defaultValue = "1") int sortingOption,
                          @PathVariable int category){

        String nicknameConfirm = null;
        String userEmail = null;
        if (authentication != null && authentication.isAuthenticated()) {
            // UserService에서 로그인 유저 닉네임 반환하는 메소드 호출
            nicknameConfirm = userService.getAuthNickname(userEmail, nicknameConfirm, authentication);

            // 해당 nickname을 사용하여 유저 정보 가져오기
            UserEntity userEntity = userService.getUserNick(nicknameConfirm);
            model.addAttribute("userEntity", userEntity);

            List<PostEntity> postEntityList = this.postService.getList();
            model.addAttribute("postList", postEntityList);
        }

        // 상단 헤더바 부분 내정보 이미지 클릭시 로그인한 해당 유저의 정보 페이지를 이동하기 위해 nicknameConfirm을 그대로 템플릿에 보내줌
        model.addAttribute("nicknameConfirm", nicknameConfirm);

        // 사용자가 선택한 정렬 기준을 서비스에 전달하여 해당 카테고리의 게시물 목록을 가져옴
        List<PostEntity> sortedPosts = postService.getSortedPosts_category(sortingOption, nicknameConfirm, category);

        // 뷰에 필요한 데이터 전달
        model.addAttribute("postList", sortedPosts);
        model.addAttribute("sortingOption", sortingOption);

        model.addAttribute("category", category);
        return "pages_by_topic";
    }

    @GetMapping("/search")
    public String searchform(Model model, Authentication authentication, @RequestParam("keyword") String keyword,  @RequestParam(name = "sortingOption", defaultValue = "1") int sortingOption){

        String nicknameConfirm = null;
        String userEmail = null;
        if (authentication != null && authentication.isAuthenticated()) {
            // UserService에서 로그인 유저 닉네임 반환하는 메소드 호출
            nicknameConfirm = userService.getAuthNickname(userEmail, nicknameConfirm, authentication);

        }

        // 검색 결과에 부합하는 게시물들 반환 (+정렬)
        List<PostEntity> searchResults = postService.getPostsByHashtag(keyword, sortingOption);
        model.addAttribute("keyword", keyword);

        // 상단 헤더바 부분 내정보 이미지 클릭시 로그인한 해당 유저의 정보 페이지를 이동하기 위해 nicknameConfirm을 그대로 템플릿에 보내줌
        model.addAttribute("nicknameConfirm", nicknameConfirm);
        model.addAttribute("posts", searchResults);

        model.addAttribute("sortingOption", sortingOption);

        return "search_form";
    }

}

