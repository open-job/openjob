package io.openjob.worker.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.4
 */
public class KettleProcessorTest {
    @Test
    public void testPattern() {
        Pattern pattern = Pattern.compile("Java heap space|OutOfMemoryError|Unknown database|ORA-[0-9]{1,10}:|"
                + "ora-[0-9]{1,10}:|结果=\\[false]|执行该作业项时发生了一个错误|转换正在杀死其他步骤!|错误被检测到!|"
                + "错误初始化步骤|Kitchen - ERROR|Pan - ERROR|Error occurred while trying to connect to the database|"
                + "无法初始化至少一个步骤\\. {2}执行无法开始!|We failed to initialize at least one step\\. {2}Execution can not begin!|"
                + "Error initializing step|failed to initialize!|Errors detected!|result=\\[false]|"
                + "Transformation is killing the other steps!");

        String input = "Java heap space occurred at line 123";
        Matcher matcher = pattern.matcher(input);
        Assertions.assertTrue(matcher.find());

        String input2 = "Java 结果=[false] ";
        Matcher matcher2 = pattern.matcher(input2);
        Assertions.assertTrue(matcher2.find());

        String input3 = "Java result=[false] ";
        Matcher matcher3 = pattern.matcher(input3);
        Assertions.assertTrue(matcher3.find());

        String input4 = "Java Error occurred while trying to connect to the database ";
        Matcher matcher4 = pattern.matcher(input4);
        Assertions.assertTrue(matcher4.find());

        String input5 = "无法初始化至少一个步骤.  执行无法开始!";
        Matcher matcher5 = pattern.matcher(input5);
        Assertions.assertTrue(matcher5.find());

        String input6 = "We failed to initialize at least one step.  Execution can not begin!";
        Matcher matcher6 = pattern.matcher(input6);
        Assertions.assertTrue(matcher6.find());
    }
}
