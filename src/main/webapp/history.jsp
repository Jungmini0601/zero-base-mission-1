<%@ page import="com.zerobase.kimjungmin.repository.HistoryRepository" %>
<%@ page import="com.zerobase.kimjungmin.domain.History" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        #customers {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        #customers td, #customers th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #customers tr:nth-child(even){background-color: #f2f2f2;}

        #customers tr:hover {background-color: #ddd;}

        #customers th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #04AA6D;
            color: white;
        }
    </style>
</head>
<body>
<h1>와이파이 정보 구하기</h1>
<a href="index.jsp">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="load-wifi.jsp"> Open API 와이파이 정보 가져오기</a>
<br/>
<%
    HistoryRepository historyRepository = new HistoryRepository();
    List<History> historyList = historyRepository.selectHistory();
%>

<table id="customers">
    <tr>
        <th>ID</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>조회일자</th>
        <th>비고</th>
    </tr>
        <%
                if (historyList.size() != 0) {
                    for (History history : historyList) { %>
    <tr>
        <td><%=history.getID()%></td>
        <td><%=history.getLAT()%></td>
        <td><%=history.getLNT()%></td>
        <td><%=history.getHISTORY_AT()%></td>
        <td><a><button>삭제</button></a></td>
    </tr>
        <%
                    }
                }
            %>

</body>
</html>
