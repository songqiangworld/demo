package com.example.demo.server;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

public class ChildThread extends Thread{
    private static Logger logger = LoggerFactory.getLogger(ChildThread.class);
    public int id;
    private long startPosition;
    public CountDownLatch latch;

    public String remoteFileName;  //要下载的文件在软件中心的文件
    public String localTempFileName;   //用于存放每个线程下载的临时文件的绝对路径  （带上临时文件的名字和后缀）
    //public String path;//临时文件夹的目录

    public FTPClient ftpClient = new FTPClient();

    public ChildThread(FTPClient ftpClient,String downloadPath,String fileName,String storePath,long startPos,int id,CountDownLatch latch) {
        this.ftpClient = ftpClient;
        remoteFileName =downloadPath+fileName; // 服务器上的文件，前面是文件夹的名字，后面的是文件的名字
        startPosition=startPos;
        this.latch=latch;
        this.id=id;
        try {
//            ftpClient.connect(server,port);
//            ftpClient.login(userName, password);
  //          ftpClient.changeWorkingDirectory(downloadPath);
   //         ftpClient.setBufferSize(1024);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);    // 设置文件类型（二进制）

            if (storePath.endsWith("\\") || storePath.endsWith("/"))//给出的路径下新建一个临时文件夹，里面存储的是各个线程下载的文件
            {
                localTempFileName=storePath +fileName.substring(0, fileName.indexOf("."))+"Temp/" +id+"_"+fileName;//保证临时文件夹唯一 也应保证临时文件的命名唯一
            } else{
                localTempFileName=storePath + "/" +fileName.substring(0, fileName.indexOf("."))+"Temp/" + id+"_"+fileName;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
        }
    }


    public void run() {

        FileOutputStream outputStream = null;
        try {
            ftpClient.enterLocalPassiveMode(); // 这一句话一定要记得加上
            File threadTempFile=new File(localTempFileName);
            outputStream = new FileOutputStream(localTempFileName,true);
            ftpClient.setRestartOffset(startPosition+threadTempFile.length());  //设置每个线程开始的下载位置  如果之前threadTempFile.length()不等于0，则从上次那个地方继续下载  断点下载

            InputStream in= ftpClient.retrieveFileStream(remoteFileName);
            int len = 0;
            byte[] b = new byte[1024];
            long count=threadTempFile.length();
            while((len = in.read(b)) != -1) {
                count +=len;//记录文件中的长度加上这次准备写的长度
                if (count > FtpServer.threadBlock) { //加上最后一次读到的已经比规定的线程块大，则只取前面一部分即可
                    int lastLen= (int) (FtpServer.threadBlock-threadTempFile.length());
                    outputStream.write(b, 0,lastLen);//方法write(b, off, len)，b[off]是写入的第一个字节和b[off+len-1]是写的这个操作的最后一个字节。
                    outputStream.flush();
                    break;
                }
                outputStream.write(b, 0, len);
                outputStream.flush();
            }
            in.close();//关闭流
            File file=new File(localTempFileName);
            System.out.println("Thread file "+id+" "+file.length());
            outputStream.close();
            ftpClient.disconnect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        latch.countDown();//每个线程结束的时候，则总的线程数减1
    }
}
