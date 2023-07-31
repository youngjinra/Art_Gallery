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

    // 이미지 미리보기 함수
    function setThumbnail(event) {
        var reader = new FileReader();
        reader.onload = function (event) {
            var img = document.createElement("img");
            img.setAttribute("src", event.target.result);
            img.setAttribute("class", "col-lg-6");
            img.setAttribute("id", "myImage");

            var existingImage = document.getElementById("myImage");
            if (existingImage) {
                existingImage.remove();
            }

            document.querySelector("div#image_container").appendChild(img);
        };

        reader.readAsDataURL(event.target.files[0]);
    }

// 해시태그 기능 스크립트
  const hashtagsInput = document.getElementById("hashtags-input");
  const hashtagsHidden = document.getElementById("hashtags-hidden");
  const addedHashtags = new Set();

  hashtagsInput.addEventListener("keypress", addHashtagInput);

  function addHashtagInput(event) {
    if (event.key === "Enter" || event.key === " ") {
      const input = document.getElementById("hashtags-input");
      const hashtag = input.value.trim();

      if (hashtag.length > 0 && addedHashtags.size < 8) {
        const container = document.getElementById("hashtags-display");
        const tagDiv = document.createElement("div");
        tagDiv.classList.add("tag", "flex", "gap-1");
        tagDiv.textContent = "#" + hashtag;

        const xIcon = document.createElement("span");
        xIcon.innerHTML = "<i class='fa-solid fa-xmark'></i>";

        tagDiv.appendChild(xIcon);
        container.appendChild(tagDiv);

        // 입력란 초기화
        input.value = "";

        // 숨겨진 input 태그에 값을 추가
        addedHashtags.add(hashtag);
        hashtagsHidden.value = Array.from(addedHashtags).join(",");

        // i태그 클릭 이벤트 추가
        xIcon.addEventListener("click", function() {
          tagDiv.remove(); // 클릭한 해시태그 삭제
          addedHashtags.delete(hashtag); // Set에서도 삭제
          hashtagsHidden.value = Array.from(addedHashtags).join(","); // 숨겨진 input 업데이트
        });
      }

      event.preventDefault(); // 엔터나 스페이스바를 입력해도 폼이 전송되는 것을 방지
    }
  }

  function togglePriceInput(isPaid) {
      const priceInput = document.getElementById('price');
      priceInput.disabled = !isPaid;
    }
