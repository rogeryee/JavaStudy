package com.yee.study.java;

import com.yee.study.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Roger.Yi
 */
public class Test {

    public static void main(String[] args) {

        int handsome = 1;
        int rich = 5;
        handsome = handsome ^ rich;
        rich = handsome ^ rich;
        handsome = rich ^ handsome;

        ThreadLocal tl;
        ThreadPoolExecutor tpe;

        System.out.println(20210416100102L / 1000000);

        Map<String,Integer> map = new HashMap<>();
        map.put("1",1);
        map.put("2",2);
        map.put("3",3);
        map.put("4",4);

        Map<String,Integer> map2 = new HashMap<>();
        map2.put("1",10);
        map2.put("2",20);
        map2.put("3",30);
        map2.put("5",50);
        map2.forEach((key, value) -> {
            map.computeIfPresent(key, (k, v) -> {
                return v + value;
            });
        });

        System.out.println(map);

        Boolean[] list = new Boolean[] {true, false, true};
        Arrays.stream(list).forEach(ret -> {
//            ret.
        });

        // a && b || c && d || e && f
        // (a && b) || (c && d) || (e && f)

        boolean a = true, b = false, c = true, d = true;
        System.out.println("(a && b) || (c && d) = " + ((a && b) || (c && d)));
        System.out.println("a && b || c && d = " + (a && b || c && d));

        System.out.println("(a && b) || c = " + ((a && b) || c));
        System.out.println("a && b || c = " + (a && b || c));

        //测试SpringEL解析器
        String template = "#{#user}，早上好";//设置文字模板,其中#{}表示表达式的起止，#user是表达式字符串，表示引用一个变量。
        ExpressionParser paser = new SpelExpressionParser();//创建表达式解析器

        //通过evaluationContext.setVariable可以在上下文中设定变量。
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("user","黎明");

        //解析表达式，如果表达式是一个模板表达式，需要为解析传入模板解析器上下文。
        Expression expression = paser.parseExpression(template,new TemplateParserContext());

        //使用Expression.getValue()获取表达式的值，这里传入了Evalution上下文，第二个参数是类型参数，表示返回值的类型。
        System.out.println(expression.getValue(context,String.class));
    }
}

@AllArgsConstructor
@Data
class LineCode {
    private String code;
    private String name;
}

interface ConfData<T> {
    T getData();
}

interface ConfGetter<D extends ConfData> {
    D getData(String dataId);
    void init();
    void refresh();
}

abstract class ListConfData<T> implements ConfData<List<T>> {
    private List<T> data;

    @Override
    public List<T> getData() {
        return data;
    }
}

class ConfLineCodeData extends ListConfData<LineCode> {
}

class ConfLineCodeGetter implements ConfGetter<ConfLineCodeData> {

    private Map<String, ConfLineCodeData> configData;

    @Override
    public ConfLineCodeData getData(String dataId) {
        return null;
    }

    @Override
    public void init() {
        configData = new ConcurrentHashMap<>();
    }

    @Override
    public void refresh() {

    }
}

interface ConfDataService {
    ConfData getConfData(String type, String dataId);
}

interface ConfDataSource {

}
