package connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientData {
	public ConnectDB connectDB = new ConnectDB();
	public ResultSet rs;
	public ClientData(){
		connectDB.connect();
	}
	
	public void getAllUser(){
		String sqlCommand = "select tendangnhap , hoten, sdt , diachi from khach";
		Statement st = null;
		try {
			st = connectDB.connection.createStatement();
			rs = st.executeQuery(sqlCommand);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void searchAcc(String str){
		String sqlCommand = "select distinct tendangnhap , hoten, sdt , diachi from khach where (tendangnhap like ?) "
								+ "or (hoten like ?) or (sdt like ?) or (diachi like ?) ";
		PreparedStatement pst = null;
		try {
			pst = connectDB.connection.prepareStatement(sqlCommand);
			pst.setString(1, "%" + str + "%");
			pst.setString(2, "%" + str + "%");
			pst.setString(3, "%" + str + "%");
			pst.setString(4, "%" + str + "%");
			rs = pst.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void deleteData(String acc){
		String sqlCommand="delete from khach where tendangnhap = ?";
		PreparedStatement pst = null;
		try {
			pst = connectDB.connection.prepareStatement(sqlCommand);
			pst.setString(1, acc);
			pst.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Delete Error");
		}
	}
	
	public void addUser(User user){
		String sqlCommand = "insert into khach value(?,?,?,?,?)";
		PreparedStatement pst = null;
		try {
			pst = connectDB.connection.prepareStatement(sqlCommand);
			pst.setString(1, user.getAcc());
			pst.setString(2, user.getPw());
			pst.setString(3, user.getName());
			pst.setString(4, user.getPhone());
			pst.setString(5, user.getAddress());
			pst.executeUpdate();
			System.out.println("added");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// Kiem tra xem ton tai nguoi dung khong
	public boolean checkUser(String acc){
		boolean check = false;
		ResultSet rs = null;
		String sqlCommand = "select * from khach where tendangnhap = ?";
		PreparedStatement pst = null;
		try {
			pst = connectDB.connection.prepareStatement(sqlCommand);
			pst.setString(1, acc);
			rs = pst.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (rs.next()){
				check = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public static void main(String args[]){
		ClientData clientData = new ClientData();
		System.out.println(clientData.checkUser("test"));
	}
}
