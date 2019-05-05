package data.Subscriprions;

public class User {
    private String userChatId;
    private String weatherSubscription;
    private String berthsSelected;
    private String berthStatusSubscription;
    private String berthUpdateSubscription;
    private String status;
    private String info;

    public User(){

    }

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

    public User setUserChatId(String userChatId) {
        this.userChatId = userChatId;
        return this;
    }

    public String getWeatherSubscription() {
        return weatherSubscription;
    }

    public User setWeatherSubscription(String weatherSubscription) {
        this.weatherSubscription = weatherSubscription;
        return this;
    }

    public String getBerthsSelected() {
        return berthsSelected;
    }

    public User setBerthsSelected(String berthsSelected) {
        this.berthsSelected = berthsSelected;
        return this;
    }

    public String getBerthStatusSubscription() {
        return berthStatusSubscription;
    }

    public User setBerthStatusSubscription(String berthStatusSubscription) {
        this.berthStatusSubscription = berthStatusSubscription;
        return this;
    }

    public String getBerthUpdateSubscription() {
        return berthUpdateSubscription;
    }

    public User setBerthUpdateSubscription(String berthUpdateSubscription) {
        this.berthUpdateSubscription = berthUpdateSubscription;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public User setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public User setInfo(String info) {
        this.info = info;
        return this;
    }
}
