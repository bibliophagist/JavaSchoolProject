package server.data.base.controller;

import server.connection.service.Request;
import server.connection.service.RequestError;
import server.connection.service.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AppCore {

    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost:3306/sberdata";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "mysql";

    public Response handleRequest(Request req) throws NoSuchRequestException {
        if (req == null) {
            throw new NullPointerException("Not a request");
        }
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            //TODO log
            System.out.println("JDBC_DRIVER Class not found");
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);) {
            switch (req.getReqType()) {
                case REGISTER:
                    register(conn, req);
                    break;
                case LOGIN:
                    login(conn, req);
                    break;
                case REMOVE_USER:
                    removeUser(conn, req);
                    break;
                case CREATE_ACC:
                    createAcc(conn, req);
                    break;
                case REMOVE_ACC:
                    removeAcc(conn, req);
                    break;
                case CHECK_BALANCE:
                    checkBalance(conn, req);
                    break;
                default:
                    throw new NoSuchRequestException("No Such Request" + req.getReqType().toString());
            }
        } catch (SQLException e) {
            //TODO log
            System.out.println("Database failure: " + e.getMessage());
            e.printStackTrace();
        }
        return new Response();
    }

    private Response register(Connection conn, Request req) throws SQLException {
        if (ServiceDB.checkUser(conn, req.getUsername())) {
            req.setReqError(RequestError.USER_EXISTS);
            req.setReqMessage("User with such nickname already exists.");

        } else {
            ServiceDB.addUser(conn, req.getUsername(), req.getPassword());
            ServiceDB.addAccount(conn, req.getUsername(), req.getMoney());
        }
        return new Response();
    }

    private Response login(Connection conn, Request req) throws SQLException {
        if (!ServiceDB.checkUser(conn, req.getUsername())) {
            req.setReqError(RequestError.NO_SUCH_NAME);
            req.setReqMessage("Wrong nickname.");
        } else if (!ServiceDB.checkPass(conn, req.getUsername(), req.getPassword())) {
            req.setReqError(RequestError.WRONG_PASS);
            req.setReqMessage("Wrong password.");
        }
        return new Response();
    }

    private Response createAcc(Connection conn, Request req) throws SQLException {
        if (req.getAccTitle() == null) {
            ServiceDB.addAccount(conn, req.getUsername(), req.getMoney());
        } else {
            ServiceDB.addAccount(conn, req.getUsername(), req.getAccTitle(), req.getMoney());
        }
        return new Response();
    }

    private Response removeAcc(Connection conn, Request req) throws SQLException {
        if (ServiceDB.getNumOfAccounts(conn, req.getUsername()) == 1) {
            req.setReqError(RequestError.LAST_ACCOUNT);
            req.setReqMessage("Can't delete account. Reason: it is the last one.");
        } else {
            ServiceDB.removeAccount(conn, req.getAccTitle());
        }
        return new Response();
    }

    private Response removeUser(Connection conn, Request req) throws SQLException {
        if (ServiceDB.checkUser(conn, req.getUsername())) {
            ServiceDB.removeUser(conn, req.getUsername());
        } else {
            req.setReqError(RequestError.USER_DOES_NOT_EXIST);
            req.setReqMessage("Can't delete user. Reason: such user already doesn't exist.");
        }
        return new Response();
    }

    private Response checkBalance(Connection conn, Request req) {
        return new Response();
    }

    private Response transferFunds(Connection conn, Request req) {
        return new Response();
    }

}
