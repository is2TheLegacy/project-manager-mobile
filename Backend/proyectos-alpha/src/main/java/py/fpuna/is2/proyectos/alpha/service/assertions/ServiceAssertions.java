package py.fpuna.is2.proyectos.alpha.service.assertions;

import java.util.List;
import java.util.Optional;
import py.fpuna.is2.proyectos.alpha.service.exceptions.ApplicationException;
import py.fpuna.is2.proyectos.alpha.utils.Strings;

public final class ServiceAssertions {

    private ServiceAssertions() {
    }

    /**
     * Valida si el argumento es no nulo o vacio
     *
     * @param argumento parametro a validar
     * @param message mensaje de validacion
     * @return argumento si es valido
     * @throws ApplicationException
     */
    public static String assertNotNullOrEmpty(String argumento, String message)
            throws ApplicationException {
        if (Strings.isNullOrEmpty(argumento)) {
            throw new ApplicationException.IllegalArgument(message);
        }
        return argumento;
    }

    /**
     * Valida si el argumento es no nulo o vacio
     *
     * @param argumento parametro a validar
     * @param message mensaje de validacion
     * @return argumento si es valido
     * @throws ApplicationException
     */
    public static Optional<?> assertNotNullOrEmpty(Optional<?> argumento, String message)
            throws ApplicationException {
        if (argumento == null || !argumento.isPresent()) {
            throw new ApplicationException.IllegalArgument(message);
        }
        return argumento;
    }

    /**
     * Valida que el argumento no sea nulo
     *
     * @param <T>
     * @param argumento parametro a validar
     * @param message mensaje de validacion
     * @return argumento si es valido
     * @throws ApplicationException
     */
    public static <T> T assertNotNullOrEmpty(T argumento, String message)
            throws ApplicationException {
        if (argumento == null) {
            throw new ApplicationException.IllegalArgument(message);
        }
        return argumento;
    }

    public static <T> T assertNotNullOrEmpty(T argumento, String message, Runnable r) {
        if (argumento == null) {
            r.run();
        }
        return argumento;
    }

    /**
     * Valida si la expresion es Verdadera
     *
     * @param expression argumeto a validar
     * @param message mensaje de validacion
     * @throws ApplicationException
     */
    public static void assertTrue(boolean expression, String message)
            throws ApplicationException {
        if (!expression) {
            throw new ApplicationException.IllegalArgument(message);
        }
    }

    /**
     * Valida si la expresion es Verdadera
     *
     * @param expression argumeto a validar
     * @param message mensaje de validacion
     * @throws ApplicationException
     */
    public static void assertFalse(boolean expression, String message)
            throws ApplicationException {
        if (expression) {
            throw new ApplicationException.IllegalArgument(message);
        }
    }

    /**
     * Valida que el argumento opcional contenga valor
     *
     * @param optional argumento a validar si es encontrado
     * @param message mensaje de validacion
     * @return objeto opcional si es encontrado
     * @throws ApplicationException
     */
    @SuppressWarnings("rawtypes")
    public static Optional<?> assertFound(Optional<?> optional, String message) throws ApplicationException {
        if (optional == null || !optional.isPresent()) {
            throw new ApplicationException.NotFound(message);
        }

        return optional;
    }

    /**
     * Valida que el parametro no sea Nulo o vacio en caso que el objeto sea un
     * array
     *
     * @param <T>
     * @param object argumento a validar si es encontrado
     * @param message mensaje de validacion
     * @return object si es encontrado
     * @throws ApplicationException
     */
    @SuppressWarnings("rawtypes")
    public static <T> T assertFound(T object, String message) throws ApplicationException {
        if (object == null) {
            throw new ApplicationException.NotFound(message);
        } else if (object instanceof java.util.Collection) {
            if (((java.util.Collection) object).isEmpty()) {
                throw new ApplicationException.NotFound(message);
            }
        }

        return object;
    }

    /**
     * Valida si el parametro tiene un unico elemento
     *
     * @param <T>
     * @param list el argumento a validar
     * @param message mensaje de error
     * @return retorna el unico objeto en caso que sea unico
     * @throws ApplicationException
     */
    public static <T> T assertUnique(List<T> list, String message) throws ApplicationException {
        if (list.size() > 1) {
            throw new ApplicationException.NonUniqueResult(message);
        }
        return list.get(0);
    }
}
