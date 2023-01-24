<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="model"
	class="presentation.web.model.RegisterLessonModel" scope="request" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css">
<title>AulaGes: inscrever em aula</title>
</head>
<body>
	<h2>Inscrever em Aula</h2>
	<form action="confirmarDados" method="get">

		<div class="mandatory_field">
			<label for="sportModalityName">Escolha a modalidade
				desportiva:</label> <select name="sportModalityName">
				<c:forEach var="sportModality" items="${model.sportModalities}">
					<c:choose>
						<c:when test="${model.sportModalityName == sportModality.name}">
							<option selected="selected" value="${sportModality.name}">${sportModality.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${sportModality.name}">${sportModality.name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>

		<div class="mandatory_field">
			<label for="registrationType">Tipo de inscrição:</label> <select
				name="registrationType">
				<option value="Avulso">Avulso</option>
				<option value="Regular">Regular</option>
			</select>
		</div>

		<div class="button" align="right">
			<input type="submit" value="Confirmar Dados">
		</div>
	</form>
	<c:if test="${model.hasMessages}">
		<p>Mensagens</p>
		<ul>
			<c:forEach var="mensagem" items="${model.messages}">
				<li>${mensagem}
			</c:forEach>
		</ul>
	</c:if>
</body>
</html>