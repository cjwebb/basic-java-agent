package com.github.cjwebb.basicjavaagent;

import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 *
 */
public class LoggingClassFileTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        byte[] byteCode = null;

        try {
            ClassPool cp = ClassPool.getDefault();
            CtClass ctClass = cp.get(changeSlashToDot(className));
            if (shouldInstrument(ctClass)) {
                CtMethod[] methods = ctClass.getMethods();
                for (CtMethod m : methods) {
                    m.addLocalVariable("elapsedTime", CtClass.longType);
                    m.insertBefore("elapsedTime = System.currentTimeMillis();");
                    m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
                            + "System.out.println(\"Method Executed in ms: \" + elapsedTime);}");
                }
                byteCode = ctClass.toBytecode();
                ctClass.detach();
            }
        } catch (Exception ex) {
            System.out.println("exception thrown for %s: %s".format(className, ex.getMessage()));
        }

        return byteCode;
    }

    private boolean shouldInstrument(CtClass c) {
        int modifiers = c.getModifiers();
        boolean b = true;

        if (Modifier.isAbstract(modifiers)) b = false;
        if (Modifier.isInterface(modifiers)) b = false;

        return b;
    }

    private String changeSlashToDot(String input) {
        return input.replaceAll("/", ".");
    }
}
