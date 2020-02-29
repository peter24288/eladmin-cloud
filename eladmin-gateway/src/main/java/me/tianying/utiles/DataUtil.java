package me.tianying.utiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/9/19.
 */
public class DataUtil {

    /**
     * // json to map
     * @param json
     * @return
     */
    public static Map jsonToMap(String json){
        Map map = null;
        if(json==null || "".equals(json)){
            return map;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * bean to json
     * @param o
     * @return
     */
    public static String ObjectToJson(Object o){
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(o);
            json = json.replaceAll("\"\"\"\"", "\"\"");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static Map<String, Object> objectToMap(Object obj) {
        if(obj == null){
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        try {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return map;
    }
    /**
     * JavaBean对象转换为Map,父类属性也可转
     * @param obj
     * @return
     */
    public static Map<String, Object> convertObjToMap(Object obj){
        Map<String,Object> reMap = new HashMap<String,Object>();
        if (obj == null)
            return null;
        try {
            Class<?> objClass = obj.getClass();
            while(objClass != null){
                Field[] fields = objClass.getDeclaredFields();
                for(int i=0;i<fields.length;i++){
                    try {
                        Field f = objClass.getDeclaredField(fields[i].getName());
                        f.setAccessible(true);
                        Object o = f.get(obj);
                        reMap.put(fields[i].getName(), o);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                objClass = objClass.getSuperclass();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return reMap;
    }

    public static String formatNum(String number){
        if(number.equals("0")){
            return "0.00";
        }

        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(Double.valueOf(number));
    }

    public static String formatDecimalNum(BigDecimal number){
        if(number.compareTo(new BigDecimal(0)) == 0){
            return "0.00";
        }

        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(Double.valueOf(number+""));
    }

    /**
     * 判断是否为中文
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        boolean result = false;
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
                result = true;
            }
        }
        return result;
    }

    /**
     * 把中文转成Unicode码
     *
     * @param str
     * @return
     */
    public static String chinaToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }

    /**
     * 判断是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (null == obj) {
            return true;
        }
        if ("".equals(obj)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isNotNullOrEmpty(Object obj) {
        return !isNullOrEmpty(obj);
    }

    /**
     * @param filename
     * @return
     */
    public static String getUniqueName(String filename) {
        return getUUID() + filename.substring(filename.lastIndexOf("."));
    }

    /**
     * @return
     */
    public static String getUUID() {
        String s= UUID.randomUUID().toString();
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }

    /**
     * @return
     */
    public static String getSimpleUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 32位FNV算法
     *
     * @param data
     * @return
     */
    public static String hash(String data) {

        return data.hashCode() + "";
    }

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder())
                .append(Character.toLowerCase(s.charAt(0)))
                .append(s.substring(1)).toString();
    }

    /**
     * 首字母转大写
     *
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder())
                .append(Character.toUpperCase(s.charAt(0)))
                .append(s.substring(1)).toString();
    }

    /**
     * 首字母转大写
     *
     * @param url
     * @return
     */
    public static String changeUrl(String url, String[] param) {
        if (param != null) {
            for (int i = 0; i < param.length; i++) {
                url = url.replace("{" + (i + 1) + "}", param[i]);
            }
        }
        return url;
    }

    public static String md5(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return str;
        }

        md.update(str.getBytes());
        byte[] bs = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bs.length; i++) { // 字节数组转换成十六进制字符串，形成最终的密文
            int v = bs[i] & 0xff;
            if (v < 16) {
                sb.append(0);
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

    /**
     * 获取文件后缀
     *
     * @param filename
     * @return
     */
    public static String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }

    /*
     * 生成随机数
     */
    public static String getRandomString(int length) {
        String seed = "ABCDEFGHJKMNPQRSTUVWXYY23456789";
        int subIndex = 0;
        String retString = "";
        for (int i = 0; i < length; i++) {
            subIndex = (int) (Math.random() * 100 % seed.length());
            retString += seed.substring(subIndex, subIndex + 1);
        }
        return retString;

    }
    /*
       * 生成哈希数
       */
    public static String getHashString(int length) {
        String seed = "ABCDEFGHJKMNPQRSTUVWXYY23456789";
        int subIndex = 0;
        String retString = "";
        for (int i = 0; i < length; i++) {
            subIndex = (int) (Math.random() * 100 % seed.length());
            retString += seed.substring(subIndex, subIndex + 1);
        }
        return retString.hashCode()+"";

    }
    /*
     * 生成随机数
     */
    public static String getRandomIntString(int length) {
        String seed = "0123456789";
        int subIndex = 0;
        String retString = "";
        for (int i = 0; i < length; i++) {
            subIndex = (int) (Math.random() * 100 % seed.length());
            retString += seed.substring(subIndex, subIndex + 1);
        }
        return retString;

    }

    /**
     * 计算两个字符串最长相同子串
     *
     * @param @param  s1
     * @param @param  s2
     * @param @return
     * @return String
     * @throws
     * @Title: search
     * @Description: TODO
     * @Date 2014年3月17日 下午1:44:33
     */
    public static String search(String s1, String s2) {
        String max = "";
        for (int i = 0; i < s1.length(); i++) {
            for (int j = i; j <= s1.length(); j++) {
                String sub = s1.substring(i, j);
                // System.out.println(sub);
                if ((s2.indexOf(sub) != -1) && sub.length() > max.length()) {
                    max = sub;
                }
            }
        }
        return max;
    }

    /**
     * 检测是否为手机号码
     *
     * @param @param  tel
     * @param @return
     * @return boolean
     * @throws
     * @Title: checkTelephone
     * @Description: TODO
     * @Date 2014年3月20日 下午2:46:47
     */
    public static boolean checkTelephone(String tel) {
        String regExp = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(tel.trim());
        return m.find();
    }


    /**
     * @param strLength
     * @return
     */
    public static String getFixLenthString(int strLength) {

        Random rm = new Random();

        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);

        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);
    }

    /**
     * 将金额（1.00）转为100
     * 即单位从元转为分
     *
     * @param price
     * @return
     */
    public static String priceTofen(String price) {
        BigDecimal bd = new BigDecimal(price);
        BigDecimal bd2 = new BigDecimal(100);
        BigInteger re = bd.multiply(bd2).toBigInteger();
        return re.toString();
    }

    /**
     * 将金额（1）转为1.00
     *
     * @param price
     * @return
     */
    public static String priceToString(String price) {
        int idx = price.indexOf(".");
        if (idx == -1) {
            return price + ".00";
        }
        int pos = price.substring(idx + 1).length();
        if (pos == 1) {
            return price + "0";
        }
        if (pos > 2) {
            return price.substring(0, idx + 3);
        }
        return price;
    }

    /**
     * 乘以
     *
     * @return
     */
    public static String multiply(String arg1, String arg2) {
        BigDecimal bg1 = new BigDecimal(arg1);
        BigDecimal bg2 = new BigDecimal(arg2);
        return bg1.multiply(bg2).toString();
    }

    /**
     * 数字格式化小数点后两位
     *
     * @return
     */
    public static String decimal2bite(String num) {
        Double d= Double.parseDouble(num);
        DecimalFormat df=new DecimalFormat("#.##");
        return  df.format(d);
    }

    /**
     *小数可有可无，最多两位小数，必须大于零
     * @param number
     * @return
     */
    public synchronized static boolean checkNumber(String number){
        Pattern pattern = Pattern.compile("^(?!0+(?:\\.0+)?$)(?:[1-9]\\d*|0)(?:\\.\\d{1,2})?$");
        Matcher matcher = pattern.matcher(number);
        boolean result = matcher.matches();
        return result;
    }

    public static int objToNumber(Object ob){
        if(ob==null || ob.toString().trim().equals("")){
            return 0;
        }else{
            int rs=Integer.valueOf(ob.toString());
            return rs;
        }
    }

    public static BigDecimal objToBigDecimal(Object ob){
        if(ob==null || ob.toString().trim().equals("")){
            return new BigDecimal(0);
        }else{
            BigDecimal rs = new BigDecimal(ob.toString());
            return rs;
        }
    }


    public static boolean isEmpty(Integer ob){
        if(ob==null || ob.intValue()==0){
            return true;
        }
        return false;
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1
     *            被加数
     * @param v2
     *            加数
     * @return 两个参数的和
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        BigDecimal sum = new BigDecimal(0);
        // 设一个不为0的值
        if (v1 == null || v2 == null) {
            if (v1 == null && v2 == null) {
                return sum;
            }
            return v1 == null ? v2 : v1;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(v2.doubleValue()));
        return numberDown(b1.add(b2));
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1
     *            被加数
     * @param v2
     *            加数
     * @return 两个参数的和
     */
    public static String add(String v1, String v2) {
        String sum = "0";
        // 设一个不为0的值
        if (v1 == null || v2 == null) {
            if (v1 == null && v2 == null) {
                return sum;
            }
            return v1 == null ? v2 : v1;
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return formatDecimalNum(b1.add(b2));
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1
     *            被减数
     * @param v2
     *            减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
        BigDecimal b1 = new BigDecimal("0");
        if (v1 != null) {
            b1 = new BigDecimal(Double.toString(v1.doubleValue()));
        }
        BigDecimal b2 = new BigDecimal("0");
        if (v2 != null) {
            b2 = new BigDecimal(Double.toString(v2.doubleValue()));
        }
        return numberDown(b1.subtract(b2));
    }

    /**
     * 比较两个数大小
     *
     * @param v1
     *            第一个数字
     * @param v2
     *            第二个数字
     * @return 大于 还回 1 、等于还回 0 、小于 还回 -1
     */
    public static int compare(BigDecimal v1, BigDecimal v2) {
        int intValue = 0;
        Double d1 = v1.doubleValue();
        Double d2 = v2.doubleValue();
        if (d1 > d2) {
            intValue = 1;
        }
        if (d1 == d2) {
            intValue = 0;
        }
        if (d1 < d2) {
            intValue = -1;
        }
        return intValue;
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1
     *            被乘数
     * @param v2
     *            乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
        if (v1 == null || v2 == null) {
            return new BigDecimal(0);
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(v2.doubleValue()));
        return numberDown(b1.multiply(b2));
    }

    public static BigDecimal numberDown(BigDecimal amount) {
        return amount.setScale(4, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal mul(BigDecimal v1, BigDecimal v2, int mode) {
        if (v1 == null || v2 == null) {
            return new BigDecimal(0);
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(v2.doubleValue()));
        return numberHandle(b1.multiply(b2), mode);
    }

    public static BigDecimal numberHandle(BigDecimal amount, int mode) {
        return amount.setScale(4, mode);
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1
     *            被乘数
     * @param v2
     *            乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(Long v1, BigDecimal v2) {
        if (v1 == null || v2 == null) {
            return new BigDecimal(0);
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(v2.doubleValue()));
        return numberDown(b1.multiply(b2));
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        return div(v1, v2, 4);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @param scale
     *            表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(v2.doubleValue()));
        return b1.divide(b2, scale, BigDecimal.ROUND_DOWN);
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v
     *            需要四舍五入的数字
     * @param scale
     *            小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(BigDecimal v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v.doubleValue()));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_DOWN);
    }

    /**
     * double类型保留n位小数
     *
     * @param f
     *            需要四舍五入的数字
     * @param scale
     *            小数点后保留几位
     * @return
     */
    public static double round(double f, int scale) {
        StringBuilder pattern = new StringBuilder();
        pattern.append("#.");
        for(int i =0; i < scale; i++){
            pattern.append("0");
        }
        DecimalFormat df = new DecimalFormat(pattern.toString());
        return Double.parseDouble(df.format(f));
    }

    /**
     * 数字、货币格式化
     * @version 2011-9-21 下午09:19:39
     * @param pattern
     * @param number
     * @return
     */
    public static String numberFormat(String pattern, BigDecimal number) {
        String numberStr = null;
        if(number==null){
            return "";
        }
        try {
            if (pattern == null || pattern.equals("")) {
                numberStr = new DecimalFormat("#0.00##").format(number.doubleValue());
            } else {
                numberStr = new DecimalFormat(pattern).format(number.doubleValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numberStr;
    }


    /**
     * 转换成两位小数
     * @param amount
     * @param scale
     * @param mode
     *  舍入方式
     * @return
     */
    public static BigDecimal numberScale(BigDecimal amount,int scale, int mode){
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return amount.setScale(scale, mode);
    }

    /**
     * 转换成两位小数，超过两位的直接舍掉
     * @param amount
     * @param scale
     * @
     * @return
     */
    public static BigDecimal numberScale(BigDecimal amount,int scale){
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return amount.setScale(scale, BigDecimal.ROUND_DOWN);
    }




    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null) {
            return null;
        }

        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    /**
     * 长整数转高精度数值
     * @param value
     * @return
     */
    public static BigDecimal longToBigDecimalForMoney(Long value) {
        BigDecimal totalFee = new BigDecimal(value);
        BigDecimal d100 = new BigDecimal(100);
        BigDecimal fee = totalFee.divide(d100,2,2);//小数点2位
        return fee;
    }
   /* public static void main(String [] args){
        Map<BigDecimal,BigDecimal> map =new HashMap<BigDecimal,BigDecimal>();
        map.put(new BigDecimal(100),new BigDecimal(3.5));
        map.put(new BigDecimal(200),new BigDecimal(8.5));
        System.out.println(DataUtil.ObjectToJson(map));

        System.out.println(DataUtil.jsonToMap("{\"200\":8.5,\"100\":3.5}"));

        System.out.println(longToBigDecimalForMoney(0L));

    }*/



}
