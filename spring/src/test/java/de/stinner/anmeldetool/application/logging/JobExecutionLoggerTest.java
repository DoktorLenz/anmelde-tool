package de.stinner.anmeldetool.application.logging;

import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.Task;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(OutputCaptureExtension.class)
class JobExecutionLoggerTest {

    @Test
    void logNextExecutionDates_forTriggerTaskWithFurtherExecutions(CapturedOutput output) {
        ScheduledAnnotationBeanPostProcessor postProcessor = mock(ScheduledAnnotationBeanPostProcessor.class);
        JobExecutionLogger jobExecutionLogger = new JobExecutionLogger(postProcessor);

        Instant now = Instant.now();
        String taskName = "scheduledTriggerTask";

        Set<ScheduledTask> tasks = new HashSet<>();
        tasks.add(getScheduledTriggerTask(taskName, now, null));

        Mockito.when(postProcessor.getScheduledTasks()).thenReturn(tasks);

        jobExecutionLogger.logNextExecutionsOfAllTasks();

        assertThat(output.getOut()).contains(taskName, now.toString());
    }

    private ScheduledTask getScheduledTriggerTask(String taskName, Instant nextExecution, String runnableName) {
        CronTrigger cronTrigger = mock(CronTrigger.class);
        Mockito.when(cronTrigger.nextExecution(any())).thenReturn(nextExecution);

        Runnable runnable = mock(Runnable.class);
        Mockito.when(runnable.toString()).thenReturn(runnableName);

        TriggerTask triggerTask = mock(TriggerTask.class);
        Mockito.when(triggerTask.getTrigger()).thenReturn(cronTrigger);
        Mockito.when(triggerTask.getRunnable()).thenReturn(runnable);

        ScheduledTask scheduledTask = mock(ScheduledTask.class);
        Mockito.when(scheduledTask.toString()).thenReturn(taskName);
        Mockito.when(scheduledTask.getTask()).thenReturn(triggerTask);

        return scheduledTask;
    }

    @Test
    void logNextExecutionDates_forTriggerTaskWithNoFurtherExecutions(CapturedOutput output) {
        ScheduledAnnotationBeanPostProcessor postProcessor = mock(ScheduledAnnotationBeanPostProcessor.class);
        JobExecutionLogger jobExecutionLogger = new JobExecutionLogger(postProcessor);

        String taskName = "scheduledTriggerTask";

        Set<ScheduledTask> tasks = new HashSet<>();
        tasks.add(getScheduledTriggerTask(taskName, null, null));

        Mockito.when(postProcessor.getScheduledTasks()).thenReturn(tasks);

        jobExecutionLogger.logNextExecutionsOfAllTasks();

        assertThat(output.getOut()).contains(taskName, "no further execution");
    }

    @Test
    void logNextExecutionDates_forIntervalTask(CapturedOutput output) {
        ScheduledAnnotationBeanPostProcessor postProcessor = mock(ScheduledAnnotationBeanPostProcessor.class);
        JobExecutionLogger jobExecutionLogger = new JobExecutionLogger(postProcessor);

        Duration duration = Duration.ZERO;
        String taskName = "scheduledIntervalTask";

        Set<ScheduledTask> tasks = new HashSet<>();
        tasks.add(getScheduledIntervalTask(taskName, duration));

        Mockito.when(postProcessor.getScheduledTasks()).thenReturn(tasks);

        jobExecutionLogger.logNextExecutionsOfAllTasks();

        assertThat(output.getOut()).contains(taskName);
    }

    private ScheduledTask getScheduledIntervalTask(String taskName, Duration intervalDuration) {
        IntervalTask intervalTask = mock(IntervalTask.class);
        Mockito.when(intervalTask.getIntervalDuration()).thenReturn(intervalDuration);

        ScheduledTask scheduledTask = mock(ScheduledTask.class);
        Mockito.when(scheduledTask.toString()).thenReturn(taskName);
        Mockito.when(scheduledTask.getTask()).thenReturn(intervalTask);

        return scheduledTask;
    }

    @Test
    void logNextExecutionDates_forUnknownTask(CapturedOutput output) {
        ScheduledAnnotationBeanPostProcessor postProcessor = mock(ScheduledAnnotationBeanPostProcessor.class);
        JobExecutionLogger jobExecutionLogger = new JobExecutionLogger(postProcessor);

        Duration duration = Duration.ZERO;
        String taskName = "scheduledIntervalTask";

        Set<ScheduledTask> tasks = new HashSet<>();
        tasks.add(getScheduledTask(taskName));

        Mockito.when(postProcessor.getScheduledTasks()).thenReturn(tasks);

        jobExecutionLogger.logNextExecutionsOfAllTasks();

        assertThat(output.getOut()).contains(taskName, "unknown");
    }

    private ScheduledTask getScheduledTask(String taskName) {
        Task task = mock(Task.class);

        ScheduledTask scheduledTask = mock(ScheduledTask.class);
        Mockito.when(scheduledTask.toString()).thenReturn(taskName);
        Mockito.when(scheduledTask.getTask()).thenReturn(task);

        return scheduledTask;
    }

    @Test
    @SneakyThrows
    void logExecutionCallTest_WithoutScheduledTasks(CapturedOutput output) {
        ScheduledAnnotationBeanPostProcessor postProcessor = mock(ScheduledAnnotationBeanPostProcessor.class);
        when(postProcessor.getScheduledTasks()).thenReturn(Collections.emptySet());

        JobExecutionLogger jobExecutionLogger = new JobExecutionLogger(postProcessor);

        String signatureDeclaringTypeName = "signatureDeclaringTypeName";
        String signatureName = "signatureName";

        Signature signature = mock(Signature.class);
        when(signature.getDeclaringTypeName()).thenReturn(signatureDeclaringTypeName);
        when(signature.getName()).thenReturn(signatureName);

        ProceedingJoinPoint proceedingJoinPoint = mock(ProceedingJoinPoint.class);
        when(proceedingJoinPoint.getSignature()).thenReturn(signature);

        jobExecutionLogger.logExecutionCall(proceedingJoinPoint);

        assertThat(output.getOut()).contains(
                "Start job", "End job",
                signatureDeclaringTypeName, signatureName,
                "execution took", "ms").doesNotContain("next execution time for job");
    }

    @Test
    @SneakyThrows
    void logExecutionCallTest_WithFurtherExecution(CapturedOutput output) {
        ScheduledAnnotationBeanPostProcessor postProcessor = mock(ScheduledAnnotationBeanPostProcessor.class);


        JobExecutionLogger jobExecutionLogger = new JobExecutionLogger(postProcessor);

        String signatureDeclaringTypeName = "signatureDeclaringTypeName";
        String signatureName = "signatureName";

        Set<ScheduledTask> scheduledTasks = new HashSet<>();
        Instant nextExecution = Instant.now();
        scheduledTasks.add(getScheduledTriggerTask(
                "taskName", nextExecution, signatureDeclaringTypeName + "." + signatureName
        ));
        when(postProcessor.getScheduledTasks()).thenReturn(scheduledTasks);

        Signature signature = mock(Signature.class);
        when(signature.getDeclaringTypeName()).thenReturn(signatureDeclaringTypeName);
        when(signature.getName()).thenReturn(signatureName);

        ProceedingJoinPoint proceedingJoinPoint = mock(ProceedingJoinPoint.class);
        when(proceedingJoinPoint.getSignature()).thenReturn(signature);

        jobExecutionLogger.logExecutionCall(proceedingJoinPoint);

        assertThat(output.getOut()).contains(
                "Start job", "End job",
                signatureDeclaringTypeName, signatureName,
                "execution took", "ms", "next execution time for job", nextExecution.toString());
    }

    @Test
    @SneakyThrows
    void logExecutionCallTest_WithoutFurtherExecution(CapturedOutput output) {
        ScheduledAnnotationBeanPostProcessor postProcessor = mock(ScheduledAnnotationBeanPostProcessor.class);


        JobExecutionLogger jobExecutionLogger = new JobExecutionLogger(postProcessor);

        String signatureDeclaringTypeName = "signatureDeclaringTypeName";
        String signatureName = "signatureName";

        Set<ScheduledTask> scheduledTasks = new HashSet<>();
        scheduledTasks.add(getScheduledTriggerTask(
                "taskName", null, signatureDeclaringTypeName + "." + signatureName
        ));
        when(postProcessor.getScheduledTasks()).thenReturn(scheduledTasks);

        Signature signature = mock(Signature.class);
        when(signature.getDeclaringTypeName()).thenReturn(signatureDeclaringTypeName);
        when(signature.getName()).thenReturn(signatureName);

        ProceedingJoinPoint proceedingJoinPoint = mock(ProceedingJoinPoint.class);
        when(proceedingJoinPoint.getSignature()).thenReturn(signature);

        jobExecutionLogger.logExecutionCall(proceedingJoinPoint);

        assertThat(output.getOut()).contains(
                "Start job", "End job",
                signatureDeclaringTypeName, signatureName,
                "execution took", "ms", "no further execution");
    }

}
