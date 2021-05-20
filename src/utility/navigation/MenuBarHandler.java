package utility.navigation;

import view.appHome.HomeStageController;

public class MenuBarHandler {
    private static int menuNumber = 0;
    private static HomeStageController homeStageController = null;

    public static HomeStageController getHomeController() {
        return homeStageController;
    }

    public static void setHomeController(HomeStageController homeStageController) {
        MenuBarHandler.homeStageController = homeStageController;
    }

    public static int getMenuNumber() {
        return menuNumber;
    }

    public static void setMenuNumber(int menuNumber) {
        MenuBarHandler.menuNumber = menuNumber;
    }
}
