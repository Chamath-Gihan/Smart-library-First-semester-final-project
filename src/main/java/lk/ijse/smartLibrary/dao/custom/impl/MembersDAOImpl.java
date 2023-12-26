package lk.ijse.smartLibrary.dao.custom.impl;

import lk.ijse.smartLibrary.dao.custom.MembersDAO;
import lk.ijse.smartLibrary.dao.custom.impl.util.CrudUtil;
import lk.ijse.smartLibrary.dto.Member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MembersDAOImpl implements MembersDAO {

    @Override
    public ArrayList<Member> getAll() throws SQLException {
        ArrayList<Member> data = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Members");
        while (resultSet.next()) {
            data.add(new Member(
                    resultSet.getString(1),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return data;
    }

    @Override
    public boolean add(Member dto) throws SQLException {
        return CrudUtil.execute("INSERT INTO Members(Member_Id, Member_Name, Librarian_Id, Member_Address) VALUES(?, ?, ?, ?)",
                dto.getMemberId(), dto.getName(), null, dto.getAddress());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM Members WHERE Member_Id = ?", id);
    }

    @Override
    public Member search(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Members WHERE Member_Id = ?", id);
        if(rst.next()){
            return new Member(rst.getString(1), rst.getString(3), rst.getString(4));
        }
        return null;
    }

    @Override
    public boolean update(Member dto) throws SQLException {
        return CrudUtil.execute("UPDATE Members SET Member_Name = ?, Member_Address = ? WHERE Member_Id = ?",
                dto.getName(), dto.getAddress(), dto.getMemberId());
    }
}
