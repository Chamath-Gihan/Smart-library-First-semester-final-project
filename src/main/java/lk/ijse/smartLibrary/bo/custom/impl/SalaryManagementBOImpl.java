package lk.ijse.smartLibrary.bo.custom.impl;

import lk.ijse.smartLibrary.bo.custom.SalaryManagementBO;
import lk.ijse.smartLibrary.dao.DAOFactory;
import lk.ijse.smartLibrary.dao.custom.PaymentOfSalariesDAO;
import lk.ijse.smartLibrary.dto.Salary;

import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryManagementBOImpl implements SalaryManagementBO {
    PaymentOfSalariesDAO paymentOfSalariesDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public ArrayList<Salary> getAllSalaryPayments() throws SQLException {
        return paymentOfSalariesDAO.getAll();
    }
}
