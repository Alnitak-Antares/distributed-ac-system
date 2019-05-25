package app.dto;

public class RoomState {
    private int nowTemp;
    private int tarTemp;
    private int starTemp;
    private enum FunSpeed {LOW, MIDDLE, HIGH};
    private String startime;
    private String stoptime;
    private int totaltime;
    private Boolean power;

    public int getNowTemp() {
        return nowTemp;
    }

    public int getTarTemp() {
        return tarTemp;
    }

    public int getStarTemp() {
        return starTemp;
    }

    public String getStartime() {
        return startime;
    }

    public String getStoptime() {
        return stoptime;
    }

    public int getTotaltime() {
        return totaltime;
    }

    public Boolean getPower() {
        return power;
    }

    public void setNowTemp(int nowTemp) {
        this.nowTemp = nowTemp;
    }

    public void setTarTemp(int tarTemp) {
        this.tarTemp = tarTemp;
    }

    public void setStarTemp(int starTemp) {
        this.starTemp = starTemp;
    }

    public void setStartime(String startime) {
        this.startime = startime;
    }

    public void setStoptime(String stoptime) {
        this.stoptime = stoptime;
    }

    public void setTotaltime(int totaltime) {
        this.totaltime = totaltime;
    }

    public void setPower(Boolean power) {
        this.power = power;
    }
}
