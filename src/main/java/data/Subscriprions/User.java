package data.Subscriprions;

public class User {
    private String userChatId;
    private String weatherSubscription;
    private String berthsSelected;
    private String berthStatusSubscription;
    private String berthUpdateSubscription;
    private String status;
    private String info;

    public User(String userChatId, String info){
        this.userChatId = userChatId;
        this.weatherSubscription = "false";
        this.berthsSelected = "0";
        this.berthStatusSubscription = "false";
        this.berthUpdateSubscription = "false";
        this.status = "user";
        this.info = info;
    }

    public User(String userChatId, String weatherSubscription, String berthsSelected,
                String berthStatusSubscription, String berthUpdateSubscription, String status,
                String info) {
        this.userChatId = userChatId;
        this.weatherSubscription = weatherSubscription;
        this.berthsSelected = berthsSelected;
        this.berthStatusSubscription = berthStatusSubscription;
        this.berthUpdateSubscription = berthUpdateSubscription;
        this.status = status;
        this.info = info;
    }

    public String getUserChatId() {
        return userChatId;
    }

    public void setUserChatId(String userChatId) {
        this.userChatId = userChatId;
    }

    public String getWeatherSubscription() {
        return weatherSubscription;
    }

    public void setWeatherSubscription(String weatherSubscription) {
        this.weatherSubscription = weatherSubscription;
    }

    public String getBerthsSelected() {
        return berthsSelected;
    }

    public void setBerthsSelected(String berthsSelected) {
        this.berthsSelected = berthsSelected;
    }

    public String getBerthStatusSubscription() {
        return berthStatusSubscription;
    }

    public void setBerthStatusSubscription(String berthStatusSubscription) {
        this.berthStatusSubscription = berthStatusSubscription;
    }

    public String getBerthUpdateSubscription() {
        return berthUpdateSubscription;
    }

    public void setBerthUpdateSubscription(String berthUpdateSubscription) {
        this.berthUpdateSubscription = berthUpdateSubscription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
