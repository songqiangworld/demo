package com.example.demo.server;


import com.example.demo.enums.DownloadStatus;
import com.example.demo.enums.UploadStatus;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FtpServer2 {
    public static FTPClient ftpClient = new FTPClient();
    private static Logger logger = LoggerFactory.getLogger(FtpServer2.class);
    // FTP协议里面，规定文件名编码为iso-8859-1
    private static String SERVER_CHARSET = "ISO-8859-1";
    private static String LOCAL_CHARSET="GBK";
    /**
     * 连接到FTP服务器
     * @param hostname 主机名 ftp服务器ip地址 192.168.242.133
     * @param LocalCharset 本地编码集
     * @param port
     *          要上传的ftp的端口号
     * @param userName 用户名
     * @param password 密码
     * @return 是否连接成功
     *
     */
    public  static boolean connect(String hostname,String LocalCharset,int port,String userName,String password){

        try {
            ftpClient.connect(hostname, port);
            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                if (ftpClient.login(userName, password)) {
                    if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
                        // / 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
                        LOCAL_CHARSET = "UTF-8";
                    }else {
                        LOCAL_CHARSET = LocalCharset;
                    }
                    ftpClient.setControlEncoding(LOCAL_CHARSET);
                    return true;
                }
            }

            ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("连接FTP服务器"+hostname+"失败");
        return false;
    }

    /** *//**
     * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报
     * @param remote 远程文件路径
     * @param local 本地文件路径
     * @return 上传的状态
     * @throws IOException
     */
    public static DownloadStatus download(String remote, String local) throws IOException{
        //设置被动模式
        ftpClient.enterLocalPassiveMode();
        //设置以二进制方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        DownloadStatus result;

        //检查远程文件是否存在
        FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes(LOCAL_CHARSET),SERVER_CHARSET));
        if(files.length != 1){
            logger.error("远程文件不存在"+remote);
            return DownloadStatus.Remote_File_Noexist;
        }

        long lRemoteSize = files[0].getSize();
        File f = new File(local);
        //本地存在文件，进行断点下载
        if(f.exists()){
            long localSize = f.length();
            //判断本地文件大小是否大于远程文件大小
            if(localSize >= lRemoteSize){
                logger.error(f.getName()+"本地文件大于远程文件，下载中止");
                return DownloadStatus.Local_Bigger_Remote;
            }

            //进行断点续传，并记录状态
            FileOutputStream out = new FileOutputStream(f,true);
            ftpClient.setRestartOffset(localSize);
            InputStream in = ftpClient.retrieveFileStream(new String(remote.getBytes(LOCAL_CHARSET),SERVER_CHARSET));
            byte[] bytes = new byte[1024];
            long step = lRemoteSize /100;
            long process=localSize /step;
            int c;
            while((c = in.read(bytes))!= -1){
                out.write(bytes,0,c);
                localSize+=c;
                long nowProcess = localSize /step;
                if(nowProcess > process){
                    process = nowProcess;
                    if(process % 10 == 0)
                        logger.info("下载进度："+process);
                    //TODO 更新文件下载进度,值存放在process变量中
                }
            }
            in.close();
            out.close();
            boolean isDo = ftpClient.completePendingCommand();
            if(isDo){
                result = DownloadStatus.Download_From_Break_Success;
            }else {
                result = DownloadStatus.Download_From_Break_Failed;
            }
        }else {

            OutputStream out = new FileOutputStream(f);
            InputStream in= ftpClient.retrieveFileStream(new String(remote.getBytes(LOCAL_CHARSET),SERVER_CHARSET));
            byte[] bytes = new byte[1024];
            long step = lRemoteSize /100;
            long process=0;
            long localSize = 0L;
            int c;
            while((c = in.read(bytes))!= -1){
                out.write(bytes, 0, c);
                localSize+=c;
                long nowProcess = localSize /step;
                if(nowProcess > process){
                    process = nowProcess;
                    if(process % 10 == 0)
                        logger.info("下载进度："+process);
                    //TODO 更新文件下载进度,值存放在process变量中
                }
            }
            in.close();
            out.close();
            boolean upNewStatus = ftpClient.completePendingCommand();
            if(upNewStatus){
                result = DownloadStatus.Download_New_Success;
            }else {
                result = DownloadStatus.Download_New_Failed;
            }
        }
        return result;
    }

    /** *//**
     * 上传文件到FTP服务器，支持断点续传
     * @param local 本地文件名称，绝对路径
     * @param remote 远程文件路径，使用/home/directory1/subdirectory/file.ext或是 http://www.guihua.org /subdirectory/file.ext 按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
     * @return 上传结果
     * @throws IOException
     */
    public  static UploadStatus upload(String local, String remote) throws IOException{
        //设置PassiveMode传输
        ftpClient.enterLocalPassiveMode();
        //设置以二进制流的方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        UploadStatus result;
        //对远程目录的处理
        String remoteFileName = remote+new File(local).getName()+".tmp";


        //检查远程是否存在文件
        FTPFile[] files = ftpClient.listFiles(new String(remoteFileName.getBytes(LOCAL_CHARSET),SERVER_CHARSET));
        if(files.length == 1){
            long remoteSize = files[0].getSize();
            File f = new File(local);
            long localSize = f.length();
            if(remoteSize==localSize){
                return UploadStatus.File_Exits;
            }else if(remoteSize > localSize){
                return UploadStatus.Remote_Bigger_Local;
            }

            //尝试移动文件内读取指针,实现断点续传
            result = uploadFile(remoteFileName, f, ftpClient, remoteSize);

            //如果断点续传没有成功，则删除服务器上文件，重新上传
            if(result == UploadStatus.Upload_From_Break_Failed){
                if(!ftpClient.deleteFile(remoteFileName)){
                    return UploadStatus.Delete_Remote_Faild;
                }
                result = uploadFile(remoteFileName, f, ftpClient, 0);
            }
        }else {
            result = uploadFile(remoteFileName, new File(local), ftpClient, 0);
        }
        return result;
    }
    /** *//**
     * 断开与远程服务器的连接
     * @throws IOException
     */
    public  static void disconnect() throws IOException{
        if(ftpClient.isConnected()){
            ftpClient.disconnect();
        }
    }

    /** *//**
     * 递归创建远程服务器目录
     * @param remote 远程服务器文件绝对路径
     * @param ftpClient FTPClient 对象
     * @return 目录创建是否成功
     * @throws IOException
     */
    public static UploadStatus CreateDirecroty(String remote,FTPClient ftpClient) throws IOException{
        UploadStatus status = UploadStatus.Create_Directory_Success;
        String directory = remote.substring(0,remote.lastIndexOf("/")+1);
        if(!directory.equalsIgnoreCase("/")&&!ftpClient.changeWorkingDirectory(new String(directory.getBytes(LOCAL_CHARSET),SERVER_CHARSET))){
            //如果远程目录不存在，则递归创建远程服务器目录
            int start=0;
            int end = 0;
            if(directory.startsWith("/")){
                start = 1;
            }else{
                start = 0;
            }
            end = directory.indexOf("/",start);
            while(true){
                String subDirectory = new String(remote.substring(start,end).getBytes(LOCAL_CHARSET),SERVER_CHARSET);
                if(!ftpClient.changeWorkingDirectory(subDirectory)){
                    if(ftpClient.makeDirectory(subDirectory)){
                        ftpClient.changeWorkingDirectory(subDirectory);
                    }else {
                        logger.error("创建目录失败");
                        return UploadStatus.Create_Directory_Fail;
                    }
                }

                start = end + 1;
                end = directory.indexOf("/",start);

                //检查所有目录是否创建完毕
                if(end <= start){
                    break;
                }
            }
        }
        return status;
    }

    /** *//**
     * 上传文件到服务器,新上传和断点续传
     * @param remoteFile 远程文件名，在上传之前已经将服务器工作目录做了改变
     * @param localFile 本地文件 File句柄，绝对路径
     * @param remoteSize 需要显示的处理进度步进值
     * @param ftpClient FTPClient 引用
     * @return
     * @throws IOException
     */
    public  static UploadStatus uploadFile(String remoteFile,File localFile,FTPClient ftpClient,long remoteSize) throws IOException{
        UploadStatus status;
        //显示进度的上传
        long step = localFile.length() / 100;
        long process = 0;
        long localreadbytes = 0L;
        RandomAccessFile raf = new RandomAccessFile(localFile,"r");
        OutputStream out = ftpClient.appendFileStream(new String(remoteFile.getBytes(LOCAL_CHARSET),SERVER_CHARSET));
        //断点续传
        if(remoteSize>0){
            ftpClient.setRestartOffset(remoteSize);
            process = remoteSize /step;
            raf.seek(remoteSize);
            localreadbytes = remoteSize;
        }
        byte[] bytes = new byte[1024];
        int c;
        while((c = raf.read(bytes))!= -1){
            out.write(bytes,0,c);
            localreadbytes+=c;
            if(localreadbytes / step != process){
                process = localreadbytes / step;
                logger.info("上传进度:" + process);
                //TODO 汇报上传状态
            }
           /* try {
                Thread.sleep(1000*10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        out.flush();
        raf.close();
        out.close();
        boolean result =ftpClient.completePendingCommand();
        if(remoteSize > 0){
            status = result?UploadStatus.Upload_From_Break_Success:UploadStatus.Upload_From_Break_Failed;
        }else {
            status = result?UploadStatus.Upload_New_File_Success:UploadStatus.Upload_New_File_Failed;
        }
        return status;
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub

        try {
            //connect("192.168.130.47", 10021, "user03", "user");
            //UploadStatus status = upload("D:\\nclgrprgtbpo\\gm-nci-export\\10000000001.xml", "/nclgrprgtbpo/jwgrp/LPReturn/10000000001.xml");

            connect("192.168.130.193","GBK" ,21, "myftp", "123456");
//           UploadStatus status = upload("D:\\nclgrprgtbpo\\gm-nci-export\\test.xml", "/BPO/nclgrprgtbpo/jwgrp/LPReturn/");
            DownloadStatus status = download("/BPO/nclgrprgtbpo/jwgrp/LPReturn/90000614018.xml", "D:\\nclgrprgtbpo\\gm-nci-export-backup\\90000614018.xml");
          System.out.println(status);
            disconnect();
        } catch (IOException e) {
            System.out.println("连接FTP出错："+e.getMessage());
        }
    }
}

