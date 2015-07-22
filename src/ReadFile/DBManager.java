package ReadFile;

import java.sql.*;

public class DBManager {
    private Connection con;
    private ResultSet rs;
    private PreparedStatement ps;
    private Statement stmt;

    final static String JDBC_DRIVER = "org.postgresql.Driver";
    final static String URL = "jdbc:postgresql://localhost:5432/";
    final static String USER = "postgres";
    final static String PASS = "123qwe";
    final static String DBNAME = "phonebook";
    final static String TBLNAME = "contacts";


    public void initDataBase() {
        boolean checkDB = false;

        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(URL + DBNAME, USER, PASS);
            if (con != null) {
                System.out.println("Check if db exist.");
                rs = con.getMetaData().getCatalogs();
                while (rs.next()) {
                    String catalogs = rs.getString(1);
                    if (DBNAME.equals(catalogs)) {
                        System.out.println("DB exist.");
                        checkDB = true;
                    }
                }
            } else {
                System.out.println("unable connection.");
            }
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("DB not exist.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (!checkDB) {
                createDB();
                createTBL();
            }
            if (checkDB) {
                try {
                    con = DriverManager.getConnection(URL + DBNAME, USER, PASS);
                    DatabaseMetaData dbm = con.getMetaData();
                    rs = dbm.getTables(DBNAME, null, TBLNAME, null);
                    if (rs.next()) {
                        System.out.println("Table exist.");
                    } else {
                        stmt = con.createStatement();
                        String sql = "CREATE TABLE CONTACTS " +
                                "(ID serial PRIMARY KEY  NOT NULL, " + "NAME CHARACTER VARYING(30) NOT NULL, " +
                                "SURNAME CHARACTER VARYING(30) NOT NULL, " +
                                "PHONENUMBER CHARACTER VARYING(30) NOT NULL)";
                        stmt.executeUpdate(sql);
                        System.out.println("Table created.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

        private void createDB() {
            String sql = "CREATE DATABASE PHONEBOOK";
            try {
                con = DriverManager.getConnection(URL, USER, PASS);
                stmt = con.createStatement();
                stmt.executeUpdate(sql);
                System.out.println("DB create.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        private void createTBL() {
            String sql = "CREATE TABLE CONTACTS " +
                    "(ID serial PRIMARY KEY  NOT NULL, " + "NAME CHARACTER VARYING(30) NOT NULL, " +
                    "SURNAME CHARACTER VARYING(30) NOT NULL, " +
                    "PHONENUMBER CHARACTER VARYING(30) NOT NULL)";
            try {
                con = DriverManager.getConnection(URL + DBNAME, USER, PASS);
                stmt = con.createStatement();
                stmt.executeUpdate(sql);
                System.out.println("Table created.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public void finalExp() {
        try {
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkRecordExist(String phone) {
        boolean res = false;
        String sql = "SELECT PHONENUMBER FROM CONTACTS WHERE PHONENUMBER = ?";
        try {
            con = DriverManager.getConnection(URL + DBNAME, USER, PASS);
            ps = con.prepareStatement(sql);
            ps.setString(1, phone);
            rs = ps.executeQuery();
            res = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } //finally {
//                finalExp();
//            }
        return res;
    }

    public void updateDB(int id, String name, String surname, String phone) {
        String sql = "UPDATE CONTACTS SET NAME = ?, SURNAME = ?, PHONENUMBER = ? WHERE ID = ?";
        try {
            con = DriverManager.getConnection(URL + DBNAME, USER, PASS);
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, phone);
            ps.setInt(4, id);
            ps.executeUpdate();
            System.out.println("Update successful.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            finalExp();
        }
    }

    public void addContact(String name, String surname, String phone) throws SQLException {
        try {
            if (checkRecordExist(phone)) {
                System.out.println("Contact whit this phone number already exist.");
                System.out.println("Choose 'update' menu to change contact.");
            } else {
                String sql = "INSERT INTO CONTACTS (NAME, SURNAME, PHONENUMBER) " +
                        "VALUES (?, ?, ?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, surname);
                ps.setString(3, phone);
                ps.executeUpdate();
                System.out.println("Contact added.\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("DB error. Contact can`t be added.");
        } finally {
            finalExp();
        }
    }

    public void tableContent() {
        String sql = "SELECT * FROM CONTACTS ORDER BY ID";
        try {
            con = DriverManager.getConnection(URL + DBNAME, USER, PASS);
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            System.out.println("\nId  Name       Surname       Phone number");
            System.out.println("--  ----       -------       ------------");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String phone = rs.getString("phonenumber");
                System.out.format("%-4d%-11s%-14s%-7s%n", id, name, surname, phone);
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            finalExp();
        }
    }

    public void findByName(String name) {
        String sql = "SELECT ID, NAME, SURNAME, PHONENUMBER FROM CONTACTS WHERE NAME = ?";
        try {
            whileForFind(sql, name);
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            finalExp();
        }
    }

    /**
     * This method aimed to find contact by Surname.
     * @param surname
     */
    public void findBySurname(String surname) {
        String sql = "SELECT NAME, SURNAME, PHONENUMBER FROM CONTACTS WHERE SURNAME = ?";
        try {
            whileForFind(sql, surname);
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            finalExp();
        }
    }

    public void findByPhone(String phone) {
        String sql = "SELECT NAME, SURNAME, PHONENUMBER FROM CONTACTS WHERE PHONENUMBER = ?";
        try {
            whileForFind(sql, phone);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            finalExp();
        }
    }

    public void whileForFind(String sql, String txt) throws SQLException {
        con = DriverManager.getConnection(URL + DBNAME, USER, PASS);
        ps = con.prepareStatement(sql);
        ps.setString(1, txt);
        rs = ps.executeQuery();
        boolean check = false;
        int count = 0;
        while (rs.next()) {
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String phone = rs.getString("phonenumber");
            System.out.println("name: " + name + ", surname: " + surname + ", phone: " + phone);
            check = true;
            count++;
        }
        if (check) {
            System.out.println("Found " + count + " people.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    public void deleteRowByID(int id) {
        try {
            if (checkIDExist(id)) {
                con = DriverManager.getConnection(URL + DBNAME, USER, PASS);
                String sql = "DELETE FROM CONTACTS WHERE ID = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                System.out.println("Contact has been deleted.");
            } else {
                System.out.println("ID not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            finalExp();
        }
    }

    public boolean checkIDExist(int id) {
        String sql = "SELECT ID FROM CONTACTS WHERE ID = ?";
        boolean res = false;
        try {
            con = DriverManager.getConnection(URL + DBNAME, USER, PASS);
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            res = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            finalExp();
        }
        return res;
    }
}