package org.odesamama.mcd.services;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by starnakin on 07.10.2015.
 */

@Service
public class FileServiceImpl implements FileService{

    @Value("${hdfs.namenode.url}")
    private String nameNodeUrl;

    private String fileLocation = "/testfileslocation/";

    @Override
    public void uploadFileToHDFSServer(byte[] bytes, String fileName)  throws URISyntaxException, IOException {
        System.setProperty("HADOOP_USER_NAME", "hadoopuser");

        Configuration conf = new Configuration();
        // save file
        Path filePath = new Path(nameNodeUrl + fileLocation + fileName);

        try (FileSystem fileSystem = FileSystem.get(new URI(nameNodeUrl), conf)) {

            OutputStream os = fileSystem.create(filePath, () -> System.out.println("File written"));
            try (BufferedOutputStream bw = new BufferedOutputStream(os)) {
                bw.write(bytes);
            }
        }
    }
}