package utilesWeb;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

public class UtilesWeb {
	
	private String IMGUR_POST_URI = "URL";
	private String IMGUR_API_KEY = "KEY";
	private String PATH = "/home/javier/images/";
	private UploadDropbox upload;
	
	public UtilesWeb() {
		upload = new UploadDropbox();
	}
	
	public String fileUpload(String fileName, String nombreCatastrofe, InputStream in){
		
		String retorno = null;
		File file = null;
		
		try {
			
			file = new File(PATH + fileName);
			
			OutputStream out = new FileOutputStream(file);
			
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = in.read(bytes)) != -1){
				out.write(bytes, 0, read);
			}
			
			in.close();
			out.flush();
			out.close();
			
			if(isImage(file)) {
				retorno = this.uploadImgUr(PATH + fileName);
			} else {
				retorno = upload.uploadDropbox(PATH + fileName);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retorno;
		
	}
	
	private String uploadImgUr(String path) throws Exception {

		URL url;
	    url = new URL(IMGUR_POST_URI);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    
        BufferedImage image = null;
	    File file = new File(path);
	    
	    image = ImageIO.read(file);
	    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
	    ImageIO.write(image, FilenameUtils.getExtension(path), byteArray);
	    byte[] byteImage = byteArray.toByteArray();
	    String dataImage = Base64.encodeBase64String(byteImage);

	    String data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(dataImage, "UTF-8");

	    conn.setDoOutput(true);
	    conn.setDoInput(true);
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Authorization", "Client-ID " + IMGUR_API_KEY);
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

	    conn.connect();
	    
	    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	    wr.write(data);
	    wr.flush();

	    // Get the response
	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    
	    wr.close();
	    
	    return this.getUrlFromResponse(rd);
	}
	
	private String getUrlFromResponse(BufferedReader rd){
		
		StringBuilder stb = new StringBuilder();
		
		String line;
		
	    try {
	    	
			while ((line = rd.readLine()) != null) {
			    stb.append(line).append("\n");
			}
			
		    rd.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return this.getUrl(stb);
	}
	
	private String getUrl(StringBuilder stb){
		
		String[] prueba = stb.toString().split(",");
		String urlRetorno = null;
		
	    int count = prueba.length;
	    for (int i = 0; i < count; i++){
	    	if(prueba[i].contains("link"))
	    		urlRetorno = prueba[i].replaceAll("\"link\":\"", "").replaceAll("\"}", "");
	    }
	    
	    return urlRetorno;
	    
	}
	
	private Boolean isImage (File file){
		String mimetype= new MimetypesFileTypeMap().getContentType(file);
        String type = mimetype.split("/")[0];
        if(type.equals("image")){
        	return true;
        } else {
        	return false;
        }
	}
	
}
