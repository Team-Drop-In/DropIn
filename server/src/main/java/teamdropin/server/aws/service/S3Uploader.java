package teamdropin.server.aws.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Uploader {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.imageS3.bucket}")
    private String bucket;

    @Value("${cloud.aws.imageS3.bucketUrl}")
    private String bucketUrl;

    public String upload(MultipartFile multipartFile, String newFileName, String dirName) throws IOException{
        File uploadFile = convert(multipartFile, newFileName)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FILE_CONVERT_FAILED));
        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName){
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeFile(uploadFile);

        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName){
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeFile(File targetFile){
        if(targetFile.delete()){
            log.info("파일이 삭제 되었습니다.");
        }else{
            log.info("파일이 삭제되지 않았습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file, String newFileName) throws IOException{
        File convertFile = new File(newFileName);

        if(convertFile.createNewFile()){
            try(FileOutputStream fos = new FileOutputStream(convertFile)){
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    public void deleteFile(String fileName) throws IOException{
        String fileKey = fileName.replaceAll(bucketUrl,"");
        try {
            log.info("filename={}" , fileKey);
            amazonS3Client.deleteObject(bucket, fileKey);
        } catch (SdkClientException e){
            throw new IOException("Error deleting file from S3");
        }
    }
}
