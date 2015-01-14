package com.schlik.pubmate;


import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.schlik.pubmate.dao;

public class PubRemoveServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public void doGet(HttpServletRequest req, HttpServletResponse resp)
  throws IOException {
    String name  = req.getParameter("name");
    dao.INSTANCE.remove(name);
    resp.sendRedirect("/TodoApplication.jsp");
  }
} 
