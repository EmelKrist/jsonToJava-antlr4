package org.emel.translator;

import org.emel.antlr4.gen.JSONBaseVisitor;
import org.emel.antlr4.gen.JSONParser;

import java.util.*;

/**
 * Класс для обхода json (полученное дерево синтаксического анализа)
 * и трансляции в Java структуры данных
 */
public class MyJsonVisitor extends JSONBaseVisitor {

    /**
     * Запись с парой ключ-значение для map
     */
    static class Entry {
        String key;
        Object value;
    }

    /**
     * Метод для обхода json и трансляции его в Java структуры данных
     *
     * @param ctx json дерево синтаксического анализа
     * @return объект с результатом трансляции json
     */
    @Override
    public Object visitJson(JSONParser.JsonContext ctx) {
        if (ctx.object() != null) { // если в json распознан объект
            return visitObject(ctx.object());
        } else if (ctx.array() != null) { // если в json распознан массив
            return visitArray(ctx.array());
        }
        throw new RuntimeException("Недопустимый формат JSON!");
    }

    /**
     * Метод для обхода объекта json и трансляции его в Java структуру данных (Map)
     *
     * @param ctx json поддерево синтаксического анализа
     * @return результат 'посещения' объекта json
     */
    @Override
    public Object visitObject(JSONParser.ObjectContext ctx) {
        Map<String, Object> map = new HashMap<>();
        // получаем все пары клю-значение и добавляем в map
        ctx.pair().forEach(pair -> {
            Entry entry = visitPair(pair);
            map.put(entry.key, entry.value);
        });
        return map;
    }

    /**
     * Метод для обхода массива json и трансляции его в Java структуру данных (List)
     *
     * @param ctx json поддерево синтаксического анализа
     * @return список значений с результатом посещения массива json
     */
    @Override
    public List<Object> visitArray(JSONParser.ArrayContext ctx) {
        List<Object> values = new LinkedList<>();
        ctx.value().forEach(val ->
                values.add(visitValue(val))
        );
        return values;
    }

    /**
     * Метод для получения пары ключ-значение json и трансляции в еntry для map
     *
     * @param ctx json поддерево синтаксического анализа
     * @return пара ключ-значение
     */
    @Override
    public Entry visitPair(JSONParser.PairContext ctx) {
        Entry entry = new Entry();
        entry.key = ctx.STRING().getText().replaceAll("\"", "");
        entry.value = visitValue(ctx.value());
        return entry;
    }

    /**
     * Метод для получения значения json и трансляции его в Java объект
     *
     * @param ctx json поддерево синтаксического анализа
     * @return объект со значением/след.структурой json
     */
    @Override
    public Object visitValue(JSONParser.ValueContext ctx) {
        if (ctx.NUMBER() != null) { // в json распознано число
            return ctx.NUMBER().getText().replaceAll("\"", "");
        } else if (ctx.STRING() != null) { // в json распознана строка
            return ctx.STRING().getText().replaceAll("\"", "");
        } else if (ctx.getChildCount() == 0) { // если потомков у узла больше нет
            return ctx.getText().replaceAll("\"", "");
        } else if (ctx.array() != null) { // если в json распознан массив
            return visitArray(ctx.array());
        } else if (ctx.object() != null) { // если в json распознан объект
            return visitObject(ctx.object());
        }
        throw new RuntimeException("Недопустимый символ " + ctx.getRuleIndex() + " на позиции: " + ctx.depth() + ctx.start);
    }
}