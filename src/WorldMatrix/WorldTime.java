package WorldMatrix;

public class WorldTime
{
    public double time;
    private double rate;

    public WorldTime(double rate)
    {
        this.rate = rate;
        time = 0;
    }

    public void nextTime()
    {
        time+=rate;
    }

    public double getTime() {
        return time;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    public double getTimeMod() {
        return Math.sin(time);
    }
}
