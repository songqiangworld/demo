package com.example.demo.enums;

public enum ErrorStatus {
   Success,
   Error_1001_ResolveXmlFail,//根据影像信息节点获取Image文件失败
   Error_1002_MissImagePathByXml,//获取xml中的影像信息节点失败
   Error_1003_RemoteNotFoundImageFail,//找不到影像信息节点记录的Image文件路径
   Error_1004_DownloadImageFail,//下载影像失败
   Error_1005_DownloadImageFormatFail,//下载影像的格式有误，目前仅支持jpg,tif
   Error_1006_LocalPackageFileToZipFail,//报文和对应的影像在本地打zip包失败
   Error_1007_RemoteDelXmlFail,//删除远程xml文件失败
   Error_1008_RemoteDelImgFail,//删除远程影像文件失败
   Error_1009_ConnFtpServiceFail,//连接FTP远程服务器失败
   Error_1010_RemoteRenameXmlToErrorFail,//修改远程报文文件为错误状态失败
   Error_1011_GetXmlNodeNameFail,//获取xml中的节点名称失败
   Error_1012_XmlNodeNameRepetitionFail,//"xml中的节点名称重复
   Error_1013_XmlNodeNameLackFail,//"xml中的节点名称缺少
   Error_2001_BackupFail,//备份上传文件失败
   Error_2002_FileUploadFail,//上传文件失败
   Error_2003_UpdateFileSuffixFail,//上传文件失败修改尾缀为fail失败
   Error_2004_UpdateFileTmpSuffixFail;//成功后 删除尾缀.tmp失败 请自行到ftp上删除
}
