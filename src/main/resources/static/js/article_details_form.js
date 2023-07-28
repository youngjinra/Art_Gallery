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

function side_info_2_click() {
  $("body .side_info_imoji").click(function () {
    $(this).addClass("hidden");
  });
}

function delete_function() {
  $(".delete_btn").click(function () {
    var confirmDelete = confirm("정말로 삭제하시겠습니까?");

    if (confirmDelete) {
      alert("삭제되었습니다.");
      // 현재 페이지로 이동 (리다이렉션)
      window.location.href = "https://cdpn.io/pen/debug/bGQogqX";
    }
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

function validateCommentForm() {
    // textarea의 값을 가져옵니다.
    var commentContent = document.getElementById('commentContent').value.trim();

    // textarea의 값이 공백인지 확인합니다.
    if (commentContent === '') {
        alert('내용을 입력해주세요.');
        return false; // 폼 제출을 막습니다.
    }

    return true; // 폼 제출을 허용합니다.
}

function validateReplyForm() {
    // textarea의 값을 가져옵니다.
    var replyContent = document.getElementById('replyContent').value.trim();

    // textarea의 값이 공백인지 확인합니다.
    if (replyContent === '') {
        alert('답글을 입력해주세요.'); // 사용자에게 답글 내용을 입력하라는 메시지를 보여줍니다.
        return false; // 폼 제출을 막습니다.
    }

    return true; // 폼 제출을 허용합니다.
}

$(function () {
  SideClick__ft();
  textarea_resize();
  additional_heart_ivent();
  side_info_2_click();
  delete_function();
  replies_box_ivent();
  validateCommentForm();
  validateReplyForm();
});
