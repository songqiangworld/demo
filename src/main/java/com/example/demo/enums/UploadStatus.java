package com.example.demo.enums;

/**
 * 枚举类UploadStatus代码
 *
 * @author zzr
 */
public enum UploadStatus {
    Create_Directory_Fail,   //远程服务器目录创建失败
    Create_Directory_Success, //远程服务器创建目录成功
    Upload_New_File_Success, //上传新文件成功
    Upload_New_File_Failed,   //上传新文件失败
    File_Exits,      //文件已经存在
    Remote_Bigger_Local,   //远程文件大于本地文件
    Upload_From_Break_Success, //断点续传成功
    Upload_From_Break_Failed, //断点续传失败
    Delete_Remote_Faild,   //删除远程文件失败
    Remote_Rename_Success, //重命名文件成功
    Remote_Rename_Faild,  //重命名文件失败
    Not_Exist_File; //远程文件不存在
}
