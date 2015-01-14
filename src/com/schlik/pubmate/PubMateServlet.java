package com.schlik.pubmate;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.schlik.pubmate.dao;

@SuppressWarnings("serial")
public class PubMateServlet extends HttpServlet {
 
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    System.out.println("Creating new pub ");


    String name = checkNull(req.getParameter("name"));

    dao.INSTANCE.add(name);

    resp.sendRedirect("/TodoApplication.jsp");
  }

  private String checkNull(String s) {
    if (s == null) {
      return "";
    }
    return s;
  }
} 