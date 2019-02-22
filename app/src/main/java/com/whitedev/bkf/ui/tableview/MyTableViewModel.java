package com.whitedev.bkf.ui.tableview;

import android.view.Gravity;
import com.whitedev.bkf.data.network.pojo.TableList;
import com.whitedev.bkf.ui.tableview.model.CellModel;
import com.whitedev.bkf.ui.tableview.model.ColumnHeaderModel;
import com.whitedev.bkf.ui.tableview.model.RowHeaderModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evrencoskun on 4.02.2018.
 */

public class MyTableViewModel {
    // View Types
    public static final int GENDER_TYPE = 1;
    public static final int MONEY_TYPE = 2;

    private List<ColumnHeaderModel> mColumnHeaderModelList;
    private List<RowHeaderModel> mRowHeaderModelList;
    private List<List<CellModel>> mCellModelList;

    //todo modifier pour un type
    public int getCellItemViewType(int column) {

        switch (column) {
            case 5:
                // 5. column header is gender.
                return GENDER_TYPE;
            case 8:
                // 8. column header is Salary.
                return MONEY_TYPE;
            default:
                return 0;
        }
    }

     /*
       - Each of Column Header -
            "Id"
            "Name"
            "Nickname"
            "Email"
            "Birthday"
            "Gender"
            "Age"
            "Job"
            "Salary"
            "CreatedAt"
            "UpdatedAt"
            "Address"
            "Zip Code"
            "Phone"
            "Fax"
     */

    public int getColumnTextAlign(int column) {
        switch (column) {
            // Id
            case 0:
                return Gravity.CENTER;
            // Name
            case 1:
                return Gravity.LEFT;
            // Nickname
            case 2:
                return Gravity.LEFT;
            // Email
            case 3:
                return Gravity.LEFT;
            // BirthDay
            case 4:
                return Gravity.CENTER;
            // Gender (Sex)
            case 5:
                return Gravity.CENTER;
            // Age
            case 6:
                return Gravity.CENTER;
            // Job
            case 7:
                return Gravity.LEFT;
            // Salary
            case 8:
                return Gravity.CENTER;
            // CreatedAt
            case 9:
                return Gravity.CENTER;
            // UpdatedAt
            case 10:
                return Gravity.CENTER;
            // Address
            case 11:
                return Gravity.LEFT;
            // Zip Code
            case 12:
                return Gravity.RIGHT;
            // Phone
            case 13:
                return Gravity.RIGHT;
            // Fax
            case 14:
                return Gravity.RIGHT;
            default:
                return Gravity.CENTER;
        }

    }

    private List<ColumnHeaderModel> createColumnHeaderModelList() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel("Id"));
        list.add(new ColumnHeaderModel("Name"));
        list.add(new ColumnHeaderModel("Nickname"));
        list.add(new ColumnHeaderModel("Email"));
        list.add(new ColumnHeaderModel("Birthday"));
        list.add(new ColumnHeaderModel("Sex"));
        list.add(new ColumnHeaderModel("Age"));
        list.add(new ColumnHeaderModel("Job"));
        list.add(new ColumnHeaderModel("Salary"));
        list.add(new ColumnHeaderModel("CreatedAt"));
        list.add(new ColumnHeaderModel("UpdatedAt"));
        list.add(new ColumnHeaderModel("Address"));
        list.add(new ColumnHeaderModel("Zip Code"));
        list.add(new ColumnHeaderModel("Phone"));
        list.add(new ColumnHeaderModel("Fax"));
        list.add(new ColumnHeaderModel("Fax"));
        list.add(new ColumnHeaderModel("Fax"));
        list.add(new ColumnHeaderModel("Fax"));

        return list;
    }

    private List<List<CellModel>> createCellModelList(List<TableList> tableList) {
        List<List<CellModel>> lists = new ArrayList<>();

        // Creating cell model list from User list for Cell Items
        // In this example, User list is populated from web service

        for (int i = 0; i < tableList.size(); i++) {
            TableList listT = tableList.get(i);

            List<CellModel> list = new ArrayList<>();

            // The order should be same with column header list;
            list.add(new CellModel("1-" + i, listT.getName()));
            list.add(new CellModel("2-" + i, listT.getDisplay()));
            list.add(new CellModel("3-" + i, listT.getType()));

            // Add
            lists.add(list);
        }

        return lists;
    }

    private List<RowHeaderModel> createRowHeaderList(int size) {
        List<RowHeaderModel> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            // In this example, Row headers just shows the index of the TableView List.
            list.add(new RowHeaderModel(String.valueOf(i + 1)));
        }
        return list;
    }


    public List<ColumnHeaderModel> getColumHeaderModeList() {
        return mColumnHeaderModelList;
    }

    public List<RowHeaderModel> getRowHeaderModelList() {
        return mRowHeaderModelList;
    }

    public List<List<CellModel>> getCellModelList() {
        return mCellModelList;
    }


    public void generateListForTableView(List<TableList> users) {
        mColumnHeaderModelList = createColumnHeaderModelList();
        mCellModelList = createCellModelList(users);
        mRowHeaderModelList = createRowHeaderList(users.size());
    }

}



