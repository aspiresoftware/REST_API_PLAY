package com.aspire.service;

import java.util.List;

import play.db.ebean.Model.Finder;

import com.aspire.model.Todo;
import com.aspire.model.User;


public class TodoService {

    public static List<Todo> findByUser(User user) {
        Finder<Long, Todo> finder = new Finder<Long, Todo>(Long.class, Todo.class);
        return finder.where().eq("user", user).findList();
    }
}
