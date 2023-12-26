package lk.ijse.smartLibrary.bo.custom.impl;

import lk.ijse.smartLibrary.bo.custom.FinanceManagementBO;
import lk.ijse.smartLibrary.dao.DAOFactory;
import lk.ijse.smartLibrary.dao.custom.ExpenditureDAO;
import lk.ijse.smartLibrary.dao.custom.QueryDAO;
import lk.ijse.smartLibrary.dao.custom.impl.util.CrudUtil;
import lk.ijse.smartLibrary.dto.Expenditure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FinanceManagementBOImpl implements FinanceManagementBO {
    ExpenditureDAO expenditureDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.EXPENDITURE);
    QueryDAO queryDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    @Override
    public ArrayList<Expenditure> getAllExpenditures() throws SQLException {
        return expenditureDAO.getAll();
    }

    @Override
    public int getNextExpenditureId() throws SQLException {
        return queryDAO.getNextExpenditureId();
    }

    @Override
    public double getTotalExpenditurePrice() throws SQLException {
        return queryDAO.getTotalExpenditurePrice();
    }

    @Override
    public boolean addExpenditures(Expenditure dto) throws SQLException {
        return expenditureDAO.add(dto);
    }

    @Override
    public Expenditure searchExpenditures(String id) throws SQLException {
        return expenditureDAO.search(id);
    }


}
