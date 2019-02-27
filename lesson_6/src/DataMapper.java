import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

class Location {
    private String name;
    private String region;
    private String country;
    private Double lat;
    private Double lon;
    private boolean isShowingCity;

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public boolean isShowingCity() {
        return isShowingCity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public void setShowingCity(boolean showingCity) {
        isShowingCity = showingCity;
    }
}

class LocationMapped{
    private final HashMap<String, Location> hashMap = new HashMap<>();
    private final Connection connection;

    LocationMapped(Connection connection) {
        this.connection = connection;
    }

    public Location getLocation(Double lat, Double lon){
        String key = lat + "," + lon;
        if (hashMap.containsKey(key)) return hashMap.get(key);
        Location location = null;
        String sql = "SELECT * FROM cities WHERE lat = ? AND lon = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setDouble(1, lat);
            ps.setDouble(2, lon);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                location = new Location();
                location.setName(rs.getString(rs.findColumn("name")));
                location.setRegion(rs.getString(rs.findColumn("region")));
                location.setCountry(rs.getString(rs.findColumn("country")));
                location.setLat(rs.getDouble(rs.findColumn("lat")));
                location.setLon(rs.getDouble(rs.findColumn("lon")));
                location.setShowingCity(rs.getBoolean(rs.findColumn("isShowing")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        if (location != null) hashMap.put(key, location);
        return location;
    }

    public boolean save(Location location){
        String sql = "INSERT INTO cities (name, region, country, lat, lon, isShowing) VALUES (?, ?, ?, ?, ?, ?);";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, location.getName());
            ps.setString(2, location.getRegion());
            ps.setString(3, location.getCountry());
            ps.setDouble(4, location.getLat());
            ps.setDouble(5, location.getLon());
            ps.setBoolean(6, location.isShowingCity());
            if (ps.executeUpdate() != 0){
                if (!hashMap.containsValue(location)) hashMap.put(location.getLat() + "," + location.getLon(), location);
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Location location) {
        String sql = "UPDATE cities SET iaShowing = ? WHERE lat = ? AND lon = ?;";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setBoolean(1, location.isShowingCity());
            ps.setDouble(2, location.getLat());
            ps.setDouble(3, location.getLon());
            return ps.executeUpdate() != 0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Location location) {
        String sql = "DELETE FROM cities WHERE lat = ? AND lon = ?;";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setDouble(1, location.getLat());
            ps.setDouble(2, location.getLon());
            return ps.executeUpdate() != 0 && hashMap.remove(location.getLat() + "," + location.getLon(), location);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}