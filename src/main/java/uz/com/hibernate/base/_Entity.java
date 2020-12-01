package uz.com.hibernate.base;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.proxy.HibernateProxy;
import uz.com.enums.State;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@MappedSuperclass
@Data
public abstract class _Entity implements Serializable {

    @Transient
    private static final Logger log = LogManager.getLogger(_Entity.class);
    @Transient
    public static HashMap<Class, List<Field>> fieldsMap = new HashMap<>();
    @Transient
    public static HashMap<Class, HashMap<String, Method>> methodsMap = new HashMap<>();

    public static String getEntityName(_Entity o) {
        if (o instanceof HibernateProxy) {
            HibernateProxy proxy = (HibernateProxy) o;
            return proxy.getHibernateLazyInitializer().getEntityName();
        }
        Entity entity = o.getClass().getAnnotation(Entity.class);
        if (entity != null) {
            return !"".equals(entity.name()) ? entity.name() : o.getClass().getName();
        }
        return "";
    }

    public static List<Field> getFields(Class<?> clazz) {
        if (!fieldsMap.containsKey(clazz)) {
            Class<?> cls = clazz;
            List<Field> fields = new ArrayList<>();
            HashMap<String, Method> methods = new HashMap<>();
            while (!_Entity.class.equals(cls)) {
                fields.addAll(Arrays.asList(cls.getDeclaredFields()));
                Arrays.stream(cls.getDeclaredMethods()).forEach(method -> methods.put(method.getName(), method));
                cls = cls.getSuperclass();
            }
            fields = fields.stream().filter(field -> !field.getName().startsWith("$$")).collect(Collectors.toList());
            fieldsMap.put(clazz, fields);
            methodsMap.put(clazz, methods);
        }
        return fieldsMap.get(clazz);
    }

    public abstract Long getId();

    public abstract void setId(Long id);

    public abstract State getState();

    public abstract void setState(State state);

    public Boolean isNew() {
        return getId() == null;
    }

    public String toString() {
        return "[id=" + getId() + "]";
    }

    public int hashCode() {
        return (getId() != null ? getId().hashCode() : 0);
    }
}
