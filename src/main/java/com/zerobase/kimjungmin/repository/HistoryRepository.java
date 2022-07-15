package com.zerobase.kimjungmin.repository;

import com.zerobase.kimjungmin.domain.History;
import com.zerobase.kimjungmin.domain.WifiInfo;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepository extends SuperRepository{
    public void insertHistory(History history) {
        final String sql = "INSERT INTO history (LAT, LNT, HISTORYAT)" +
                "VALUES (?,?,?)";

        createConnection();
        PreparedStatement preparedStatement = createPreparedStatement(sql);

        try {
            preparedStatement.setDouble(1, history.getLAT());
            preparedStatement.setDouble(2, history.getLNT());
            preparedStatement.setString(3, LocalDateTime.now().toString());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            System.out.println("insertHistory 실패!");
            e.printStackTrace();
        }

        closePreparedStatement();
        closeConnection();
    }

    public List<History> selectHistory() {
        final String sql = "SELECT * FROM history";
        List<History> historyList = new ArrayList<>();
        Connection connection = createConnection();
        Statement statement = createStatement();
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                History history = new History();
                history.setID(rs.getInt("ID"));
                history.setLAT(rs.getDouble("LAT"));
                history.setLNT(rs.getDouble("LNT"));
                history.setHISTORY_AT(rs.getString("HISTORYAT"));
                historyList.add(history);
            }

            rs.close();
        }catch (SQLException e) {
            System.out.println("selectHistory 실패!");
            e.printStackTrace();
        }finally {
            closeStatement();
            closeConnection();
        }

        return historyList;
    }
}
