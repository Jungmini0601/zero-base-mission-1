package com.zerobase.kimjungmin.repository;

import java.sql.*;

public class SuperRepository {
    private static final String SQLITE_JDBC_DRIVER = "org.sqlite.JDBC";

    private static final String SQLITE_FILE_DB_URL = "jdbc:sqlite:C:\\Users\\82105\\zero-base\\kimjungmin\\zero-base.db";

    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private Statement statement = null;
    private String driver = null;
    private String url = null;

    public SuperRepository() {
        this(SQLITE_FILE_DB_URL);
    }

    public SuperRepository(String url) {
        this.url = url;
        this.driver = SQLITE_JDBC_DRIVER;
    }

    public Connection createConnection() {
        try {
            Class.forName(this.driver);
            this.conn = DriverManager.getConnection(this.url);
            System.out.println("CONNECT SUCCESS ");
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public void closeConnection() {
        try {
            if( this.conn != null ) {
                this.conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.conn = null;
            // 로그 출력
            System.out.println("CLOSED");
        }
    }

    public PreparedStatement createPreparedStatement(String sql) {
        try {
            preparedStatement = conn.prepareStatement(sql);
        }catch (SQLException e) {
            System.out.println("createPreparedStatement 실패");
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public void closePreparedStatement() {
        try {
            if (this.preparedStatement != null) {
                this.preparedStatement.close();
            }
        }catch (SQLException e) {
            System.out.println("closePreparedStatement 실패");
        } finally {
            this.preparedStatement = null;
            System.out.println("preparedStatement close");
        }
    }

    public Statement createStatement() {
        try {
            statement = conn.createStatement();
        }catch (SQLException e) {
            System.out.println("createStatement 실패!");
        }

        return statement;
    }

    public void closeStatement() {
        try {
            if (this.statement != null) {
                this.statement.close();
            }
        }catch (SQLException e) {
            System.out.println("closeStatement 실패");
        } finally {
            this.statement = null;
            System.out.println("closeStatement close");
        }
    }
}
