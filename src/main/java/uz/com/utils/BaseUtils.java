package uz.com.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Auditable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import uz.com.config.ApplicationContextProvider;
import uz.com.dto.CrudDto;
import uz.com.dto.Dto;
import uz.com.dto.GenericDto;
import uz.com.dto.TranslationDto;
import uz.com.exception.ValidatorException;
import uz.com.hibernate.dao.FunctionParam;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static uz.com.hibernate.base._Entity.getFields;

@Component
public class BaseUtils {
    private static final Logger log = LogManager.getLogger(BaseUtils.class);
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public boolean isEmpty(List<?> items) {
        return items == null || items.isEmpty();
    }

    public boolean isEmpty(Object l) {
        return l == null;
    }

    public static void error(Logger log, Exception e) {
        if (e.getCause() == null)
            log.error(e.getStackTrace(), e);
        else
            log.error(e.getStackTrace(), e.getCause());
    }

    public static Object getBean(String name) {
        return ApplicationContextProvider.applicationContext.getBean(name);
    }

    public Object parseJson(Object object, String jsonString) {
        JsonObject json = new Gson().fromJson(jsonString, JsonObject.class);
        try {
            for (Field field : getFields(object.getClass())) {
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) continue;
                field.setAccessible(true);
                if (!json.has(field.getName()))
                    continue;
                switch (field.getType().getSimpleName()) {
                    case "String": {
                        field.set(object, json.get(field.getName()).getAsString());
                    }
                    break;
                    case "Double":
                    case "double": {
                        field.set(object, json.get(field.getName()).getAsDouble());
                    }
                    break;
                    case "Boolean":
                    case "boolean": {
                        field.set(object, json.get(field.getName()).getAsBoolean());
                    }
                    break;
                    case "Integer": {
                        field.set(object, json.get(field.getName()).getAsBigInteger());
                    }
                    break;
                    case "int": {
                        field.set(object, json.get(field.getName()).getAsInt());
                    }
                    break;
                    case "Long":
                    case "long": {
                        field.set(object, json.get(field.getName()).getAsLong());
                    }
                    break;
                    case "Date": {
                        field.set(object, simpleDateFormat.parse(json.get(field.getName()).getAsString()));
                    }
                    break;
                    case "Instant": {
                        Date date = simpleDateFormat.parse(json.get(field.getName()).getAsString());
                        if (date == null)
                            field.set(object, null);
                        else
                            field.set(object, date.toInstant());
                    }
                    break;
                    case "LocalTime": {
                        if (!StringUtils.isEmpty(json.get(field.getName()).getAsString())) {
                            LocalTime time = LocalTime.parse(json.get(field.getName()).getAsString());
                            field.set(object, time);
                        } else
                            field.set(object, null);
                    }
                    break;
                    case "LocalDate": {
                        if (!StringUtils.isEmpty(json.get(field.getName()).getAsString())) {
                            LocalDate time = LocalDate.parse(json.get(field.getName()).getAsString(), dateFormat);
                            field.set(object, time);
                        } else
                            field.set(object, null);
                    }
                    break;
                    default: {
                        if (field.getType().isArray() || Collection.class.isAssignableFrom(field.getType()) || Map.class.isAssignableFrom(field.getType())) {
                            continue;
                        } else {
                            log.error(field.getType());
                        }
                    }
                }
            }
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new RuntimeException("!!!!!!!!!!!!!! PARSE ERROR !!!!!!!!!!!!!!!");
        }
    }

    public void fillTranslations(TranslationDto dto, String entityName) {
        if (isEmpty(dto)) {
            throw new ValidatorException("Translation Dto should not be null !!!");
        }

        String name = dto.getName();
        if (isEmpty(name)) {
            throw new ValidatorException(entityName + " name should not be null !!!");
        }

        if (isEmpty(dto.getNameEn())) {
            dto.setNameEn(name);
        }
        if (isEmpty(dto.getNameRu())) {
            dto.setNameRu(name);
        }
        if (isEmpty(dto.getNameUz())) {
            dto.setNameUz(name);
        }
    }

    public String generateParamText(List<FunctionParam> params) {
        StringBuilder builder = new StringBuilder();
        builder.append(" ( ");
        for (int i = 0; i < params.size(); i++) {
            if (i == 0) {
                builder.append(" ? ");
            } else {
                builder.append(" ,? ");
            }
        }
        builder.append(" ) ");
        return builder.toString();
    }

    public String encodeToBase64(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    public String toErrorParams(Object... args) {
        StringBuilder builder = new StringBuilder();
        Arrays.asList(args).forEach(t -> builder.append("#").append(toStringErrorParam(t)));
        return builder.toString().substring(1);
    }

    private String toStringErrorParam(Object argument) {

        try {
            if (((Class) argument).getGenericSuperclass().getTypeName().contains(Auditable.class.getSimpleName())) {
                return ((Class) argument).getSimpleName();
            } else if (((Class) argument).getGenericSuperclass().getTypeName().contains(Dto.class.getSimpleName())) {
                return ((Class) argument).getSimpleName();
            } else if (((Class) argument).getGenericSuperclass().getTypeName().contains(CrudDto.class.getSimpleName())) {
                return ((Class) argument).getSimpleName();
            } else if (((Class) argument).getTypeName().contains(GenericDto.class.getSimpleName())) {
                return ((Class) argument).getSimpleName();
            }
        } catch (Exception ignored) {
        }
        return argument.toString();
    }
}
