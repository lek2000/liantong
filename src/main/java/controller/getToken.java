package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class getToken {
//public static String gettoken(String appKey,String appSecret,String timestamp,String serialNumber){

public static String gettoken(String APP_ID,String APPID,String TIMESTAMP,String timestamp_id,String TRANS_ID,String trans_id,String APPSECRET){
			
//	TOKEN=APP_ID【】TIMESTAMP【】TRANS_ID【】【AppSecret】
		StringBuffer foo = new StringBuffer();
		        foo.append("APP_ID");
		        foo.append(APPID);
				foo.append("TIMESTAMP");
				foo.append(timestamp_id);
				foo.append("TRANS_ID");
				foo.append(trans_id);
				foo.append(APPSECRET);
//				foo.append(appSecret);
//				foo.append(appKey);
//				foo.append("TIMESTAMP");
//				foo.append(timestamp);
//				foo.append("TRANS_ID");
//				foo.append(serialNumber);
//				foo.append(appSecret);
//				.append(Constants.APP_ID)
//				.append(appKey)
//				.append(Constants.TIMESTAMP)
//				.append(timestamp)
//				.append(Constants.TRANS_ID)
//				.append(serialNumber)
//				.append(appSecret);
		System.out.println(foo);
		return getMd5(foo.toString());
	}
	
	public static String getMd5(String plainText) {  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            md.update(plainText.getBytes());  
            byte b[] = md.digest();  
  
            int i;  
  
            StringBuffer buf = new StringBuffer("");  
            for (int offset = 0; offset < b.length; offset++) {  
                i = b[offset];  
                if (i < 0)  
                    i += 256;  
                if (i < 16)  
                    buf.append("0");  
                buf.append(Integer.toHexString(i));  
            }  
            //32位加密  
            return buf.toString();  
        } catch (NoSuchAlgorithmException e) {  
        		throw new RuntimeException(e);
        }  
    }	

}
