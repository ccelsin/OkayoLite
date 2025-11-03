package backend.utilities;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class BeanCopyUtils {

    private BeanCopyUtils() {}

    public static void copyNonNullProperties(Object source, Object target, String... extraIgnored) {
        String[] nullProps = getNullOrIgnoredPropertyNames(source, extraIgnored);
        // Copie toutes les propriétés sauf celles nulles / ignorées
        BeanUtils.copyProperties(source, target, nullProps);
    }

    private static String[] getNullOrIgnoredPropertyNames(Object source, String... extraIgnored) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> ignored = new HashSet<>(Arrays.asList(extraIgnored));
        ignored.add("class"); // toujours ignorer

        for (PropertyDescriptor pd : src.getPropertyDescriptors()) {
            String name = pd.getName();
            if ("class".equals(name)) continue;
            Object value = src.getPropertyValue(name);
            if (value == null) {
                ignored.add(name);
            }
        }
        return ignored.toArray(new String[0]);
    }
}