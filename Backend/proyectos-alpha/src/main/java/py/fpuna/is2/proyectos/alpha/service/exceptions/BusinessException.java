package py.fpuna.is2.proyectos.alpha.service.exceptions;

import java.util.ArrayList;
import java.util.List;
import py.fpuna.is2.proyectos.alpha.utils.Strings;

/**
 * Excepcion a propagar cuando ocurra alguna exccepcion de regla de negocio.
 *
 * @author Rafael Benegas
 *
 */
public abstract class BusinessException extends Exception {

    private static final long serialVersionUID = 9122458587681754074L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Esta clase representa a una violacion de una o mas reglas de negocio
     * relacionadas a restricciones del modelo
     *
     * @author Rafael Benegas
     *
     */
    public static class ModelValidationException extends BusinessException {

        private static final long serialVersionUID = 1149303182428995606L;
        private List<String> violationMessages;

        /**
         *
         * @param message Mensaje general de la excepcion.
         * @param violationMessages representa la lista de violaciones o
         * contraints que fueron violadas.
         */
        public ModelValidationException(String message, List<String> violationMessages) {

            super(message);

            if (violationMessages != null && !violationMessages.isEmpty()) {
                this.violationMessages = violationMessages;
            } else {
                this.violationMessages = new ArrayList<String>();
            }
        }

        /**
         *
         * @param message Mensaje general de la excepcion.
         * @param violationMessages representa la lista de violaciones o
         * contraints que fueron violadas.
         */
        public ModelValidationException(String message, String violation) {

            super(message);

            if (!Strings.isNullOrEmpty(violation)) {

                this.violationMessages = new ArrayList<String>();
                this.violationMessages.add(violation);

            } else {

                this.violationMessages = new ArrayList<String>();

            }
        }

        public List<String> getViolationMessages() {
            return violationMessages;
        }

        public void setViolationMessages(List<String> violationMessages) {
            this.violationMessages = violationMessages;
        }
    }

    /**
     * Esta clase representa a una violacion de una regla de negocios.
     *
     * @author Rafael Benegas
     *
     */
    public static class RuleValidationException extends BusinessException {

        private static final long serialVersionUID = 1149303182428995606L;

        /**
         *
         * @param message Mensaje general de la excepcion.
         * @param violation representa la violacion cometida sobre una regla de
         * negocio.
         */
        public RuleValidationException(String violation) {
            super(violation);
        }

        public RuleValidationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
