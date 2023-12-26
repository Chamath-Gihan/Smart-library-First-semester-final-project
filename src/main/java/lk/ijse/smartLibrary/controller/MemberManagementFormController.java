package lk.ijse.smartLibrary.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.smartLibrary.bo.BoFactory;
import lk.ijse.smartLibrary.bo.custom.MemberManagementBO;
import lk.ijse.smartLibrary.dao.custom.MembersDAO;
import lk.ijse.smartLibrary.dto.Member;
import lk.ijse.smartLibrary.dto.tm.MemberTM;
import lk.ijse.smartLibrary.model.MemberModel;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemberManagementFormController implements Initializable {

    private static final String URL = "jdbc:mysql://localhost:3306/SmartLibrary";
    private static final Properties props = new Properties();
    private static final Notifications notification = Notifications.create();
    private Pattern namePattern = Pattern.compile("^[a-zA-Z\\s]+$");
    private Pattern addressPattern = Pattern.compile("^[a-zA-Z0-9\\s#,./-]+$");
    MemberManagementBO memberManagementBO = BoFactory.getBoFactory().getBO(BoFactory.BOTypes.MEMBER_BO);

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "Chamath2005.");
    }

    public TableView<MemberTM> tblMember;

    @FXML
    private TableColumn<?, ?> address;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private TextField btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lblError;

    @FXML
    private TableColumn<?, ?> memberId;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtGuaranteeFee;

    @FXML
    private TextField txtMemberAddress;

    @FXML
    private TextField txtMemberId;

    @FXML
    private TextField txtMemberName;

    @FXML
    private TextField txtMonthlyFee;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        guaranteeFee();
        try {
            txtMemberId.setText(String.valueOf(memberManagementBO.getNextMemberId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        curDate();
        setCellValueFactory();
        getAll();
    }

    private void setCellValueFactory() {
        memberId.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String id = txtMemberId.getText();
        String name = txtMemberName.getText();
        String address = txtMemberAddress.getText();
        LocalDate today = LocalDate.now();
        String month = today.getMonth().name();

        if (id.isEmpty() || name.isEmpty() || address.isEmpty()) {
            lblError.setText("All Fields Are Required !");
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> lblError.setText(""));
                        }
                    },
                    2000
            );
            return;
        }
        if(!validateInputs()){
            return;
        }
        try {
            if(true == memberManagementBO.addMember(new Member (id, name, address, month))){

                notification.title("Smart Library")
                        .text("Member Added Success !").darkStyle()
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();

                txtGuaranteeFee.clear();
                txtMemberId.clear();
                try {
                    txtMemberId.setText(String.valueOf(memberManagementBO.getNextMemberId()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                guaranteeFee();
                getAll();
                txtMemberName.clear();
                txtMemberAddress.clear();

            }else {
                notification.title("Smart Library")
                        .text("Member Added Unsuccss !").darkStyle()
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String memberId = txtMemberId.getText();
        try {
            if(true== memberManagementBO.deleteMember(memberId)){

                notification.title("Smart Library")
                        .text("Member Delete Success !").darkStyle()
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();

                String gf = txtGuaranteeFee.getText();
                int gf1 = Integer.parseInt(gf);
                String id = txtMemberId.getText();
                int memberId1 = Integer.parseInt(id);

                if(gf1 == memberId1){
                    txtMemberName.clear();
                    txtMemberAddress.clear();
                }else{
                    txtMemberId.clear();
                    txtMemberId.setText(Integer.toString(gf1));
                    txtMemberName.clear();
                    txtMemberAddress.clear();
                }
            }else{
                notification.title("Smart Library")
                        .text("Member Delete Unsuccess !").darkStyle()
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getAll();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = btnSearch.getText();

        String ar[] = new String[0];
        Member member = null;
        try {
            member = memberManagementBO.searchMember(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        txtMemberId.setText(member.getMemberId());
        txtMemberName.setText(member.getName());
        txtMemberAddress.setText(member.getAddress());
        getAll();
        btnSearch.clear();
        System.out.println(id);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtMemberId.getText();
        String name = txtMemberName.getText();
        String address = txtMemberAddress.getText();

        try {
            if(true == memberManagementBO.updateMember(new Member(name, address, id))){
                notification.title("Smart Library")
                        .text("All Data Updated !").darkStyle()
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        getAll();
        txtMemberId.clear();
        txtMemberName.clear();
        txtMemberAddress.clear();
        try {
            txtMemberId.setText(String.valueOf(memberManagementBO.getNextMemberId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event)  {
        try {
            txtMemberId.setText(String.valueOf(memberManagementBO.getNextMemberId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txtMemberName.clear();
        txtMemberAddress.clear();
    }

    private void getAll() {
        try {
            ObservableList<MemberTM> obList = FXCollections.observableArrayList();
            List<Member> memberList = memberManagementBO.getAllMembers();

            for (Member member : memberList) {
                obList.add(new MemberTM(
                        member.getMemberId(),
                        member.getName(),
                        member.getAddress()
                ));
            }
            tblMember.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            notification.title("Smart Library")
                    .text("Something is wrong !").darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
        }
    }

    private void curDate() {
        LocalDate currentDate = LocalDate.now();
        String currentMonthName = currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        txtMonthlyFee.setText(currentMonthName);
    }
    
    private int guaranteeFee() {
        int nextId = 1;
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "SELECT MAX(Fee_Id) FROM GuaranteeFee";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                nextId = rs.getInt(1) + 1;
            }
            // Use the nextId variable as needed
        } catch (SQLException e) {
            // Handle the exception as needed
        }
        txtGuaranteeFee.setText(String.valueOf(nextId));
        return nextId;
    }

    private boolean validateInputs() {
        String name = txtMemberName.getText().trim();
        String address = txtMemberAddress.getText().trim();
        Matcher nameMatcher = namePattern.matcher(name);
        Matcher addressMatcher = addressPattern.matcher(address);
        if (!nameMatcher.matches() || !addressMatcher.matches()) {
            lblError.setText("Invalid input. Please enter valid name and address.");
            new Timeline(new KeyFrame(Duration.seconds(2), e -> lblError.setText(""))).play();
            return false;
        }else{
            return true;
        }
    }
}
