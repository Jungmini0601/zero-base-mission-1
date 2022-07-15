<%@ page import="com.zerobase.kimjungmin.repository.WifiRepository" %>
<%@ page import="com.zerobase.kimjungmin.http.HttpClient" %>
<%@ page import="com.zerobase.kimjungmin.domain.WifiInfo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        h1{
            text-align: center;
        }
        div {
            text-align: center;
        }
    </style>
</head>
<body>
    <%
        WifiRepository wifiRepository = new WifiRepository();
        HttpClient httpClient = new HttpClient();
        List<WifiInfo> allWifiInfo = null;

        try {
            allWifiInfo = httpClient.getAllWifiInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (allWifiInfo != null) {
            for (WifiInfo wifiInfo : allWifiInfo) {
                wifiRepository.insertWifiInfo(wifiInfo);
            }
        }

    %>

    <h1><%=allWifiInfo != null ? allWifiInfo.size() : 0%>개의 WIFI 정보를 정상적으로 저장 하였습니다</h1>
    <div>
        <a href="index.jsp">홈으로 가기</a>
    </div>
</body>
</html>
