package com.codeit.springwebbasic.member.service;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3FileService {

    //s3 버킷을 제어하는 객체
    private S3Client s3Client;

    @Value("${spring.cloud.aws.credentials.accessKey}")
    private  String accessKey;
    @Value("${spring.cloud.aws.credentials.secretKey}")
    private  String secretKey;
    @Value("${spring.cloud.aws.s3.bucket}")
    private  String bucketName;
    @Value("${spring.cloud.aws.region.static}")
    private  String region;
    //s3에 연결해서 인증 처리하는 로직
    @PostConstruct //클래스를 기반으로 객체가 생성될때1번반 자동실행되는 어노테이션
    private void initializeAmazonS3Client(){

        AwsBasicCredentials credentials
                = AwsBasicCredentials.create(accessKey, secretKey);


        //지역설정 및인증 정보를 담은 s3l
        this.s3Client  = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    public String uploadTos3Bucket(MultipartFile file) throws IOException {
        //1. 고유한 파일명 생성한다 (uuid+ 원본파일명)
        String originalFilename = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID() + "_" + originalFilename;
        //2.s3 업로드 할 요청 객체 생성
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)//버킷이름
                .key(uniqueFileName)//저장될 파일명
                .contentType(file.getContentType())
                .build();

        //3.실제 x3 에 파일 업로드
        s3Client.putObject(
                request,
                RequestBody.fromBytes(file.getBytes())
        );
        //4. 업로드된 파일의 URL 반환
        //db에저장할 스트링으로 받아준다
        //utiltties 통해 버킷고 ㅏ이름명이 결합된 url 쉽게 얻어낼수가 있다
        return s3Client.utilities()
                .getUrl( b->b.bucket(bucketName).key(uniqueFileName))
                .toString();
    }

 //특정 폴더에 파일을업로드 (실제로는 폴더가 아니고 ,prefix로 파일을 구분 )
    //ex: users/profile users profile uuid filename.jpg
    public String uploadToFolder(MultipartFile file,String forder) throws IOException {
        //1. 고유한 파일명 생성한다 (uuid+ 원본파일명)
        String originalFilename = file.getOriginalFilename();
        String uniqueFileName = forder + UUID.randomUUID() + "_" + originalFilename;
        //2.s3 업로드 할 요청 객체 생성
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)//버킷이름
                .key(uniqueFileName)//저장될 파일명
                .contentType(file.getContentType())
                .build();

        //3.실제 x3 에 파일 업로드
        s3Client.putObject(
                request,
                RequestBody.fromBytes(file.getBytes())
        );
        //4. 업로드된 파일의 URL 반환
        //db에저장할 스트링으로 받아준다
        //utiltties 통해 버킷고 ㅏ이름명이 결합된 url 쉽게 얻어낼수가 있다
        return s3Client.utilities()
                .getUrl( b->b.bucket(bucketName).key(uniqueFileName))
                .toString();
    }

    //파일 객체 삭제
    // 우리가 가진 데이터: https://s3-bucket-practice8917.s3.ap-northeast-2.amazonaws.com/74b59c79-d5da-4d05-b99a-557f00b4da07_fileName.gif
    // 가공 결과: 74b59c79-d5da-4d05-b99a-557f00b4da07_fileName.gif

    //이미지의 url 이전달이될껑
    //그치만 깔끔하게 객체이름만 부내줘야해
    //그러니 가공을해줘야해
    //객체를 지우기위해는 키값 (파일명을 )줘야하는데
    //우리가 db에 저장해서 가지고있는건 url 그럼 정제해서 ->s3에 전달해야한다
   public void deleteFile(String imageUrl) throws Exception {
       String key = extracFileName(imageUrl);

       //이것도 빌드패턴
       //지우고자하는객체의 키값
       DeleteObjectRequest request =
               DeleteObjectRequest.builder()
                       .bucket(bucketName)
                       .key(key)
                       .build();

       s3Client.deleteObject(request);
   }

    //여러파일 일괄삭제
    public void deleteFiles(List<String> imageUrls) throws Exception {

        List<String> fileNames = new ArrayList<>();
         for(String imageUrl : imageUrls){
             String fileName = extracFileName(imageUrl);
             fileNames.add(fileName);
         }

         //삭제할 객체 목록 생성
        List<ObjectIdentifier> objectIdentifiers = fileNames.stream()
                .map(fileName -> ObjectIdentifier.builder()
                        .key(fileName)
                        .build())
                .collect(Collectors.toList());

        Delete delete = Delete.builder()
                .objects(objectIdentifiers)
                .build();

        //이것도 빌드패턴
        //지우고자하는객체의 키값
        //객체 생성 및 메서드 명을 조심
        DeleteObjectsRequest request =
                DeleteObjectsRequest.builder()
                        .bucket(bucketName)
                        .delete(delete)
                        .build();

        s3Client.deleteObjects(request);
    }

   //파일다운로드 요청
   public byte[] downloadFile(String imageUrl) throws Exception{
       String fileName = extracFileName(imageUrl);
       GetObjectRequest getObjectRequest = GetObjectRequest.builder()
               .bucket(bucketName)
               .key(fileName)
               .build();

       ResponseBytes<GetObjectResponse> objectAsBytes
               = s3Client.getObjectAsBytes(getObjectRequest);

       return objectAsBytes.asByteArray();
   }
 //파일 존재 여부 확인
    public  boolean isFileExist(String imageurl) {
        try {
            String fileName = extracFileName(imageurl);

            HeadObjectRequest request = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.headObject(request);
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }catch (S3Exception e){
            //요청 보낸 객 체 (데이터가 )조회 되지 않으면 s3Eception 이 발생한다
            //그냥 요청자체가 망가져도 발생할수가 있다
            //그래서 예외에서
            if(e.statusCode() == 404){
                //이건 조회해보니없네
                return false;
            }
            throw new RuntimeException("파일 존재 요청 실패 ! : " + e.getMessage(),e);
        }
    }

// url에서 파일명만 추출
    private static String extracFileName(String imageurl) throws MalformedURLException, UnsupportedEncodingException {
        URL url = new URL(imageurl); //여기서 이따구로받아

        //getpath()-> 프로토콜,ip 도메인 ,포트번호를 제외한 리소스 내부 경로만 받을수가 있다
        //74b59c79-d5da-4d05-b99a-557f00b4da07_fileName.gif
        //서브스트링은 맨앞에 / 이걸 빼기위해서
        //특정 자원의 경로만 완성된다
        String decodeUrl = URLDecoder.decode(url.getPath(), "UTF-8");
        String key = decodeUrl.substring(1);
        return key;
    }




}
