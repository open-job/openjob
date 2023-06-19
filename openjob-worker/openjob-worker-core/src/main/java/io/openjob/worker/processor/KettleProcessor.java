package io.openjob.worker.processor;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.worker.context.JobContext;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.4
 */
public class KettleProcessor extends ShellProcessor {
    private Boolean finalResult = true;

    @Override
    public ProcessResult process(JobContext context) throws IOException, InterruptedException {
        ProcessResult processResult = super.process(context);
        if (this.finalResult) {
            processResult.setStatus(TaskStatusEnum.FAILED);
        }
        return processResult;
    }

    @Override
    protected String[] parseCommand(JobContext context) {
        return context.getProcessorInfo().split(" ");
    }

    @Override
    protected void processStdout(String message) {
        Pattern pattern = Pattern.compile("Java heap space|OutOfMemoryError|Unknown database|ORA-[0-9]{1,10}:|ora-[0-9]{1,10}:|结果=\\[false]|执行该作业项时发生了一个错误|转换正在杀死其他步骤!|错误被检测到!|错误初始化步骤|Kitchen - ERROR|Pan - ERROR", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            logger.error("Kettle final result is failed!");
            finalResult = false;
        }
        super.processStdout(message);
    }

    @SuppressWarnings(value = "all")
    public long getProcessId(Process p) {
        long pid = -1;
        try {
            pid = p.pid();
        } catch (UnsupportedOperationException e) {
            try {
                //Windows
                if (p.getClass().getName().equals("java.lang.Win32Process") || p.getClass().getName().equals("java.lang.ProcessImpl")) {
                    Field f = p.getClass().getDeclaredField("handle");
                    f.setAccessible(true);
                    long handl = f.getLong(p);
                    Kernel32 kernel = Kernel32.INSTANCE;
                    WinNT.HANDLE hand = new WinNT.HANDLE();
                    hand.setPointer(Pointer.createConstant(handl));
                    pid = kernel.GetProcessId(hand);
                    f.setAccessible(false);
                }

                //Unix
                else if (p.getClass().getName().equals("java.lang.UNIXProcess")) {
                    Field f = p.getClass().getDeclaredField("pid");
                    f.setAccessible(true);
                    pid = f.getLong(p);
                    f.setAccessible(false);
                }
            } catch (Exception ex) {
                pid = -1;
            }
        }
        return pid;
    }
}
