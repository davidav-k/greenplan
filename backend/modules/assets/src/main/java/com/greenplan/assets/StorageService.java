package com.greenplan.assets;

import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.InitializingBean;

public class StorageService implements InitializingBean {
    private final String endpoint, access, secret, bucket;
    private MinioClient client;

    public StorageService(String endpoint, String access, String secret, String bucket) {
        this.endpoint = endpoint;
        this.access = access;
        this.secret = secret;
        this.bucket = bucket;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        client = MinioClient.builder().endpoint(endpoint).credentials(access, secret).build();
        boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!exists) client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
    }

    public void put(String key, byte[] bytes, String contentType) throws Exception {
        client.putObject(PutObjectArgs.builder().bucket(bucket).object(key)
                .stream(new java.io.ByteArrayInputStream(bytes), bytes.length, -1)
                .contentType(contentType).build());
    }

    public String presignedGet(String key, int seconds) throws Exception {
        return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET).bucket(bucket).object(key).expiry(seconds).build());
    }
}