package com.example.demo.utils;

import org.apache.log4j.Logger;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author chenminglei
 *
 */
public class FileUtils {
private static Logger logger = Logger.getLogger(FileUtils.class); 
/**
 * 修改文件后缀名
 * @param file
 * @param toFile
 */
public static boolean renameFile(String file, String toFile) {  
	  
    File toBeRenamed = new File(file);  
    //检查要重命名的文件是否存在，是否是文件  
    if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {  
    	logger.error("renameFile:File does not exist: " + file);
            return false;  
        }  
  
        File newFile = new File(toFile);  
  
        //修改文件名  
    if (toBeRenamed.renameTo(newFile)) {  
        System.out.println("File has been renamed.");  
        return true;  
    } else {  
        logger.error("renameFile:Error renmaing file: " + file);
            return false;  
        }  
  
    }

/**
 * 扫描本地路径下的文件
 * @param num_ 获取个数
 * @param path 扫描的路径
 * @param suffix 文件的尾缀
 * @return
 */
public static List<String> getSuffixFilesPath(String path,String suffix, int num_) {
	
	List<String> r = new ArrayList<String>();
	File f = new File(path);

	if (!f.exists() ||!f.isDirectory()) {
		logger.error("影像标准化:  请检测当前路径:"+path);
		return r;
	}

	File[] files = f.listFiles();
	
	int k =1;
	for(File i:files){
		if(k>num_){
			break;
		}
		if(i.isFile()){
			String fileName=i.getName();
			String Suffix=fileName.substring(fileName.lastIndexOf(".")+1);
			if(Suffix.equals(suffix)){
				r.add(i.getAbsolutePath());
				k++;
			}
		}
	}
	return r;
}

/**
 * 获取目录下所有文件路径
 * @param path
 * @param suffix
 * @return
 */
public static List<String> getSuffixFilesPath(String path,String suffix) {
		
		List<String> r = new ArrayList<String>();
		File f = new File(path);
		File[] files = f.listFiles();
		
		for(File i:files){
			if(i.isFile()){
				String fileName=i.getName();
				String Suffix=fileName.substring(fileName.lastIndexOf(".")+1);
			if(Suffix.equals(suffix)){
				r.add(i.getAbsolutePath());
			}
		}
	}
	return r;
}
	
/**
 * 获取指定路径下所有包含指定后缀的文件
 * @param path 路径
 * @param suffix 后缀，不包括.（点）
 * @return 文件列表
 */
public static List<File> getSuffixFiles(String path,String suffix){
	List<File> r = new ArrayList<File>();
	File f = new File(path);
	File[] files = f.listFiles();
	for(File i:files){
		if(i.isFile()){
			String fileName=i.getName();
			String Suffix=fileName.substring(fileName.lastIndexOf(".")+1);
			if(Suffix.equals(suffix)){
				r.add(i);
			}
		}
	}
	return r;
}	
	
/**
 * 复制文件
 * @param oldPath
 * @param newPath
 * @return 
 */
@SuppressWarnings({ "unused" })
public static boolean copyFile(String oldPath, String newPath) { 
	boolean is=false;
	
	InputStream inStream = null;
	FileOutputStream fs = null;
	
	try {
		int bytesum = 0;
		int byteread = 0;
		File oldfile = new File(oldPath);
		if (oldfile.exists()) { // 文件存在时
			
			File newFile = new File(newPath);
			//校验生成文件夹是否存在
		    String newFileDirPath = newFile.getPath().replace(newFile.getName(), "");  
	        File fp = new File(newFileDirPath); 
	        if (!fp.exists() ||!fp.isDirectory()) { 
	            fp.mkdirs();// 目录不存在的情况下，创建目录。  
	        }  
	        
			//读入原文件
			inStream = new FileInputStream(oldPath); 
			fs = new FileOutputStream(newPath);
			byte[] buffer = new byte[1444];
			int length;
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread; // 字节数 文件大小
				fs.write(buffer, 0, byteread);
			}
			is = true;
		}else{
			logger.info(oldPath+"不存在，请检查--");
		}
	} catch (Exception e) {
		logger.info(oldPath+"-复制单个文件操作出错--"+newPath);
		e.printStackTrace();
    
	}finally{
		try {
			if(null!=inStream){
				inStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if(null!=fs){
				fs.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	return is;
	
}

/**
 * 删除目录或文件，当目录中存在子目录和文件的话，也会全部删除
 * @param path
 */
public static void deleteAllFilesOfDir(File path) {  
    if (!path.exists())  
        return;  
    if (path.isFile()) {  
        path.delete();  
        return;  
    }  
    File[] files = path.listFiles();  
    for (int i = 0; i < files.length; i++) {  
        deleteAllFilesOfDir(files[i]);  
    }  
    path.delete();  
}

		
/**
 * @param filePath 获取路径中的文件名,不包含后缀
 * */
public static String getFileName(String filePath) 
{
	int pos = -1;
	int endPos = -1;
	if (!filePath.equals("")) {
		if (filePath.lastIndexOf("/") != -1) {
			pos = filePath.lastIndexOf("/") + 1;
		} else if (filePath.lastIndexOf("//") != -1) {
			pos = filePath.lastIndexOf("//") + 1;
		}
		if (pos == -1)
			pos = 0;
		filePath = filePath.substring(pos);
		endPos = filePath.lastIndexOf(".");
		if (endPos == -1) {
			return filePath;
		} else {
			return filePath.substring(0, endPos);
		}
	} else {
		return "";
	}
} 
		

/**
 * 创建文件
 * @throws IOException
 */
public static boolean creatFile(String name) throws IOException {
    boolean flag = false;
    File filename = new File(name);
    if (!filename.exists()) {
        filename.createNewFile();
    }
    filename = new File(name);
    if(filename.exists()){
    	flag = true;
    }
    return flag;
}
	    
/**
 * 追加内容	    
 * @param path
 * @param content
 * @return
 */
public static boolean appendContentToFile(String path,String content){
		FileWriter fw = null;
		try {
			// 如果文件存在，则追加内容；如果文件不存在，则创建文件
			File f = new File(path);
			fw = new FileWriter(f, true);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(content+"\r\n");
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
}
	/**
	* @Author: songqiang
	* @Description:读取文件成String字符串
	* @Date: 14:39 2018/3/1
	* @Params:参数string为你的文件名
	* @Return:字符串
	*/
	public static String readFileContent(String fileName) throws IOException {

		File file = ResourceUtils.getFile(fileName);

		BufferedReader bf = new BufferedReader(new FileReader(file));

		String content = "";
		StringBuilder sb = new StringBuilder();

		while(content != null){
			content = bf.readLine();

			if(content == null){
				break;
			}

			sb.append(content.trim());
		}

		bf.close();
		return sb.toString();
	}

	public static void main(String[] args) {
		try {
			String s = readFileContent("classpath:pid.txt");
			System.out.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
