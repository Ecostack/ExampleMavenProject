package de.techstack.example;

import java.sql.SQLException;

/**
 * Created by basti on 27.11.15.
 */
public class Environment {
    public static Environment instance = new Environment();

    private Checker checker = new Checker();

    private DatabaseManager dbManager = new DatabaseManager();

    private Environment() {
        try {
            dbManager.init();
            dbManager.setupDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Checker getChecker() {
        return checker;
    }

    public DatabaseManager getDbManager() {
        return dbManager;
    }
}
