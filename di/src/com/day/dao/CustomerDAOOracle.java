package com.day.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.springframework.stereotype.Repository;

import com.day.dto.Customer;
import com.day.exception.AddException;
import com.day.exception.DuplicatedException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;
import com.day.sql.MyConnection;

@Repository("customerDAO")
public class CustomerDAOOracle implements CustomerDAO {
	
	@Override
	public void insert(Customer c) throws AddException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
			System.out.println("connection success");
		} catch (Exception e) {
			e.printStackTrace();
			throw new AddException(null);
		}

		String insertSQL = "INSERT INTO customer(id,pwd,name) VALUES (?,?,?)";
		PreparedStatement pstmt = null;
		System.out.println(insertSQL);
		try {
			pstmt = con.prepareStatement(insertSQL);
			pstmt.setString(1, c.getId());
			pstmt.setString(2, c.getPwd());
			pstmt.setString(3, c.getName());

			pstmt.executeUpdate();
			System.out.println("정보가 추가되었습니다.");
		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
			throw new DuplicatedException("이미 존재하는 아이디입니다.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException("추가에 실패했습니다.");
		} finally {
			MyConnection.close(con, pstmt, null);
		}

	}

	@Override
	public Customer selectById(String id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}

		String selectSQL = "SELECT * FROM customer WHERE id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Customer c = null;
		try {
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String buildingno = rs.getString("buildingno");
				int enabled = rs.getInt("enabled");

				c = new Customer(id, pwd, name, buildingno, enabled);
				return c;
			} else {
				throw new FindException("해당 고객을 찾을 수 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	@Override
	public void update(Customer c) throws ModifyException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
			System.out.println("connection success");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}

		String str = "";
//		String updateSQL = "UPDATE customer SET ";

		if ("".equals(c.getPwd()) && "".equals(c.getName()) && "".equals(c.getBuildingno())) {
			System.out.println("변경할 내용이 없습니다.");
			return;
		}
		if (!"".equals(c.getPwd())) {
			str += "pwd='" + c.getPwd() + "',";
		}
		if (!"".equals(c.getName())) {
			str += "name='" + c.getName() + "',";
		}
		if (!"".equals(c.getBuildingno())) {
			str += "buildingno='" + c.getBuildingno() + "',";
		}

		if (c.getEnabled() > -1) {
			str = "enabled=" + c.getEnabled() + ",";
		}

		String updateSQL = "UPDATE customer SET " + str.substring(0, str.length() - 1) + " WHERE id=?";
		System.out.println(updateSQL);
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(updateSQL);
			pstmt.setString(1, c.getId());
			int rowcnt = pstmt.executeUpdate();
			if (rowcnt == 1 && c.getEnabled() == 1) {
				System.out.println(c.getId() + "의 정보가 변경되었습니다.");
			} else if (rowcnt == 1 && c.getEnabled() == 0) {
				System.out.println(c.getId() + "가 탈퇴하였습니다.");
			} else {
				throw new ModifyException("해당 고객의 정보를 변경할 수 없습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}

	}

	public static void main(String[] args) {
		// 고객은 가입한다.
//		String id="";
//		String pwd="";
//		String name="";
//		
//		try {
//			CustomerDAOOracle dao = new CustomerDAOOracle();
//			Customer c = new Customer(id,pwd,name);
//			dao.insert(c);
//		}catch (DuplicatedException e) {
//			System.out.println(e.getMessage());
//		}
//		catch (AddException e) {
//			System.out.println(e.getMessage());
//		}catch(Exception e) {
//			System.out.println(e.getMessage());
//		}

		// 고객은 로그인한다.

		// 고객은 자기정보를 조회한다.
//		String id="";
//		try {
//			CustomerDAOOracle dao = new CustomerDAOOracle();
//			Customer c = dao.selectById(id);
//			System.out.println(c);
//		}catch (FindException e) {
//			System.out.println(e.getMessage());
//		}catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		// 고객은 자기정보를 수정한다.
//		Scanner sc = new Scanner(System.in);
//		System.out.println("아이디를 입력하세요");
//		String id = sc.nextLine();
//		System.out.println("변경할 비밀번호를 입력하세요");
//		String pwd = sc.nextLine();
//		System.out.println("변경할 이름을 입력하세요");
//		String name = sc.nextLine();
//		System.out.println("변경할 빌딩 번호를 입력하세요");
//		String buildingno = sc.nextLine();
//		
//		try {
//			CustomerDAOOracle dao = new CustomerDAOOracle();
//			Customer c = new Customer(id,pwd,name,buildingno);
//			dao.update(c);
//		}catch (ModifyException e) {
//			System.out.println(e.getMessage());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		// 고객은 탈퇴한다.

	}
}
