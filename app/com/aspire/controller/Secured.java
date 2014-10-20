package com.aspire.controller;

import play.libs.Json;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

import com.aspire.model.User;
import com.aspire.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by saeed on 30/June/14 AD.
 */
@CorsComposition.Cors
public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        User user = null;
        String[] authTokenHeaderValues = ctx.request().headers().get(SecurityController.AUTH_TOKEN_HEADER);
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
            user =  UserService.findByAuthToken(authTokenHeaderValues[0]);
            if (user != null) {
                ctx.args.put("user", user);
                return user.getEmailAddress();
            }
        }

        return null;
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        //401 - 
        ObjectNode result = Json.newObject();
        result.put("result", "fail");
        result.put("message", "Please login before use this service!!!");
        return status(401, result);
    }
}