package core;

/**
 * Created by Kamil on 29.12.2016.
 */
public class DatabaseConnectionProperties
{
    private String serverName;

    private String databaseName;
    private String userName;
    private char[] password;
    private boolean useSSL;
    private boolean verifyServerCertificate;
    private boolean autoReconnect;

    public DatabaseConnectionProperties(String serverName, String databaseName, String userName, char[] password, boolean useSSL, boolean verifyServerCertificate, boolean autoReconnect)
    {
        this.serverName = serverName;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
        this.useSSL = useSSL;
        this.verifyServerCertificate = verifyServerCertificate;
        this.autoReconnect = autoReconnect;
    }

    public String getServerName()
    {
        return serverName;
    }

    public String getDatabaseName()
    {
        return databaseName;
    }

    public String getUserName()
    {
        return userName;
    }

    public char[] getPassword()
    {
        return password;
    }

    public boolean isUsingSSL()
    {
        return useSSL;
    }

    public boolean shouldVerifyServerCertificate()
    {
        return verifyServerCertificate;
    }

    public boolean shouldAutoReconnect()
    {
        return autoReconnect;
    }
}
