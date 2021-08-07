package algonquin.cst2335.Application;

public class OCBusData {
    private String routeNum;
    private String routeName;

    public OCBusData(String routeNum, String routeName) {
        this.routeNum = routeNum;
        this.routeName = routeName;
    }

    public String getRouteNum() {
        return routeNum;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteNum(String routeNum) {
        this.routeNum = routeNum;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
}
