package com.example.demo.utils;




import org.apache.commons.lang3.StringUtils;


import java.io.*;
import java.net.Socket;

public class SocketFTP {
    private BufferedReader mReader = null;

    private BufferedWriter mWriter = null;
    private Socket mFtpClient;
    private String IP = "192.168.150.193";
    private Integer PORT =21;
    private String USER = "myftp";
    private String PASS = "123456";


    public void connectFtp() {
        try {
            mFtpClient = new Socket(IP, PORT);
            mReader = new BufferedReader(new InputStreamReader(mFtpClient.getInputStream()));
            mWriter = new BufferedWriter(new OutputStreamWriter(mFtpClient.getOutputStream()));

            sendCommand("USER " + USER);
            sendCommand("PASS " + PASS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendCommand(String command) throws IOException {
        if (StringUtils.isEmpty(command)) {
            return;
        }

        if (mFtpClient == null) {
            return;
        }

        mWriter.write(command + "\r\n");
        mWriter.flush();
    }

    public void uploadFile(String localPath, String ftpPath) throws IOException {
        // 进入被动模式
        sendCommand("PASV");

        // 建立数据端口的连接
        Socket dataSocket = new Socket(IP, PORT);
        sendCommand("STOR " + ftpPath);

        // 上传文件前的准备
        File localFile = new File(localPath);
        OutputStream outputStream = dataSocket.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream(localFile);

        // 上传文件
        int offset;
        byte[] bytes = new byte[1024];
        while ((offset = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, offset);
        }
        System.out.println("upload success!!");

        // 上传文件后的善后工作
        outputStream.close();
        fileInputStream.close();
        dataSocket.close();
    }

    public void downloadFile(String localPath, String ftpPath) throws IOException {
        // 进入被动模式
        sendCommand("PASV");

        // 建立数据端口的连接
        Socket dataSocket = new Socket(IP, PORT);
        sendCommand("RETR " + ftpPath);

        // 下载文件前的准备
        File localFile = new File(localPath);
        InputStream inputStream = dataSocket.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(localFile);

        // 下载文件
        int offset;
        byte[] bytes = new byte[1024];
        while ((offset = inputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, offset);
        }
        System.out.println("download success!!");

        // 下载文件后的善后工作
        inputStream.close();
        fileOutputStream.close();
        dataSocket.close();
    }

    public void disconnectFtp() {
        if (mFtpClient == null) {
            return;
        }

        if (!mFtpClient.isConnected()) {
            return;
        }

        try {
            mFtpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SocketFTP socketFTP = new SocketFTP();
        socketFTP.connectFtp();
        try {
            socketFTP.uploadFile("C:/Users/songqiang/Desktop/NCI_GM_upload.jar","/NCI_GM_upload.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
