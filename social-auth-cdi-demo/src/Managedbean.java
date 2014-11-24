@Named(value = "userSession")
@SessionScoped
public class UserSessionBean implements Serializable {
    private SocialAuthManager manager;
    private String            originalURL;
    private String            providerID;
    private Profile           profile;
    
    public UserSessionBean() { ... }
    
    public void socialConnect() throws Exception {
        // Put your keys and secrets from the providers here 
        Properties props = System.getProperties();
        props.put("graph.facebook.com.consumer_key", FACEBOOK_APP_ID);
        props.put("graph.facebook.com.consumer_secret", FACEBOOK_APP_SECRET);
        // Define your custom permission if needed
        props.put("graph.facebook.com.custom_permissions", "publish_stream,email,user_birthday,user_location,offline_access");
        
        // Initiate required components
        SocialAuthConfig config = SocialAuthConfig.getDefault();
        config.load(props);
        manager = new SocialAuthManager();
        manager.setSocialAuthConfig(config);
        
        // 'successURL' is the page you'll be redirected to on successful login
        ExternalContext externalContext   = FacesContext.getCurrentInstance().getExternalContext();
        String          successURL        = externalContext.getRequestContextPath() + "socialLoginSuccess.xhtml"; 
        String          authenticationURL = manager.getAuthenticationUrl(providerID, successURL);
        FacesContext.getCurrentInstance().getExternalContext().redirect(authenticationURL);
    }
    
    public void pullUserInfo() {
        try {
            // Pull user's data from the provider
            ExternalContext    externalContext = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletRequest request         = (HttpServletRequest) externalContext.getRequest();
            Map                map             = SocialAuthUtil.getRequestParametersMap(request);
            if (this.manager != null) {
                AuthProvider provider = manager.connect(map);
                this.profile          = provider.getUserProfile();
            
                // Do what you want with the data (e.g. persist to the database, etc.)
                System.out.println("User's Social profile: " + profile);
            
                // Redirect the user back to where they have been before logging in
                FacesContext.getCurrentInstance().getExternalContext().redirect(originalURL);
            
            } else FacesContext.getCurrentInstance().getExternalContext().redirect(externalContext.getRequestContextPath() + "home.xhtml");
        } catch (Exception ex) {
            System.out.println("UserSession - Exception: " + ex.toString());
        } 
    }
    
    public void logOut() {
        try {
            // Disconnect from the provider
            manager.disconnectProvider(providerID);
            
            // Invalidate session
            ExternalContext    externalContext = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletRequest request         = (HttpServletRequest) externalContext.getRequest();
            this.invalidateSession(request);
            
            // Redirect to home page
            FacesContext.getCurrentInstance().getExternalContext().redirect(externalContext.getRequestContextPath() + "home.xhtml";);
        } catch (IOException ex) {
            System.out.println("UserSessionBean - IOException: " + ex.toString());
        }
    }
    
    // Getters and Setters
}
view rawManaged bean.java hosted with ❤ by GitHub

Based on this sample for the oauth_consumer.properties file, you should be able to figure out the properties to put in the props easily. In fact, instead of using Java's Properties, there are also several other ways to load the configuration. The complete list includes:

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
/**
 * Loads the application configuration from the given input stream Format of
 * the input stream should be as follows: <br/>
 * www.google.com.consumer_key = opensource.brickred.com
 * 
 * @param inputStream
 *            property file input stream which contains the configuration.
 * @throws Exception
 */
public void load(final InputStream inputStream) throws Exception { ... }
 
/**
 * Loads the application configuration from the given file
 * 
 * @param fileName
 *            the file name which contains the application configuration
 *            properties
 * @throws Exception
 */
public void load(final String fileName) throws Exception { ... }
 
/**
 * Loads the application properties from oauth_consumer.properties file.
 * 
 * @throws Exception
 */
public void load() throws Exception { ... }
 
/**
 * Loads the application configuration from the given properties
 * 
 * @param properties
 *            application configuration properties
 * @throws Exception
 */
public void load(final Properties properties) throws Exception { ... }
- See more at: http://mrj4mes.blogspot.com/2013/03/how-to-implement-facebook-login-in-jsf.html#sthash.ONNsAsou.dpuf