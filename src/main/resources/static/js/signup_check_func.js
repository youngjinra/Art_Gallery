// 이메일 인증번호

var idCheck = false;
var nickCheck = false;
var pwdCheck = false;
var pwdCheckConfirm = false;
var emailCheck = false;
var emailCodeCheck = false;

$(document).ready(function () {

    // ID부분
	$("#userIdChk").on("keyup", function () {  // id입력칸에 입력할 순간순간 이벤트
		var regExp = /^[a-z]+[a-z0-9]{5,15}$/g;
		var userId = $("#userIdChk").val();

		if (!regExp.test($("#userIdChk").val())) {  // id 공백인 경우 체크
			idCheck = false;
			checkActivation();
			$("#userIdChkTxt").html("<span id='checkid'>사용할 수 없는 아이디입니다.</span>")
			$("#checkid").css({
				"color": "#FA3E3E",
				"font-weight": "bold",
				"font-size": "10px"
			})
		} else if (userId.length > 15) {    // id 가 5 ~ 15 자 만족이 안될 경우
			idCheck = false;
			checkActivation();
			$("#userIdChkTxt").html("<span id='checkid'>아이디는 5~15자 사이로 입력해주세요.</span>");
			$("#checkid").css({
				"color": "#FA3E3E",
				"font-weight": "bold",
				"font-size": "10px"
			});
		} else {
			$.ajax({
				type: "POST",
				url: "/user/signup/checkid",
				data: {
					"type": "user",
					"id": $("#userIdChk").val()
				},
				success: function (data) {      // 컨트롤러와 서비스를 통해 반환된 데이터를 갖고 수행
					if (data === "exist") {
						idCheck = false;
						checkActivation();
						$("#userIdChkTxt").html("<span id='checkid'>이미 존재하는 아이디입니다.</span>")
						$("#checkid").css({
							"color": "#FA3E3E",
							"font-weight": "bold",
							"font-size": "10px"
						})

					} else {
						idCheck = true;
						checkActivation();  // id 체크함수
						$("#userIdChkTxt").html("<span id='checkid'>사용 가능한 아이디입니다.</span>")
						$("#checkid").css({
							"color": "#0D6EFD",
							"font-weight": "bold",
							"font-size": "10px"
						})
					}
				}
			})
		}
	})

	// 닉네임 부분
	$("#userNickChk").on("keyup", function () {  // 닉네임 입력칸에 입력할 순간순간 이벤트
            var regExp = /^[a-zA-Z가-힣][a-zA-Z0-9가-힣]{1,19}$/;
    		var userId = $("#userNickChk").val();

    		if (!regExp.test($("#userNickChk").val())) {  // 닉네임 공백인 경우 체크
    			nickCheck = false;
    			checkActivation();
    			$("#userNickChkTxt").html("<span id='checknick'>사용할 수 없는 닉네임입니다.</span>")
    			$("#checknick").css({
    				"color": "#FA3E3E",
    				"font-weight": "bold",
    				"font-size": "10px"
    			})
    		} else {
    			$.ajax({
    				type: "POST",
    				url: "/user/signup/checknickname",
    				data: {
    					"type": "user",
    					"id": $("#userNickChk").val()
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
    						})

    					} else {
    						nickCheck = true;
    						$("#userNickChkTxt").html("<span id='checknick'>사용 가능한 닉네임입니다.</span>")
    						$("#checknick").css({
    							"color": "#0D6EFD",
    							"font-weight": "bold",
    							"font-size": "10px"
    						})
    						checkActivation();  // 닉네임 체크함수
    					}
    				}
    			})
    		}
    	})

    // 비밀번호 부분
	$("#mainPwd").on("keyup", function () {
        var regExp = /^(?=.*[A-Za-z])(?=.*\d|.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{10,}$/;

		if (!regExp.test($("#mainPwd").val())) {  // 패스워드 형식 확인
			pwdCheck = false;
			checkActivation();
			$("#mainPwdTxt").html("<span id='chkpwd'>패스워드 형식이 맞지 않습니다</span>")
			$("#chkpwd").css({
				"color": "#FA3E3E",
				"font-weight": "bold",
				"font-size": "10px"
			})

		} else {
			pwdCheck = true;
			$("#mainPwdTxt").html("<span id='chkpwd'>사용 가능한 패스워드 입니다.</span>")
			$("#chkpwd").css({
				"color": "#0D6EFD",
				"font-weight": "bold",
				"font-size": "10px"
			})
			checkActivation(); // pwd 체크함수
		}
		// 미리 2개 맞춰놓고 처음꺼 변경시 패스워드 확인이 true로 유지되는거 방지
		if ($("#pwdChk").val() != $("#mainPwd").val()) {
        			pwdCheckConfirm = false;
        			checkActivation();
        			$("#pwdChkTxt").html("<span id='checkpwd'>비밀번호가 일치하지 않습니다</span>")
        			$("#checkpwd").css({
        				"color": "#FA3E3E",
        				"font-weight": "bold",
        				"font-size": "10px"
        			})

        		} else {
        			pwdCheckConfirm = true;
        			$("#pwdChkTxt").html("<span id='checkpwd'>비밀번호 일치 확인</span>")
        			$("#checkpwd").css({
        				"color": "#0D6EFD",
        				"font-weight": "bold",
        				"font-size": "10px"
        			})
        			checkActivation(); //
        		}
	})

    // 비밀번호 확인 부분
	$("#pwdChk").on("keyup", function () {  // 패스워드 일치 확인
		if ($("#pwdChk").val() != $("#mainPwd").val()) {
			pwdCheckConfirm = false;
			checkActivation();
			$("#pwdChkTxt").html("<span id='checkpwd'>비밀번호가 일치하지 않습니다</span>")
			$("#checkpwd").css({
				"color": "#FA3E3E",
				"font-weight": "bold",
				"font-size": "10px"
			})

		} else {
			pwdCheckConfirm = true;
			$("#pwdChkTxt").html("<span id='checkpwd'>비밀번호 일치 확인</span>")
			$("#checkpwd").css({
				"color": "#0D6EFD",
				"font-weight": "bold",
				"font-size": "10px"
			})
			checkActivation(); //
		}
	})

    // 이메일 중복만 체크 구현필요
	$("#memail").on("keyup", function () {  // 이메일 입력칸에 입력할 순간순간 이벤트

    		var userId = $("#memail").val();

    		 if (!validateEmail(userId)) {
                 emailCheck = false;
                 checkActivation();
                 $("#mailTxt").html("<span id='checkemail'>올바른 이메일 형식이 아닙니다.</span>")
                 $("#checkemail").css({
                 	"color": "#FA3E3E",
                    "font-weight": "bold",
                    "font-size": "10px"
                 })
             }else {
    			$.ajax({
    				type: "POST",
    				url: "/user/signup/checkemail",
    				data: {
    					"type": "user",
    					"id": $("#memail").val()
    				},
    				success: function (data) {      // 컨트롤러와 서비스를 통해 반환된 데이터를 갖고 수행
    					if (data === "exist") {
    						emailCheck = false;
    						checkActivation();
    						$("#mailTxt").html("<span id='checkemail'>이미 존재하는 이메일입니다.</span>")
    						$("#checkemail").css({
    							"color": "#FA3E3E",
    							"font-weight": "bold",
    							"font-size": "10px"
    						})

    					} else {
    						emailCheck = true;
    						$("#mailTxt").html("<span id='checkemail'>사용 가능한 이메일입니다.</span>")
    						$("#checkemail").css({
    							"color": "#0D6EFD",
    							"font-weight": "bold",
    							"font-size": "10px"
    						})
    						checkActivation();  // 닉네임 체크함수
    					}
    				}
    			})
    		}
    	})
    //

	$("#checkEmail").click(function () {   // 인증번호 버튼 클릭시 이벤트
        if(emailCheck){
            $.ajax({
                type: "POST",
                url: "/user/signup/mailConfirm",
                async: false,
                data: {
                    "email": $("#memail").val()
                },
                success: function (data) {
                    alert("해당 이메일로 인증번호 발송이 완료되었습니다. \n 확인부탁드립니다.")
                    console.log("code : " + data);  // 콘솔 창에서 생성된 코드 미리보기
                    chkEmailConfirm(data, $("#memailconfirm"), $("#memailconfirmTxt"), $("#memailcodeChk"));
                    $("#memailconfirm").attr("disabled", false);  // 인증번호 발송 성공을 해야 인증번호 텍스트칸 활성화
                }
            });
        }
	});

	// 이메일 인증번호 체크 함수
	function chkEmailConfirm(data, memailconfirm, memailconfirmTxt, memailcodeChk) {
		memailcodeChk.on("click", function () {
			if (data != memailconfirm.val()) { //
				emailCodeCheck = false;
				checkActivation();
				memailconfirmTxt.html("<span id='emconfirmchk'>인증번호가 잘못되었습니다</span>")
				$("#emconfirmchk").css({
				    "display": "block",
					"color": "#FA3E3E",
					"font-weight": "bold",
					"font-size": "10px"
				});

			} else {
				emailCodeCheck = true;
				memailconfirmTxt.html("<span id='emconfirmchk'>인증번호 확인 완료</span>")
				$("#emconfirmchk").css({
				    "display": "block",
					"color": "#0D6EFD",
					"font-weight": "bold",
					"font-size": "10px"
				});
                $("#memailconfirm").attr("disabled", true); // 맞는 코드로 입력하면 다시 입력칸 disabled
				checkActivation();
			}
		});
	}

	function checkActivation() {    // 회원가입 버튼 활성화 조건함수
        if (idCheck && nickCheck && pwdCheck && pwdCheckConfirm && emailCheck && emailCodeCheck) {
          // 모든 조건이 충족되면 버튼 활성화
          $("#signupFin").attr("disabled", false);
        } else {
          // 조건이 하나라도 충족되지 않으면 버튼 비활성화
          $("#signupFin").attr("disabled", true);
        }
    }

    function validateEmail(email) {
        // 이메일 유효성 검사 로직 작성
        // 정규식을 사용하여 이메일 형식을 검사
        var emailRegex = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/i;
        return emailRegex.test(email);
    }

    checkActivation();
});