package lk.ijse.smartLibrary.bo.custom;

import lk.ijse.smartLibrary.bo.SuperBO;
import lk.ijse.smartLibrary.dto.Expenditure;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FinanceManagementBO extends SuperBO {
    ArrayList<Expenditure> getAllExpenditures() throws SQLException;
    int getNextExpenditureId() throws SQLException;
    double getTotalExpenditurePrice() throws SQLException;
    boolean addExpenditures(Expenditure dto) throws SQLException;
    Expenditure searchExpenditures(String id) throws SQLException;
}
