package com.example.administrator.oisee.View;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpAssistant {
    public static String upLoadFilePost(String actionUrl, Map<String, File> files) throws IOException {
        String BOUNDARY ="========7d4a6d158c9";
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";
        URL uri = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setReadTimeout(5 * 1000);
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false);
        conn.setRequestMethod("POST"); // Post方式
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                + ";boundary=" + BOUNDARY);

        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        // 发送文件数据
        if (files != null)
            for (Map.Entry<String, File> file : files.entrySet()) {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(PREFIX);
                sb1.append(BOUNDARY);
                sb1.append(LINEND);
                sb1.append("Content-Disposition: form-data; name=\"JSON\"; filename=\""
                        + file.getValue().getName() + "\"" + LINEND);

                sb1.append("Content-Type: application/octet-stream; charset="
                        + CHARSET + LINEND);
                sb1.append(LINEND);
                outStream.write(sb1.toString().getBytes());
                InputStream is = new FileInputStream(file.getValue());
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }


                outStream.write(LINEND.getBytes());
                is.close();
            }

        // 请求结束标志
        byte[] end_data = (LINEND + PREFIX+BOUNDARY+PREFIX+LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();

        // 得到响应码
        int res = conn.getResponseCode();
        if (res == 200) {
            InputStream in = conn.getInputStream();
            InputStreamReader isReader = new InputStreamReader(in);
            BufferedReader bufReader = new BufferedReader(isReader);
            String line = "";
            String data = "";
            while ((line = bufReader.readLine()) != null) {
                data += line;
            }
            outStream.close();
            conn.disconnect();
            return data;
        }
        outStream.close();
        conn.disconnect();
        return "";
    }

}
