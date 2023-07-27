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

//function anchor() {
//  // 댓글과 답글 입력창(form)을 모두 가져옵니다.
//  var forms = $('.comment_body_form_wrapper, .replies_box.comment_body_form_wrapper');
//
//  // 각각의 폼에 대해 처리합니다.
//  forms.each(function() {
//    var form = $(this);
//
//    // 댓글과 답글 입력창(form) 중에서 유효성 검사 에러 메시지를 담은 div를 가져옵니다.
//    var errorDiv = form.find('.alert.alert-danger');
//
//    // 유효성 검사 에러가 있을 경우 처리합니다.
//    if (errorDiv.length > 0) {
//      // 해당 폼의 hidden 속성을 제거하여 보이도록 변경합니다.
//      form.css('display', 'flex');
//
//      // 댓글 혹은 답글 입력창(form)의 위치보다 180px 위로 스크롤합니다.
//      var scrollTopPosition = errorDiv.closest('.comment_body_form_wrapper, .replies_box.comment_body_form_wrapper').offset().top - 180;
//
//      // 스크롤 애니메이션을 수행합니다.
//      $('html, body').animate({
//        scrollTop: scrollTopPosition
//      }, 0);
//
//      // 해당 폼을 제외한 나머지 폼들의 유효성 검사 에러 메시지를 숨깁니다.
//      forms.not(form).find('.alert.alert-danger').hide();
//      return false; // 각각의 폼에 대한 처리를 종료합니다.
//    }
//  });
//}

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


$(function () {
  SideClick__ft();
  textarea_resize();
  additional_heart_ivent();
  side_info_2_click();
  delete_function();
  replies_box_ivent();
  anchor();
  validateCommentForm();
  replyCommentContent();
});
