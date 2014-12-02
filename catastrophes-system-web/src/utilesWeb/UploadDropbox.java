package utilesWeb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

/**	
 * En caso de que se caiga nuevamente el token, lo cual espero que no 'o_o
 */
/*
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.entity.InputStreamEntity;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxWebAuthNoRedirect;
*/
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWriteMode;

public class UploadDropbox {
	
	//private final static String APP_KEY = "KEY";
	//private final static String APP_SECRET = "SECRET";
    public static String sharedUrl;
    private final static String terminacion = "plan_de_emergencia";
    
    public UploadDropbox() {
		sharedUrl = null;
	}
	
    //public static void main(String[] args) throws IOException, DbxException {
	public String uploadDropbox(String path) throws IOException, DbxException {
        // Get your app key and secret from the Dropbox developers website.

        DbxRequestConfig config = new DbxRequestConfig("catastrophes-system",
            Locale.getDefault().toString());
        
        /*
         * Obtenemos el token en caso de que se pierda.
         */
        /*
         * 
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        
        // Have the user sign in and authorize your app.
        String authorizeUrl = webAuth.start();
        System.out.println("1. Go to: " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
        
        // This will fail if the user enters an invalid authorization code.
        DbxAuthFinish authFinish = webAuth.finish("y_CxxWVFQGEAAAAAAAAAbM5ySyKv_69juSBN4WiKdcI");
        */
        
        String accessToken = "y_CxxWVFQGEAAAAAAAAAbUt4hGKA7JWSFyl_aFG6DVsq8ADnVWTu2x-EofasFxX0";
        
        //System.out.println("TOKEN: " + accessToken);
        DbxClient client = new DbxClient(config, accessToken);        

        System.out.println("Linked account: " + client.getAccountInfo().displayName);

        File inputFile = new File(path);
        FileInputStream inputStream = new FileInputStream(inputFile);
        try {
            DbxEntry.File uploadedFile = client.uploadFile("/" + terminacion + ".pdf",
                DbxWriteMode.add(), inputFile.length(), inputStream);
            sharedUrl = client.createShareableUrl("/"+uploadedFile.name);
            System.out.println("Uploaded: " + uploadedFile.name + " URL: " + sharedUrl );
        } finally {
            inputStream.close();
        }
        
        return sharedUrl;
    }
    
}