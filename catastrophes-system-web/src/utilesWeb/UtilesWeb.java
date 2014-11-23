package utilesWeb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;

import com.sun.faces.facelets.util.Resource;

public class UtilesWeb {
	
	public UtilesWeb() {
		
	}
	
	public String fileUpload(String fileName, InputStream in){
		
		String path = "/usr/local/share/jboss/prueba/"; 
		
		try {
			
			OutputStream out = new FileOutputStream(new File(path + fileName));
			
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = in.read(bytes)) != -1){
				out.write(bytes, 0, read);
			}
			
			in.close();
			out.flush();
			out.close();
			
			return path + fileName;
			
		} catch (FileNotFoundException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		}
	}
	
}
