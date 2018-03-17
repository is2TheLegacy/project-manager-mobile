package py.fpuna.is2.proyectos.alpha.service.exceptions;

public abstract class ApplicationException extends Exception {

    private static final long serialVersionUID = 4183357516385092326L;

    /**
     *
     */
    public ApplicationException() {
        super();
    }

    /**
     *
     * @param message
     */
    public ApplicationException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public ApplicationException(Throwable cause) {
        super(cause);
    }

    @javax.ejb.ApplicationException(rollback = true)
    public static class InternalError extends ApplicationException {

        private static final long serialVersionUID = -3139036056656795230L;

        /**
         * @deprecated Use {@link #InternalError(String, Throwable)} or
         * {@link #InternalError(Throwable)} instead
         * @param message
         */
        @Deprecated
        public InternalError(String message) {
            super(message);
        }

        public InternalError(String message, Throwable cause) {
            super(message, cause);
        }

        public InternalError(Throwable cause) {
            super(cause);
        }

    }

    public static class IllegalArgument extends ApplicationException {

        private static final long serialVersionUID = -8765864922699868014L;

        public IllegalArgument(String message) {
            super(message);
        }

        public IllegalArgument(String message, Throwable cause) {
            super(message, cause);
        }

        public IllegalArgument(Throwable cause) {
            super(cause);
        }
    }

    public static class NotFound extends ApplicationException {

        private static final long serialVersionUID = 1149303182428995606L;

        public NotFound(String message) {
            super(message);
        }

        public NotFound(String message, Throwable cause) {
            super(message, cause);
        }

        public NotFound(Throwable cause) {
            super(cause);
        }
    }

    public static class NonUniqueResult extends ApplicationException {

        private static final long serialVersionUID = 1149303182428995606L;

        public NonUniqueResult(String message, Throwable cause) {
            super(message, cause);
        }

        public NonUniqueResult(String message) {
            super(message);
        }

        public NonUniqueResult(Throwable cause) {
            super(cause);
        }
    }

    /**
     * Esta excepci&oacute;n se propaga para abortar la transacci&oacute;n
     * activa en un contexto EJB.
     *
     * @param message
     * @author Rafael E. Benegas - rbenegas@konecta.com.py
     */
    @javax.ejb.ApplicationException(rollback = true)
    public static class AbortException extends ApplicationException {

        private static final long serialVersionUID = 2285713586705660603L;

        public AbortException(String message) {
            super(message);
        }

        public AbortException(String message, Throwable cause) {
            super(message, cause);
        }

        public AbortException(Throwable cause) {
            super(cause);
        }
    }

    /**
     * Excepción propagada desde las operaciones de grano fino del CRUD de las
     * tablas.
     *
     * @author Rafael Benegas {@link mailto:rafael.benegas@konecta.com.py}
     *
     */
    public static class SQLException extends ApplicationException {

        private static final long serialVersionUID = -2466497195992873347L;

        public SQLException(String message, Throwable cause) {
            super(message, cause);
        }

        public SQLException(Throwable cause) {
            super(cause);
        }
    }
}
