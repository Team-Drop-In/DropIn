package teamdropin.server.aws.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Uploader {
    private final AmazonS3Client amazonS3Client;

    private final List<String> IMAGE_FILE_FORMAT = List.of("jpg",".jpeg",".png","JPG","JPEG","PNG");

    @Value("${cloud.aws.imageS3.bucket}")
    private String bucket;

    @Value("${cloud.aws.imageS3.bucketUrl}")
    private String bucketUrl;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException{

        String newFileName = createFileName(multipartFile.getOriginalFilename());

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

    public List<String> uploadImages(List<MultipartFile> multipartFileList, String dirName) throws IOException {
        List<String> imageUrlList = new ArrayList<>();

        for (MultipartFile file : multipartFileList) {
            String uploadImageUrl = upload(file, dirName);
            imageUrlList.add(uploadImageUrl);
        }
        return imageUrlList;
    }

    private String createFileName(String filename) {
        return UUID.randomUUID().toString().concat(getFileExtension(filename));
    }

    private String getFileExtension(String filename) {
        if(filename.length() == 0){
            throw new BusinessLogicException(ExceptionCode.WRONG_INPUT_IMAGE);
        }
        String fileExtension = filename.substring(filename.lastIndexOf("."));
        if(!IMAGE_FILE_FORMAT.contains(fileExtension)){
            throw new BusinessLogicException(ExceptionCode.WRONG_IMAGE_FORMAT);
        }
        return fileExtension;
    }
}
