package Model;

public class EventModel {

    public String date;
    public String time;
    public String description;
    public String Raw_Year;
    public String Raw_Month;
    public String Raw_Day;
    public String Raw_Hour;
    public String Raw_Min;

    public EventModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public EventModel(String date, String time, String description ,String Raw_Year,String Raw_Month,String Raw_Day,String Raw_Hour, String Raw_Min) {
        this.date = date;
        this.time = time;
        this.description = description;

        this.Raw_Year = Raw_Year;
        this.Raw_Month = Raw_Month;
        this.Raw_Day = Raw_Day;
        this.Raw_Hour = Raw_Hour;
        this.Raw_Min = Raw_Min;
    }

}
