package com.rc.controlador;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;



/**
 * Servlet implementation class UploadFile
 */
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final String UPLOAD_DIRECTORY = "C:/uploads";
	private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 10; // 10MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 20; // 20MB
   
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	 public void init(ServletConfig config) throws ServletException {
	        super.init(config);
	      }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		System.out.println("llega upload servlet");
		if(isMultipart) {
			// Create a factory for disk-based file items
			System.out.println("Es multipart");
			// Set factory constraints
			//factory.setSizeThreshold(yourMaxMemorySize);
			//factory.setRepository(yourTempDirectory);
			String dir = System.getProperty("java.io.tmpdir");
			// Configure a repository (to ensure a secure temp location is used)
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(MEMORY_THRESHOLD);
			//factory.setSizeThreshold(51200);
			factory.setRepository(new File (dir));
			ServletFileUpload upload = new ServletFileUpload(factory);
			//ServletFileUpload upload = new ServletFileUpload();
			//ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			//upload.setFileSizeMax(MAX_FILE_SIZE);
	        //upload.setSizeMax(MAX_REQUEST_SIZE);
		 
			
			//String uploadPath = getServletContext().getRealPath("")  + File.separator + UPLOAD_DIRECTORY;
		    //File uploadDir = new File(uploadPath);
		    //if (!uploadDir.exists()) {
		      //  uploadDir.mkdir();
		    //}
			//ServletContext servletContext = this.getServletConfig().getServletContext();
			//File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			
			
			//System.out.println("*1*:" + uploadDir.getAbsolutePath());
			
			// Parse the request
			try {
				//List<FileItem> items = upload.parseRequest(request);
				FileItemIterator iter = upload.getItemIterator(request);
	               FileItemStream item = null;
	               InputStream stream = null;
	               System.out.println("*1* llega a try");
				//if(item != null && item.size() > 0) {
				// Process the uploaded items
				//Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
				    item = iter.next();
				    System.out.println("*2*:" + item.getFieldName());
				    stream = item.openStream();
				    if (!item.isFormField()) {
				    	String fieldName = item.getFieldName();
				    	
				    	String fileName = new File(item.getName()).getName();//String fileName = item.getName();
				        
				    	// String contentType = item.getContentType();
				        //boolean isInMemory = item.isInMemory();
				        //long sizeInBytes = item.getSize();
				        //String filePath = uploadPath + File.separator + fileName;
				        //File storeFile = new File(filePath);
				        //item.write(storeFile);
				    	 File file = new File(getServletContext().getRealPath("/" + fileName));
                         FileOutputStream fos = new FileOutputStream(file);
                         long fileSize = Streams.copy(stream, fos, true);
				    	System.out.println("File " + file.getAbsolutePath() + " has uploaded successfully!");
				    	
				    	HttpSession session = request.getSession();
				    	session.setAttribute("fullPath", file.getAbsolutePath());
				    	if(fieldName.contentEquals("nfile")) {
				    		//request.setAttribute("contractNm", request.getParameter("contractNm"));
				    		//request.set
				    		//request.getRequestDispatcher("NuevaConversacion.jsp?contractId="+request.getParameter("contractId")+"&contractNm="+request.getParameter("contractNm")).forward(request, response);
				    		//request.getRequestDispatcher("NuevaConversacion.jsp").forward(request, response);
				    		PrintWriter out = response.getWriter();
				    		out.print("El archivo no se adjuntó, intentalo nuevamente.");
				    	}
				    	//ArrayList<Conversacion> c = callCRM.getConversations(session .getAttribute("accountId").toString());
		    			//	request.getSession().setAttribute("contratos", c);
						//request.setAttribute("conver", c);
		    			//request.getRequestDispatcher("conversaciones.jsp").forward(request, response);
				    	PrintWriter out = response.getWriter();
				        //out.print("{\"status\":1}");
				    	out.print("Se adjunto el archivo.");
				    }
				        //
				        /*InputStream uploadedStream = item.getInputStream();
				        FileOutputStream out = new FileOutputStream(repository.getAbsolutePath() + "\\"+ fileName);
				        int len = 0;
						 byte[] buffer = new byte[4096];
						 while((len = uploadedStream.read(buffer)) != -1) {
							 out.write(buffer, 0, len);
						 }
						 System.out.println(repository.getAbsolutePath());
						 out.flush();
				        uploadedStream.close();
				        out.close();*/
				    
					//}
				}
			} 
			
			catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
