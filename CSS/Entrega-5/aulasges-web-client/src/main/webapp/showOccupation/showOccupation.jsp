<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="model"
	class="presentation.web.model.ShowOccupationModel" scope="request" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css">
<title>AulaGes: Visualizar Ocupação</title>
</head>
<body>
	<h2>Visualizar Ocupação</h2>
	<form action="ocupacao" method="get">
		<div class="mandatory_field">
			<label for="facilityName">Nome da instalação:</label> <input
				type="text" name="facilityName" value="${model.facilityName}" />
		</div>

		<%--Serve para o formato da 
data <p>Formatted Date (7): <fmt:formatDate pattern = "dd-MM-yyyy" value = "${now}" /></p>  --%>
		<div class="mandatory_field">
			<label for="day">Dia:</label> <input type="text" name="day"
				value="${model.day}" />
		</div>
		<div class="mandatory_field">
			<label for="month">Mês:</label> <input type="text" name="month"
				value="${model.month}" />
		</div>
		<div class="mandatory_field">
			<label for="year">Ano:</label> <input type="text" name="year"
				value="${model.year}" />
		</div>

		<div class="button" align="right">
			<input type="submit" value="Ver ocupacao">
		</div>
	</form>

	<c:if test="${!empty model.occupations}">
		<label for="occupations">Ocupação no dia ${model.day}/${model.month}/${model.year}:</label>
		<ul>
			<c:forEach var="occupation" items="${model.occupations}">
				<li>${occupation}</li>
			</c:forEach>
		</ul>
	</c:if>


	<c:if test="${model.hasMessages}">
		<p>Mensagens</p>
		<ul>
			<c:forEach var="mensagem" items="${model.messages}">
				<li>${mensagem}</li>
			</c:forEach>
		</ul>
	</c:if>
</body>
</html>