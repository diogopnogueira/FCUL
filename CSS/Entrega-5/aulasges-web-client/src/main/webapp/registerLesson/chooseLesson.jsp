<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-15"
	pageEncoding="ISO-8859-15"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="model"
	class="presentation.web.model.RegisterLessonModel" scope="request" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css">

</head>
<body>
	<c:choose>
		<c:when test="${empty model.cost && !empty model.activeLessons}">
			<h2>Inscrever em Aula</h2>

			<form action="finalizarInscricao" method="post">
				<div class="mandatory_field">
					<label for="userId">Número de utilizador:</label> <input
						type="text" name="userId" value="${model.userId}" />
				</div>


				<label for="activeLessons">Aulas ativas:</label>

				<ul>
					<c:forEach var="lesson" items="${model.activeLessons}">
						<li>${lesson}</li>
					</c:forEach>
				</ul>

				<div class="mandatory_field">
					<label for="lessonName">Escolha a aula:</label> <select
						name="lessonName">
						<c:forEach var="lesson" items="${model.activeLessons}">
							<c:choose>
								<c:when test="${model.lessonName == lesson.name}">
									<option selected="selected" value="${lesson.name}">${lesson.name}</option>
								</c:when>
								<c:otherwise>
									<option value="${lesson.name}">${lesson.name}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>

				<div class="button" align="right">
					<input type="submit" value="Inscrever em Aula">
				</div>
			</form>
		</c:when>

		<c:when test="${!empty model.cost}">
			<h2>Inscrição concluida com sucesso</h2>
		Custo: ${model.cost} ¤
		<a href="<c:url value="/index.html" />">Menu Inicial</a>
		</c:when>
		<c:otherwise>
    De momento não há aulas ativas!
    <a href="<c:url value="/index.html" />">Menu Inicial</a>
		</c:otherwise>
	</c:choose>
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