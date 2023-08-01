function textarea_resize() {
  $(".comment_body_textarea").on("keydown keyup", function () {
    $(this).height(1).height($(this).prop("scrollHeight"));
  });
}

function dot_click() {
  $(".update_delete_wrap .dot").click(function (event) {
    event.stopPropagation();
    var target = $(this).siblings(".update_delete");
    target.toggleClass("hidden");
  });
}

// 문서(document) 클릭 시, .update_delete 요소를 숨기는 함수
function hideUpdateDeleteOnClickDocument() {
  $(document).click(function (event) {
    var target = $(".update_delete");
    if (!target.is(event.target) && target.has(event.target).length === 0) {
      target.addClass("hidden");
    }
  });
}

function replies_box_ivent() {
  $(".reply").click(function () {
    var repliesBox = $(this).closest(".particle").find(".replies_box");
    repliesBox.toggleClass("hidden");
    $(".replies_box")
      .not(repliesBox)
      .addClass("hidden");
  });
}

function side_info_2_click() {
  $("body .side_info_imoji").click(function () {
    $(this).addClass("hidden");
  });
}

    // 삭제 버튼을 클릭했을 때 호출되는 함수
    $('.delete_btn_2').on('click', function () {
        var postId = $(this).find('.trash').data('post-id'); // 해당 게시물의 ID 가져오기

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

// 댓글 및 답글 유효성 검사 시작
function validateAndSubmitCommentForm(event) {
    var commentContent = document.getElementById('commentContent').value.trim();

    if (commentContent === '') {
        alert('내용을 입력해주세요.');
        event.preventDefault(); // 댓글 내용이 비어있으면 폼 제출을 막습니다.
    }
}

function validateAndSubmitReplyForm(event) {
    var replyContent = event.target.querySelector('textarea[name="replyContent"]').value.trim();

    if (replyContent === '') {
        alert('답글을 입력해주세요.');
        event.preventDefault(); // 답글 내용이 비어있으면 폼 제출을 막습니다.
    }
}

// 폼 제출 이벤트 핸들러를 등록합니다.
document.getElementById('commentForm').addEventListener('submit', validateAndSubmitCommentForm);

// 답글 폼이 여러 개일 수 있으므로 반복문을 통해 모든 답글 폼에 이벤트 핸들러를 등록합니다.
var replyForms = document.querySelectorAll('.replies_box form');
for (var i = 0; i < replyForms.length; i++) {
    replyForms[i].addEventListener('submit', validateAndSubmitReplyForm);
}
// 댓글 및 답글 유효성 검사 끝

// 댓글 및 답글 삭제부분
function delete_reply(dataUri) {
// 수정: 댓글 ID 파라미터를 함수로 전달 받음
    console.log(dataUri);
    let commentId = dataUri.match(/\/comment\/delete\/[^/]+\/[^/]+\/(\d+)/)[1];
    console.log("Comment ID:" + commentId);
    if (confirm("정말로 삭제하시겠습니까?")) {
        // AJAX 요청을 보낼 데이터 구성
        var dataToSend = {
            id: commentId // 수정: 넘겨받은 댓글 ID를 데이터에 넣어줌
        };

        // AJAX 요청을 보냅니다.
        $.ajax({
            url: dataUri,
            type: 'GET',
            async: true,
            dataType: "json",
            data: dataToSend, // 요청에 데이터 추가
            success: function(data) {
                alert("성공했습니다.");

                // 이벤트 발생 시점의 스크롤 위치 저장
                $(window).on('scroll', function() {
                  sessionStorage.setItem('scrollPosition', window.scrollY);
                });

                // 페이지 새로고침
                window.location.reload();
              },
            error: function(e) {
                console.log("AJAX 요청 실패:", e);
                alert("비정상적인 접근입니다. 다시 시도해주십시오.");
            }
        });
    }
}

$(function () {
  textarea_resize();
  side_info_2_click();
  dot_click();
  hideUpdateDeleteOnClickDocument();
  replies_box_ivent();
});

// 좋아요 확인창
const recommend_elements = document.getElementsByClassName("recommend");
Array.from(recommend_elements).forEach(function(element) {
    element.addEventListener('click', function() {
        const confirmed = confirm("좋아요를 누르시겠습니까?");
        if (confirmed) {
            alert("10 point가 적립되었습니다.");
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

// 포인트 결제
function purchaseImage() {
    var postId = parseInt(document.getElementById("articlePurchase").getAttribute("data-post-id"));
    var userId = parseInt(document.getElementById("articlePurchase").getAttribute("data-user-id"));

    var confirmMessage = "결제하시겠습니까?";
    if (!confirm(confirmMessage)) {
        return; // 사용자가 취소를 누른 경우 함수 종료
    }
    var requestData = {
        postId: postId,
        userId: userId
    };

    fetch("/purchase", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
    .then(response => {
        if (response.ok) {
            return response.json(); // 성공 시 JSON 데이터를 파싱하여 반환
        } else {
            throw new Error('Network response was not ok');
        }
    })
    .then(result => {
        // 서버로부터 받은 결과 데이터(result)를 처리
        if (result) {
            // 결제가 성공한 경우
            alert("결제 성공!");
            location.reload();
        } else {
            // 결제가 실패한 경우 (포인트 부족 등)
            alert("결제에 실패하였습니다. 잔여 포인트를 확인해주세요.");
        }
    })
    .catch(error => {
        alert("서버와의 통신 중에 오류가 발생하였습니다.");
    });
}

