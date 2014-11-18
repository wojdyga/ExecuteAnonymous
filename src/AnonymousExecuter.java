import java.util.Scanner;
import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.BuildException;

import com.salesforce.ant.SFDCAntTask;
import com.sforce.soap.apex.ExecuteAnonymousResult;
import com.sforce.soap.apex.SoapConnection;
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
			System.out.println(code);

    		SoapConnection connection = getApexConnection();
            ExecuteAnonymousResult result = connection.executeAnonymous(code);
            if (result.isSuccess()) {
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
