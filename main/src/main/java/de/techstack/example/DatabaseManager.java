package de.techstack.example;

import com.thomas_bayer.blz.DetailsType;

import java.sql.*;

/**
 * Created by basti on 27.11.15.
 */
public class DatabaseManager {

    private Connection conn;
    private boolean initAlready = false;

    public DatabaseManager() {

    }

    public void init() throws SQLException, ClassNotFoundException {
        if (!initAlready) {
            Class.forName("org.h2.Driver");
            conn = DriverManager.
                    getConnection("jdbc:h2:mem:test");
            initAlready = true;
        }
    }

    public void setupDatabase() throws SQLException {
        StringBuilder lcSQL = new StringBuilder();
        lcSQL = lcSQL.append("CREATE TABLE IF NOT EXISTS BLZ_BIC\n" +
                "(" +
                "ID int NOT NULL AUTO_INCREMENT," +
                "BLZ varchar(8)," +
                "NAME varchar(100)," +
                "BIC varchar(11)," +
                "CITY varchar(50)," +
                "ERROR_HAPPEND boolean," +
                "PRIMARY KEY (ID)" +
                ");");
        PreparedStatement lcStatement = conn.prepareStatement(lcSQL.toString());
        lcStatement.execute();
    }

    public void closeDatabase() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public boolean writeResult(DetailsType pResult, String pBLZ) throws SQLException {
        StringBuilder lcSQL = new StringBuilder();
        int lcResult;
        if (pResult != null) {
            lcSQL = lcSQL.append("INSERT INTO BLZ_BIC (BLZ, NAME, BIC, CITY, ERROR_HAPPEND)\n" +
                    "VALUES (?, ?, ?, ?, ?);");
            PreparedStatement lcStatement = conn.prepareStatement(lcSQL.toString());
            lcStatement.setString(1, pBLZ);
            lcStatement.setString(2, pResult.getBezeichnung());
            lcStatement.setString(3, pResult.getBic());
            lcStatement.setString(4, pResult.getOrt());
            lcStatement.setBoolean(5, true);
            lcResult = lcStatement.executeUpdate();
        } else {
            lcSQL = lcSQL.append("INSERT INTO BLZ_BIC (BLZ, ERROR_HAPPEND) VALUES (?, ?);");
            PreparedStatement lcStatement = conn.prepareStatement(lcSQL.toString());
            lcStatement.setString(1, pBLZ);
            lcStatement.setBoolean(2, true);
            lcResult = lcStatement.executeUpdate();
        }
        // System.out.printf("DB write result %d at BLZ %s \n", lcResult, pBLZ);
        return lcResult > 0;
    }

    public String readBLZNameEntry(String pBLZ) throws SQLException {
        String lcSQL = "SELECT name FROM BLZ_BIC WHERE BLZ = ?";
        PreparedStatement lcStatement = conn.prepareStatement(lcSQL);
        lcStatement.setString(1, pBLZ);
        ResultSet lcRS = lcStatement.executeQuery();

        String lcName = null;

        while (lcRS.next()) {
            lcName = lcRS.getString("name");
        }

        //System.out.printf("Row Count %d for BLZ %s \n", lcRowCount, pBLZ);
        return lcName;
    }
}
