<%@ page import="com.zerobase.kimjungmin.repository.WifiRepository" %>
<%@ page import="java.util.List" %>
<%@ page import="com.zerobase.kimjungmin.domain.WifiInfo" %>
<%@ page import="com.zerobase.kimjungmin.repository.HistoryRepository" %>
<%@ page import="com.zerobase.kimjungmin.domain.History" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
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

    <%
        double lat = 0.0;
        double lnt = 0.0;

        try {
            lat = Double.parseDouble(request.getParameter("lat"));
            lnt = Double.parseDouble(request.getParameter("lnt"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<WifiInfo> wifiInfos = null;
        WifiRepository wifiRepository = new WifiRepository();
        HistoryRepository historyRepository = new HistoryRepository();
        if (lat > 0 && lnt > 0) {
            wifiInfos = wifiRepository.getWifiListOrderByDistance(lat, lnt);
            History history = new History();
            history.setLAT(lat);
            history.setLNT(lnt);
            history.setHISTORY_AT(LocalDateTime.now().toString());
            historyRepository.insertHistory(history);
        }
    %>

    <h1>와이파이 정보 구하기</h1>
    <a href="index.jsp">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="load-wifi.jsp"> Open API 와이파이 정보 가져오기</a>
    <form method="get" action="index.jsp">
        LAT: <input type="text" name="lat"/> ,
        LNT: <input type="text" name="lnt"/>
        <button>내 위치 가져오기</button>
        <input type="submit" value="근처 WIFI 정보 보기" />
    </form>

    <br/>
    <table id="customers">
        <tr>
            <th>거리(Km)</th>
            <th>관리번호</th>
            <th>자치구</th>
            <th>와이파이명</th>
            <th>도로명주소</th>
            <th>상세주소</th>
            <th>설치위치(층)</th>
            <th>설치유형</th>
            <th>설치기관</th>
            <th>서비스구분</th>
            <th>망종류</th>
            <th>설치년도</th>
            <th>실내외구분</th>
            <th>WIFI접속환경</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>작업일자</th>
        </tr>
            <%
                if (wifiInfos != null) {
                    for (WifiInfo wifiInfo : wifiInfos) { %>
                            <tr>
                                <td><%=Math.round( wifiInfo.getDistance() * 10000 ) / 10000.0%></td>
                                <td><%=wifiInfo.getX_SWIFI_MGR_NO()%></td>
                                <td><%=wifiInfo.getX_SWIFI_WRDOFC()%></td>
                                <td><%=wifiInfo.getX_SWIFI_MAIN_NM()%></td>
                                <td><%=wifiInfo.getX_SWIFI_ADRES1()%></td>
                                <td><%=wifiInfo.getX_SWIFI_ADRES2()%></td>
                                <td><%=wifiInfo.getX_SWIFI_INSTL_FLOOR()%></td>
                                <td><%=wifiInfo.getX_SWIFI_INSTL_TY()%></td>
                                <td><%=wifiInfo.getX_SWIFI_INSTL_MBY()%></td>
                                <td><%=wifiInfo.getX_SWIFI_SVC_SE()%></td>
                                <td><%=wifiInfo.getX_SWIFI_CMCWR()%></td>
                                <td><%=wifiInfo.getX_SWIFI_CNSTC_YEAR()%></td>
                                <td><%=wifiInfo.getX_SWIFI_INOUT_DOOR()%></td>
                                <td><%=wifiInfo.getX_SWIFI_REMARS3()%></td>
                                <td><%=wifiInfo.getLAT()%></td>
                                <td><%=wifiInfo.getLNT()%></td>
                                <td><%=wifiInfo.getWORK_DTTM()%></td>
                            </tr>
            <%
                    }
                }
            %>
    </table>

</body>
</html>