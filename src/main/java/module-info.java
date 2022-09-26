module mypackage.yj_tp3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens mypackage.yj_tp3 to javafx.fxml;
    exports mypackage.yj_tp3;
}