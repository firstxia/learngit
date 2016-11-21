package com.xialm.googleplayme.protocol;

import android.os.SystemClock;
import android.util.Log;

import com.xialm.googleplayme.bean.AppInfos;
import com.xialm.googleplayme.utils.FileUtils;
import com.xialm.googleplayme.utils.HttpHelper;
import com.xialm.googleplayme.utils.IOUtils;
import com.xialm.googleplayme.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 协议类的基类
 * 泛型的使用:
 *      1.在类上声明,属性和方法都可以使用
 *      2.在方法上声明,只有本方法可以使用
 *      原则:返回什么,泛型就写什么
 * Created by Xialm on 2016/11/2.
 */

public abstract class BaseProtocol<T> {

    private FileWriter fw;
    private FileReader fr;

    public T load(int index) {
        SystemClock.sleep(2000);
        // 从本地获取缓存
        String jsonStr = loadFromLocal(index);
        // 缓存数据为null,重新请求服务器数据
        if (jsonStr == null) {
            jsonStr = loadFromServer(index);
            // 请求回来数据不为null,缓存到本地
            if (jsonStr != null) {
                save2Loacl(index, jsonStr);
            }
        } else {
            LogUtil.d("%s","复用了缓存数据");
        }
        if (jsonStr != null) {
            // 解析json字符串
            return parseJson(jsonStr);
        }
        return null;
    }

    /**
     * 从本地获取缓存数据:
     *      如果本地的缓存数据已过期,返回null;
     *      如果未过期,返回json字符串
     * @param index
     * @return 本地的json缓存数据或者null
     */
    private String loadFromLocal(int index) {
        File dir= FileUtils.getCache();
        File file = new File(dir, getKey() + "_" + getExtraParams() + "_" + index);
        FileReader fr=null;
        BufferedReader br=null;
        StringWriter sw=null;
        try {
            fr =  new FileReader(file);
            br = new BufferedReader(fr);
            long outOfTime = Long.parseLong(br.readLine());
            // 如果当前时间大于过期时间,过期了;返回一个null,表示本地没有缓存数据
            if (System.currentTimeMillis() > outOfTime) {
                return null;
            } else {
                // 没有过期,把本地缓存读到内存中
                /**
                 * 读:ByteArrayOutputStream基本字节输出流;读到内存中
                 *    StringWriter sw丝袜  将字符串读到内存中
                 */
                sw = new StringWriter();
                String line=null;
//                if ((line = br.readLine()) != null) {
//                    sw.write(line); // 将读到的行写到内存中
//                }
                while ((line = br.readLine()) != null) {
                    sw.write(line); // 将读到的行写到内存中
                }
                return sw.toString(); //将内存中的数据返回
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            IOUtils.close(sw);
            IOUtils.close(br);
            IOUtils.close(fr);
        }
        return null;
    }

    /**
     * 从服务器请求新数据
     * @param index
     * @return 从服务器返回一个json字符串
     */
    private String loadFromServer(int index) {
        // baseUrl:8090/home
        //  http://127.0.0.1:8090/home?index=0
        //  http://127.0.0.1:8090/subject?index=0
        HttpHelper httpHelper = new HttpHelper(HttpHelper.BASEURL + "/" + getKey() + "?index=" + index + getExtraParams());//+"&xxx="+xxx
        String jsonStr=httpHelper.getSync();
        LogUtil.d("%S","请求服务器数据成功");
        LogUtil.d("%S",jsonStr);
        return jsonStr;
    }

    protected String getExtraParams() {
        return "";
    }


    /**
     * 将请求的数据缓存到本地
     * 两种方式:
     *  1.直接将服务器返回的json数据,缓存到本地
     *  2.将json数据解析后,把解析后的数据缓存到数据库
     *  如果保证客户端数据与服务器端的数据同步呢?(是一致的,有效的呢)
     *  1.将本地的json字符串的md5值与服务器端的json字符串的MD5值进行比对,如果一致,则是最新数据;如果不一致,
     *  则服务器端数据有更新,直接请求服务器即可
     *  2.服务器在返回一个json字符串时,附带有过期时间,在复用本地json数据时,先判断数据是否已过期,
     *  如果为过期,直接复用;如果已过期,重新请求;
     * @param index
     * @param jsonStr
     */
    private void save2Loacl(int index, String jsonStr) {
        /**
         * 1.我们采用直接缓存json数据--在第一行添加一个过期时间(做模拟用,服务器没有返回)
         * 读取第一行的过期时间:
         *      如果当前时间>过期时间   说明数据已过期,重新请求服务器数据
         *      如果当前时间<过期时间     没有过期,复用缓存数据
         */

//        File dir=new File("/mnt/sdcard"); //先放到sd卡根目录(这种字符串形式的是不需要权限的)
        File dir= FileUtils.getCache();
        File file = new File(dir, getKey() + "_" + getExtraParams() + "_" + index);
        // 1.字节流 可以是任何文件    2.字符流 writer/Reader
        FileWriter fw=null;
        BufferedWriter bw=null;
        try {
            fw =  new FileWriter(file);
//            fw.write(System.currentTimeMillis()+120*1000+"");
//            fw.write("\r\n"); // 换行
            bw = new BufferedWriter(fw);
            bw.write(System.currentTimeMillis()+120*1000+"");
            bw.newLine(); //换行
            // 将json字符串写到文件中
            bw.write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关闭流,会抛异常,写一个工具类,来关闭流
            // 后开的流先关闭,先开的流后关闭
            IOUtils.close(bw);
            IOUtils.close(fw);
        }

    }

    /**
     * 解析json字符串(服务器或者本地)
     * @param jsonStr 待解析的json字符串
     */
    protected abstract T parseJson(String jsonStr);

    /**
     * 请求网络关键词:
     //  http://127.0.0.1:8090/home?index=0    >home
     //  http://127.0.0.1:8090/subject?index=0 >subject
     * @return
     */
    protected abstract String getKey();

}
