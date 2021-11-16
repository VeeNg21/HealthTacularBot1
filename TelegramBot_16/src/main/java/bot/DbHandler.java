package bot;

import org.sqlite.JDBC;

import java.sql.*;

public class DbHandler {

    private static final String CON_STR = "jdbc:sqlite:bot.db";

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

    public void addPlayer(long chat_id){
        String query = "INSERT INTO Players (chat_id) VALUES (?)";
        try(PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setObject(1, chat_id);
            statement.executeUpdate();
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

    public boolean isInGame(long chat_id){

        boolean inGame = false;

        String query = "SELECT inGame FROM Players where chat_id = ?";
        try(PreparedStatement statement = this.connection.prepareStatement(query)){
            statement.setObject(1, chat_id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) inGame = resultSet.getBoolean("inGame");
            return inGame;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return inGame;
    }

    public boolean checkPlayer(long chat_id){
        String query = "SELECT * FROM Players WHERE chat_id = ?";
        try(PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setObject(1, chat_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) if(resultSet.getInt("chat_id") != 0) return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
}
