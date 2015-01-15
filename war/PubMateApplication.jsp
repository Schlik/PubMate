<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="com.schlik.pubmate.PubModel"%>
<%@ page import="com.schlik.pubmate.dao"%>

<!DOCTYPE html>


<%@page import="java.util.ArrayList"%>

<html>
<head>
<title>Todos</title>
<link rel="stylesheet" type="text/css" href="css/main.css" />
<meta charset="utf-8">
</head>
<body>
	<%
dao my_dao = dao.INSTANCE;


List<PubModel> pubs = new ArrayList<PubModel>();          
pubs = my_dao.listPubs();

    
%>
	<div style="width: 100%;">
		<div class="line"></div>
		<div class="topLine">
			<div style="float: left;">
				<img src="images/todo.png" />
			</div>
			<div style="float: left;" class="headline">Pubs</div>
			<div style="float: right;"></div>
		</div>
	</div>

	<div style="clear: both;"></div>
	You have a total number of
	<%= pubs.size() %>
	Todos.

	<table>
		<tr>
			<th>Short description</th>
			<th>Long Description</th>
			<th>URL</th>
			<th>Done</th>
		</tr>

		<% for (PubModel pub : pubs) {%>
		<tr>
			<td><%=pub.getName()%></td>
		</tr>
		<%}
%>
	</table>


	<hr />

	<div class="main">

		<div class="headline">New Pub</div>

		<form action="/new" method="post" accept-charset="utf-8">
			<table>
				<tr>
					<td><label for="summary">Name</label></td>
					<td><input type="text" name="name" id="name" size="65" /></td>
				</tr>
				<tr>
					<td valign="description"><label for="description">Description</label></td>
					<td><textarea rows="4" cols="50" name="description"
							id="description"></textarea></td>
				</tr>
				<tr>
					<td valign="top"><label for="url">URL</label></td>
					<td><input type="url" name="url" id="url" size="65" /></td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input type="submit"
						value="Create" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
