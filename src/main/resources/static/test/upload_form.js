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


<!-- 이미지 미리보기 -->

  function setThumbnail(event){
  		var reader = new FileReader();

  		reader.onload = function(event){
  			var img = document.createElement("img");
  			img.setAttribute("src", event.target.result);
  			img.setAttribute("class", "col-lg-6");
  			document.querySelector("div#image_container").appendChild(img);
  		};

  		reader.readAsDataURL(event.target.files[0]);

  }

/*


// JavaScript 코드 (upload_form.js 파일 등)
document.getElementById('submitButton').addEventListener('click', () => {
  // 콘텐츠 데이터를 수집하고 객체로 만듭니다.
  const contentData = {
    subject: document.querySelector('input[name="subject"]').value,
    content: document.querySelector('textarea[name="content"]').value,
    category: document.querySelector('select[name="category"]').value,
    hashtags: document.querySelector('input[name="hashtags"]').value,
  };

  // Fetch API를 사용하여 데이터를 서버로 전송합니다.
  fetch('/post/create', {
    method: 'POST', // HTTP POST 메서드로 전송
    headers: {
      'Content-Type': 'application/json', // JSON 형식으로 데이터 전송
    },
    body: JSON.stringify(contentData), // JavaScript 객체를 JSON 문자열로 변환하여 전송
  })
  .then(response => response.json()) // 서버로부터 응답을 JSON 형식으로 파싱
  .then(data => {
    // 서버로부터 받은 응답을 처리하는 로직을 추가할 수 있습니다.
    console.log(data);
  })
  .catch(error => {
    // 에러가 발생한 경우 처리하는 로직을 추가할 수 있습니다.
    console.error('Error:', error);
  });
});*/
