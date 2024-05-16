package org.example.stockmanagementsystem.AdminManagerClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.stockmanagementsystem.ConfirmationDialog;
import org.example.stockmanagementsystem.DAOclasses.UserDAO;
import org.example.stockmanagementsystem.searchingModels.UserSearchModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserManager {

    private final UserDAO userDAO;
    private final TableView<UserSearchModel> userTableView;
    private final ObservableList<UserSearchModel> userSearchModelObservableList;
    private final String userId;

    public UserManager(UserDAO userDAO, TableView<UserSearchModel> userTableView,
                       TableColumn<UserSearchModel, Integer> userIdColumn,
                       TableColumn<UserSearchModel, String> userFirstNameColumn,
                       TableColumn<UserSearchModel, String> userLastNameColumn,
                       TableColumn<UserSearchModel, String> usernameColumn,
                       TableColumn<UserSearchModel, String> userCreatedAtColumn,
                       TableColumn<UserSearchModel, String> userIsAdminColumn,
                       TableColumn<UserSearchModel, Double> userBalanceColumn,
                       String userId) {
        this.userDAO = userDAO;
        this.userTableView = userTableView;
        this.userId = userId;
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        userLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        userCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        userIsAdminColumn.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
        userBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        styleTableColumns(userIdColumn, userFirstNameColumn, userLastNameColumn,
                usernameColumn, userCreatedAtColumn, userIsAdminColumn, userBalanceColumn);

        this.userSearchModelObservableList = FXCollections.observableArrayList();
        this.userTableView.setItems(userSearchModelObservableList);
    }

    public void initialize(TextField usersSearchBar) {
        setupUserSearchFilter(usersSearchBar);
        populateUserTableView();
    }

    private void styleTableColumns(TableColumn<?, ?>... columns) {
        for (TableColumn<?, ?> column : columns) {
            column.setStyle("-fx-alignment: CENTER;");
        }
    }

    private void setupUserSearchFilter(TextField usersSearchBar) {
        FilteredList<UserSearchModel> filteredData = new FilteredList<>(userSearchModelObservableList, b -> true);
        usersSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(userModel -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return userModelMatches(userModel, lowerCaseFilter);
            });
        });
        SortedList<UserSearchModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(userTableView.comparatorProperty());
        userTableView.setItems(sortedData);
    }

    private boolean userModelMatches(UserSearchModel userModel, String filter) {
        String isAdminString = userModel.getIsAdmin().toLowerCase();
        String balanceString = String.format("%.2f", userModel.getBalance());

        return userModel.getFirstName().toLowerCase().contains(filter)
                || userModel.getLastName().toLowerCase().contains(filter)
                || userModel.getUsername().toLowerCase().contains(filter)
                || userModel.getCreatedAt().toLowerCase().contains(filter)
                || isAdminString.contains(filter)
                || balanceString.contains(filter)
                || String.valueOf(userModel.getUserId()).contains(filter);
    }

    private void populateUserTableView() {
        try {
            List<String[]> allUsers = userDAO.getAllUsers();
            userSearchModelObservableList.clear();

            for (String[] userInfo : allUsers) {
                int userId = Integer.parseInt(userInfo[0]);
                String firstName = userInfo[1];
                String lastName = userInfo[2];
                String username = userInfo[3];
                String createdAt = userInfo[4];
                String isAdmin = userInfo[5].equalsIgnoreCase("true") ? "YES" : "NO";
                double balance = Double.parseDouble(userInfo[6]);

                UserSearchModel userModel = new UserSearchModel(userId, firstName, lastName, createdAt, username, isAdmin, balance);
                userSearchModelObservableList.add(userModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void removeUser() {
        UserSearchModel selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            int userId = selectedUser.getUserId();
            if (userId == Integer.parseInt(this.userId)) {
                System.out.println("Cannot remove yourself.");
                return;
            }

            Optional<Boolean> confirmationResult = ConfirmationDialog.show("Confirmation", "Are you sure ?");
            confirmationResult.ifPresent(result -> {
                if (result) {
                    try {
                        boolean removed = userDAO.removeUser(userId);
                        if (removed) {
                            userSearchModelObservableList.remove(selectedUser);
                            int remainingUsers = userDAO.getAllUsers().size();
                            System.out.println("User removed successfully.");
                            System.out.println("Remaining users: " + remainingUsers);
                        } else {
                            System.out.println("Failed to remove user.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            });
        } else {
            System.out.println("No user selected.");
        }
    }
}
