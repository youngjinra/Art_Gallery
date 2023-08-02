// 게시물의 조회수 증가
$(function () {
  $(".post-link").click(function (e) {

    var postId = $(this).data("post-id");
    console.log("postid : ", postId);

    $.ajax({
      type: "PUT",
      url: "/api/increase-views/" + postId,
      success: function () {
        // 조회수 증가 성공
      },
      error: function () {
        // 조회수 증가 실패
      }
    });
  });
});

// 팔로우 확인창
const follow = document.getElementsByClassName("followUser");
Array.from(follow).forEach(function (element) {
  element.addEventListener('click', function () {
    const confirmed = confirm("정말 팔로우 하시겠습니까?");
    if (confirmed) {
      const followUrl = this.dataset.uri;
      fetch(followUrl, { method: 'POST' })
        .then((response) => {
          if (response.ok) {
            // 요청이 성공적으로 처리되었을 때, 페이지 새로고침
            console.log("end1")
            location.reload();

          } else {
            // 요청이 실패했을 때, 오류 처리를 합니다.
            //                  alert('팔로우 추가에 실패했습니다.');
          }
        })
        .catch((error) => {
          console.error('Error:', error);
          alert('팔로우 추가에 실패했습니다.');
        });
    }
  });
});


// 언팔로우 확인창
const unfollow = document.getElementsByClassName("unfollowUser");
Array.from(unfollow).forEach(function (element) {
  element.addEventListener('click', function () {
    const unconfirmed = confirm("정말 팔로우 취소 하시겠습니까?");
    if (unconfirmed) {
      const unfollowUrl = this.dataset.uri;
      fetch(unfollowUrl, { method: 'POST' })
        .then((response) => {
          if (response.ok) {
            // 요청이 성공적으로 처리되었을 때, 페이지 새로고침
            location.reload();
          } else {
            // 요청이 실패했을 때, 오류 처리를 합니다.
            //                  alert('팔로우 추가에 실패했습니다.');
          }
        })
        .catch((error) => {
          console.error('Error:', error);
          alert('팔로우 취소에 실패했습니다.');
        });
    }
  });
});

// 즐겨찾기 추가 확인창
const addToCollection = document.getElementsByClassName("addToCollection");
Array.from(addToCollection).forEach(function (element) {
  element.addEventListener('click', function () {
    const confirmed = confirm("정말 즐겨찾기에 추가하시겠습니까?");
    if (confirmed) {
      const addToCollectionUrl = this.dataset.uri;
      fetch(addToCollectionUrl, { method: 'POST' })
        .then((response) => {
          if (response.ok) {
            // 요청이 성공적으로 처리되었을 때, 페이지 새로고침
            location.reload();
          } else {
            // 요청이 실패했을 때, 오류 처리를 합니다.
            alert('즐겨찾기 추가에 실패했습니다.');
          }
        })
        .catch((error) => {
          console.error('Error:', error);
          alert('즐겨찾기 추가에 실패했습니다.');
        });
    }
  });
});


// 저장 취소 버튼 클릭 이벤트
const removeFromCollection = document.getElementsByClassName("removeToCollection");
Array.from(removeFromCollection).forEach(function (element) {
  element.addEventListener('click', function () {
    const unconfirmed = confirm("정말 즐겨찾기에서 제거하시겠습니까?");
    if (unconfirmed) {
      const removeFromCollectionUrl = this.dataset.uri;
      fetch(removeFromCollectionUrl, { method: 'POST' })
        .then((response) => {
          if (response.ok) {
            // 요청이 성공적으로 처리되었을 때, 페이지 새로고침
            location.reload();
          } else {
            // 요청이 실패했을 때, 오류 처리를 합니다.
            alert('즐겨찾기 제거에 실패했습니다.');
          }
        })
        .catch((error) => {
          console.error('Error:', error);
          alert('즐겨찾기 제거에 실패했습니다.');
        });
    }
  });
});