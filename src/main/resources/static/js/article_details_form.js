function SideClick__ft() {
  $(".click").click(function () {
    $(this).toggleClass("active");
  });
}

function textarea_resize() {
  $(".comment_body_textarea").on("keydown keyup", function () {
    $(this).height(1).height($(this).prop("scrollHeight"));
  });
}

function additional_heart_ivent() {
  $(".additional > .heart").click(function () {
    $(this).children(".heart_1").toggleClass("hidden");
    $(this).children(".heart_2").toggleClass("hidden");
  });
}

function replies_box_ivent() {
  $(".reply").click(function () {
    $(this).parent().siblings(".replies_box").toggleClass("hidden");
    $(".replies_box")
      .not($(this).parent().siblings(".replies_box"))
      .addClass("hidden");
  });
}

function side_info_2_click() {
  $("body .side_info_imoji").click(function () {
    $(this).addClass("hidden");
  });
}

    // 삭제 버튼을 클릭했을 때 호출되는 함수
    $('.delete_btn').on('click', function () {
        var postId = $(this).find('.fa-trash').data('post-id'); // 해당 게시물의 ID 가져오기

        // 게시물 삭제 전에 사용자에게 확인을 받는다.
        var confirmDelete = confirm('게시물을 삭제하시겠습니까?');
        if (!confirmDelete) {
            return; // 사용자가 취소하면 삭제 동작을 수행하지 않음.
        }

        // 게시물 삭제 요청을 보내는 API 호출
        $.ajax({
            type: 'DELETE',
            url: '/api/post/delete/' + postId, // 게시물 삭제 API 엔드포인트로 변경해야 함
            success: function (data) {
                // 삭제 성공 시, 원하는 동작 수행 (예: 해당 게시물을 화면에서 제거하는 등)
                alert("게시물 삭제 성공");
                history.back(); // 이전 페이지로 돌아감
                console.log('게시물 삭제 성공');
            },
            error: function (error) {
                // 삭제 실패 시, 원하는 동작 수행
                alert("게시물 삭제 실패");
                history.back(); // 이전 페이지로 돌아감
                console.error('게시물 삭제 실패');
            }
        });
    });

$(function () {
//  SideClick__ft();
  textarea_resize();
  additional_heart_ivent();
  replies_box_ivent();
  side_info_2_click();
});

// 좋아요 확인창
const recommend_elements = document.getElementsByClassName("recommend");
Array.from(recommend_elements).forEach(function(element) {
    element.addEventListener('click', function() {
        const confirmed = confirm("좋아요를 누르시겠습니까?");
        if (confirmed) {
//            alert("10 point가 적립되었습니다.");
            location.href = this.dataset.uri;
        }
    });
});
/********* 수정 *********/

function downloadImage() {
    const imageSrc = document.getElementById('myImage').src;
    const fileName = getFileNameFromURL(imageSrc);

    const anchor = document.createElement('a');
    anchor.href = imageSrc;
    anchor.download = fileName;
    anchor.click();

    // AJAX 요청을 보내서 postDownloads 증가시키기
    const postId = document.querySelector('.donwload').dataset.postId;
    const encodedPostId = encodeURIComponent(postId); // postId를 인코딩
    incrementPostDownloads(encodedPostId);
  }

  function getFileNameFromURL(url) {
    const urlParts = url.split('/');
    return urlParts[urlParts.length - 1];
  }

// 다운로드 클릭시 ajax요청 메서드
function incrementPostDownloads(postId) {
    fetch("/post/incrementDownloads/" + postId , {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            console.log('다운로드 횟수 증가 성공');
        } else {
            console.error('다운로드 횟수 증가 실패');
        }
    })
    .catch(error => {
        console.error('다운로드 횟수 증가 중 오류 발생:', error);
    });
}

// 관련 이미지 '+' 검색페이지 이동
$(function() {
    $(".plus_btn").click(function(e) {
        e.preventDefault();

        var hashtagsStr = $(this).data("hashtags"); // data-hashtags 값을 가져옴
        var hashtags = hashtagsStr.replace(/\[|\]/g, '').split(", "); // 대괄호 제거 후 쉼표로 분리하여 배열로 변환
        var keyword = hashtags.join(' ');       /* 해시태그들을 공백으로 연결 */

        // 검색 페이지로 이동
        window.location.href = "/search?keyword=" + encodeURIComponent(keyword);
    });
});
