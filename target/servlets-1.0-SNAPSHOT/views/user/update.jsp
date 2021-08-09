<%--
  Created by IntelliJ IDEA.
  User: CDS
  Date: 09/07/2021
  Time: 09:24 a. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<html>
<head>
  <title>Modificar Juego</title>
  <link rel="stylesheet" href="${context}/assets/plugins/bootstrap/css/bootstrap.min.css">
  <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
</head>
<body>
<h1>Modificar juego</h1>
<form action="${context}/updateUser" method="POST">
  <input type="hidden" value="update" name="action">
  <input type="hidden" value="${ user.id }" name="id">
  <label>Nombre</label>
  <input class="form-control" type="text" name="nombre" value="${ user.nombre}" />
  <br>
  <label>Descripción</label>
  <input class="form-control" type="text" name="descripcion" value="${ user.descripcion}" />
  <br>
  <label>Fecha</label>
  <input class="form-control" type="text" name="fecha" value="${ user.fecha }" />
  <br>
  <select class="form-select" name="estado">
    <option value="1">Activo</option>
    <option value="0">Inactivo</option>
  </select>
  <br>
  <button type="button" class="btn btn-secondary"><i class="fas fa-times"></i> Cancelar</button>
  <button type="submit" class="btn btn-primary"><i class="fas fa-edit"></i> Modificar</button>
</form>

<script src="${context}/assets/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
</body>
</html>

