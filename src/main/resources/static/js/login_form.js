function showLoginErrorMessage() {
    const errorMessage = 'ID 또는 비밀번호를 다시 확인해주세요.';
    const loginErrorMessage = document.getElementById('loginErrorMessage');
    loginErrorMessage.innerHTML = errorMessage;
    loginErrorMessage.style.display = 'block';
}

// 로그인 실패 시 호출할 함수
showLoginErrorMessage();