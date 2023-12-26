package lk.ijse.smartLibrary.bo.custom.impl;

import lk.ijse.smartLibrary.bo.custom.MemberManagementBO;
import lk.ijse.smartLibrary.dao.DAOFactory;
import lk.ijse.smartLibrary.dao.custom.MembersDAO;
import lk.ijse.smartLibrary.dao.custom.QueryDAO;
import lk.ijse.smartLibrary.dto.Member;

import java.sql.SQLException;
import java.util.ArrayList;

public class MemberManagementBOImpl implements MemberManagementBO {
    MembersDAO membersDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.MEMBERS);
    QueryDAO queryDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    @Override
    public ArrayList<Member> getAllMembers() throws SQLException {
        return membersDAO.getAll();
    }

    @Override
    public boolean addMember(Member dto) throws SQLException {
        return membersDAO.add(dto);
    }

    @Override
    public boolean deleteMember(String id) throws SQLException {
        return membersDAO.delete(id);
    }

    @Override
    public Member searchMember(String id) throws SQLException {
        return membersDAO.search(id);
    }

    @Override
    public boolean updateMember(Member dto) throws SQLException {
        return membersDAO.update(dto);
    }

    @Override
    public int getNextMemberId() throws SQLException {
        return queryDAO.getNextMemberId();
    }

}
