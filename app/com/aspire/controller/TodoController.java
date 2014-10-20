package com.aspire.controller;

import java.util.ArrayList;
import java.util.List;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.aspire.service.TodoService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CorsComposition.Cors
@Security.Authenticated(Secured.class)
public class TodoController extends Controller {

    public static Result getAllTodos() {
        Logger.debug("getAllTodos");
        List<com.aspire.model.Todo> todos = TodoService.findByUser(SecurityController.getUser());
        ObjectNode result = Json.newObject();
        if(todos != null){
          result.put("result", "success");
          List<com.aspire.dto.Todo> todoDtos = new ArrayList<com.aspire.dto.Todo>();
          for(com.aspire.model.Todo todo:todos){
            com.aspire.dto.Todo todoDto = new com.aspire.dto.Todo();
            todoDto.id = todo.id;
            todoDto.task = todo.value;
            todoDtos.add(todoDto);
          }
          result.put("todos", Json.toJson(todoDtos));
        }
        return ok(result);
    }
    
}
