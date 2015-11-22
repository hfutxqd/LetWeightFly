package cf.imxqd.LetWeightFly.Application;

/**
 * Created by Henry on 2015/7/18.
 *
 */
public class Application extends android.app.Application{
    private static Application app;

    public static Application newInstance()
    {
        if(app != null)
        {
            app = new Application();
        }
        return app;
    }

    private Application() {
        super();
    }
}
