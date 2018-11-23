package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/api/sendBox")
public class MyController {

	@GetMapping("/accountHandApply/v1") 
	//@ResponseBody
	public void accountHandApply(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("utf-8");

		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String result = "";
		String strurl = "http://10.124.164.110:8000/api/sendBox/accountHandApply/v1";
		try {
	        URL url = new URL(strurl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setRequestMethod("POST");
	        connection.setUseCaches(false);
	        connection.setInstanceFollowRedirects(true);
	        connection.setRequestProperty("connection", "Keep-Alive");
	        //connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
	        connection.connect();
	        //POST����
	        DataOutputStream out = new DataOutputStream(
	                connection.getOutputStream());
	        
	 
			//先定义一个当前时间
			Date newDate=new Date();
			
			//把当前时间加上序列得到TRAND_ID
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String trans_id=sdf1.format(newDate);
					
			//把当前时间处理得到 TIMESTAMP
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
			String timestamp_id=sdf.format(newDate);
			//定义APPSECRET、APPID
			String APPSECRET= "eaTWSZ7MKguYJ0Ih2zwyYEkN5K1CrdRq";
			String APPID= "HINLT";
			
			//获取TOKEN
//			System.out.println("=========getToken开始");
			String token_id=getToken.gettoken( "APP_ID",APPID,"TIMESTAMP",timestamp_id,"TRANS_ID",trans_id,APPSECRET);
			System.out.println("token_id="+token_id.toString());
//			System.out.println(token_id);
//			System.out.println("=========getToken结束");
//			TOKEN=APP_ID【】TIMESTAMP【】TRANS_ID【】【AppSecret】
			
	        JSONObject UNI_BSS_HEAD = new JSONObject();
	        UNI_BSS_HEAD.element("TRANS_ID", trans_id);           
	        UNI_BSS_HEAD.element("TIMESTAMP", timestamp_id);
	        UNI_BSS_HEAD.element("APP_ID", APPID);
	        UNI_BSS_HEAD.element("TOKEN", token_id);
	        

	        JSONObject ACCOUNT_HAND_APPLY_REQ = new JSONObject();
	        
	        ACCOUNT_HAND_APPLY_REQ.element("CITY_CODE","0898");
	 //       QRY_UNSOLD_NUM_REQ.element("CHANNEL_TYPE", "90000002");
	        ACCOUNT_HAND_APPLY_REQ.element("STAFF_ID","HKMOYINGC");
	        ACCOUNT_HAND_APPLY_REQ.element("BUSI_TYPE","01");
//	        QRY_UNSOLD_NUM_REQ.element("SERIAL_NUMBER", "186");
	        ACCOUNT_HAND_APPLY_REQ.element("CHANNEL_ID", "90000002");
	        ACCOUNT_HAND_APPLY_REQ.element("SYS_CODE","9900");
	        ACCOUNT_HAND_APPLY_REQ.element("BUSINESS_TYPE","01");
//	        QRY_UNSOLD_NUM_REQ.element("TAIL_TYPE","03");
	        ACCOUNT_HAND_APPLY_REQ.element("DISTRICT_CODE","902006");
	        ACCOUNT_HAND_APPLY_REQ.element("PROVINCE_CODE","90");
	        
	        JSONObject UNI_BSS_BODY = new JSONObject();
	        UNI_BSS_BODY.element("ACCOUNT_HAND_APPLY_REQ", ACCOUNT_HAND_APPLY_REQ.toString());
	        
	        JSONObject accountHandApply = new JSONObject();
	        accountHandApply.element("UNI_BSS_HEAD", UNI_BSS_HEAD.toString());
	        accountHandApply.element("UNI_BSS_BODY", UNI_BSS_BODY.toString());
	        
	       // String message = java.net.URLEncoder.encode("������","utf-8");
	         
	       // out.writeBytes("data="+qryNum.toString());
	        System.out.println("accountHandApply="+accountHandApply.toString());
	        if (accountHandApply != null ) {
	            byte[] writebytes = accountHandApply.toString().getBytes();
	            // �����ļ�����
	           // connection.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
	            OutputStream outwritestream = connection.getOutputStream();
	            outwritestream.write(accountHandApply.toString().getBytes());
	            outwritestream.flush();
	            outwritestream.close();
	        }
	        //��ȡ��Ӧ
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                connection.getInputStream()));
	        String lines;
	        StringBuffer sb = new StringBuffer("");
	        while ((lines = reader.readLine()) != null) {
	            lines = new String(lines.getBytes(), "utf-8");
	            sb.append(lines);
	        }
	        System.out.println(sb);
	        result = sb.toString();
	        reader.close();
	        connection.disconnect();
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		OutputStream outputStream = response.getOutputStream();// ��ȡOutputStream�����
		response.setHeader("content-type", "text/html;charset=UTF-8");// ͨ��������Ӧͷ�����������UTF-8�ı�����ʾ���ݣ����������仰����ô�������ʾ�Ľ�������
		 
		result = result.trim();
		//result = result.replace(" ", "");
		byte[] dataByteArr = result.getBytes("UTF-8");// ���ַ�ת�����ֽ����飬ָ����UTF-8�������ת��
		outputStream.write(dataByteArr);// ʹ��OutputStream����ͻ�������ֽ�����
	    //����toString()������ֱ�ӽ������ݴ�ӡ����
	   // System.out.println(obj.toString());
		//PrintWriter out = response.getWriter();
	}
	
	@GetMapping("/qryNum/v1") 
	//@ResponseBody
	public void qryNum(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("utf-8");

		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String result = "";
		String strurl = "http://10.124.164.110:8000/api/sendBox/qryNum/v1";
		try {
            URL url = new URL(strurl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("connection", "Keep-Alive");
            //connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            connection.setRequestProperty("accept","application/json");
            connection.connect();
            //POST����
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            
            /*ƴ������*/
            JSONObject RESERVED1 = new JSONObject();
            RESERVED1.element("RESERVED_VALUE", "dl6NHAyDL7aNzH7naf1EGkmFx0it");
            RESERVED1.element("RESERVED_ID", "IB0t8tedeFvP35hlyjg");
            
            String RESERVED = "["+RESERVED1.toString()+"]";
            
            JSONObject UNI_BSS_HEAD = new JSONObject();
            UNI_BSS_HEAD.element("RESERVED", RESERVED);
            UNI_BSS_HEAD.element("APP_ID", "HINLT");
            UNI_BSS_HEAD.element("TIMESTAMP", "2018-10-08 16:01:22 339");
            UNI_BSS_HEAD.element("TRANS_ID", "20181008160122339792353");
            UNI_BSS_HEAD.element("TOKEN", "05c94a071c2d76a120546a937c431dba");
            
            JSONObject UNI_BSS_ATTACHED = new JSONObject();
            UNI_BSS_ATTACHED.element("MEDIA_INFO", "qhrOl1Pv6bwqc85tF1ln3iazukLwHNH2do3nOiJmhlAGzc1h8FsDANnewbfncsxeyCok8pn52Kdn8AGCot4JNdaOr6idtAOBl2G807MhDa83ibjo8aoB8iu9Dw6mivwLP88s7P0csh9dCywnA6eiyMPGlaoJuA0wP93g8r7rAknz1N9DJHHpKi2ps");
            
            JSONObject QRY_NUM_REQ = new JSONObject();
            QRY_NUM_REQ.element("BUSINESS_TYPE", "00");
            QRY_NUM_REQ.element("AMOUNTS", "5");
            QRY_NUM_REQ.element("CHANNEL_ID", "19a2109");
            QRY_NUM_REQ.element("STAFF_ID", "A0018994");
            QRY_NUM_REQ.element("CITY_CODE", "198");
            QRY_NUM_REQ.element("CODE_TYPE_CODE" ,"ABCD");
            QRY_NUM_REQ.element("GROUP_KEY", "59237227");
            QRY_NUM_REQ.element("MONTH_LIMIT","0");
            QRY_NUM_REQ.element("POOL_TYPE","00");
            QRY_NUM_REQ.element("PROVINCE_CODE","19");
            QRY_NUM_REQ.element("QRY_TYPE","02");
            QRY_NUM_REQ.element("SEARCH_CATEGORY","3");
            QRY_NUM_REQ.element("STAFF_ID","XZZB0024");
            QRY_NUM_REQ.element("SYS_CODE","mall");
            
            JSONObject UNI_BSS_BODY = new JSONObject();
            UNI_BSS_BODY.element("QRY_NUM_REQ", QRY_NUM_REQ.toString());
            
            JSONObject qryNum = new JSONObject();
            qryNum.element("UNI_BSS_HEAD", UNI_BSS_HEAD.toString());
            qryNum.element("UNI_BSS_ATTACHED", UNI_BSS_ATTACHED.toString());
            qryNum.element("UNI_BSS_BODY", UNI_BSS_BODY.toString());
            
           // String message = java.net.URLEncoder.encode("������","utf-8");
             
           // out.writeBytes("data="+qryNum.toString());
            System.out.println("qryNum="+qryNum.toString());
            if (qryNum != null ) {
                byte[] writebytes = qryNum.toString().getBytes();
                // �����ļ�����
               // connection.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = connection.getOutputStream();
                outwritestream.write(qryNum.toString().getBytes());
                outwritestream.flush();
                outwritestream.close();
            }
            //��ȡ��Ӧ
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            System.out.println(sb);
            result = sb.toString();
            reader.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		OutputStream outputStream = response.getOutputStream();// ��ȡOutputStream�����
		response.setHeader("content-type", "text/html;charset=UTF-8");// ͨ��������Ӧͷ�����������UTF-8�ı�����ʾ���ݣ����������仰����ô�������ʾ�Ľ�������
		 
		result = result.trim();
		//result = result.replace(" ", "");
		byte[] dataByteArr = result.getBytes("UTF-8");// ���ַ�ת�����ֽ����飬ָ����UTF-8�������ת��
		outputStream.write(dataByteArr);// ʹ��OutputStream����ͻ�������ֽ�����
        //����toString()������ֱ�ӽ������ݴ�ӡ����
       // System.out.println(obj.toString());
		//PrintWriter out = response.getWriter();
	}
	
	@GetMapping("/qryUnsoldNum/v1") 
	//@ResponseBody
	public void qryUnsoldNum(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("utf-8");

		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String result = "";
		String strurl = "http://10.124.164.110:8000/api/sendBox/qryUnsoldNum/v1";
		try {
	        URL url = new URL(strurl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setRequestMethod("POST");
	        connection.setUseCaches(false);
	        connection.setInstanceFollowRedirects(true);
	        connection.setRequestProperty("connection", "Keep-Alive");
	        //connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
	        connection.connect();
	        //POST����
	        DataOutputStream out = new DataOutputStream(
	                connection.getOutputStream());
	        
	        /*ƴ������*/
	        JSONObject RESERVED1 = new JSONObject();
	        RESERVED1.element("RESERVED_VALUE", "bbbbbb");
	        RESERVED1.element("RESERVED_ID", "aaaaaa");
	        
	        JSONObject RESERVED2 = new JSONObject();
	        RESERVED2.element("RESERVED_VALUE", "222222");
	        RESERVED2.element("RESERVED_ID", "111111");
	        
	        String RESERVED = "["+RESERVED1.toString()+","+RESERVED2.toString()+"]";
	        
			//先定义一个当前时间
			Date newDate=new Date();
			
			//把当前时间加上序列得到TRAND_ID
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String trans_id=sdf1.format(newDate);
					
			//把当前时间处理得到 TIMESTAMP
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
			String timestamp_id=sdf.format(newDate);
			//定义APPSECRET、APPID
			String APPSECRET= "eaTWSZ7MKguYJ0Ih2zwyYEkN5K1CrdRq";
			String APPID= "HINLT";
			
			//获取TOKEN
//			System.out.println("=========getToken开始");
			String token_id=getToken.gettoken( "APP_ID",APPID,"TIMESTAMP",timestamp_id,"TRANS_ID",trans_id,APPSECRET);
			System.out.println("token_id="+token_id.toString());
//			System.out.println(token_id);
//			System.out.println("=========getToken结束");
//			TOKEN=APP_ID【】TIMESTAMP【】TRANS_ID【】【AppSecret】
			
	        JSONObject UNI_BSS_HEAD = new JSONObject();
	        UNI_BSS_HEAD.element("RESERVED", RESERVED);
	        UNI_BSS_HEAD.element("TRANS_ID", trans_id);           
	        UNI_BSS_HEAD.element("TIMESTAMP", timestamp_id);
	        UNI_BSS_HEAD.element("APP_ID", APPID);
	        UNI_BSS_HEAD.element("TOKEN", token_id);
	        
	        JSONObject UNI_BSS_ATTACHED = new JSONObject();
	        UNI_BSS_ATTACHED.element("MEDIA_INFO", "");
	        
	        JSONObject QRY_UNSOLD_NUM_REQ = new JSONObject();
	        
	        QRY_UNSOLD_NUM_REQ.element("CITY_CODE","0898");
	 //       QRY_UNSOLD_NUM_REQ.element("CHANNEL_TYPE", "90000002");
	        QRY_UNSOLD_NUM_REQ.element("STAFF_ID","HKMOYINGC");
	        QRY_UNSOLD_NUM_REQ.element("BUSI_TYPE","01");
//	        QRY_UNSOLD_NUM_REQ.element("SERIAL_NUMBER", "186");
	        QRY_UNSOLD_NUM_REQ.element("CHANNEL_ID", "90000002");
	        QRY_UNSOLD_NUM_REQ.element("SYS_CODE","9900");
	        QRY_UNSOLD_NUM_REQ.element("BUSINESS_TYPE","01");
//	        QRY_UNSOLD_NUM_REQ.element("TAIL_TYPE","03");
	        QRY_UNSOLD_NUM_REQ.element("DISTRICT_CODE","902006");
	        QRY_UNSOLD_NUM_REQ.element("PROVINCE_CODE","90");
	        
	        JSONObject UNI_BSS_BODY = new JSONObject();
	        UNI_BSS_BODY.element("QRY_UNSOLD_NUM_REQ", QRY_UNSOLD_NUM_REQ.toString());
	        
	        JSONObject qryUnsoldNum = new JSONObject();
	        qryUnsoldNum.element("UNI_BSS_HEAD", UNI_BSS_HEAD.toString());
	        qryUnsoldNum.element("UNI_BSS_ATTACHED", UNI_BSS_ATTACHED.toString());
	        qryUnsoldNum.element("UNI_BSS_BODY", UNI_BSS_BODY.toString());
	        
	       // String message = java.net.URLEncoder.encode("������","utf-8");
	         
	       // out.writeBytes("data="+qryNum.toString());
	        System.out.println("qryUnsoldNum="+qryUnsoldNum.toString());
	        if (qryUnsoldNum != null ) {
	            byte[] writebytes = qryUnsoldNum.toString().getBytes();
	            // �����ļ�����
	           // connection.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
	            OutputStream outwritestream = connection.getOutputStream();
	            outwritestream.write(qryUnsoldNum.toString().getBytes());
	            outwritestream.flush();
	            outwritestream.close();
	        }
	        //��ȡ��Ӧ
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                connection.getInputStream()));
	        String lines;
	        StringBuffer sb = new StringBuffer("");
	        while ((lines = reader.readLine()) != null) {
	            lines = new String(lines.getBytes(), "utf-8");
	            sb.append(lines);
	        }
	        System.out.println(sb);
	        result = sb.toString();
	        reader.close();
	        connection.disconnect();
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		OutputStream outputStream = response.getOutputStream();// ��ȡOutputStream�����
		response.setHeader("content-type", "text/html;charset=UTF-8");// ͨ��������Ӧͷ�����������UTF-8�ı�����ʾ���ݣ����������仰����ô�������ʾ�Ľ�������
		 
		result = result.trim();
		//result = result.replace(" ", "");
		byte[] dataByteArr = result.getBytes("UTF-8");// ���ַ�ת�����ֽ����飬ָ����UTF-8�������ת��
		outputStream.write(dataByteArr);// ʹ��OutputStream����ͻ�������ֽ�����
	    //����toString()������ֱ�ӽ������ݴ�ӡ����
	   // System.out.println(obj.toString());
		//PrintWriter out = response.getWriter();
	}
	@GetMapping("/qryUserNumber/v1") 
	//@ResponseBody
	public void qryUserNumber(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("utf-8");

		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String result = "";
		String strurl = "http://10.124.164.110:8000/api/sendBox/qryUserNumber/v1";
		try {
	        URL url = new URL(strurl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setRequestMethod("POST");
	        connection.setUseCaches(false);
	        connection.setInstanceFollowRedirects(true);
	        connection.setRequestProperty("connection", "Keep-Alive");
	        //connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
	        connection.connect();
	        //POST����
	        DataOutputStream out = new DataOutputStream(
	                connection.getOutputStream());
	        
	        /*ƴ������*/
	        JSONObject RESERVED1 = new JSONObject();
	        RESERVED1.element("RESERVED_VALUE", "bbbbbb");
	        RESERVED1.element("RESERVED_ID", "aaaaaa");
	        
	        JSONObject RESERVED2 = new JSONObject();
	        RESERVED2.element("RESERVED_VALUE", "222222");
	        RESERVED2.element("RESERVED_ID", "111111");
	        
	        String RESERVED = "["+RESERVED1.toString()+","+RESERVED2.toString()+"]";
	        
	        String APPID= "HINLT";
	        String TIMESTAMP= "2018-10-08 16:01:22 339";
	        String TRANSID= "20181008160122339792353";
	        String APPSECRET= "eaTWSZ7MKguYJ0Ih2zwyYEkN5K1CrdRq";
	        String TOKEN= "05c94a071c2d76a120546a937c431dba";
	        
//	        String beforeEncode = "APP_ID" + APPID + "TIMESTAMP" + TIMESTAMP + "TRANS_ID" + TRANSID + APPSECRET;
//	        return MD5Util.md5encode(beforeEncode);
	        
	        
	        
	        JSONObject UNI_BSS_HEAD = new JSONObject();
	        UNI_BSS_HEAD.element("RESERVED", RESERVED);
	        UNI_BSS_HEAD.element("APP_ID", APPID);
	        UNI_BSS_HEAD.element("TIMESTAMP", TIMESTAMP);
	        UNI_BSS_HEAD.element("TRANS_ID", TRANSID);
	        UNI_BSS_HEAD.element("TOKEN", TOKEN);
	        

	        

	        String CERT_NUM= request.getParameter("CERTNUM");
	        String CERT_NAME= request.getParameter("CERTNAME");
	        JSONObject QRY_USER_NUMBER_REQ = new JSONObject();
	        

	        QRY_USER_NUMBER_REQ.element("CERT_NAME", CERT_NAME);
	        QRY_USER_NUMBER_REQ.element("CERT_NUM", CERT_NUM);
//	      QRY_USER_NUMBER_REQ.element("CERT_NAME", "秦胜强");
//	      QRY_USER_NUMBER_REQ.element("CERT_NUM", "230281197906170959");
	        QRY_USER_NUMBER_REQ.element("CERT_TYPE", "01");
	        QRY_USER_NUMBER_REQ.element("CHECK_TYPE", "0");
	        
	        
	        JSONObject UNI_BSS_BODY = new JSONObject();
	        UNI_BSS_BODY.element("QRY_USER_NUMBER_REQ", QRY_USER_NUMBER_REQ.toString());
	        
	        JSONObject qryUserNumber = new JSONObject();
	        qryUserNumber.element("UNI_BSS_HEAD", UNI_BSS_HEAD.toString());
	        qryUserNumber.element("UNI_BSS_BODY", UNI_BSS_BODY.toString());
	        

	        
	       // String message = java.net.URLEncoder.encode("������","utf-8");
	         
	       // out.writeBytes("data="+qryNum.toString());
	        System.out.println("qryUserNumber="+qryUserNumber.toString());
	        if (qryUserNumber != null ) {
	            byte[] writebytes = qryUserNumber.toString().getBytes();
	            // �����ļ�����
	           // connection.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
	            OutputStream outwritestream = connection.getOutputStream();
	            outwritestream.write(qryUserNumber.toString().getBytes());
	            outwritestream.flush();
	            outwritestream.close();
	        }
	        //��ȡ��Ӧ
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                connection.getInputStream()));
	        String lines;
	        StringBuffer sb = new StringBuffer("");
	        while ((lines = reader.readLine()) != null) {
	            lines = new String(lines.getBytes(), "utf-8");
	            sb.append(lines);
	        }
	        System.out.println(sb);
	        result = sb.toString();
	        reader.close();
	        connection.disconnect();
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		OutputStream outputStream = response.getOutputStream();// ��ȡOutputStream�����
		response.setHeader("content-type", "text/html;charset=UTF-8");// ͨ��������Ӧͷ�����������UTF-8�ı�����ʾ���ݣ����������仰����ô�������ʾ�Ľ�������
		 
		result = result.trim();
		//result = result.replace(" ", "");
		byte[] dataByteArr = result.getBytes("UTF-8");// ���ַ�ת�����ֽ����飬ָ����UTF-8�������ת��
		outputStream.write(dataByteArr);// ʹ��OutputStream����ͻ�������ֽ�����
	    //����toString()������ֱ�ӽ������ݴ�ӡ����
	   // System.out.println(obj.toString());
		//PrintWriter out = response.getWriter();
	}
	@GetMapping("/selectedNum/v1") 
	//@ResponseBody
	public void selectedNum(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("utf-8");

		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String result = "";
		String strurl = "http://10.124.164.110:8000/api/sendBox/selectedNum/v1";
		try {
            URL url = new URL(strurl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("connection", "Keep-Alive");
            //connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            connection.connect();
            //POST����
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            
            /*ƴ������*/
            JSONObject RESERVED1 = new JSONObject();
            RESERVED1.element("RESERVED_VALUE", "bbbbbb");
            RESERVED1.element("RESERVED_ID", "aaaaaa");
            
            String RESERVED = "["+RESERVED1.toString()+"]";
            

            
            JSONObject UNI_BSS_HEAD = new JSONObject();
            UNI_BSS_HEAD.element("RESERVED", RESERVED);
            UNI_BSS_HEAD.element("APP_ID", "HINLT");
            UNI_BSS_HEAD.element("TIMESTAMP", "2018-10-08 16:01:22 339");
            UNI_BSS_HEAD.element("TRANS_ID", "20181008160122339792353");
            UNI_BSS_HEAD.element("TOKEN", "05c94a071c2d76a120546a937c431dba");
            
            JSONObject UNI_BSS_ATTACHED = new JSONObject();
            UNI_BSS_ATTACHED.element("MEDIA_INFO", "");
            
            JSONObject SELECTED_NUM_REQ = new JSONObject();
            
            SELECTED_NUM_REQ.element("CERT_CODE","410108197809096057");
            SELECTED_NUM_REQ.element("CITY_CODE", "198");
            SELECTED_NUM_REQ.element("CHANNEL_TYPE", "1010300");
            SELECTED_NUM_REQ.element("STAFF_ID", "XZZB0024");
            SELECTED_NUM_REQ.element("SERIAL_NUMBER", "17636482345");
            SELECTED_NUM_REQ.element("CHANNEL_ID", "19a2109");
            SELECTED_NUM_REQ.element("SYS_CODE", "mall");
            SELECTED_NUM_REQ.element("CERT_TYPE_CODE", "01");
            SELECTED_NUM_REQ.element("DISTRICT_CODE", "766486");
            SELECTED_NUM_REQ.element("SELECTION_TIME", "30");
            SELECTED_NUM_REQ.element("PROVINCE_CODE", "19" );
            
            JSONObject UNI_BSS_BODY = new JSONObject();
            UNI_BSS_BODY.element("SELECTED_NUM_REQ", SELECTED_NUM_REQ.toString());
            
            JSONObject selectedNum = new JSONObject();
            selectedNum.element("UNI_BSS_HEAD", UNI_BSS_HEAD.toString());
            selectedNum.element("UNI_BSS_ATTACHED", UNI_BSS_ATTACHED.toString());
            selectedNum.element("UNI_BSS_BODY", UNI_BSS_BODY.toString());
            
           // String message = java.net.URLEncoder.encode("������","utf-8");
             
           // out.writeBytes("data="+qryNum.toString());
            System.out.println("selectedNum="+selectedNum.toString());
            if (selectedNum != null ) {
                byte[] writebytes = selectedNum.toString().getBytes();
                // �����ļ�����
               // connection.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = connection.getOutputStream();
                outwritestream.write(selectedNum.toString().getBytes());
                outwritestream.flush();
                outwritestream.close();
            }
            //��ȡ��Ӧ
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            System.out.println(sb);
            result = sb.toString();
            reader.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		OutputStream outputStream = response.getOutputStream();// ��ȡOutputStream�����
		response.setHeader("content-type", "text/html;charset=UTF-8");// ͨ��������Ӧͷ�����������UTF-8�ı�����ʾ���ݣ����������仰����ô�������ʾ�Ľ�������
		 
		result = result.trim();
		//result = result.replace(" ", "");
		byte[] dataByteArr = result.getBytes("UTF-8");// ���ַ�ת�����ֽ����飬ָ����UTF-8�������ת��
		outputStream.write(dataByteArr);// ʹ��OutputStream����ͻ�������ֽ�����
        //����toString()������ֱ�ӽ������ݴ�ӡ����
       // System.out.println(obj.toString());
		//PrintWriter out = response.getWriter();
	}
	
	
	@GetMapping("/isBlackName/v1") 
	//@ResponseBody
	public void isBlackName(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("utf-8");

		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String result = "";
		String strurl = "http://10.124.164.110:8000/api/sendBox/isBlackName/v1";
		try {
	        URL url = new URL(strurl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setRequestMethod("POST");
	        connection.setUseCaches(false);
	        connection.setInstanceFollowRedirects(true);
	        connection.setRequestProperty("connection", "Keep-Alive");
	        //connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
	        connection.connect();
	        //POST����
	        DataOutputStream out = new DataOutputStream(
	                connection.getOutputStream());
	        
	        /*ƴ������*/
	        JSONObject RESERVED1 = new JSONObject();
	        RESERVED1.element("RESERVED_VALUE", "bbbbbb");
	        RESERVED1.element("RESERVED_ID", "aaaaaa");
	        
	        JSONObject RESERVED2 = new JSONObject();
	        RESERVED2.element("RESERVED_VALUE", "222222");
	        RESERVED2.element("RESERVED_ID", "111111");
	        
	        String RESERVED = "["+RESERVED1.toString()+","+RESERVED2.toString()+"]";

	        
	        
	        
	      //先定义一个当前时间
	      		Date newDate=new Date();
	      		
	      		//把当前时间加上序列得到TRAND_ID
	      		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	      		String trans_id=sdf1.format(newDate);
	      				
	      		//把当前时间处理得到 TIMESTAMP
	      		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	      		String timestamp_id=sdf.format(newDate);
	      		//定义APPSECRET、APPID
	      		String APPSECRET= "eaTWSZ7MKguYJ0Ih2zwyYEkN5K1CrdRq";
	      		String APPID= "HINLT";
	      		
	      		//获取TOKEN
//	      		System.out.println("=========getToken开始");
	      		String token_id=getToken.gettoken( "APP_ID",APPID,"TIMESTAMP",timestamp_id,"TRANS_ID",trans_id,APPSECRET);
	      		System.out.println("token_id="+token_id.toString());
//	      		System.out.println(token_id);
//	      		System.out.println("=========getToken结束");
//	      		TOKEN=APP_ID【】TIMESTAMP【】TRANS_ID【】【AppSecret】
	      		
	              JSONObject UNI_BSS_HEAD = new JSONObject();
	              UNI_BSS_HEAD.element("RESERVED", RESERVED);
	              UNI_BSS_HEAD.element("TRANS_ID", trans_id);           
	              UNI_BSS_HEAD.element("TIMESTAMP", timestamp_id);
	              UNI_BSS_HEAD.element("APP_ID", "HINLT");
	              UNI_BSS_HEAD.element("TOKEN", token_id);
	        

	       
	              
	              

	       
	        JSONObject UNI_BSS_BODY = new JSONObject();
	        UNI_BSS_BODY.element("psptId", "412727196607107431");
	        UNI_BSS_BODY.element("provinceCode", "76");
	        UNI_BSS_BODY.element("psptTypeCode", "1");

	        
	        JSONObject isBlackName = new JSONObject();
	        isBlackName.element("UNI_BSS_HEAD", UNI_BSS_HEAD.toString());
	        isBlackName.element("UNI_BSS_BODY", UNI_BSS_BODY.toString());
	        
	       // String message = java.net.URLEncoder.encode("������","utf-8");
	         
	       // out.writeBytes("data="+qryNum.toString());
	        System.out.println("isBlackName="+isBlackName.toString());
	        if (isBlackName != null ) {
	            byte[] writebytes = isBlackName.toString().getBytes();
	            // �����ļ�����
	           // connection.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
	            OutputStream outwritestream = connection.getOutputStream();
	            outwritestream.write(isBlackName.toString().getBytes());
	            outwritestream.flush();
	            outwritestream.close();
	        }
	        //��ȡ��Ӧ
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                connection.getInputStream()));
	        String lines;
	        StringBuffer sb = new StringBuffer("");
	        while ((lines = reader.readLine()) != null) {
	            lines = new String(lines.getBytes(), "utf-8");
	            sb.append(lines);
	        }
	        System.out.println(sb);
	        result = sb.toString();
	        reader.close();
	        connection.disconnect();
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		OutputStream outputStream = response.getOutputStream();// ��ȡOutputStream�����
		response.setHeader("content-type", "text/html;charset=UTF-8");// ͨ��������Ӧͷ�����������UTF-8�ı�����ʾ���ݣ����������仰����ô�������ʾ�Ľ�������
		 
		result = result.trim();
		//result = result.replace(" ", "");
		byte[] dataByteArr = result.getBytes("UTF-8");// ���ַ�ת�����ֽ����飬ָ����UTF-8�������ת��
		outputStream.write(dataByteArr);// ʹ��OutputStream����ͻ�������ֽ�����
	    //����toString()������ֱ�ӽ������ݴ�ӡ����
	   // System.out.println(obj.toString());
		//PrintWriter out = response.getWriter();
	}
}
