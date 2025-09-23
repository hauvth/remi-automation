package page;

import annotation.Platform;
import driver.DriverManager;
import org.reflections.Reflections;

import java.util.Objects;
import java.util.Set;

public class PageFactory {
    @SuppressWarnings("unchecked")
    public static <T> T getPage(Class<T> pageInterface) {
        String platformName = Objects.requireNonNull
                (DriverManager.getDriver().getCapabilities().getPlatformName()).toString().toLowerCase();
        Reflections reflections = new Reflections("page");
        Set<Class<? extends T>> pageClasses = reflections.getSubTypesOf(pageInterface);

        for (Class<? extends T> pageClass : pageClasses) {
            Platform platformAnnotation = pageClass.getAnnotation(Platform.class);
            if (platformAnnotation != null && platformAnnotation.value().toLowerCase().equals(platformName)) {
                try {
                    return (T) pageClass.getConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Failed to instantiate " + pageClass.getSimpleName(), e);
                }
            }
        }
        throw new IllegalArgumentException("No implementation found for " + pageInterface.getSimpleName() + " on platform: " + platformName);
    }
}
