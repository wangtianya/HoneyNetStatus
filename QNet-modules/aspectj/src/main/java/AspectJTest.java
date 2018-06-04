import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import android.util.Log;

/**
 * Created by wangtianya on 2018/4/25.
 */

@Aspect
public class AspectJTest {

    @Pointcut("execution(* *onCreate(..))")
    public void executionAspectJ() {

    }


    @Around("executionAspectJ()")
    public Object aroundAspectJ(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Log.e("wangtianya", "asasdasdasdasdasdasdas");
        return joinPoint.proceed();
    }

}
