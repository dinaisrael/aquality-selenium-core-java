package tests.application;

import aquality.selenium.core.application.AqualityModule;
import aquality.selenium.core.application.AqualityServices;
import com.google.inject.Injector;
import tests.application.browser.ChromeApplication;

public class CustomAqualityServices extends AqualityServices<ChromeApplication> {
    private static final ThreadLocal<CustomAqualityServices> INSTANCE_CONTAINER = ThreadLocal.withInitial(CustomAqualityServices::new);

    private CustomAqualityServices() {
        super(CustomAqualityServices::getApplication, null);
    }

    private <T extends AqualityModule<ChromeApplication>>  CustomAqualityServices(T module) {
        super(CustomAqualityServices::getApplication, () -> module);
    }

    private static CustomAqualityServices getInstance() {
        return INSTANCE_CONTAINER.get();
    }

    public static ChromeApplication getApplication() {
        return getInstance().getApp(injector -> tests.application.browser.AqualityServices.getApplication());
    }

    public static Injector getServiceProvider() {
        return getInstance().getInjector();
    }

    public static <T extends AqualityModule<ChromeApplication>> void initInjector(T module) {
        if (INSTANCE_CONTAINER.get() != null){
            INSTANCE_CONTAINER.remove();
        }
        INSTANCE_CONTAINER.set(new CustomAqualityServices(module));
    }
}