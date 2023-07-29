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
  $(".additional .heart").click(function () {
    $(this).children(".heart_1").toggleClass("hidden");
    $(this).children(".heart_2").toggleClass("hidden");
  });
}

function side_info_2_click() {
  $("body .side_info_imoji").click(function () {
    $(this).addClass("hidden");
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

function deleteElements() {
    const deleteElements = $(".delete_btn");
    deleteElements.on('click', function() {
        if (confirm("정말로 삭제하시겠습니까?")) {
            location.href = $(this).data("uri");
        }
    });
}

$(function () {
  SideClick__ft();
  textarea_resize();
  additional_heart_ivent();
  side_info_2_click();
  replies_box_ivent();
  dot_click();
  hideUpdateDeleteOnClickDocument();
  deleteElements();
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
