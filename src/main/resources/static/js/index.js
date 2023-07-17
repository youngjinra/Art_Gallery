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

function TopBar__init() {
  // 초기화 시점에 스타일 적용
  $(".small-search-bar").addClass("hidden");
  $(".top-bar-bg").removeClass("menu-bg");
  $(".menu").removeClass("active");

  // 스크롤 이벤트 처리
  $(window).scroll(function () {
    let scrollTop = $(window).scrollTop();
    if (scrollTop == 0 || scrollTop < 300) {
      $(".small-search-bar").addClass("hidden");
      $(".top-bar-bg").removeClass("menu-bg");
      $(".menu").removeClass("active");
    } else {
      $(".top-bar-bg").addClass("menu-bg");
      $(".menu").addClass("active");
      $(".small-search-bar").removeClass("hidden");
    }
  });
}

function slide_1() {
  var swiper = new Swiper(".slide-1", {
    watchSlidesProgress: true,
    slidesPerView: 8,
    spaceBetween: 8,
    navigation: {
      nextEl: ".swiper-button-next",
      prevEl: ".swiper-button-prev"
    }
  });
}

$(function () {
  PageLayout__init();
  Click__init();
  TopBar__init();
  slide_1();
});
