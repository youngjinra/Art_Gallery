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

$(function () {
  SideClick__ft();
  textarea_resize();
  additional_heart_ivent();
  replies_box_ivent();
  side_info_2_click();
  delete_function();
});

/********* 수정 *********/

function downloadImage() {
    const imageSrc = document.getElementById('myImage').src;
    const fileName = getFileNameFromURL(imageSrc);

    const anchor = document.createElement('a');
    anchor.href = imageSrc;
    anchor.download = fileName;
    anchor.click();
  }

  function getFileNameFromURL(url) {
    const urlParts = url.split('/');
    return urlParts[urlParts.length - 1];
  }
