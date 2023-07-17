function Click__init_1() {
  $(".upload-wrapper .search_btn").click(function () {
    $(".upload-wrapper").addClass("hidden");
    $(".upload-wrapper-2").removeClass("hidden");
  });
}

function Click__init_2() {
  $(".upload-wrapper-2 .trash").click(function () {
    $(".upload-wrapper").removeClass("hidden");
    $(".upload-wrapper-2").addClass("hidden");
  });
}

function Click__init_3() {
  $(".upload-wrapper-2 .tag > span").click(function () {
    $(this).parent().toggleClass("hidden");
  });
}

$(function () {
  Click__init_1();
  Click__init_2();
  Click__init_3();
});
