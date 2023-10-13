package br.com.eduardotoriani.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

    public static void beanNonNullProps(Object src, Object target) {
        BeanUtils.copyProperties(src, target, extractNonNullProps(src));
    }

    
    private static String[] extractNonNullProps(Object src) {
        final BeanWrapper bean = new BeanWrapperImpl(src);
        PropertyDescriptor[] props = bean.getPropertyDescriptors();
        Set<String> nullProps = new HashSet<>();

        for (PropertyDescriptor prop: props) {
            Object propValue = bean.getPropertyValue(prop.getName());
            if (propValue == null) {
                nullProps.add(prop.getName());
            }
        }

        return nullProps.toArray(new String[nullProps.size()]);
    }

}
