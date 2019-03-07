package com.whitedev.bkf.ui.tableview;

/**
 * Created by evrencoskun on 4.02.2018.
 */

public class MyTableViewModel {

    public static final int CHECKBOX_TYPE = 1;

    //todo modifier pour un type
    public int getCellItemViewType(int column) {

        switch (column) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                // 5. column header is gender.
                return CHECKBOX_TYPE;
            default:
                return 0;
        }
    }

    public int getCellItemViewTypeCb(String type) {

        switch (type) {
            case "CHECKBOX":
                return CHECKBOX_TYPE;
            default:
                return 0;
        }
    }


}



