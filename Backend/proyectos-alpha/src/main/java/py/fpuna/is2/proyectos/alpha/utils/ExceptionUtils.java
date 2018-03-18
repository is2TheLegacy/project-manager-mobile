/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.utils;

import java.util.Optional;

/**
 *
 * @author rafae
 */
public final class ExceptionUtils {
    
    private ExceptionUtils(){
    }
    
    public static <T extends Throwable> Optional<String> getMessageFromDeepCause(Class<T> throwableClass, Throwable parentCause) {
        if(parentCause == null) {
            return Optional.empty();
        } else {
            if(parentCause.getClass().equals(throwableClass)) {
                return Optional.of(parentCause.getMessage());
            } else {
                return getMessageFromDeepCause(throwableClass, parentCause.getCause());
            }
        }
    }
}
