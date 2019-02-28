package data;

import java.sql.*;

public class PersonMapper {

    private final Connection connection;

    public PersonMapper(Connection connection) {
        this.connection = connection;
    }

    void save(Person person){
        //не совсем корректно написано, данные должны вставлятся в 2 таблицы
        String sql = "INSERT INTO persons (name) VALUES (?);";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, person.getName());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    void update(Person person) {
        //не совсем корректно написано, данные должны обновлятся в 2-х таблицах
        String sql = "UPDATE persons SET name = ? WHERE id = ?;";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, person.getName());
            ps.setInt(2, person.getId());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    void delete(Person person) {
        String sql = "DELETE FROM persons WHERE id = ?;";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, person.getId());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
