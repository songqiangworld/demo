package com.example.demo.utils;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 图文识别帮助类
 *
 * @author Felix Li
 * @create 2017-12-19-9:12
 */
public class OCRHelper {

    private final String LANG_OPTION = "-l";
    private final String EOL = System.getProperty("line.separator");

    /**
     *  Tesseract-OCR的安装路径
     */
    private String tessPath = "C://Program Files (x86)//Tesseract-OCR";
    //private String tessPath = new File("tesseract").getAbsolutePath();

    /**
     * @param imageFile   传入的图像文件
     *
     * @return 识别后的字符串
     */
    public String recognizeText(File imageFile) throws Exception {
        /**
         * 设置输出文件的保存的文件目录
         */
        File outputFile = new File(imageFile.getParentFile(), "output");

        StringBuffer strB = new StringBuffer();
        List<String> cmd = new ArrayList<String>();

        cmd.add(tessPath + "\\tesseract");

        cmd.add("");
        cmd.add(outputFile.getName());
        cmd.add(LANG_OPTION);
        //cmd.add("chi_sim");
        cmd.add("eng");

        ProcessBuilder pb = new ProcessBuilder();
        /**
         *Sets this process builder's working directory.
         */
        pb.directory(imageFile.getParentFile());
        cmd.set(1, imageFile.getName());
        pb.command(cmd);
        pb.redirectErrorStream(true);
        long startTime = System.currentTimeMillis();
        System.out.println("开始时间：" + startTime);
        Process process = pb.start();
        // tesseract.exe 1.jpg 1 -l chi_sim
        //不习惯使用ProcessBuilder的，也可以使用Runtime，效果一致
        // Runtime.getRuntime().exec("tesseract.exe 1.jpg 1 -l chi_sim");
        /**
         * the exit value of the process. By convention, 0 indicates normal
         * termination.
         */
//      System.out.println(cmd.toString());
        int w = process.waitFor();
        if (w == 0)// 0代表正常退出
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(outputFile.getAbsolutePath() + ".txt"),
                    "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                strB.append(str).append(EOL);
            }
            in.close();

            long endTime = System.currentTimeMillis();
            System.out.println("结束时间：" + endTime);
            System.out.println("耗时：" + (endTime - startTime) + "毫秒");
        } else {
            String msg;
            switch (w) {
                case 1:
                    msg = "Errors accessing files. There may be spaces in your image's filename.";
                    break;
                case 29:
                    msg = "Cannot recognize the image or its selected region.";
                    break;
                case 31:
                    msg = "Unsupported image format.";
                    break;
                default:
                    msg = "Errors occurred.";
            }
            throw new RuntimeException(msg);
        }
        new File(outputFile.getAbsolutePath() + ".txt").delete();
        return strB.toString().replaceAll("\\s*", "");
    }

    public static void main(String[] args) {
      /*  try {
            //图片文件：此图片是需要被识别的图片
            File file = new File("D:\\test\\01.jpg");
            String recognizeText = new OCRHelper().recognizeText(file);
            System.out.print(recognizeText + "\t");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /*String result=FindOCR("D:\\test\\02.png",false);
        System.out.println(result);*/

        File file = new File("D:\\test\\02.png");
        Tesseract instance=new Tesseract();
        instance.setDatapath("C:\\Program Files (x86)\\Tesseract-OCR\\tessdata");//设置训练库
        String result = null;
        try {
            result = instance.doOCR(file);
            System.out.println(result);
        } catch (TesseractException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param srImage 图片路径
     * @param ZH_CN 是否使用中文训练库,true-是
     * @return 识别结果
     */
    public static String FindOCR(String srImage, boolean ZH_CN) {
        try {
            System.out.println("start");
            double start=System.currentTimeMillis();
            File imageFile = new File(srImage);
            if (!imageFile.exists()) {
                return "图片不存在";
            }
            BufferedImage textImage = ImageIO.read(imageFile);
            Tesseract instance=new Tesseract();
            instance.setDatapath("C:\\Program Files (x86)\\Tesseract-OCR\\tessdata");//设置训练库
            if (ZH_CN)
                instance.setLanguage("chi_sim");//中文识别
            String result = null;
            result = instance.doOCR(textImage);
            double end=System.currentTimeMillis();
            System.out.println("耗时"+(end-start)/1000+" s");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "发生未知错误";
        }
    }
}