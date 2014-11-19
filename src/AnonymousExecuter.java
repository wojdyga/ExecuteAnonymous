import java.util.Scanner;
import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.BuildException;

import com.salesforce.ant.SFDCAntTask;
import com.sforce.soap.apex.ExecuteAnonymousResult;
import com.sforce.soap.apex.SoapConnection;
import com.sforce.soap.apex.LogInfo;
import com.sforce.soap.apex.LogCategoryLevel;
import com.sforce.soap.apex.LogCategory;
import com.sforce.soap.apex.LogType;
import com.sforce.ws.ConnectionException;

public class AnonymousExecuter extends SFDCAntTask {
	String code;
    public void setCode(String c) {
        code = c;
    }

    String fileName;
    public void setFileName(String fn) {
    	fileName = fn;
    }

    public void execute() {
    	try {
    		if (fileName != null && ! fileName.isEmpty()) {
		    	code = new Scanner(new File(fileName)).useDelimiter("\\A").next();
			}

    		SoapConnection connection = getApexConnection();

            LogInfo infoApex = new LogInfo();
            infoApex.setCategory(LogCategory.Apex_code);
            infoApex.setLevel(LogCategoryLevel.Finest);

            LogInfo infoDB = new LogInfo();
            infoDB.setCategory(LogCategory.Db);
            infoDB.setLevel(LogCategoryLevel.Finest);

            connection.setDebuggingHeader(new LogInfo[] { infoApex, infoDB }, LogType.Detail);

            ExecuteAnonymousResult result = connection.executeAnonymous(code);
            if (result.isSuccess()) {
                log(connection.getDebuggingInfo().getDebugLog());
                log("Success");
            } else {
                throw new BuildException(result.getExceptionMessage());
            }
        } catch (ConnectionException e) {
            log("Note: use ant -verbose to get more information on the failure");
            throw new BuildException("Failed: " + e.getMessage(), e);
		} catch (IOException ioe) {
			log(ioe.getMessage());
		}
    }
}
