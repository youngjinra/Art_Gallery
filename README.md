# gallary_sample

07_17 : 완성한 템플릿에 로그인 회원가입 > DB에 유저들 데이터 추가 완료
application-secure 에 API 주요 보안부분 따로 분리해둠
회원가입시 비밀번호 적혀있는 조건에 맞게 입력해야 confirm되도록 수정완료
닉네임 입력 부분 한글도 입력될 수 있게 수정완료

07_24 : 게시물 이미지 업로드, 다운로드, 조회수, 좋아요
        회원 팔로우기능 구현완료

========================== 각자 수정해야하는 부분 ==========================

spring.servlet.multipart.location = 업로드한 이미지 저장경로 각자 수정

PostController.java
String imagePath = "~~" + uuid + "_" + fileName;
 -> ~~ 부분도 업로드한 이미지 저장경로 각자 수정

FileService.java
fileEntity.setFilePath("~~" + uuid + "_fileName.jpg");
 -> ~~ 부분도 업로드한 이미지 저장경로 각자 수정
