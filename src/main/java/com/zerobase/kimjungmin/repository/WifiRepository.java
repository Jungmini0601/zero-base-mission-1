package com.zerobase.kimjungmin.repository;

import com.zerobase.kimjungmin.domain.WifiInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiRepository extends SuperRepository {
    public void insertWifiInfo(WifiInfo wifiInfo) {
        final String sql = "INSERT INTO wifiInfo " +
                "VALUES (" +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?)";
        createConnection();
        PreparedStatement preparedStatement = createPreparedStatement(sql);

        try {
            preparedStatement.setString(1, wifiInfo.getX_SWIFI_MGR_NO());
            preparedStatement.setString(2, wifiInfo.getX_SWIFI_WRDOFC());
            preparedStatement.setString(3, wifiInfo.getX_SWIFI_MAIN_NM());
            preparedStatement.setString(4, wifiInfo.getX_SWIFI_ADRES1());
            preparedStatement.setString(5, wifiInfo.getX_SWIFI_ADRES2());
            preparedStatement.setString(6, wifiInfo.getX_SWIFI_INSTL_FLOOR());
            preparedStatement.setString(7, wifiInfo.getX_SWIFI_INSTL_TY());
            preparedStatement.setString(8, wifiInfo.getX_SWIFI_INSTL_MBY());
            preparedStatement.setString(9, wifiInfo.getX_SWIFI_SVC_SE());
            preparedStatement.setString(10, wifiInfo.getX_SWIFI_CMCWR());
            preparedStatement.setString(11, wifiInfo.getX_SWIFI_CNSTC_YEAR());
            preparedStatement.setString(12, wifiInfo.getX_SWIFI_INOUT_DOOR());
            preparedStatement.setString(13, wifiInfo.getX_SWIFI_REMARS3());
            preparedStatement.setDouble(14, wifiInfo.getLNT());
            preparedStatement.setDouble(15, wifiInfo.getLAT());
            preparedStatement.setString(16, wifiInfo.getWORK_DTTM());

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            System.out.println("insertWifiInfo 실패!");
            e.printStackTrace();
        }finally {
            closePreparedStatement();
            closeConnection();
        }
    }

    public List<WifiInfo> getWifiListOrderByDistance(double latitude, double longitude) {
        String sql = "SELECT * " + ", " + buildDistanceQuery(latitude, longitude)
                + " FROM wifiInfo"
                + " ORDER BY partial_distance ASC"
                + " LIMIT 20";
        System.out.println(sql);
        Connection connection = createConnection();
        Statement statement = createStatement();
        ResultSet rs = null;
        List<WifiInfo> wifiInfoList = new ArrayList<>();


        try {
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                WifiInfo wifiInfo = new WifiInfo();
                wifiInfo.setDistance(rs.getDouble("partial_distance"));
                wifiInfo.setX_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"));
                wifiInfo.setX_SWIFI_WRDOFC(rs.getString("X_SWIFI_WRDOFC"));
                wifiInfo.setX_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"));
                wifiInfo.setX_SWIFI_ADRES1(rs.getString("X_SWIFI_ADRES1"));
                wifiInfo.setX_SWIFI_ADRES2(rs.getString("X_SWIFI_ADRES2"));
                wifiInfo.setX_SWIFI_INSTL_FLOOR(rs.getString("X_SWIFI_INSTL_FLOOR"));
                wifiInfo.setX_SWIFI_INSTL_TY(rs.getString("X_SWIFI_INSTL_TY"));
                wifiInfo.setX_SWIFI_INSTL_MBY(rs.getString("X_SWIFI_INSTL_MBY"));
                wifiInfo.setX_SWIFI_SVC_SE(rs.getString("X_SWIFI_SVC_SE"));
                wifiInfo.setX_SWIFI_CMCWR(rs.getString("X_SWIFI_CMCWR"));
                wifiInfo.setX_SWIFI_CNSTC_YEAR(rs.getString("X_SWIFI_CNSTC_YEAR"));
                wifiInfo.setX_SWIFI_INOUT_DOOR(rs.getString("X_SWIFI_INOUT_DOOR"));
                wifiInfo.setX_SWIFI_REMARS3(rs.getString("X_SWIFI_REMARS3"));
                wifiInfo.setLAT(rs.getDouble("LAT"));
                wifiInfo.setLNT(rs.getDouble("LNT"));
                wifiInfo.setWORK_DTTM(rs.getString("WORK_DTTM"));
                wifiInfoList.add(wifiInfo);
            }
            rs.close();
        }catch (SQLException e) {
            System.out.println("getWifiListOrderByDistance 실패!");
            e.printStackTrace();
        }finally {
            closeStatement();
            closeConnection();
        }

        return wifiInfoList;
    }

    private String buildDistanceQuery(double latitude, double longitude) {
        String sql = " (6371*acos(cos(radians(" + latitude + "))*cos(radians(LAT))*cos(radians(LNT) " +
                "    -radians(" + longitude + "))+sin(radians(" + latitude + "))*sin(radians(LAT)))) " +
                "    AS partial_distance ";

        return sql;
    }

}
