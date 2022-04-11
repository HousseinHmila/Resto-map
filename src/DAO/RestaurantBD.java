package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import oo.Restaurant;
import java.sql.*;

public class RestaurantBD {
    public ObservableList<Restaurant> getResto() {
        ObservableList<Restaurant> resto = FXCollections.observableArrayList();
    Connection c = ConnexionBD.getConnection();
        if (c != null){
            try {
                PreparedStatement ps=c.prepareStatement("SELECT * FROM `restaurant`;");
                ResultSet r = ps.executeQuery();
                while(r.next()){
                    resto.add(new Restaurant(r.getInt("id"),r.getString("nom"),r.getString("email"),Float.parseFloat(r.getString("avis")),Double.parseDouble(r.getString("latitude")),Double.parseDouble(r.getString("longitude")),r.getString("phone")));
                }
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return resto;
    }
   public void delete(int Idselected){
        Connection c = ConnexionBD.getConnection();
        ObservableList< Restaurant> datalist = FXCollections.observableArrayList();
        if (c != null) {
            try {
                PreparedStatement ps = c.prepareStatement("DELETE FROM restaurant WHERE id = ?");
                ps.setString(1,String.valueOf(Idselected));
                ps.executeUpdate();
            } catch (SQLException e){ e.printStackTrace(); }

        }
          }
}




