package utilesWeb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

/**	
 * En caso de que se caiga nuevamente el token, lo cual espero que no 'o_o
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.entity.InputStreamEntity;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;

public class UploadDropbox {
	
	private final static String APP_KEY = "KEY";
    private final static String APP_SECRET = "SECRET";
    public static String sharedUrl;
    
    public UploadDropbox() {
		sharedUrl = null;
	}
	
    //public static void main(String[] args) throws IOException, DbxException {
	public String uploadDropbox(String path) throws IOException, DbxException {
        // Get your app key and secret from the Dropbox developers website.

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig("catastrophes-system",
            Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        
        /*
         * Obtenemos el token en caso de que se pierda.
         */
        // Have the user sign in and authorize your app.
        String authorizeUrl = webAuth.start();
        System.out.println("1. Go to: " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
        
        // This will fail if the user enters an invalid authorization code.
        DbxAuthFinish authFinish = webAuth.finish(code);
        String accessToken = authFinish.accessToken;

        DbxClient client = new DbxClient(config, accessToken);        

        System.out.println("Linked account: " + client.getAccountInfo().displayName);

        File inputFile = new File("/home/javier/Descargas/Requerimientos.pdf");
        FileInputStream inputStream = new FileInputStream(inputFile);
        try {
            DbxEntry.File uploadedFile = client.uploadFile("/magnum-opus.pdf",
                DbxWriteMode.add(), inputFile.length(), inputStream);
            sharedUrl = client.createShareableUrl("/magnum-opus.pdf");
            System.out.println("Uploaded: " + uploadedFile.toString() + " URL: " + sharedUrl );
        } finally {
            inputStream.close();
        }
        
        return sharedUrl;
    }
    
}