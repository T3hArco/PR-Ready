package be.ehb.swp2.util;

import be.ehb.swp2.ui.LoadingWindow;

/**
 * Created by domienhennion on 3/01/16.
 */
public class LoadingThread implements Runnable {
    public void run() {
        LoadingWindow lw = new LoadingWindow();
    }
}
