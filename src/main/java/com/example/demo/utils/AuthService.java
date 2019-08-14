package com.example.demo.utils;



import com.baidu.aip.bodyanalysis.AipBodyAnalysis;
import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取token类
 *
 */
public class AuthService {
    //设置APPID/AK/SK
    public static final String APP_ID = "11458199";
    public static final String API_KEY = "H3GArUeEPstTxlfULRYfYIj8";
    public static final String SECRET_KEY = "WFU9gViQuGNnQFhBM1DNZ6nGqo25fpM7";

    /**24.502adad259c07c5c78390ae3de9801f5.2592000.1532755225.282335-11458199
     * 获取权限token
     * @return 返回示例：
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     * "expires_in": 2592000
     * }
     */
    public static void main(String[] args) {
        /*// 官网获取的 API Key 更新为你注册的
        String clientId = "H3GArUeEPstTxlfULRYfYIj8";//"百度云应用的AK";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "WFU9gViQuGNnQFhBM1DNZ6nGqo25fpM7";//"百度云应用的SK";
        System.out.println(getAuth(clientId, clientSecret));*/

        // 初始化一个AipBodyAnalysis
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("detect_risk", "false");

        String idCardSide = "back";
        // 调用接口
        // 参数为本地路径
        String image = "C:\\Users\\songqiang\\Documents\\1.png";
        JSONObject res = client.idcard(image, idCardSide, options);
        System.out.println(res.toString(2));


    }
    public static String getAuth() {
        // 官网获取的 API Key 更新为你注册的
        String clientId = "H3GArUeEPstTxlfULRYfYIj8";//"百度云应用的AK";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "WFU9gViQuGNnQFhBM1DNZ6nGqo25fpM7";//"百度云应用的SK";
        return getAuth(clientId, clientSecret);
    }



    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

    public void sample(AipBodyAnalysis client) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("type", "gender");


        // 参数为本地路径
        String image = "test.jpg";
        JSONObject res = client.bodyAttr(image, options);
        System.out.println(res.toString(2));

        // 参数为二进制数组
        byte[] file = getImageBinary("test.jpg");
//        res = client.bodyAttr(file, options);
//        System.out.println(res.toString(2));
    }

    /**
     * 将图片转换成二进制
     *
     * @return
     */
    static byte[] getImageBinary(String imagePath) {
        File f = new File(imagePath);
        BufferedImage bi;
        try {
            bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);  //经测试转换的图片是格式这里就什么格式，否则会失真
            byte[] bytes = baos.toByteArray();

            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
