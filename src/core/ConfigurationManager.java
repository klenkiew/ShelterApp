package core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kamil on 29.12.2016.
 */
public class ConfigurationManager
{
    private final String configFilePath = "config.conf";
    private final String[] configurableProperties = new String[]
    {
        "serverName",
        "databaseName",
        "userName",
        "password"
    };
    boolean useSSL = true;
    boolean verifyServerCertificate = false;
    boolean autoReconnect = true;

    public DatabaseConnectionProperties readDatabaseConnectionProperties() throws IOException, ParseException
    {
        String regexPattern = "";
        for (int i = 0; i < configurableProperties.length; i++)
        {
            String property = configurableProperties[i];
            regexPattern += property + ":[\\s\\n\\r]*\"(\\w+)\"[\\s\\n\\r]*";
            if (i != configurableProperties.length - 1)
                regexPattern += ",[\\s\\n\\r]*";
        }
        Pattern pattern = Pattern.compile(regexPattern);
        Path filePath = Paths.get(configFilePath);
        String config = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
        Matcher matcher = pattern.matcher(config);
        boolean doMatches = matcher.matches();
        int groupCount = matcher.groupCount();
        if (!doMatches || groupCount != configurableProperties.length)
            throw new ParseException("Cannot parse config file", 0);
        String serverName = matcher.group(1);
        String databaseName = matcher.group(2);
        String userName = matcher.group(3);
        char[] password = matcher.group(4).toCharArray();
        DatabaseConnectionProperties databaseProperties = new DatabaseConnectionProperties(
                serverName, databaseName, userName, password, useSSL, verifyServerCertificate, autoReconnect
        );
        return databaseProperties;
    }
}
