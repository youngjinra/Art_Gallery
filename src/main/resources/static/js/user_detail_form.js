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

function Member__click() {
  $(".add_follow").click(function () {
    $(this).addClass("hidden");
    $(".point").addClass("hidden");
    $(".collection").addClass("hidden");
    $(".member").css("width", "240px");
  });
}

function updete_delete() {
  // 업데이트 삭제 아이콘 클릭 시
  $(".section-1 .grid-item .update_delete_wrap > i").click(function (event) {
    var target = $(this).siblings(".update_delete");
    target.css("display", target.css("display") === "none" ? "flex" : "none");
    event.stopPropagation(); // 클릭 이벤트 전파 중지
  });

  // 문서(document) 클릭 시
  $(document).click(function (event) {
    var target = $(".section-1 .grid-item .update_delete_wrap .update_delete");
    if (!target.is(event.target) && target.has(event.target).length === 0) {
      target.css("display", "none");
    }
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
  PageLayout__init();
  Click__init();
  TopBar__init();
  Member__click();
  updete_delete();
  delete_function();
});
