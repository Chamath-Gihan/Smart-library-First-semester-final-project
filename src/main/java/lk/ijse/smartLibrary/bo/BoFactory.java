package lk.ijse.smartLibrary.bo;

import lk.ijse.smartLibrary.bo.custom.impl.*;

public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory() {}

    public static BoFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BoFactory() : boFactory;
    }

    public enum BOTypes {
        ADD_BO, ATTENDANCE_BO, BOOK_BO, CHANGE_EMAIL_BO, DONATE_BO, EMPLOYEE_BO, FINANCE_BO, HOMEPAGE_BO,
        LOGIN_BO, MEMBER_BO, OTP_BO, RE_ACTIVATE_BO, SALARY_BO
    }

    public <T extends SuperBO> T getBO(BOTypes boTypes) {
        switch (boTypes) {
            case ADD_BO:
                return (T) new AddAccountBOImpl();
            case ATTENDANCE_BO:
                return (T) new AttendanceManagementBOImpl();
            case BOOK_BO:
                return (T) new BookManagementBOImpl();
            case CHANGE_EMAIL_BO:
                return (T) new ChangeEmailBOImpl();
            case DONATE_BO:
                return (T) new DonateManagementBOImpl();
            case EMPLOYEE_BO:
                return (T) new EmployeeManagementBOImpl();
            case FINANCE_BO:
                return (T) new FinanceManagementBOImpl();
            case HOMEPAGE_BO:
                return (T) new HomePageBOImpl();
            case LOGIN_BO:
                return (T) new LoginFormBOImpl();
            case MEMBER_BO:
                return (T) new MemberManagementBOImpl();
            case OTP_BO:
                return (T) new OtpConfirmationBOImpl();
            case RE_ACTIVATE_BO:
                return (T) new ReActivateBOImpl();
            case SALARY_BO:
                return (T) new SalaryManagementBOImpl();
            default:
                return null;
        }
    }
}
