function textarea_resize() {
  $(".textarea").on("keydown keyup", function () {
    $(this).height(254).height($(this).prop("scrollHeight"));
  });
}


$(function () {
  textarea_resize();
});