package com.example.demo.utils;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Ftp {
    Socket ctrlSocket;// 控制用Socket
    public PrintWriter ctrlOutput;// 控制输出用的流
    public BufferedReader ctrlInput;// 控制输入用的流
    ServerSocket serverDataSocket;
    // openConnection方法
// 由地址和端口号构造Socket，形成控制用的流
    public void openConnection(String hostname,int port,String userName,String password) throws Exception {
        ctrlSocket = new Socket(hostname, port);
        ctrlOutput = new PrintWriter(ctrlSocket.getOutputStream());
        ctrlInput = new BufferedReader(new InputStreamReader(ctrlSocket
                .getInputStream()));
        ctrlOutput.println("USER " + userName);
        System.out.println(userName);
        ctrlOutput.flush();
        ctrlOutput.println("PASS " + password);
        System.out.println(password);
        ctrlOutput.flush();
    }
    // closeConnection方法
// 关闭控制用的Socket
    public void closeConnection() throws IOException {
        ctrlSocket.close();
    }

    public void doQuit() {
        try {
            ctrlOutput.println("QUIT ");
            ctrlOutput.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void doCd(String dirName) {
        try {
            ctrlOutput.println("CWD " + dirName);// CWD命令
            ctrlOutput.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    // doLs方法
// 取得目录信息
    public void doLs() {
        try {
            int n;
            byte[] buff = new byte[65530];

            // 建立数据连接
            Socket dataSocket = dataConnection("NLST ");

            // 准备读取数据用的流
            BufferedInputStream dataInput = new BufferedInputStream(dataSocket
                    .getInputStream());
            // 读取目录信息

            while ((n = dataInput.read(buff)) > 0) {
                System.out.write(buff, 0, n);
            }
            dataSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    // dataConnection方法
// 构造与服务器交换数据用的Socket
// 再用PORT命令将端口通知服务器
    public Socket dataConnection(String ctrlcmd) {
        String cmd = "PORT "; // PORT存放用PORT命令传递数据的变量
        int i;
        Socket dataSocket = null;// 传送数据用Socket
        try {
            // 得到自己的地址
            byte[] address = InetAddress.getLocalHost().getAddress();
            // 用适当的端口号构造服务器
            serverDataSocket = new ServerSocket(0,1);

            // 准备传送PORT命令用的数据
            for (i = 0; i < 4; ++i)
                cmd = cmd + (address[i] & 0xff) + ",";
            cmd = cmd + (((serverDataSocket.getLocalPort()) / 256) & 0xff)
                    + "," + (serverDataSocket.getLocalPort() & 0xff);
            // 利用控制用的流传送PORT命令
            ctrlOutput.println(cmd);

            ctrlOutput.flush();
            // 向服务器发送处理对象命令(LIST,RETR,及STOR)
            ctrlOutput.println(ctrlcmd);
            ctrlOutput.flush();

            // 接受与服务器的连接

            dataSocket = serverDataSocket.accept();
            serverDataSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return dataSocket;
    }
    // doAscii方法
// 设置文本传输模式
    public void doAscii() {
        try {
            ctrlOutput.println("TYPE A");// A模式
            ctrlOutput.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    // doBinary方法
// 设置二进制传输模式
    public void doBinary() {
        try {
            ctrlOutput.println("TYPE I");// I模式
            ctrlOutput.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    // doGet方法
// 取得服务器上的文件
    public void doGet(String fileName,
            String loafile) {


        try {
            int n;
            byte[] buff = new byte[1024];
            File local=new File(loafile);
            FileOutputStream outfile = new FileOutputStream(local);
            // 构造传输文件用的数据流
            Socket dataSocket = dataConnection("RETR " + fileName);
            BufferedInputStream dataInput = new BufferedInputStream(dataSocket
                    .getInputStream());
            // 接收来自服务器的数据，写入本地文件
            while ((n = dataInput.read(buff)) > 0) {
                outfile.write(buff, 0, n);
            }
            dataSocket.close();
            outfile.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    // doPut方法
// 向服务器发送文件
    public void doPut(String fileName,String lonfile) {
        try {
            int n;
            byte[] buff = new byte[1024];
            FileInputStream sendfile = null;


            // 准备读出客户端上的文件
            //BufferedInputStream dataInput = new BufferedInputStream(new FileInputStream(fileName));
            try {

                sendfile = new FileInputStream(fileName);
            } catch (Exception e) {
                System.out.println("文件不存在");
                return;
            }

            // 准备发送数据的流
            Socket dataSocket = dataConnection("STOR " + lonfile);
            OutputStream outstr = dataSocket.getOutputStream();
            while ((n = sendfile.read(buff)) > 0) {
                outstr.write(buff, 0, n);
            }

            dataSocket.close();
            sendfile.close();
            System.out.println("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    // main方法
// 建立TCP连接，开始处理
    public static void main(String[] arg) {

        try {
            Ftp f = null;
            f = new Ftp();
            f.openConnection("192.168.150.193",21,"myftp","123456"); // 控制连接建立,设置为自己的IP.
            f.doBinary();
            f.doPut("C:/Users/songqiang/Desktop/NCI_GM_upload.jar","/sq/test/NCI_GM_upload.jar");
            f.closeConnection(); // 关闭连接
            System.exit(0); // 结束程序
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
// 读取控制流的CtrlListen 类
class CtrlListen implements Runnable {
    BufferedReader ctrlInput = null;
    public CtrlListen(BufferedReader in) {
        ctrlInput = in;
    }
    public void run() {
        while (true) {
            try {
                // 按行读入并输出到标准输出上
                System.out.println(ctrlInput.readLine());
            } catch (Exception e) {
                System.exit(1);
            }
        }
    }
}
