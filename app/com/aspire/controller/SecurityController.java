package com.aspire.controller;

import java.io.IOException;

import play.Logger;
import play.data.validation.Constraints;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import com.aspire.model.User;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author Pratik Patel
 *
 */
@CorsComposition.Cors
public class SecurityController extends Controller {

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String AUTH_TOKEN = "authToken";


    public static com.aspire.model.User getUser() {
        return (User)Http.Context.current().args.get("user");
    }
    
    public static Result preflight(String path) {
      Logger.debug("Executing method : checkPreFlight()");
      return ok();
    }

    // returns an authToken
    /**
     * @return
     * @throws IOException
     */
    public static Result login() throws IOException {
      Logger.debug("IN Login");
      String auth = request().getHeader("authorization");
      String[] authComp = auth.split(" ");
      String basic = new String(new sun.misc.BASE64Decoder().decodeBuffer(authComp[1]));
      String[] credentials = basic.split(":");
      User user = User.findByEmailAddressAndPassword(credentials[0], credentials[1]);
      
        if (user == null) {
            return unauthorized();
        }
        else {
            String authToken = user.createToken();
            com.aspire.dto.User userDto = new com.aspire.dto.User();
            com.aspire.dto.Details detailsDto = new com.aspire.dto.Details();
            userDto.token = authToken;
            detailsDto.username = user.getEmailAddress();
            detailsDto.firstname = user.fullName;
            detailsDto.lastname = user.fullName;
            detailsDto.displayname = user.fullName;
            detailsDto.roles = new String[]{"admin"};
            detailsDto.profileImage = "N/A";
            userDto.details = detailsDto;
            response().setCookie(AUTH_TOKEN, authToken);
            ObjectNode result = Json.newObject();
            result.put("result", "success");
            result.put("user", Json.toJson(userDto));
            Logger.debug(""+Json.toJson(result));
            response().setHeader("Access-Control-Allow-Credentials", "true");
            return ok(result);
        }
    }

    /**
     * @return
     */
    @Security.Authenticated(Secured.class)
    public static Result logout() {
        response().discardCookie(AUTH_TOKEN);
        getUser().deleteAuthToken();
        ObjectNode result = Json.newObject();
        result.put("result", "success");
        result.put("message", "logout successfully!!!");
        response().setHeader("Access-Control-Allow-Credentials", "true");
        return ok(result);
    }

    /**
     * @author Pratik Patel
     *
     */
    public static class Login {

        @Constraints.Required
        @Constraints.Email
        public String emailAddress;

        @Constraints.Required
        public String password;

    }


}
