import static play.mvc.Results.notFound;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;

import com.aspire.model.User;
import com.aspire.util.DemoData;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application application) {
        // load the demo data in dev mode
        if (Play.isDev() && (User.find.all().size() == 0)) {
            DemoData.loadDemoData();
        }

        super.onStart(application);
    }
    
    /*
     * (non-Javadoc) This method will be called when request url not found
     * 
     * @see play.GlobalSettings#onHandlerNotFound(play.mvc.Http.RequestHeader)
     */
    public Promise<Result> onHandlerNotFound(RequestHeader request) {
      Logger.debug("Executing method : onHandlerNotFound()");
      ObjectNode result = Json.newObject();
      result.put("result", "fail");
      result.put("message", "Method not defined");
      return Promise.<Result>pure(notFound(result));
    }
}
