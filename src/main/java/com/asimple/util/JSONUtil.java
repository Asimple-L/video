package com.asimple.util;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import org.apache.commons.beanutils.BeanUtils;

import java.util.*;

/**
 * @author Asimple JSON工具类
 */
public class JSONUtil {

    /***
     * 将List对象序列化为JSON文本
     */
    public static <T> String toJSONString(List<T> list) {

        JSONArray jsonArray = JSONArray.fromObject(list);

        return jsonArray.toString();
    }

    /***
     * 将对象序列化为JSON文本
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        JSONArray jsonArray = JSONArray.fromObject(object);

        return jsonArray.toString();
    }

    public static Map<String, Object> toStringForMap(String jsonString) {
        HashMap<String, Object> data = new HashMap<>(4);
        JSONObject jsonObject = JSONUtil.toJSONObject(jsonString);
        Iterator it = jsonObject.keys();
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            Object value = jsonObject.get(key);
            data.put(key, value);
        }
        return data;
    }


    /***
     * 将JSON对象数组序列化为JSON文本(未测)
     * @param jsonArray
     * @return
     */
    public static String toJSONString(JSONArray jsonArray) {
        return jsonArray.toString();
    }

    /***
     * 将JSON对象序列化为JSON文本(未测)
     * @param jsonObject
     * @return
     */
    public static String toJSONString(JSONObject jsonObject) {
        return jsonObject.toString();
    }

    /***
     * 将对象转换为List对象
     * @param object
     * @return
     */
    public static List toArrayList(Object object) {
        List arrayList = new ArrayList();

        JSONArray jsonArray = JSONArray.fromObject(object);

        Iterator it = jsonArray.iterator();
        while (it.hasNext()) {
            JSONObject jsonObject = (JSONObject) it.next();

            Iterator keys = jsonObject.keys();
            while (keys.hasNext()) {
                Object key = keys.next();
                Object value = jsonObject.get(key);
                arrayList.add(value);
            }
        }

        return arrayList;
    }

    /***
     * 将对象转换为Collection对象
     * @param object
     * @return
     */
    public static Collection toCollection(Object object) {
        JSONArray jsonArray = JSONArray.fromObject(object);

        return JSONArray.toCollection(jsonArray);
    }

    /***
     * 将对象转换为JSON对象数组
     * @param object
     * @return
     */
    public static JSONArray toJSONArray(Object object) {
        return JSONArray.fromObject(object);
    }

    /***
     * 将对象转换为JSON对象
     * @param object
     * @return
     */
    public static JSONObject toJSONObject(Object object) {
        return JSONObject.fromObject(object);
    }


    /***
     * 将对象转换为HashMap
     * @param object
     * @return
     */
    public static HashMap toHashMap(Object object) {
        HashMap<String, Object> data = new HashMap<>(16);
        JSONObject jsonObject = JSONUtil.toJSONObject(object);
        Iterator it = jsonObject.keys();
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            Object value = jsonObject.get(key);
            data.put(key, value);
        }

        return data;
    }

    /***
     * 将对象转换为List>(未测)
     * @param object
     * @return
     */
    // 返回非实体类型(Map)的List
    public static List<Map<String, Object>> toList(Object object) {
        List<Map<String, Object>> list = new ArrayList<>();
        JSONArray jsonArray = JSONArray.fromObject(object);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            Map<String, Object> map = new HashMap<>(16);
            Iterator it = jsonObject.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                Object value = jsonObject.get(key);
                map.put((String) key, value);
            }
            list.add(map);
        }
        return list;
    }

    /***
     * 将JSON对象数组转换为传入类型的List(未测)
     * @param
     * @param jsonArray
     * @param objectClass
     * @return
     */
    public static <T> List<T> toList(JSONArray jsonArray, Class<T> objectClass) {
        return JSONArray.toList(jsonArray, objectClass, new JsonConfig());
    }

    /***
     * 将对象转换为传入类型的List(未测)
     * @param
     * @param //jsonArray
     * @param objectClass
     * @return
     */
    public static <T> List<T> toList(Object object, Class<T> objectClass) {
        JSONArray jsonArray = JSONArray.fromObject(object);

        return JSONArray.toList(jsonArray, objectClass, new JsonConfig());
    }

    /***
     * 将JSON对象转换为传入类型的对象(未测)
     * @param
     * @param jsonObject
     * @param beanClass
     * @return
     */
    public static <T> T toBean(JSONObject jsonObject, Class<T> beanClass) {
        return (T) JSONObject.toBean(jsonObject, beanClass);
    }

    /***
     * 将将对象转换为传入类型的对象(未测)
     * @param
     * @param object
     * @param beanClass
     * @return
     */
    public static <T> T toBean(Object object, Class<T> beanClass) {
        JSONObject jsonObject = JSONObject.fromObject(object);

        return (T) JSONObject.toBean(jsonObject, beanClass);
    }

    /***
     * 将JSON文本反序列化为主从关系的实体(未测)
     * @param //泛型T 代表主实体类型
     * @param //泛型D 代表从实体类型
     * @param jsonString JSON文本
     * @param mainClass 主实体类型
     * @param detailName 从实体类在主实体类中的属性名称
     * @param detailClass 从实体类型
     * @return
     */
    public static <T, D> T toBean(String jsonString, Class<T> mainClass,
                                  String detailName, Class<D> detailClass) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray jsonArray = (JSONArray) jsonObject.get(detailName);

        T mainEntity = JSONUtil.toBean(jsonObject, mainClass);
        List<D> detailList = JSONUtil.toList(jsonArray, detailClass);

        try {
            BeanUtils.setProperty(mainEntity, detailName, detailList);
        } catch (Exception ex) {
            throw new RuntimeException("主从关系JSON反序列化实体失败！");
        }

        return mainEntity;
    }

    /***
     * 将JSON文本反序列化为主从关系的实体(未测)
     * @param //泛型T 代表主实体类型
     * @param// 泛型D1 代表从实体类型
     * @param //泛型D2 代表从实体类型
     * @param jsonString JSON文本
     * @param mainClass 主实体类型
     * @param detailName1 从实体类在主实体类中的属性
     * @param detailClass1 从实体类型
     * @param detailName2 从实体类在主实体类中的属性
     * @param detailClass2 从实体类型
     * @return
     */
    public static <T, D1, D2> T toBean(String jsonString, Class<T> mainClass,
                                       String detailName1, Class<D1> detailClass1, String detailName2,
                                       Class<D2> detailClass2) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
        JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);

        T mainEntity = JSONUtil.toBean(jsonObject, mainClass);
        List<D1> detailList1 = JSONUtil.toList(jsonArray1, detailClass1);
        List<D2> detailList2 = JSONUtil.toList(jsonArray2, detailClass2);

        try {
            BeanUtils.setProperty(mainEntity, detailName1, detailList1);
            BeanUtils.setProperty(mainEntity, detailName2, detailList2);
        } catch (Exception ex) {
            throw new RuntimeException("主从关系JSON反序列化实体失败！");
        }

        return mainEntity;
    }

    /***
     * 将JSON文本反序列化为主从关系的实体(未测)
     * @param //泛型T 代表主实体类型
     * @param //泛型D1 代表从实体类型
     * @param //泛型D2 代表从实体类型
     * @param jsonString JSON文本
     * @param mainClass 主实体类型
     * @param detailName1 从实体类在主实体类中的属性
     * @param detailClass1 从实体类型
     * @param detailName2 从实体类在主实体类中的属性
     * @param detailClass2 从实体类型
     * @param detailName3 从实体类在主实体类中的属性
     * @param detailClass3 从实体类型
     * @return
     */
    public static <T, D1, D2, D3> T toBean(String jsonString,
                                           Class<T> mainClass, String detailName1, Class<D1> detailClass1,
                                           String detailName2, Class<D2> detailClass2, String detailName3,
                                           Class<D3> detailClass3) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
        JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);
        JSONArray jsonArray3 = (JSONArray) jsonObject.get(detailName3);

        T mainEntity = JSONUtil.toBean(jsonObject, mainClass);
        List<D1> detailList1 = JSONUtil.toList(jsonArray1, detailClass1);
        List<D2> detailList2 = JSONUtil.toList(jsonArray2, detailClass2);
        List<D3> detailList3 = JSONUtil.toList(jsonArray3, detailClass3);

        try {
            BeanUtils.setProperty(mainEntity, detailName1, detailList1);
            BeanUtils.setProperty(mainEntity, detailName2, detailList2);
            BeanUtils.setProperty(mainEntity, detailName3, detailList3);
        } catch (Exception ex) {
            throw new RuntimeException("主从关系JSON反序列化实体失败！");
        }

        return mainEntity;
    }

    public static ArrayList<JSONObject> getJsonArrayForString(String jsonArray) {
        ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
        try {
            JSONArray array = new JSONArray();
            array.add(jsonArray);
            for (int i = 0; i < array.size(); i++) {
                arrayList.add(array.getJSONObject(i));
            }
        } catch (JSONException e) {
        }
        return arrayList;
    }

    /**
     * 将json字符串转化为对象
     *
     * @param jsonString json字符串
     * @param pojoCalss  要转化为的类
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String jsonString, Class<T> pojoCalss) {
        try {
            Object pojo;
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"}));
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"}));
            pojo = JSONObject.toBean(jsonObject, pojoCalss);
            return (T) pojo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /***
     * 将JSON文本反序列化为主从关系的实体(未测)
     * @param //主实体类型
     * @param jsonString JSON文本
     * @param mainClass 主实体类型
     * @param detailClass 存放了多个从实体在主实体中属性名称和类型
     * @return
     */
    public static <T> T toBean(String jsonString, Class<T> mainClass,
                               HashMap<String, Class> detailClass) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        T mainEntity = JSONUtil.toBean(jsonObject, mainClass);
        for (Object key : detailClass.keySet()) {
            try {
                Class value = (Class) detailClass.get(key);
                BeanUtils.setProperty(mainEntity, key.toString(), value);
            } catch (Exception ex) {
                throw new RuntimeException("主从关系JSON反序列化实体失败！");
            }
        }
        return mainEntity;
    }
}
