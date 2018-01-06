import java.math.BigInteger;
import java.util.concurrent.*;

public class KR {
    public static void main(String[] args) {
            CalcCallable cC = new CalcCallable();
            int i = 0;
            ExecutorService es = Executors.newSingleThreadExecutor();
            while(i < 10) {
                Future<Number> future = es.submit(cC);
                es.shutdown();
                try {
                    System.out.println(future.get());
                } catch (InterruptedException | ExecutionException t) {
                    t.printStackTrace();
                }
                finally {
                    i++;
                }
            }
    }
}
class CalcCallable implements Callable <Number>{
    Double e = 1.0;
    Long factrorial = 1L;
    int number = 2;
    @Override
    public Number call() throws Exception {
            e += 1. / factrorial.doubleValue();
            factrorial *= number;
	    number++;
            return e;
    }
}

/*class FutureStop<Number> implements Future <Number> {

    @Override
    public boolean cancel(boolean b) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Number get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Number get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}*/
