package com.aspire.controller;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import play.Logger;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Http.Response;
import play.mvc.Result;
import play.mvc.With;

public class CorsComposition {

    /**
     * Wraps the annotated action in an <code>CorsAction</code>.
     */
    @With(CorsAction.class)
    @Target({ ElementType.TYPE, ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Cors {
        String value() default "http://192.168.1.13:9999";
    }

    public static class CorsAction extends Action<Cors> {
        public Promise<Result> call(Context context) throws Throwable{
          Logger.debug("CorsAction IN");
            Response response = context.response();
            response.setHeader("Access-Control-Allow-Origin",configuration.value());

            //Handle preflight requests
            //if(context.request().method().equals("OPTIONS")) {
                response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers", "X-AUTH-TOKEN, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, Authorization");
                response.setHeader("Access-Control-Allow-Credentials", "true");
              //  return delegate.call(context);
            //}

            //response.setHeader("Access-Control-Allow-Headers","X-Requested-With");
            return delegate.call(context);
        }
    }
}