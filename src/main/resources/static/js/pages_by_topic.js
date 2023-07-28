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


// URL에서 /by_topic/ 다음에 오는 숫자를 추출하는 함수
    function getCategoryFromURL() {
        const url = window.location.href;
        const categoryRegex = /\/by_topic\/(\d+)/;
        const match = url.match(categoryRegex);
        if (match && match[1]) {
            return match[1];
        }
        return null;
    }

    document.addEventListener("DOMContentLoaded", function () {
        // category 값을 추출
        const category = getCategoryFromURL();

        // categoryName 요소를 가져옴
        const categoryNameSpan = document.getElementById("categoryName");

        // category에 따라 텍스트 변경
        switch (category) {
            case "1":
                categoryNameSpan.innerText = "Ai Art";
                break;
            case "2":
                categoryNameSpan.innerText = "Digital Art";
                break;
            case "3":
                categoryNameSpan.innerText = "Fan Art";
                break;
            case "4":
                categoryNameSpan.innerText = "Photography";
                break;
            case "5":
                categoryNameSpan.innerText = "Fantasy";
                break;
            case "6":
                categoryNameSpan.innerText = "Resources";
                break;
            case "7":
                categoryNameSpan.innerText = "Cosplay";
                break;
            case "8":
                categoryNameSpan.innerText = "3D";
                break;
            case "9":
                categoryNameSpan.innerText = "Fractal";
                break;
            case "10":
                categoryNameSpan.innerText = "Emoji";
                break;
            default:
                categoryNameSpan.innerText = "Unknown Category";
        }
    });
