package YOUmI.domain.post.service.Impl;

import YOUmI.domain.post.model.entity.PostFile;
import YOUmI.domain.post.respository.PostFileRepository;
import YOUmI.domain.post.service.PostFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 현재는 Local에 저장하기 위해서 이와 같은 방법 사용
 * 나중에 S3로 변경하게 되면 Presigned URL 방식을 이용해서 구현하여 이미지 업로드의 로직을 클라이언트에게 부담시킬 예정
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class PostFileServiceLocal implements PostFileService {

    private final PostFileRepository postFileRepository;

    @Value("${file.path}")
    private String basePath;

    @Override
    @Transactional
    public PostFile saveFile(MultipartFile multipartFile) {

        String originalName = multipartFile.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("//") + 1);

        // 날짜별 분류
        String directoryPath = makeDir();

        String uuid = UUID.randomUUID().toString();
        String saveName = uuid + "_" + fileName;

        String savePathWithName = basePath
                + File.separator + directoryPath
                + File.separator + saveName;
        File file = new File(savePathWithName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 파일 전송에 성공한다면 postFile 생성
        PostFile newFile = PostFile.builder()
                .saveName(saveName)
                .filePath(savePathWithName)
                .fileSize(multipartFile.getSize())
                .type(multipartFile.getContentType())
                .deleteYn(false)
                .build();

        return postFileRepository.save(newFile);

    }

    private String makeDir() {
        String newDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")).replace("/", File.separator);
        File file = new File(basePath, newDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return newDir;

    }
}
