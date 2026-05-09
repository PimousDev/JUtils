package dev.pimous.pu.jutils.base;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class ProtectedScheduledExecutor implements ScheduledExecutorService{

	private final ScheduledExecutorService executor;

	public ProtectedScheduledExecutor(ScheduledExecutorService executor){
		this.executor = executor;
	}

	// GETTERS
	@Override
	public boolean isShutdown(){ return executor.isShutdown(); }
	@Override
	public boolean isTerminated(){ return executor.isTerminated(); }

	// SETTERS
	@Override
	public void execute(@NotNull Runnable command){
		executor.execute(command);
	}

	@Override
	@NotNull
	public <T> Future<T> submit(@NotNull Callable<T> task){
		return executor.submit(task);
	}
	@Override
	@NotNull
	public <T> Future<T> submit(@NotNull Runnable task, T result){
		return executor.submit(task, result);
	}
	@Override
	@NotNull
	public Future<?> submit(@NotNull Runnable task){
		return executor.submit(task);
	}

	@Override
	@NotNull
	public <T> List<Future<T>> invokeAll(
		@NotNull Collection<? extends Callable<T>> tasks
	) throws InterruptedException{
		return executor.invokeAll(tasks);
	}
	@Override
	@NotNull
	public <T> List<Future<T>> invokeAll(
		@NotNull Collection<? extends Callable<T>> tasks,
		long timeout, @NotNull TimeUnit unit
	) throws InterruptedException{
		return executor.invokeAll(tasks, timeout, unit);
	}
	@Override
	@NotNull
	public <T> T invokeAny(@NotNull Collection<? extends Callable<T>> tasks)
		throws InterruptedException, ExecutionException
	{
		return executor.invokeAny(tasks);
	}
	@Override
	public <T> T invokeAny(@NotNull Collection<? extends Callable<T>> tasks,
		long timeout, @NotNull TimeUnit unit
	) throws InterruptedException, ExecutionException, TimeoutException{
		return executor.invokeAny(tasks, timeout, unit);
	}

	@Override
	@NotNull
	public ScheduledFuture<?> schedule(@NotNull Runnable command,
		long delay, @NotNull TimeUnit unit
	){
		return executor.schedule(command, delay, unit);
	}
	@Override
	@NotNull
	public <V> ScheduledFuture<V> schedule(@NotNull Callable<V> callable,
		long delay, @NotNull TimeUnit unit
	){
		return executor.schedule(callable, delay, unit);
	}
	@Override
	@NotNull
	public ScheduledFuture<?> scheduleAtFixedRate(@NotNull Runnable command,
		long initialDelay, long period, @NotNull TimeUnit unit
	){
		return executor.scheduleAtFixedRate(command,
			initialDelay, period, unit
		);
	}
	@Override
	@NotNull
	public ScheduledFuture<?> scheduleWithFixedDelay(@NotNull Runnable command,
		long initialDelay, long delay, @NotNull TimeUnit unit
	){
		return executor.scheduleWithFixedDelay(command,
			initialDelay, delay, unit
		);
	}

	@Override
	public void shutdown(){
		throw new OperationNotPermittedException(
			"Unable to 'shutdown a protected executor;"
		);
	}
	@Override
	@NotNull
	public List<Runnable> shutdownNow(){
		throw new OperationNotPermittedException(
			"Unable to 'shutdownNow a protected executor;"
		);
	}

	// FUNCTIONS
	@Override
	public boolean awaitTermination(long timeout, @NotNull TimeUnit unit)
		throws InterruptedException
	{
		return executor.awaitTermination(timeout, unit);
	}
}