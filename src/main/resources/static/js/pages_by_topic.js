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
  $(".small-search-bar").removeClass("active");
  $(".top-bar-bg").removeClass("menu-bg");
  $(".menu").removeClass("active");

  // 스크롤 이벤트 처리
  $(window).scroll(function () {
    let scrollTop = $(window).scrollTop();
    if (scrollTop == 0 || scrollTop < 200) {
      // $(".small-search-bar").addClass("active");
      $(".top-bar-bg").removeClass("menu-bg");
      $(".menu").removeClass("active");
    } else {
      // $(".small-search-bar").removeClass("active");
      $(".top-bar-bg").addClass("menu-bg");
      $(".menu").addClass("active");
    }
  });
}

$(function () {
  PageLayout__init();
  Click__init();
  TopBar__init();
  slide_1();
});



function sortPosts() {
   // 1. 선택된 옵션 값 가져오기
   var selectedOption = document.getElementById("sortingOption").value;

   // 2. 새로운 URL 생성
   var newUrl = "/by_topic?sortingOption=" + selectedOption;

   // 3. URL로 이동
   location.href = newUrl;
}


  // select 요소에 대한 onchange 이벤트 리스너 등록
  document.getElementById("category").onchange = function () {
    // 선택된 옵션의 값을 가져옴
    var selectedCategory = document.getElementById("category").value;

    // 선택된 카테고리에 따라 span 태그의 내용 변경
    switch (selectedCategory) {
      case "1":
        document.getElementById("categoryName").innerText = "Ai Art";
        break;
      case "2":
        document.getElementById("categoryName").innerText = "Digital Art";
        break;
      case "3":
        document.getElementById("categoryName").innerText = "Fan Art";
        break;
      case "4":
        document.getElementById("categoryName").innerText = "Photography";
        break;
      case "5":
        document.getElementById("categoryName").innerText = "Fantasy";
        break;
      case "6":
        document.getElementById("categoryName").innerText = "Resources";
        break;
      case "7":
        document.getElementById("categoryName").innerText = "Cosplay";
        break;
      case "8":
        document.getElementById("categoryName").innerText = "3D";
        break;
      case "9":
        document.getElementById("categoryName").innerText = "Fractal";
        break;
      case "10":
        document.getElementById("categoryName").innerText = "Emoji";
        break;
      default:
        // 기본적으로는 "Ai Art"로 설정
        document.getElementById("categoryName").innerText = "Ai Art";
    }
  };
