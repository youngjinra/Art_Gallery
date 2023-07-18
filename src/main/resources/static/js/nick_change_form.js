
var nickCheck = false;

$(document).ready(function (){
    	$("#nickname").on("keyup", function () {  // 닉네임 입력칸에 입력할 순간순간 이벤트
                var regExp = /^[a-zA-Z가-힣][a-zA-Z0-9가-힣]{1,19}$/;
        		var userId = $("#nickname").val();

        		if (!regExp.test($("#nickname").val())) {  // 닉네임 공백인 경우 체크
        			nickCheck = false;
        			checkActivation();
        			$("#userNickChkTxt").html("<span id='checknick'>사용할 수 없는 닉네임입니다.</span>")
        			$("#checknick").css({
        				"color": "#FA3E3E",
        				"font-weight": "bold",
        				"font-size": "10px"
        			});
        		} else {
        			$.ajax({
        				type: "POST",
        				url: "/user/signup/checknickname",
        				data: {
        					"type": "user",
        					"id": $("#nickname").val()
        				},
        				success: function (data) {      // 컨트롤러와 서비스를 통해 반환된 데이터를 갖고 수행
        					if (data === "exist") {
        						nickCheck = false;
        						checkActivation();
        						$("#userNickChkTxt").html("<span id='checknick'>이미 존재하는 닉네임입니다.</span>")
        						$("#checknick").css({
        							"color": "#FA3E3E",
        							"font-weight": "bold",
        							"font-size": "10px"
        						});

        					} else {
        						nickCheck = true;
        						$("#userNickChkTxt").html("<span id='checknick'>사용 가능한 닉네임입니다.</span>")
        						$("#checknick").css({
        							"color": "#0D6EFD",
        							"font-weight": "bold",
        							"font-size": "10px"
        						});
        						checkActivation();  // 닉네임 체크함수
        					}
        				}
        			})
        		}
        	});

        		function checkActivation() {    // 회원가입 버튼 활성화 조건함수
                    if (nickCheck) {
                      // 조건이 충족되면 버튼 활성화
                      $("#checkNickFin").attr("disabled", false);
                    } else {
                      // 조건이 충족되지 않으면 버튼 비활성화
                      $("#checkNickFin").attr("disabled", true);
                    }
                }

                checkActivation()
});