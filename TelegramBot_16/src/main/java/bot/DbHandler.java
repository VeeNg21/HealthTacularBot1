package bot;

import org.sqlite.JDBC;

import java.sql.*;

public class DbHandler {

    private static final String CON_STR = "jdbc:sqlite:bot.db";

    // Use the singleton pattern to avoid spawning many
    // instances of the DbHandler class
    private static DbHandler instance = null;

    public static synchronized DbHandler getInstance() throws SQLException {
        if(instance == null) instance = new DbHandler();
        return instance;
    }

    private Connection connection;

    private DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public void addPlayer(long chat_id, boolean inGame){
        String query = "INSERT INTO Players(`chat_id`, `inGame`) VALUES (?, ?)";
        try(PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setObject(1, chat_id);
            statement.setObject(2, inGame);

            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void changePlay(long chat_id, boolean inGame) throws SQLException {
        String query = "UPDATE Players SET inGame=" + inGame + " WHERE chat_id=" + chat_id;
        try(Statement statement = this.connection.createStatement()){
            statement.executeUpdate(query);
        }
    }

    public boolean checkPlayer(int chat_id){
        String query = "SELECT * FROM Players WHERE chat_id = ?";
        try(PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setObject(1, chat_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) if(resultSet.getInt("chat_id") != 0) return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
