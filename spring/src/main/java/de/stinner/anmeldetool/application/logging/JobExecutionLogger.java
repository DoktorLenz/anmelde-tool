package de.stinner.anmeldetool.application.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Aspect
@Component
class JobExecutionLogger {

    private final ScheduledAnnotationBeanPostProcessor beanPostProcessor;

    public JobExecutionLogger(final ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor) {
        this.beanPostProcessor = scheduledAnnotationBeanPostProcessor;
    }

    /**
     * When application is ready logs all registered jobs and their next execution date.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void logNextExecutionsOfAllTasks() {
        beanPostProcessor
                .getScheduledTasks()
                .forEach(this::logNextExecutionDate);
    }

    @Around("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public Object logExecutionCall(final ProceedingJoinPoint joinPoint) throws Throwable {
        LoggingTracker.start();

        String jobName = getJobName(joinPoint);

        StopWatch stopWatch = StopWatch.start();
        log.info("Start job " + jobName);

        final Object result = joinPoint.proceed();

        long duration = stopWatch.stop();
        log.info("End job " + jobName + ", execution took " + duration + "ms");

        getCurrentTask(jobName).ifPresent(this::logNextExecutionDate);

        LoggingTracker.stop();

        return result;
    }

    private String getJobName(final ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
    }

    private void logNextExecutionDate(final ScheduledTask task) {
        log.info("next execution time for job " + task.toString() + ": " + this.getNextExecutionDate(task));
    }

    private String getNextExecutionDate(final ScheduledTask task) {
        if (task.getTask() instanceof TriggerTask triggerTask) {
            Date nextExecutionTime =
                    triggerTask.getTrigger().nextExecutionTime(new SimpleTriggerContext());
            return Optional.ofNullable(nextExecutionTime).map(Date::toString).orElse("no further execution");
        } else if (task.getTask() instanceof IntervalTask intervalTask) {
            return new Date(System.currentTimeMillis() + intervalTask.getInterval()).toString();
        } else {
            return "unknown";
        }
    }

    private Optional<ScheduledTask> getCurrentTask(final String runnableName) {
        return beanPostProcessor
                .getScheduledTasks()
                .stream()
                .filter(s -> s.getTask().getRunnable().toString().equals(runnableName))
                .findAny();
    }

}