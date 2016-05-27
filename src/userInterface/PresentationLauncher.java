package userInterface;

/**
 * Created by Seb on 25/05/2016.
 */
public class PresentationLauncher implements Controllable {
    private StackPaneUpdater screenParent;
    private Main mainApp;

    @Override
    public void setScreenParent(StackPaneUpdater screenParent) {
        this.screenParent = screenParent;
    }

    @Override
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}
