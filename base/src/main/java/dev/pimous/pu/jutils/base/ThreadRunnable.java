package dev.pimous.pu.jutils.base;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public abstract class ThreadRunnable implements Runnable{

	public ThreadRunnable(){}

	// FUNCTIONS
	public abstract void threadMain();
	public abstract void catchException(Exception e);
	public abstract void threadEnd();

	@Override
	public final void run(){
		try{
			threadMain();
		}catch(Exception e){
			catchException(e);
		}finally{
			threadEnd();
		}
	}
}
