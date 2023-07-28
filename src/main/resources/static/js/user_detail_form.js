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
      location.reload();
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


/*function sortPosts2() {
   // 1. 선택된 옵션 값 가져오기
   var selectedOption = document.getElementById("sortingOption").value;
   // 2. 게시물 작성자의 닉네임 값 가져오기
   var nickname = document.getElementById("postNickname").value;
   // 2. AJAX 요청 보내기
   var xhr = new XMLHttpRequest();
   xhr.open("GET", "/user/detail_form/" + nickname +"?sortingOption=" + selectedOption, true);
   xhr.onreadystatechange = function () {
       if (xhr.readyState === XMLHttpRequest.DONE) {
           if (xhr.status === 200) {
              // 3. 요청이 성공하면 새로고침
              location.href = "/user/detail_form/" + nickname +"?sortingOption=" + selectedOption
           } else {
              // 4. 요청 실패 처리
              console.error("요청 실패");
           }
       }
   };
   xhr.send();
}*/


function sortPosts2() {
   var selectedOption = document.getElementById("sortingOption").value;
   var newUrl = "/user/detail_form/" + postNickname + "?sortingOption=" + selectedOption;

   location.href = newUrl;
}


function toggleLists() {
    var imageList = document.getElementById("imageList");
    var collectionList = document.getElementById("collectionList");
    var currentUrl = window.location.href;
    var isCollectionVisible = currentUrl.includes("/collection");

    if (!isCollectionVisible) {
        var newUrl = currentUrl + "/collection";
        window.location.href = newUrl;
        document.getElementById('sortingOption').style.display = 'none';
    } else {
        var newUrl = currentUrl.replace("/collection", "");
        window.location.href = newUrl;
    }
}

window.onload = function() {
    var currentUrl = window.location.href;
    var isCollectionVisible = currentUrl.includes("/collection");
    var sortingOption = document.getElementById("sortingOption");

    if (isCollectionVisible) {
        sortingOption.style.display = "none";
    } else {
        sortingOption.style.display = "block";
    }
}
