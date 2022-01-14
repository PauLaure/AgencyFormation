<%@ page import="it.unisa.agency_formation.autenticazione.domain.RuoliUtenti" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/Common.css">
    <link rel="icon" type="image/png" href="img/Logo%20Team%204-5.png"/>
    <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="js/Formazione.js"></script>

    <title>Home</title>
</head>
<body>
<jsp:include page="/static/Header.jsp">
    <jsp:param value="false" name="sameLocation"/>
</jsp:include>
<h1>Bentornato ${user.getName()}</h1>
<div class="content">
    <div class="flex">
        <div id="home"><a href="ProfiloControl">
            <h2> Profilo </h2></a>
            <p>Accedi al tuo profilo personale per gestire le tue skill e i tuoi dati personali
                e visualizzare il proprio stato</p>
        </div>
        <div name="materialeDaNascondere" id="home">
            <h2 id="materiale" onclick="view()" onmouseover="viewLink()" onmouseleave="deleteSpanMateriale()"> Materiale
                di formazione </h2>
            <div id="drop" class="dropdown-content" style="display:none">
                <a href="DownloadMaterialeControl" id="hrefDocumenti">
                    <img src="img/Materiale.png">
                    <p>Materiale</p>
                </a>
            </div>
            <span id="noMateriale"></span>
        </div>
    </div>
</div>
</body>
</html>