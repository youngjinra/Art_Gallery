package com.example.ArtGallery.article.file;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    @Value("${spring.servlet.multipart.location}")
    private String filePath;

    private List<FileEntity> allFiles;

    public void save(FileEntity fileEntity){
        FileEntity file = new FileEntity();
        file.setUuid(fileEntity.getUuid());
        file.setFileName(fileEntity.getFileName());
        file.setFilePath(fileEntity.getFilePath());

        System.out.println("fileRepository 저장됨");

        fileRepository.save(file);
    }

    public FileEntity uploadFile(MultipartFile uploadFile){
        if(uploadFile != null && !uploadFile.isEmpty()){
            String uuid = UUID.randomUUID().toString();
            String fileName = uploadFile.getOriginalFilename();
            String contentType = uploadFile.getContentType();

            FileEntity fileEntity = new FileEntity(uuid, fileName, contentType, filePath);

            String diFileName = filePath + File.separator + uuid + "_" + fileName;

            try{
                // 실제 파일 저장하는 부분
                uploadFile.transferTo(new File(diFileName));

                this.fileRepository.save(fileEntity);
            } catch (IOException e){
                // 파일 저장 중 오류 발생 시 예외처리
                e.printStackTrace();
                // 예외 처리 방식에 따라 적절히 수정
            }
            return fileEntity;
        }
        return null;
    }

    public List<FileEntity> getAllFiles(){
        return  allFiles;
    }
    public List<FileEntity> getList(){
        return this.fileRepository.findAll();
    }

    public FileEntity getFileByUuid(String uuid){
        // 파일을 가져오는 로직 구현
        // uuid를 기반으로 파일을 찾아온 후 FileEntity 형태로 반환
        FileEntity fileEntity = new FileEntity();
        fileEntity.setUuid(uuid);
        fileEntity.setFileName("fileName.jpg");
        fileEntity.setFilePath("C:/Users/Administrator/uploadFileFolder/" + uuid + "_fileName.jpg");
        return fileEntity;
    }
}
