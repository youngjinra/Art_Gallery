// 해시태그 기능 스크립트
const hashtagsInput = document.getElementById("hashtags-input");
const hashtagsHidden = document.getElementById("hashtags-hidden");
const addedHashtags = new Set();

// 기존의 해시태그들을 추가 (페이지가 로드될 때)
const existingHashtags = document.querySelectorAll("#hashtags-display .tag span");
existingHashtags.forEach((span) => {
    const hashtag = span.textContent.replace("#", "").trim();
    if (hashtag.length > 0) {
        addedHashtags.add(hashtag);
    }
});

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
            xIcon.addEventListener("click", function () {
                tagDiv.remove(); // 클릭한 해시태그 삭제
                addedHashtags.delete(hashtag); // Set에서도 삭제
                hashtagsHidden.value = Array.from(addedHashtags).join(","); // 숨겨진 input 업데이트
            });
        }

        event.preventDefault(); // 엔터나 스페이스바를 입력해도 폼이 전송되는 것을 방지
    }
}

// 기존의 해시태그들을 삭제하는 함수
function removeHashtag(hashtag) {
    addedHashtags.delete(hashtag); // Set에서 삭제
    hashtagsHidden.value = Array.from(addedHashtags).join(","); // 숨겨진 input 업데이트
}

// 기존의 해시태그들에 대한 삭제 이벤트 추가
const existingTags = document.querySelectorAll("#hashtags-display .tag");
existingTags.forEach((tag) => {
    tag.querySelector("i.fa-solid").addEventListener("click", function () {
        const existingHashtag = tag.querySelector("span").textContent.replace("#", "").trim();
        if (existingHashtag.length > 0) {
            removeHashtag(existingHashtag);
        }
        tag.remove(); // 클릭한 해시태그 삭제
    });
});

  // 콘텐츠 제출하기 버튼 클릭 이벤트 처리
  const submitButton = document.getElementById("submit-button");

  submitButton.addEventListener("click", function (event) {
    // 이벤트 기본 동작(폼 제출)을 막기 위해 preventDefault() 호출
    event.preventDefault();

    // 확인창을 띄우고 사용자가 확인 버튼을 눌렀을 때만 폼 제출
    const confirmMessage = "정말 수정하시겠습니까?";
    if (confirm(confirmMessage)) {
      // 폼 제출
      document.getElementById("modify-form").submit();
    }
  });

  // 폼 제출 성공 시 메시지 표시
  function showSuccessMessage() {
    alert("수정이 완료되었습니다.");
  }