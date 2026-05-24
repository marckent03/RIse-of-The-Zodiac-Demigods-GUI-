package dao;

import model.Order;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public boolean placeOrder(int userId, int mealId, int quantity, String pickupTime) {
        String sql = "INSERT INTO orders (user_id, meal_id, quantity, pickup_time, status) VALUES (?, ?, ?, ?, 'Pending')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, mealId);
            pstmt.setInt(3, quantity);
            pstmt.setString(4, pickupTime);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Order> getOrdersByUser(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.*, m.name as meal_name, m.price, o.admin_note " +
                 "FROM orders o JOIN menu m ON o.meal_id = m.id " +
                 "WHERE o.user_id = ? ORDER BY o.order_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setMealName(rs.getString("meal_name"));
                order.setQuantity(rs.getInt("quantity"));
                order.setPickupTime(rs.getString("pickup_time"));
                order.setStatus(rs.getString("status"));
                order.setTotal(rs.getDouble("price") * order.getQuantity());
                order.setMealId(rs.getInt("meal_id"));
                order.setMealPrice(rs.getDouble("price"));
                order.setAdminNote(rs.getString("admin_note"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public boolean updateOrder(int orderId, int quantity, String pickupTime) {
        String sql = "UPDATE orders SET quantity = ?, pickup_time = ? WHERE id = ? AND status = 'Pending'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setString(2, pickupTime);
            pstmt.setInt(3, orderId);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelOrder(int orderId) {
        String sql = "UPDATE orders SET status = 'Cancelled' WHERE id = ? AND status = 'Pending'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.*, m.name as meal_name, m.price, u.full_name as user_name, o.admin_note " +
                    "FROM orders o " +
                    "JOIN menu m ON o.meal_id = m.id " +
                    "JOIN users u ON o.user_id = u.id " +
                    "ORDER BY o.order_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setMealName(rs.getString("meal_name"));
                order.setQuantity(rs.getInt("quantity"));
                order.setPickupTime(rs.getString("pickup_time"));
                order.setStatus(rs.getString("status"));
                order.setTotal(rs.getDouble("price") * order.getQuantity());
                order.setMealId(rs.getInt("meal_id"));
                order.setMealPrice(rs.getDouble("price"));
                order.setAdminNote(rs.getString("admin_note"));
                order.setUserId(rs.getInt("user_id"));
                order.setUserName(rs.getString("user_name"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Update order status and optional note
    public boolean updateOrderStatus(int orderId, String status, String adminNote) {
        String sql = "UPDATE orders SET status = ?, admin_note = ? WHERE id = ? AND status <> 'Cancelled'";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setString(2, adminNote);
            pstmt.setInt(3, orderId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}