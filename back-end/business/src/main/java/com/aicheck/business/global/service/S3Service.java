package com.aicheck.business.global.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadImageFile(MultipartFile image) throws IOException {
        String fileName = image.getOriginalFilename();
        String extension = image.getOriginalFilename().substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString();

        InputStream inputStream = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(inputStream);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/" + extension);
        metadata.setContentLength(bytes.length);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try {
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, uploadFileName, byteArrayInputStream, metadata);
            // 실제 업로드 동작하는 부분
            amazonS3.putObject(putObjectRequest);
            return amazonS3.getUrl(bucketName, uploadFileName).toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AmazonS3Exception("업로드에 실패했습니다");
        } finally {
            byteArrayInputStream.close();
            inputStream.close();
        }
    }

}
