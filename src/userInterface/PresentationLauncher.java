package userInterface;

/**
 * Created by Seb on 25/05/2016.
 */
public class PresentationLauncher implements Controllable {
    private TheScreen screenParent;
    private Main mainApp;

    @Override
    public void setScreenParent(TheScreen screenParent) {
        this.screenParent = screenParent;
    }

    @Override
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}
