package lk.ijse.smartLibrary.bo.custom;

import javafx.scene.control.Label;
import lk.ijse.smartLibrary.bo.SuperBO;
import lk.ijse.smartLibrary.dto.Member;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MemberManagementBO extends SuperBO {

    ArrayList<Member> getAllMembers() throws SQLException;
    boolean addMember(Member dto) throws SQLException;
    boolean deleteMember(String id) throws SQLException;
    Member searchMember(String id) throws SQLException;
    boolean updateMember(Member dto) throws SQLException;
    int getNextMemberId() throws SQLException;
}
