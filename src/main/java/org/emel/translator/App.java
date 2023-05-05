package org.emel.translator;

import org.antlr.v4.runtime.*;
import org.emel.antlr4.gen.JSONLexer;
import org.emel.antlr4.gen.JSONParser;

import java.io.IOException;
import java.util.Map;

/**
 * Приложение для трансляции файлов формата JSON в Java структуры данных
 */
public class App {

    public static void main(String[] args) throws IOException {
        // чтение json файла
        CharStream jsonStream = CharStreams.fromFileName("src/main/resources/data.json");
        // лексический анализ и парсинг json
        JSONLexer lexer = new JSONLexer(jsonStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JSONParser parser = new JSONParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());
        JSONParser.JsonContext json = parser.json();
        // обход json и трансляция в Java структуру данных Map
        MyJsonVisitor jsonVisitor = new MyJsonVisitor();
        Map<Object, Object> map =  (Map<Object, Object>) jsonVisitor.visitJson(json);
        System.out.println(map.get("student2")); // вывод данных объекта с ключом 'student2'
    }

}
