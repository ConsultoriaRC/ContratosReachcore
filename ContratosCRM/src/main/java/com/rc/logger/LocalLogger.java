package com.rc.logger;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import com.rc.model.crm.Attachments;

public class LocalLogger {
	LocalLogger(){
		
	}
	public static void writeIntoLog(String message, String path) {
		System.out.println("Paso 1 " );
		try {
			//System.out.println("Paso 2 " );
			Properties properties = new Properties();
			properties.load(Attachments.class.getResourceAsStream("/pom.properties"));
			//System.out.println("Paso 3 " );
			String dateTime =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
			
			//System.out.println("Paso 4 " );
			FileWriter fileWriter = new FileWriter(properties.getProperty("logLocation"));
			//System.out.println("Paso 5 " );
			fileWriter.write(dateTime +"|Ubicación|"+path+"|Message|"+message);
			//System.out.println("Write log: " + fileWriter.getEncoding());
			fileWriter.close();
		}
		catch(IOException e){
			e.getStackTrace();
		}
	}

}
