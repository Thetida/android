package com.MyHook;

/**
 * Created by giannis on 11/19/15.
 */
public class Stopper {
    volatile boolean checker;
    public Stopper(){
        this.checker=true;
    }

    /**
     *Checks value of volatile variable
     */
    public boolean isChecker() {
        return checker;
    }

    /**
     *Sets value of volatile variable
     */
    public void setChecker(boolean checker) {
        this.checker = checker;
    }
}
