package com.changgou;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FastdfsTest {

    /**
     * 上传文件
     */
    @Test
    public void upload() throws IOException, MyException {
        // 1.创建配置文件fdfs_client.conf，用于连接tracker服务端的ip和端口
        // 2.加载配置文件
        ClientGlobal.init("D:\\workspace\\legou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");
        // 3.创建trackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        // 4.创建trackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        // 5.创建storageServer对象
        StorageServer storageServer = null;
        // 6.创建storageClient对象，该对象拥有相关的API，可以操作文件
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        // 7.上传操作
        // 参数1：文件路径
        // 参数2：图片后缀，不要带点
        // 参数3：图片的元数据信息：作者，时间，等等
        String[] files = storageClient.upload_file("D:\\workspace\\畅购资料\\changgou-day02\\上课资料\\课堂资料\\tracker的安装.png", "png", null);
        for (String file : files) {
            System.out.println(file);
        }
    }

    /**
     * 下载图片
     */
    @Test
    public void download() throws IOException, MyException {
        // 1.创建配置文件fdfs_client.conf，用于连接tracker服务端的ip和端口
        // 2.加载配置文件
        ClientGlobal.init("D:\\workspace\\legou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");
        // 3.创建trackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        // 4.创建trackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        // 5.创建storageServer对象
        StorageServer storageServer = null;
        // 6.创建storageClient对象，该对象拥有相关的API，可以操作文件
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        // 7.下载文件
        byte[] bytes = storageClient.download_file("group1", "M00/00/00/wKjThGEvOraAFi-mAADCcwzlHvM697.png");
        // 通过流写入磁盘
        FileOutputStream outputStream = new FileOutputStream(new File("D:\\workspace\\fileTest\\changgou\\123456.png"));
        outputStream.write(bytes);
        if (outputStream != null) {
            outputStream.close();
        }
    }

    /**
     * 下载图片
     */
    @Test
    public void delete() throws IOException, MyException {
        // 1.创建配置文件fdfs_client.conf，用于连接tracker服务端的ip和端口
        // 2.加载配置文件
        ClientGlobal.init("D:\\workspace\\legou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");
        // 3.创建trackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        // 4.创建trackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        // 5.创建storageServer对象
        StorageServer storageServer = null;
        // 6.创建storageClient对象，该对象拥有相关的API，可以操作文件
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        // 7. 删除文件
        int status = storageClient.delete_file("group1", "M00/00/00/wKjThGEvOraAFi-mAADCcwzlHvM697.png");
        if (status == 0) {
            System.out.println("删除文件成功！");
        } else {
            System.out.println("删除文件失败！");
        }

    }

    /**
     * 获取文件信息
     *
     * @throws Exception
     */
    @Test
    public void getfileInfo() throws Exception {
        // 1.创建配置文件fdfs_client.conf，用于连接tracker服务端的ip和端口
        // 2.加载配置文件
        ClientGlobal.init("D:\\workspace\\legou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");
        // 3.创建trackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        // 4.创建trackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        // 5.创建storageServer对象
        StorageServer storageServer = null;
        // 6.创建storageClient对象，该对象拥有相关的API，可以操作文件
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        FileInfo fileInfo = storageClient.get_file_info("group1", "M00/00/00/wKjThGEvPyWAXAXbAADCcwzlHvM596.png");
        System.out.println(fileInfo.getSourceIpAddr() + ":" + fileInfo.getFileSize());

        // 获取文件组信息
        ServerInfo[] group1s = trackerClient.getFetchStorages(trackerServer, "group1", "M00/00/00/wKjThGEvPyWAXAXbAADCcwzlHvM596.png");
        for (ServerInfo group1 : group1s) {
            System.out.println(group1.getIpAddr() + ":" + group1.getPort());
        }
    }
}
