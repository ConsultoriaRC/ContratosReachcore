package com.rc.model.crm;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Example how to use multipart/form encoded POST request.
 */
public class ClientMultipartFormPost {
	
	public static String UploadFile(String url, String tkn, String fullPathFile){
		
		 CloseableHttpClient httpclient = HttpClients.createDefault();
	        try {
	            HttpPost httppost = new HttpPost(url);
	            httppost.addHeader("Authorization", "Zoho-oauthtoken " + tkn);
	            FileBody bin = new FileBody(new File(fullPathFile));
	            HttpEntity reqEntity = MultipartEntityBuilder.create()
	                    .addPart("file", bin)
	                    .build();

	            httppost.setEntity(reqEntity);

	            System.out.println("executing request " + httppost.getRequestLine());
	            CloseableHttpResponse response = httpclient.execute(httppost);
	            try {
	                System.out.println("----------------------------------------");
	                System.out.println(response.getStatusLine());
	                HttpEntity resEntity = response.getEntity();
	                if (resEntity != null) {
	                    System.out.println("Response content length: " + resEntity.getContentLength());
	                }
	                EntityUtils.consume(resEntity);
	            }
	            catch (IOException e) {
					e.printStackTrace();
					return "error";
				}
	            finally {
	                response.close();
	            }
	        } 
	        catch (ClientProtocolException e1) {
				e1.printStackTrace();
				return "error";
			}
	        catch (IOException e1) {
				e1.printStackTrace();
				return "error";
			} 
	        finally {
	            try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	        
	        return "success";
	}
	

    public static void main(String[] args) throws Exception {
        /*if (args.length != 1)  {
            System.out.println("File path not given");
            System.exit(1);
        }*/
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost("https://www.zohoapis.com/crm/v2/Conversaciones/2146999000023383002/Attachments");
            httppost.addHeader("Authorization", "Zoho-oauthtoken 1000.d356703c9932883ccabf7bf6f0b8663a.ed4809a5899418aa82832d9a0816fedc");
            
            FileBody bin = new FileBody(new File("C:\\Users\\kmonge\\eclipse-workspace\\Mavenprjct\\tmp\\2146999000014643367\\COMPLEMENTO DE PAGO.pdf"));
            //StringBody comment = new StringBody("name=\"file\"", ContentType.MULTIPART_FORM_DATA);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("file", bin)
                    //.addPart("comment", comment)
                    .build();


            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

}