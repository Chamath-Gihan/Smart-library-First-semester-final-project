package lk.ijse.smartLibrary.dao;

import lk.ijse.smartLibrary.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {}

    public static DAOFactory getDAOFactory() {return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;}

    public enum DAOTypes {
        ATTENDANCE, BOOK_RESERVATION, BOOKS, DONATE, EMPLOYEE, EXPENDITURE, GUARANTEE_FEE,
        LIBRARIAN, MEMBER_DONATIONS, MEMBERS, MONTHLY_FEE, PAYMENT, RESERVATIONS, QUERY
    }

    public <T extends SuperDAO> T getDAO(DAOTypes res) {
        switch (res) {
            case ATTENDANCE:
                return (T) new AttendanceDAOImpl();
            case BOOK_RESERVATION:
                return (T) new BookReservationDAOImpl();
            case BOOKS:
                return (T) new BooksDAOImpl();
            case DONATE:
                return (T) new DonateDAOImpl();
            case EMPLOYEE:
                return (T) new EmployeeDAOImpl();
            case EXPENDITURE:
                return (T) new ExpenditureDAOImpl();
            case GUARANTEE_FEE:
                return (T) new GuaranteeFeeDAOImpl();
            case LIBRARIAN:
                return (T) new LibrarianDAOImpl();
            case MEMBER_DONATIONS:
                return (T) new MemberDonationsDAOImpl();
            case MEMBERS:
                return (T) new MembersDAOImpl();
            case MONTHLY_FEE:
                return (T) new MonthlyFeeDAOImpl();
            case PAYMENT:
                return (T) new PaymentOfSalariesDAOImpl();
            case RESERVATIONS:
                return (T) new ReservationDAOImpl();
            case QUERY:
                return (T) new QueryDAOImpl();
            default:
                return null;
        }
    }
}
