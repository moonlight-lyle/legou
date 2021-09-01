package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class FastDFSClient {

    /**
     * 静态代码块，程序启动的时候加载配置文件
     */
    static {
        try {
            //获取tracker的配置文件fdfs_client.conf的位置
            String filePath = new ClassPathResource("fdfs_client.conf").getPath();
            // 加载配置文件
            ClientGlobal.init(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    //上传图片
    public static String[] upload(FastDFSFile file)throws Exception{
        //3.创建一个trackerclient对象
        TrackerClient trackerClient = new TrackerClient();
        //4.创建一个trackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //5.创建storageserver的对象
        StorageServer storageServer = null;
        //6.创建storageclient对象  有相关的API :CURD
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        //7.上传图片即可
        //参数1 指定的就是要上传的图片的的本身的字节数组
        //参数2 指定的是该文件的后缀扩展名 不要带点
        //参数3 指定的是该图片的元数据：图片：图片名，拍摄地 作者，像素，拍摄日期.......
        NameValuePair[] nameValuePairs = new NameValuePair[]{
                new NameValuePair(file.getAuthor()),
                new NameValuePair(file.getName())
        };
        String[] jpgs = storageClient.upload_file(file.getContent(), file.getExt(), nameValuePairs);

        return jpgs;

    }

    //下载图片
    public static byte[] downFile(String groupName,String remoteFileName)throws  Exception{
        //1.创建配置文件 用于链接tracker服务端的ip 和端口
        //2.加载配置文件
        //3.创建一个trackerclient对象
        TrackerClient trackerClient = new TrackerClient();
        //4.创建一个trackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //5.创建storageserver的对象
        StorageServer storageServer = null;
        //6.创建storageclient对象  有相关的API :CURD
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        //7.上传图片即可
        //参数1 指定的就是要上传的图片的本地路径
        //参数2 指定的是该文件的后缀扩展名 不要带点
        //参数3 指定的是该图片的元数据：图片：拍摄地 作者，像素，拍摄日期.......
        byte[] bytes = storageClient.download_file(groupName, remoteFileName);
        return bytes;
    }

    //删除图片
    public static int deleteFile(String groupName,String remoteFileName) throws Exception{
        //1.创建配置文件 用于链接tracker服务端的ip 和端口
        //2.加载配置文件
        //3.创建一个trackerclient对象
        TrackerClient trackerClient = new TrackerClient();
        //4.创建一个trackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //5.创建storageserver的对象
        StorageServer storageServer = null;
        //6.创建storageclient对象  有相关的API :CURD
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        //7.上传图片即可
        int group1 = storageClient.delete_file(groupName, remoteFileName);
        if (group1 == 0) {
            System.out.println("删除成功");
        }else{
            System.out.println("失败");
        }
        return group1;
    }

    //获取信息
    public static FileInfo getFile(String groupName,String remoteFileName){
        try {
            //创建TrackerClient对象
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient获得TrackerServer信息
            TrackerServer trackerServer =trackerClient.getConnection();
            //通过TrackerServer获取StorageClient对象
            StorageClient storageClient = new StorageClient(trackerServer,null);
            //获取文件信息
            return storageClient.get_file_info(groupName,remoteFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //针对
    public static String getTrackerUrl(){
        try {
            //创建TrackerClient对象
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient获取TrackerServer对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取Tracker地址
            return "http://"+trackerServer.getInetSocketAddress().getHostString()+":"+ClientGlobal.getG_tracker_http_port();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
