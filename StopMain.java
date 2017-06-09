package StartingMain;

/**
 * Created by giannis on 11/19/15.
 */
public class StopMain {
    volatile boolean stopmain;
    public StopMain(){
        this.stopmain=true;
    }

    /**
     *Sets Value for volatile variable
     */
    public void setStopmain(boolean stopmain) {
        this.stopmain = stopmain;
    }

    /**
     *Checks value of volatile variable
     */
    public boolean isStopmain() {
        return stopmain;
    }
}
