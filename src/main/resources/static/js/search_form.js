var $window = $(window);
var $html = $("html");

var PageLayout__ani1Delay = 500;

function PageLayout__init() {
  $(".section-1").imagesLoaded(function () {
    setTimeout(function () {
      $(".section-1_loading").remove();
    }, PageLayout__ani1Delay + 200);

    $(".masonry-grid").masonry({
      itemSelector: ".grid-item"
    });

    $window.resize(
      _.debounce(function () {
        $(".masonry-grid").masonry("layout");
      }, PageLayout__ani1Delay + 200)
    );
  });
}

function Click__init() {
  $(".font-awsome").click(function () {
    $(this).toggleClass("bg-green-500");
  });
}

$(function () {
  PageLayout__init();
  Click__init();
});

function sortPosts() {
    var selectedOption = document.getElementById("sortingOption").value;
    var keyword = document.getElementById("keywordSpan").innerText;
    var newUrl = "/search?keyword=" + keyword + "&sortingOption=" + selectedOption;

    location.href = newUrl;
}