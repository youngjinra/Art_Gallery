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



/*
// 비동기적으로 서버와 통신해야 하거나 서버 응답에 따른 추가적인 처리가 필요하다면
function sortPosts() {
   // 1. 선택된 옵션 값 가져오기
   var selectedOption = document.getElementById("sortingOption").value;

   // 2. AJAX 요청 보내기
   var xhr = new XMLHttpRequest();
   xhr.open("GET", "/?sortingOption=" + selectedOption, true);
   xhr.onreadystatechange = function () {
       if (xhr.readyState === XMLHttpRequest.DONE) {
           if (xhr.status === 200) {
              // 3. 요청이 성공하면 새로고침
              location.href = "/?sortingOption=" + selectedOption
//                location.reload();
           } else {
              // 4. 요청 실패 처리
              console.error("요청 실패");
           }
       }
   };
   xhr.send();
}*/

// 서버 응답이 필요 없고 즉시 페이지를 새로고침하고 싶다면
function sortPosts() {
   // 1. 선택된 옵션 값 가져오기
   var selectedOption = document.getElementById("sortingOption").value;

   // 2. 새로운 URL 생성
   var newUrl = "/?sortingOption=" + selectedOption;

   // 3. URL로 이동
   location.href = newUrl;
}

